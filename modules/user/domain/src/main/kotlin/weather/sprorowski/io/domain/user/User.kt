package weather.sprorowski.io.domain.user

import weather.sprorowski.io.domain.Uuid
import weather.sprorowski.io.domain.exception.EntityNotFoundException
import weather.sprorowski.io.domain.user.ErrorCode.USER_NOT_FOUND
import java.time.LocalDateTime

class User(
    val id: String,
    name: String,
    val email: Email,
    val createdAt: LocalDateTime,
) {
    class NotFound : EntityNotFoundException("User", null, USER_NOT_FOUND)
    enum class Status { COMPLETED }
    data class Email(val address: String)
    data class Password(val hash: String)

    var name = name
        private set

    companion object {
        fun create(name: String, email: String) = User(
            id = Uuid.create(),
            name = name,
            email = Email(address = email),
            createdAt = LocalDateTime.now(),
        )
    }
}
