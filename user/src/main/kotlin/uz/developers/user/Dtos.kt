package uz.developers.user

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseMessage(
    val code: Int?,
    val message: String?
) {
    companion object {
        val OK = BaseMessage(0, "OK")
    }
}

data class AuthUser(
    val username: String,
    val password: String?
)

data class UserInfo(
    val id: Long,
    val username: String,
    val fullName: String,
    val role: String,
    val status: UserStatus
){
    companion object {
        fun toDto(user: User) = user.run { UserInfo(id!!, username, fullName, role.key, status) }
    }
}

@Schema(description = "Data transfer object for User createRequest")
data class UserCreateRequest(

    @Schema(description = "Unique username", example = "nizomiddin097")
    var username: String,

    @Schema(description = "User full name", example = "Nizomiddin Mirzanazarov")
    var fullName: String,

    @Schema(description = "User's password", example = "root123")
    var password: String,

    @Schema(description = "Role ID for the user", example = "1")
    val roleId: Long,

    @Schema(description = "User status", example = "PENDING")
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.PENDING,

    @Schema(description = "User balance", example = "100.0")
    var balance: BigDecimal
)


@Schema(description = "Data transfer object for User createRequest")
data class UserResponse(

    @Schema(description = "User ID", example = "1")
    val id: Long?,

    @Schema(description = "Unique username", example = "nizomiddin097")
    var username: String?,

    @Schema(description = "User full name", example = "Nizomiddin Mirzanazarov")
    var fullName: String,

    @Schema(description = "User's password", example = "root123")
    var password: String?,

    @Schema(description = "Role name for the user", example = "ADMIN")
    var roleName: String?,

    @Schema(description = "User status", example = "PENDING")
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.PENDING,

    @Schema(description = "User balance", example = "100.0")
    var balance: BigDecimal?
)


@Schema(description = "Data transfer object for User createRequest")
data class UserUpdateRequest(

    @Column(unique = true, nullable = false)
    @Schema(description = "Unique username", example = "nizomiddin097")
    var username: String,

    @Schema(description = "User full name", example = "Nizomiddin Mirzanazarov")
    var fullName: String,

    @Column(nullable = false)
    @Schema(description = "User's password", example = "root123")
    var password: String,

    @Schema(description = "User status", example = "PENDING")
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.PENDING,

    @Schema(description = "User balance", example = "100.0")
    var balance: BigDecimal
)

@Schema(description = "Data transfer object for Role createRequest")
class RoleCreateRequest(

    @Schema(description = "Role key", example = "12")
    var key: String,

    @Schema(description = "Role name", example = "ADMIN")
    var name: String,
)


@Schema(description = "Data transfer object for Role createRequest")
class RoleResponse(

    @Schema(description = "Role ID", example = "1")
    val id: Long?,

    @Schema(description = "Role key", example = "12")
    var key: String,

    @Schema(description = "Role name", example = "ADMIN")
    var name: String,
)

@Schema(description = "Data transfer object for Role createRequest")
class RoleUpdateRequest(

    @Schema(description = "Role name", example = "ADMIN")
    var name: String,
)


data class UserStatsResponse(
    val totalUsers: Long,
    val totalBalance: BigDecimal
)

data class CourseStatsResponse(
    val totalCourses: Long,
    val totalIncome: BigDecimal
)

data class PaymentStatsResponse(
    val totalPayments: Long,
    val totalAmountPaid: BigDecimal
)




