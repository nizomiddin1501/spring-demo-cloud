package uz.developers.demo

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseMessage(
    val code: Int?,
    val message: String?
) {
    companion object {
        val OK = BaseMessage(0, "OK")
    }
}

data class AuthUser(
    val username: String,
    val password: String?
)
