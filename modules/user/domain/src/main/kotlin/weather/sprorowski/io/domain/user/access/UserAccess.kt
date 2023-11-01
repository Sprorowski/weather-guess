package weather.sprorowski.io.domain.user.access

import weather.sprorowski.io.domain.user.User
import java.time.LocalDateTime

class UserAccess(
    val id: String,
    val userId: String,
    val createdAt: LocalDateTime,
    revokedAt: LocalDateTime?,
) {
    var revokedAt: LocalDateTime? = revokedAt
        private set

    companion object {
        fun create(user: User) = UserAccess(
            id = weather.sprorowski.io.domain.Uuid.create(),
            userId = user.id,
            createdAt = LocalDateTime.now(),
            revokedAt = null,
        )
    }

    fun revoke() {
        revokedAt = LocalDateTime.now()
    }
}
