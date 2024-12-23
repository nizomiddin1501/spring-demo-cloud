package uz.developers.course
import org.springframework.stereotype.Component
@Component
class CourseMapper {

    fun toDto(course: Course): CourseResponse {
        return course.run {
            CourseResponse(
                id = this.id,
                name = this.name,
                description = this.description,
                price = this.price,
                courseStatus = this.courseStatus
            )
        }
    }

    fun toEntity(createRequest: CourseCreateRequest): Course {
        return createRequest.run {
            Course(
                name = this.name,
                description = this.description,
                price = this.price,
                courseStatus = this.courseStatus
            )
        }
    }

    fun updateEntity(course: Course, updateRequest: CourseUpdateRequest): Course {
        return updateRequest.run {
            course.apply {
                updateRequest.name.let { this.name = it }
                updateRequest.description.let { this.description = it }
                updateRequest.price.let { this.price = it }
                updateRequest.courseStatus.let { this.courseStatus = it }
            }
        }
    }
}


@Component
class PurchaseMapper {

    fun toDto(purchase: Purchase): PurchaseResponse {
        return purchase.run {
            PurchaseResponse(
                id = this.id,
                userId = this.userId,
                courseName = this.course.name,
                purchaseDate = this.purchaseDate,
                purchaseStatus = this.purchaseStatus
            )
        }
    }

    fun toEntity(createRequest: PurchaseCreateRequest, course: Course): Purchase {
        return createRequest.run {
            Purchase(
                userId = this.userId,
                course = course,
                purchaseDate = this.purchaseDate,
                purchaseStatus = this.purchaseStatus
            )
        }
    }
 }







