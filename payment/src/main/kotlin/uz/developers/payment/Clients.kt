package uz.developers.payment

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*


@FeignClient(name = "user", configuration = [Auth2TokenConfiguration::class])
interface UserService {

    @GetMapping("/api/users/{id}")
    fun getOne(@PathVariable("id") id: Long): UserResponse

}

@FeignClient(name = "course", configuration = [Auth2TokenConfiguration::class])
interface CourseService {

    @GetMapping("/api/courses/{id}")
    fun getOne(@PathVariable id: Long): CourseResponse


}
