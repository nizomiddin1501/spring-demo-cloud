package uz.developers.course
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import javax.validation.Valid

@FeignClient(name = "user", configuration = [Auth2TokenConfiguration::class])
interface UserService {

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): UserResponse


    @GetMapping("/balance/{id}")
    fun getUserBalance(@PathVariable id: Long): BigDecimal


    @PostMapping("/deduct/{id}")
    fun deductBalance(
        @PathVariable id: Long,
        @RequestParam amount: BigDecimal): Boolean

}


@FeignClient(name = "payment", configuration = [Auth2TokenConfiguration::class])
interface PaymentService {

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): UserResponse


    @PostMapping
    fun create(@RequestBody @Valid request: PaymentCreateRequest): PaymentResponse

}




