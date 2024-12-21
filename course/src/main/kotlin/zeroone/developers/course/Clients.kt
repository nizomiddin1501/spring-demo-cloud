package zeroone.developers.course
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import javax.validation.Valid

@FeignClient(name = "user")
interface UserService {

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): UserResponse


    @GetMapping("/{id}/balance")
    fun getUserBalance(@PathVariable id: Long): BigDecimal


    @PostMapping("/{id}/deduct")
    fun deductBalance(
        @PathVariable id: Long,
        @RequestParam amount: BigDecimal): Boolean

}


@FeignClient(name = "payment")
interface PaymentService {

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): UserResponse


    @PostMapping
    fun create(
        @RequestBody @Valid request: PaymentCreateRequest): PaymentResponse

}




