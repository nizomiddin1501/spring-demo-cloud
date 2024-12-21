package uz.developers.user

import org.springframework.stereotype.Component

@Component
class UserMapper {
//
//    fun toUserInfo(user: User, role: Role): UserInfo {
//        return user.run {
//            UserInfo(
//                id = this.id ?: throw IdIsRequiredException(),
//                username = this.username,
//                fullName = this.fullName,
//                role = this.role,
//                status = this.status
//            )
//        }
//    }


    fun toDto(user: User): UserResponse {
        return user.run {
            UserResponse(
                id = this.id,
                username = this.username,
                fullName = this.fullName,
                password = this.password,
                roleName = this.role.name,
                status = this.status,
                balance = this.balance
            )
        }
    }

    fun toEntity(createRequest: UserCreateRequest, role: Role): User {
        return createRequest.run {
            User(
                username = this.username,
                fullName = this.fullName,
                password = this.password,
                role = role,
                status = this.status,
                balance = this.balance
            )
        }
    }

    fun updateEntity(user: User, updateRequest: UserUpdateRequest): User {
        return updateRequest.run {
            user.apply {
                updateRequest.username.let { this.username = it }
                updateRequest.fullName.let { this.fullName = it }
                updateRequest.password.let { this.password = it }
                updateRequest.status.let { this.status = it }
                updateRequest.balance.let { this.balance = it }
            }
        }
    }
}

@Component
class RoleMapper {

    fun toEntity(createRequest: RoleCreateRequest): Role {
        return createRequest.run {
            Role(
                key = this.key,
                name = this.name,
            )
        }
    }
}





