package uz.developers.course

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

data class BaseMessage(val code: Int, val message: String?)


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
    var fullName: String?,

    @Schema(description = "User's password", example = "root123")
    var password: String?,

    @Schema(description = "Role name for the user", example = "ADMIN")
    val roleName: String?,

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

//    @Schema(description = "Role ID", example = "1")
//    val id: Long?,
//
//    @Schema(description = "Role key", example = "12")
//    var key: String?,

    @Schema(description = "Role name", example = "ADMIN")
    var name: String?,
)

@Schema(description = "Data transfer object for Role createRequest")
class RoleUpdateRequest(

    @Schema(description = "Role name", example = "ADMIN")
    var name: String,
)


@Schema(description = "Data transfer object for Course createRequest")
data class CourseCreateRequest(

    @Schema(description = "Course name", example = "Kotlin Programming")
    val name: String,

    @Schema(description = "Course description", example = "A complete guide to Kotlin programming language")
    val description: String,

    @Schema(description = "Course price", example = "150.0")
    val price: BigDecimal
)


@Schema(description = "Data transfer object for Course response")
data class CourseResponse(

    @Schema(description = "Course ID", example = "1")
    val id: Long?,

    @Schema(description = "Course name", example = "Kotlin Programming")
    val name: String?,

    @Schema(description = "Course description", example = "A complete guide to Kotlin programming language")
    val description: String?,

    @Schema(description = "Course price", example = "150.0")
    val price: BigDecimal?
)


@Schema(description = "Data transfer object for Course updateRequest")
data class CourseUpdateRequest(

    @Schema(description = "Course name", example = "Kotlin Programming")
    var name: String,

    @Schema(description = "Course description", example = "A complete guide to Kotlin programming language")
    var description: String,

    @Schema(description = "Course price", example = "150.0")
    var price: BigDecimal
)


@Schema(description = "Data transfer object for Purchase createRequest")
class PurchaseCreateRequest(

    @Schema(description = "User ID", example = "1")
    var userId: Long,

    @Schema(description = "Course ID for the purchase", example = "3")
    val courseId: Long,

    @Schema(description = "Purchase date and time", example = "2024-11-24T10:15:30")
    var purchaseDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Purchase status", example = "AVAILABLE")
    var purchaseStatus: PurchaseStatus
)



@Schema(description = "Data transfer object for Purchase response")
class PurchaseResponse(

    @Schema(description = "Purchase ID", example = "1")
    val id: Long?,

    @Schema(description = "User ID", example = "1")
    var userId: Long?,

    @Schema(description = "Course name for the purchase", example = "Java")
    var courseName: String?,

    @Schema(description = "Purchase date and time", example = "2024-11-24T10:15:30")
    var purchaseDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Purchase status", example = "AVAILABLE")
    var purchaseStatus: PurchaseStatus?
)



@Schema(description = "Data transfer object for Payment createRequest")
data class PaymentCreateRequest(

    @Schema(description = "User ID", example = "1")
    var userId: Long,

    @Schema(description = "Course ID", example = "3")
    var courseId: Long,

    @Schema(description = "Amount paid", example = "150.00")
    var amount: BigDecimal,

    @Schema(description = "Payment date and time", example = "2024-11-24T10:15:30")
    var paymentDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    var paymentMethod: PaymentMethod,

    @Schema(description = "Payment status", example = "SUCCESS")
    var status: Status
)



@Schema(description = "Data transfer object for Payment response")
data class PaymentResponse(

    @Schema(description = "Payment ID", example = "1")
    val id: Long?,

    @Schema(description = "User ID", example = "1")
    var userId: Long?,

    @Schema(description = "Course ID", example = "3")
    var courseId: Long?,

    @Schema(description = "Amount paid", example = "150.00")
    var amount: BigDecimal?,

    @Schema(description = "Payment date and time", example = "2024-11-24T10:15:30")
    var paymentDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    var paymentMethod: PaymentMethod?,

    @Schema(description = "Payment status", example = "PENDING")
    var status: Status?,

    @Schema(description = "User name (retrieved from User service)", example = "John Doe")
    val userName: String?,

    @Schema(description = "Course name (retrieved from Course service)", example = "Kotlin Programming")
    val courseName: String?
)


@Schema(description = "Data transfer object for Payment updateRequest")
data class PaymentUpdateRequest(

    @Schema(description = "Amount paid", example = "150.00")
    var amount: BigDecimal,

    @Schema(description = "Payment date and time", example = "2024-11-24T10:15:30")
    var paymentDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    var paymentMethod: PaymentMethod,

    @Schema(description = "Payment status", example = "SUCCESS")
    var status: Status
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








