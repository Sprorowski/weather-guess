package weather.sprorowski.io.testing.user.spawner

import weather.sprorowski.io.domain.user.access.UserAccess
import weather.sprorowski.io.testing.ClockSpawner

class UserAccessSpawner {
    companion object {
        private const val ID = "user-access-token-xxx"
        private const val USER_ID = "user-xxx"

        fun arthurDentAccess(): UserAccess = UserAccess(
            id = ID,
            userId = USER_ID,
            createdAt = ClockSpawner.now(),
            revokedAt = null,
        )

        fun arthurDentAccessRevoked(): UserAccess = UserAccess(
            id = ID,
            userId = USER_ID,
            createdAt = ClockSpawner.now(),
            revokedAt = ClockSpawner.onMarch(),
        )
    }
}
