package uz.developers.payment

enum class UserRole {
    ADMIN, USER
}


enum class Status {
    PENDING, SUCCESS, FAILED
}

enum class PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    E_WALLET,
    CASH
}


enum class ErrorCodes(val code:Int) {

    USER_NOT_FOUND(100),
    USER_ALREADY_EXISTS(101),

    COURSE_NOT_FOUND(201),
    COURSE_ALREADY_EXISTS(202),

    PAYMENT_NOT_FOUND(300),
    PAYMENT_ALREADY_EXISTS(301),
    CASH_PAYMENT_NOT_ALLOWED(302),

    FEIGN_ERROR(106),
    VALIDATION_ERROR(404),

    PAYMENT_METHOD_NOT_FOUND(603),
    INSUFFICIENT_BALANCE(111)
}


