package uz.developers.user
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.util.*
import javax.persistence.*


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @CreatedDate @Temporal(TemporalType.TIMESTAMP) var createdDate: Date? = null,
    @LastModifiedDate @Temporal(TemporalType.TIMESTAMP) var modifiedDate: Date? = null,
    @CreatedBy var createdBy: Long? = null,
    @LastModifiedBy var lastModifiedBy: Long? = null,
    @Column(nullable = false) @ColumnDefault(value = "false") var deleted: Boolean = false,
)

@Table
@Entity(name = "users")
@Schema(description = "User information")
class User(
    @Column(unique = true, length = 32, nullable = false)
    @Schema(description = "Unique username", example = "nizomiddin097")
    var username: String,

    @Schema(description = "User's password", example = "root123")
    var password: String,

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Schema(description = "User role", example = "USER")
    var role: Role,

    @Column(length = 128)
    @Schema(description = "User full name", example = "Nizomiddin Mirzanazarov")
    var fullName: String,

//    @Schema(description = "User role", example = "USER")
//    @Enumerated(EnumType.STRING)
//    var role: UserRole,

    @Column(length = 64)
    @Schema(description = "User status", example = "PENDING")
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.PENDING,

    @Column(nullable = false)
    @Schema(description = "User balance", example = "100.0")
    var balance: BigDecimal
) : BaseEntity()

@Entity(name = "roles")
class Role(
    @Column(unique = true, length = 64)
    @Schema(description = "Role key", example = "12")
    var key: String,

    @Schema(description = "Role name", example = "ADMIN")
    var name: String,
) : BaseEntity()

