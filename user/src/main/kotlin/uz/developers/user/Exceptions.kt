package uz.developers.user
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource

sealed class UserExceptionHandler : RuntimeException() {

    abstract fun errorCode(): ErrorCodes
    open fun getArguments(): Array<Any?>? = null

    fun getErrorMessage(resourceBundleMessageSource: ResourceBundleMessageSource): BaseMessage {
        val message = try {
            resourceBundleMessageSource.getMessage(
                errorCode().name, getArguments(), LocaleContextHolder.getLocale())
        } catch (e: Exception) {
            e.message ?: "Unknown error"
        }
        return BaseMessage(errorCode().code, message)
    }
}

class FeignErrorException(val code: Int?, val errorMessage: String?) : UserExceptionHandler() {
    override fun errorCode() = ErrorCodes.FEIGN_ERROR
}


class UserAlreadyExistsException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_ALREADY_EXISTS
    }
}

class UserNotFoundException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_NOT_FOUND
    }
}

class UserRoleNotFoundException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.ROLE_NOT_FOUND
    }
}

class RoleAlreadyExistsException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.ROLE_ALREADY_EXISTS
    }
}

class IncorrectPasswordException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INCORRECT_PASSWORD
    }
}

class IdIsRequiredException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.ID_IS_REQUIRED
    }
}

class CourseAlreadyExistsException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_ALREADY_EXISTS
    }
}

class CourseNotFoundException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_NOT_FOUND
    }
}


class PaymentAlreadyExistsException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_ALREADY_EXISTS
    }
}

class PaymentNotFoundException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_NOT_FOUND
    }
}


class UserAccessDeniedException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.ROLE_ACCESS_DENIED
    }
}

class CannotCancelOrderException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.CANNOT_CANCEL_ORDER
    }
}

class InvalidOrderStatusException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INVALID_ORDER_STATUS
    }
}

class InvalidPaymentMethodException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_METHOD_NOT_FOUND
    }
}

class InsufficientBalanceException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INSUFFICIENT_BALANCE
    }
}
