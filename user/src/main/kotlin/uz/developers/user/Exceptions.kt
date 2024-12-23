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


