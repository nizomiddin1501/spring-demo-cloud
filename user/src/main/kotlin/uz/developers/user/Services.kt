package uz.developers.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.math.BigDecimal
import javax.persistence.EntityManager
import javax.transaction.Transactional

interface RoleService {
    fun create(request: RoleCreateRequest)
}

interface UserService {
    fun findByAuthUser(request: AuthUser): UserInfo
    fun getAll(page: Int, size: Int): Page<UserResponse>
    fun getAll(): List<UserResponse>
    fun getOne(id: Long): UserResponse
    fun getUserEntity(id: Long): User
    fun create(request: UserCreateRequest)
    fun update(id: Long, request: UserUpdateRequest)
    fun getUserBalance(id: Long): BigDecimal
    fun fillBalance(userId: Long, amount: BigDecimal): BigDecimal
    //fun deductBalance(userId: Long, amount: BigDecimal): Boolean
    fun getUserStats(): UserStatsResponse
    fun delete(id: Long)
}

@Service
class UserServiceImpl(
    private val mapper: UserMapper,
    private val repository: UserRepository,
    private val entityManager: EntityManager,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun findByAuthUser(request: AuthUser): UserInfo {
        val user = repository.findByUsername(request.username) ?: throw UserNotFoundException()
        if (request.password == null) return UserInfo.toDto(user)
        if (passwordEncoder.matches(request.password, user.password)) {
            return UserInfo.toDto(user)
        } else {
            throw IncorrectPasswordException()
        }
    }


    override fun getAll(page: Int, size: Int): Page<UserResponse> {
        val pageable: Pageable = PageRequest.of(page, size)
        val usersPage = repository.findAllNotDeletedForPageable(pageable)
        return usersPage.map { mapper.toDto(it) }
    }

    override fun getAll(): List<UserResponse> {
        return repository.findAllNotDeleted().map {
            mapper.toDto(it)
        }
    }

    override fun getOne(id: Long): UserResponse {
        repository.findByIdAndDeletedFalse(id)?.let {
            return mapper.toDto(it)
        } ?: throw UserNotFoundException()
    }

    override fun getUserEntity(id: Long): User {
        return repository.findByIdAndDeletedFalse(id)
            ?: throw UserNotFoundException()
    }

    override fun create(request: UserCreateRequest) {
         repository.existsByUsername(request.username)
            ?: throw UserAlreadyExistsException()

        val existsByRoleId = roleRepository.existsByRoleId(request.roleId)
        if (!existsByRoleId) throw UserRoleNotFoundException()
        val referenceRole = entityManager.getReference(
            Role::class.java, request.roleId)

        val encodedPassword = passwordEncoder.encode(request.password)
        val user = mapper.toEntity(request, referenceRole)
        user.password = encodedPassword
        repository.save(user)
    }

    override fun update(id: Long, request: UserUpdateRequest) {
        val user = repository.findByIdAndDeletedFalse(id) ?: throw UserNotFoundException()
        repository.findByUsernameAndIdDeletedFalse(id, request.username)?.let { throw UserAlreadyExistsException() }

        val updateUser = mapper.updateEntity(user, request)

        if (request.password.isNotEmpty()) {
            updateUser.password = passwordEncoder.encode(request.password)
        }
        repository.save(updateUser)
    }

    override fun getUserBalance(id: Long): BigDecimal {
        val user = repository.findUserById(id) ?: throw UserNotFoundException()
        return user.balance
    }

    @Transactional
    override fun fillBalance(userId: Long, amount: BigDecimal): BigDecimal {
        if (amount <= BigDecimal.ZERO) {
            throw InvalidAmountException()
        }
        val updatedRows = repository.incrementBalance(userId, amount)
        if (updatedRows == 0) {
            throw UserNotFoundException()
        }
        return repository.findUserById(userId)?.balance
            ?: throw UserNotFoundException()
    }


//    override fun deductBalance(userId: Long, amount: BigDecimal): Boolean {
//        val user = repository.findUserById(userId) ?: throw UserNotFoundException()
//        if (user.balance >= amount) {
//            user.balance -= amount
//            repository.save(user)
//            return true
//        }
//        return false
//    }

    override fun getUserStats(): UserStatsResponse {
        val totalUsers = repository.count()
        val totalBalance = repository.sumBalance()
        return UserStatsResponse(totalUsers, totalBalance)
    }

    @Transactional
    override fun delete(id: Long) {
        repository.trash(id) ?: throw UserNotFoundException()
    }
}


@Service
class RoleServiceImpl(
    private val mapper: RoleMapper,
    private val repository: RoleRepository
) : RoleService {

    override fun create(request: RoleCreateRequest) {
        val role = repository.findByNameAndDeletedFalse(request.name)
        if (role != null) throw RoleAlreadyExistsException()
        repository.save(mapper.toEntity(request))

    }
}

