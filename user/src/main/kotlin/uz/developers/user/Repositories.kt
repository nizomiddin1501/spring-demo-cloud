package uz.developers.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import javax.persistence.EntityManager
import javax.transaction.Transactional

@NoRepositoryBean
interface BaseRepository<T : BaseEntity> : JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    fun findByIdAndDeletedFalse(id: Long): T?
    fun trash(id: Long): T?
    fun trashList(ids: List<Long>): List<T?>
    fun findAllNotDeleted(): List<T>
    fun findAllNotDeleted(pageable: Pageable): List<T>
    fun findAllNotDeletedForPageable(pageable: Pageable): Page<T>
}

class BaseRepositoryImpl<T : BaseEntity>(
    entityInformation: JpaEntityInformation<T, Long>, entityManager: EntityManager,
) : SimpleJpaRepository<T, Long>(entityInformation, entityManager), BaseRepository<T> {

    val isNotDeletedSpecification = Specification<T> { root, _, cb -> cb.equal(root.get<Boolean>("deleted"), false) }

    override fun findByIdAndDeletedFalse(id: Long) = findByIdOrNull(id)?.run { if (deleted) null else this }

    @Transactional
    override fun trash(id: Long): T? = findByIdOrNull(id)?.run {
        deleted = true
        save(this)
    }

    override fun findAllNotDeleted(): List<T> = findAll(isNotDeletedSpecification)
    override fun findAllNotDeleted(pageable: Pageable): List<T> = findAll(isNotDeletedSpecification, pageable).content
    override fun trashList(ids: List<Long>): List<T?> = ids.map { trash(it) }
    override fun findAllNotDeletedForPageable(pageable: Pageable): Page<T> =
        findAll(isNotDeletedSpecification, pageable)
}


interface UserRepository : BaseRepository<User> {
    fun findByUsername(username: String): User?
    fun existsByUsernameAndDeletedFalse(username: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun findByUsernameAndDeletedFalse(username: String): User?
    fun findByUsernameAndStatusAndDeletedFalse(username: String, status: UserStatus): User?

    @Query(value = "select u from users u where u.id = :id")
    fun findUserById(@Param("id") id: Long): User?

    @Modifying
    @Query("update users u set u.balance = u.balance + :amount where u.id = :userId")
    fun incrementBalance(@Param("userId") userId: Long, @Param("amount") amount: BigDecimal): Int



    @Query("""
        select u from users u
        where u.id != :id
        and u.username = :username
        and u.deleted = false 
    """)
    fun findByUsernameAndId(id: Long, username: String): User?

    @Query(value = "select sum(balance) from users", nativeQuery = true)
    fun sumBalance(): BigDecimal
}

interface RoleRepository : BaseRepository<Role> {

    fun findByNameAndDeletedFalse(name: String): Role?

    fun findByName(name: String): Role?

    // Role ID exists check
    @Query(value = "select count(*) > 0 from roles r where r.id = :id", nativeQuery = true)
    fun existsByRoleId(@Param("id") id: Long?): Boolean

}

