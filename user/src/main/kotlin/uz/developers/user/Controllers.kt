package uz.developers.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler
import java.math.BigDecimal
import javax.validation.Valid


@ControllerAdvice
class ExceptionHandler(private val errorMessageSource: ResourceBundleMessageSource) {

    @ExceptionHandler(UserExceptionHandler::class)
    fun handleAccountException(exception: UserExceptionHandler): ResponseEntity<BaseMessage> {
        return ResponseEntity.badRequest().body(exception.getErrorMessage(errorMessageSource))
    }
}

@RestController
@RequestMapping("internal")
class InternalController(
    private val service: UserService) {

    @PostMapping("find-by-username")
    fun findByAuthUser(@RequestBody request: AuthUser) = service.findByAuthUser(request)

}


@RestController
@RequestMapping("/api/users")
class UserController(
    private val demoService: DemoService,
    private val userService: UserService) {

    @GetMapping("get-demo-hello")
    fun getHello() = demoService.getHelloMessageSecure()

    @Operation(summary = "Get all users", description = "Fetches all users from the database.")
    @ApiResponses(ApiResponse(responseCode = "200", description = "Successfully fetched all users"))
    @GetMapping
    fun getAll() = userService.getAll()


    @Operation(summary = "Get all users with pagination", description = "Fetches users with pagination support.")
    @ApiResponses(ApiResponse(responseCode = "200", description = "Successfully fetched paginated users"))
    @GetMapping("/page")
    fun getAll(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int) =
        userService.getAll(page, size)


    @Operation(summary = "Get user by ID", description = "Fetches a single user based on the provided ID.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully fetched the user"),
        ApiResponse(responseCode = "404", description = "User not found")
    )
    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = userService.getOne(id)


    @Operation(summary = "Create new user", description = "Creates a new user record.")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "User successfully created"),
        ApiResponse(responseCode = "400", description = "Invalid request data")
    )
    @PostMapping
    fun create(@RequestBody @Valid request: UserCreateRequest) = userService.create(request)


    @Operation(summary = "Update existing user", description = "Updates an existing user based on the provided ID.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User successfully updated"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "400", description = "Invalid request data")
    )
    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid request: UserUpdateRequest) = userService.update(id, request)


    @Operation(summary = "Get user balance",description = "Fetches the balance of the user with the given ID.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully fetched user balance"),
        ApiResponse(responseCode = "404", description = "User not found")
    )
    @GetMapping("/{id}/balance")
    fun getUserBalance(@PathVariable id: Long): BigDecimal {
        return userService.getUserBalance(id)
    }


    @Operation(summary = "Deduct user balance", description = "Deducts the specified amount from the user's balance if sufficient funds are available.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Balance successfully deducted"),
        ApiResponse(responseCode = "400", description = "Insufficient balance or invalid amount"),
        ApiResponse(responseCode = "404", description = "User not found")
    )
    @PostMapping("/{id}/deduct")
    fun deductBalance(
        @PathVariable id: Long,
        @RequestParam amount: BigDecimal
    ): Boolean {
        return userService.deductBalance(id, amount)
    }

    @Operation(summary = "Add amount to user balance", description = "Adds the specified amount to the user's balance.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Balance successfully updated"),
        ApiResponse(responseCode = "400", description = "Invalid request data"),
        ApiResponse(responseCode = "404", description = "User not found")
    )
    @PutMapping("/{id}/balance")
    fun fillBalance(
        @PathVariable id: Long,
        @RequestParam("amount") amount: BigDecimal
    ): BigDecimal {
        return userService.fillBalance(id, amount)
    }

    @Operation(summary = "Get user statistics",description = "Fetches statistics about all users.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully fetched user statistics")
    )
    @GetMapping("/stats")
    fun getUserStats(): UserStatsResponse {
        return userService.getUserStats()
    }


    @Operation(summary = "Delete user by ID", description = "Deletes a user based on the provided ID.")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "User successfully deleted"),
        ApiResponse(responseCode = "404", description = "User not found")
    )
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = userService.delete(id)

}