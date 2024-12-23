package uz.developers.course


enum class UserRole {
    ADMIN, USER
}

enum class UserStatus {
    INACTIVE,
    BLOCKED,
    ACTIVE,
    PENDING
}


enum class Status {
    PENDING, SUCCESS, FAILED
}

enum class PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    BANK_TRANSFER,
    E_WALLET,
    CASH
}


enum class ErrorCodes(val code:Int) {

    USER_NOT_FOUND(100),
    USER_ALREADY_EXISTS(101),

    COURSE_NOT_FOUND(200),
    COURSE_ALREADY_EXISTS(201),

    // Custom errorlar
    ROLE_ACCESS_DENIED(600),
    INSUFFICIENT_BALANCE(111),
    DATA_NOT_FOUND(222)
}


