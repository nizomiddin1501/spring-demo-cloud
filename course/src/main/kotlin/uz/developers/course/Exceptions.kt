package uz.developers.course

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

sealed class MicroserviceExceptionHandler() : RuntimeException() {
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

class UserNotFoundException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_NOT_FOUND
    }
}

class CourseAlreadyExistsException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_ALREADY_EXISTS
    }
}

class CourseNotFoundException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_NOT_FOUND
    }
}

class InvalidCoursePriceException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INVALID_COURSE_PRICE
    }
}

class UserAccessDeniedException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.ROLE_ACCESS_DENIED
    }
}



class InsufficientBalanceException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INSUFFICIENT_BALANCE
    }
}

class DataNotFoundException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.DATA_NOT_FOUND
    }
}


class PaymentFailedException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_FAILED
    }
}

class CourseAlreadyPurchasedException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_ALREADY_PURCHASED
    }
}




