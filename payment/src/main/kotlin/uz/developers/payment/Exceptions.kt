package uz.developers.payment
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource

sealed class DBusinessException : RuntimeException() {

    abstract fun errorCode(): ErrorCodes

    open fun getErrorMessageArguments(): Array<Any?>? = null

    fun getErrorMessage(errorMessageSource: ResourceBundleMessageSource): BaseMessage {
        val errorMessage = try {
            errorMessageSource.getMessage(errorCode().name, getErrorMessageArguments(), LocaleContextHolder.getLocale())
        } catch (e: Exception) {
            e.message
        }
        return BaseMessage(errorCode().code, errorMessage)
    }
}

sealed class PaymentExceptionHandler() : RuntimeException() {
    abstract fun errorCode(): ErrorCodes
    open fun getArguments(): Array<Any?>? = null

    fun getErrorMessage(resourceBundleMessageSource: ResourceBundleMessageSource): BaseMessage {
        val message = try {
            resourceBundleMessageSource.getMessage(
                errorCode().name, getArguments(), LocaleContextHolder.getLocale()
            )
        } catch (e: Exception) {
            e.message ?: "Unknown error"
        }
        return BaseMessage(errorCode().code, message)
    }
}

class FeignErrorException(val code: Int?, val errorMessage: String?) : DBusinessException() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.FEIGN_ERROR
    }
}

class UserAlreadyExistsException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_ALREADY_EXISTS
    }
}

class UserNotFoundException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_NOT_FOUND
    }
}

class CourseNotFoundException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_NOT_FOUND
    }
}

class CourseAlreadyExistException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_ALREADY_EXISTS
    }
}

class PaymentNotFoundException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_NOT_FOUND
    }
}


class CashPaymentNotAllowedException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.CASH_PAYMENT_NOT_ALLOWED
    }
}


class InvalidPaymentMethodException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_METHOD_NOT_FOUND
    }
}

class InsufficientBalanceException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INSUFFICIENT_BALANCE
    }
}







