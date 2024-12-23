package uz.developers.user
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


class FeignErrorException(val code: Int?, val errorMessage: String?) : DBusinessException() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.FEIGN_ERROR
    }
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

class InvalidAmountException : UserExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INVALID_AMOUNT
    }
}


