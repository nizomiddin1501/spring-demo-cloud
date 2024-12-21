package uz.developers.demo


enum class ErrorCode(val code: Int) {
    VALIDATION_ERROR(40),
    USER_PENDING(20),
    USER_BLOCKED(30),
    USERNAME_ALREADY_TAKEN(100),
    GENERAL_API_EXCEPTION(101),
    USER_NOT_FOUND(102),
    OLD_PASSWORD_ERROR(103),
    ACCESS_DENIED(105),
    FEIGN_ERROR(106),
}


enum class UserStatus {
    INACTIVE,
    BLOCKED,
    ACTIVE,
    PENDING
}