package weather.sprorowski.io.domain.user.connect

import weather.sprorowski.io.domain.access.Role
import weather.sprorowski.io.domain.user.User

interface UserRoles {
    suspend fun of(user: User): List<Role>
}
