package uz.developers.user

import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataLoader(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        loadRoles()
        loadAdmin()
    }

    private fun loadRoles() {
        if (roleRepository.count() == 0L) {
            val adminRole = RoleCreateRequest(key = "1", name = "ADMIN")
            val role = Role(key = adminRole.key, name = adminRole.name)
            roleRepository.save(role)
            println("Role ADMIN loaded")
        } else {
            println("Roles already exist")
        }
    }

    private fun loadAdmin() {
        val adminUsername= "developer"
        if (userRepository.findByUsernameAndDeletedFalse(adminUsername) == null) {
            val role = roleRepository.findByName("ADMIN")
            val adminUser = User(
                username = "developer",
                fullName = "Nizomiddin Mirzanazarov",
                password = passwordEncoder.encode("nizomiddin097"),
                role = role!!,
                status = UserStatus.ACTIVE,
                balance = BigDecimal(100.0))
            userRepository.save(adminUser)
            println("Admin user loaded")
        } else {
            println("Admin user already exists")
        }
    }
}
