package uz.developers.payment
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource

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




class PaymentNotFoundException : PaymentExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_NOT_FOUND
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







