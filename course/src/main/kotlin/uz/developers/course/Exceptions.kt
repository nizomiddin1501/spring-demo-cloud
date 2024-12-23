package uz.developers.course

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource

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







