package uz.developers.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("internal")
class InternalController(
) {
    @GetMapping("test")
    fun test() = "Hello world insecure!"

}

@RestController
class UserController {
    @GetMapping("hello")
    fun sayHello() = "Hello ${currentUserName()}"
}