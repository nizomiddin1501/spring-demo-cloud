package uz.developers.payment
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl::class)
class PaymentApplication

fun main(args: Array<String>) {
    runApplication<PaymentApplication>(*args)
}


//        my portfolio
//        https://nizomiddin-portfolio.netlify.app/
