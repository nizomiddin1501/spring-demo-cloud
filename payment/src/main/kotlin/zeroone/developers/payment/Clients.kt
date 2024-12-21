package zeroone.developers.payment
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*


@FeignClient(name = "user")
interface UserService {

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): UserResponse

}

@FeignClient(name = "course")
interface CourseService {

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): CourseResponse


}
