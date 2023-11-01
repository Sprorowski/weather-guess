package weather.sprorowski.io.infrastructure.user.data.repositories.connect

import weather.sprorowski.io.domain.access.Role
import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.domain.user.connect.UserRoles

class InMemoryUserRoles : UserRoles {
    override suspend fun of(user: User): List<Role> {
        return listOf(Role.EMPLOYEE)
    }
}
