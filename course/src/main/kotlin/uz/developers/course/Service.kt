package uz.developers.course
import org.bouncycastle.asn1.x500.style.RFC4519Style.name
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.transaction.Transactional


interface CourseService {
    fun getAll(page: Int, size: Int): Page<CourseResponse>
    fun getAll(): List<CourseResponse>
    fun getOne(id: Long): CourseResponse
    fun checkAdminRole(userId: Long)
    fun create(request: CourseCreateRequest, userId: Long): CourseResponse
    fun update(id: Long, request: CourseUpdateRequest, userId: Long): CourseResponse
    fun getCourseStats(): CourseStatsResponse
    fun purchaseCourse(userId: Long, courseId: Long, paymentMethod: PaymentMethod): PaymentResponse
    fun delete(id: Long, userId: Long)
}


@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val courseMapper: CourseMapper,
    private val userService: UserService,
    private val purchaseRepository: PurchaseRepository,
    @Lazy private val paymentService: PaymentService
) : CourseService {

    override fun getAll(page: Int, size: Int): Page<CourseResponse> {
        val pageable: Pageable = PageRequest.of(page-1, size)
        val coursesPage = courseRepository.findAllNotDeletedForPageable(pageable)
        return coursesPage.map { courseMapper.toDto(it) }
    }

    override fun getAll(): List<CourseResponse> {
        return courseRepository.findAllNotDeleted().map {
            courseMapper.toDto(it)
        }
    }

    override fun getOne(id: Long): CourseResponse {
        courseRepository.findByIdAndDeletedFalse(id)?.let {
            return courseMapper.toDto(it)
        } ?: throw CourseNotFoundException()
    }

    override fun checkAdminRole(userId: Long) {
        val user = userService.getOne(userId) ?: throw UserNotFoundException()
        val roleResponse = RoleResponse((user.roleName ?: "UNKNOWN"))
        if (roleResponse.name != "ADMIN") {
            throw UserAccessDeniedException()
        }
    }


    override fun create(request: CourseCreateRequest, userId: Long): CourseResponse {
        checkAdminRole(userId)
        if (request.price < BigDecimal.ZERO) {
            throw InvalidCoursePriceException()
        }
        val course = courseRepository.findByNameAndDeletedFalse(request.name)
        if (course != null) throw CourseAlreadyExistsException()
        val courseEntity = courseMapper.toEntity(request)
        val savedCourse = courseRepository.save(courseEntity)

        return courseMapper.toDto(savedCourse)

    }

    override fun update(id: Long, request: CourseUpdateRequest, userId: Long): CourseResponse {
        checkAdminRole(userId)
        val course = courseRepository.findByIdAndDeletedFalse(id) ?: throw CourseNotFoundException()
        //courseRepository.findByName(request.name)?.let { throw CourseAlreadyExistsException() }
        val updateCourse = courseMapper.updateEntity(course, request)
        val savedCourse = courseRepository.save(updateCourse)

        return courseMapper.toDto(savedCourse)
    }

    override fun getCourseStats(): CourseStatsResponse {
        val totalCourses = courseRepository.count()
        val totalIncome = courseRepository.sumCoursePrice()?: throw DataNotFoundException()
        return CourseStatsResponse(totalCourses, totalIncome)
    }

    @Transactional
    override fun purchaseCourse(userId: Long, courseId: Long, paymentMethod: PaymentMethod): PaymentResponse {
        val userResponse = userService.getOne(userId) ?: throw UserNotFoundException()
        val course = courseRepository.findByCourseId(courseId) ?: throw CourseNotFoundException()

        if (purchaseRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw CourseAlreadyPurchasedException()
        }

        val userBalance = userService.getUserBalance(userId)
        if (userBalance<course.price) {
            throw InsufficientBalanceException()
        }
        val paymentCreateRequest = PaymentCreateRequest(
            userId = userId,
            courseId = courseId,
            amount = course.price,
            paymentDate = LocalDateTime.now(),
            paymentMethod = paymentMethod,
            status = Status.PENDING
        )
        val paymentResponse = paymentService.create(paymentCreateRequest)
        if (paymentResponse.status == Status.SUCCESS) {
            createPurchase(userId, course, PurchaseStatus.PURCHASED)
        } else {
            throw PaymentFailedException()
        }
        return paymentResponse
    }

    fun createPurchase(userId: Long, course: Course, status: PurchaseStatus): Purchase {
        val purchase = Purchase(
            userId = userId,
            course = course,
            purchaseStatus = status,
            purchaseDate = LocalDateTime.now()
        )
        return purchaseRepository.save(purchase)
    }




    @Transactional
    override fun delete(id: Long, userId: Long) {
        checkAdminRole(userId)
        courseRepository.trash(id) ?: throw CourseNotFoundException()
    }
}
