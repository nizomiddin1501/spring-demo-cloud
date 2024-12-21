package uz.developers.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
