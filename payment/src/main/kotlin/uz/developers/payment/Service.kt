package uz.developers.payment
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal


interface PaymentService {
    fun getAll(page: Int, size: Int): Page<PaymentResponse>
    fun getOne(id: Long): PaymentResponse
    fun create(request: PaymentCreateRequest): PaymentResponse
    fun getPaymentsByUserId(userId: Long): List<PaymentResponse>
    fun getPaymentStats(): PaymentStatsResponse
}

@Service
class PaymentServiceImpl(
    private val paymentRepository: PaymentRepository,
    private val paymentMapper: PaymentMapper,
    private val userService: UserService,
    private val courseService: CourseService
) : PaymentService {


    override fun getAll(page: Int, size: Int): Page<PaymentResponse> {
        val paymentPage = paymentRepository.findAll(PageRequest.of(page, size))
        return paymentPage.map { paymentMapper.toDto(it) }
    }

    override fun getOne(id: Long): PaymentResponse {
        val payment = paymentRepository.findById(id)
            .orElseThrow { PaymentNotFoundException() }
        return paymentMapper.toDto(payment)
    }


    override fun create(request: PaymentCreateRequest): PaymentResponse {
        val userResponse = userService.getOne(request.userId) ?: throw UserNotFoundException()
        val courseResponse = courseService.getOne(request.courseId) ?: throw CourseNotFoundException()

        when (request.paymentMethod) {
            PaymentMethod.CREDIT_CARD, PaymentMethod.DEBIT_CARD -> {
                if (userResponse.balance!! < request.amount) {
                    throw InsufficientBalanceException()
                }
            }
            PaymentMethod.E_WALLET -> {
                if (userResponse.balance!! < request.amount) {
                    throw InsufficientBalanceException()
                }
            }
            PaymentMethod.CASH -> {
                throw CashPaymentNotAllowedException()
            }
            else -> {
                throw InvalidPaymentMethodException()
            }
        }
        val payment = paymentMapper.toEntity(request)

        val savedPayment = paymentRepository.save(payment)
        return paymentMapper.toDto(savedPayment, userResponse.username, courseResponse.name)
    }


//    override fun update(id: Long, request: PaymentUpdateRequest): PaymentResponse {
//        val payment = paymentRepository.findById(id)
//            .orElseThrow { PaymentNotFoundException() }
//        val updatedPayment = paymentMapper.updateEntity(payment, request)
//        val savedPayment = paymentRepository.save(updatedPayment)
//        return paymentMapper.toDto(savedPayment)
//    }


    // Get all payments ts by user ID
//    override fun getPaymentsByUserId(userId: Long): List<PaymentResponse> {
//        val payments = paymentRepository.findAll()
//        val userPayments = payments.filter { payment ->
//            val user = userService.getOne(userId)
//            user.id == userId
//        }
//        return userPayments.map { paymentMapper.toDto(it) }
//    }

    override fun getPaymentsByUserId(userId: Long): List<PaymentResponse> {
        val userPayments = paymentRepository.findByUserId(userId)
        return userPayments.map { paymentMapper.toDto(it) }
    }



    // Get payment statistics
    override fun getPaymentStats(): PaymentStatsResponse {
        val totalPayments = paymentRepository.count()
        val totalAmountPaid = paymentRepository.sumAmountPaid()
        return PaymentStatsResponse(totalPayments, totalAmountPaid ?: BigDecimal.ZERO)
    }


}

