package weather.sprorowski.io.testing.user.spawner

import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.testing.ClockSpawner

class UserSpawner {
    companion object {
        private const val USER_ID = "user-xxx"
        private const val USER_NAME = "Arthur Dent"
        private const val USER_EMAIL_ADDRESS = "arthur.dent@galaxy.org"

        fun arthurDent(): User = User(
            id = USER_ID,
            name = USER_NAME,
            email = User.Email(USER_EMAIL_ADDRESS),
            createdAt = ClockSpawner.now(),
        )
    }
}
