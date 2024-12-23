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
    BANK_TRANSFER,
    E_WALLET,
    CASH
}


enum class ErrorCodes(val code:Int) {

    USER_NOT_FOUND(100),
    USER_ALREADY_EXISTS(101),

    PAYMENT_NOT_FOUND(300),
    PAYMENT_ALREADY_EXISTS(301),

    // Custom errors
    PAYMENT_METHOD_NOT_FOUND(603),
    INSUFFICIENT_BALANCE(111)
}


