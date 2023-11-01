package weather.sprorowski.io.domain.user.access.token

import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.domain.user.access.UserAccess

interface AccessToken {
    suspend fun encode(access: UserAccess, status: User.Status, scopes: List<Scope>): String
    suspend fun getUserAccess(token: String): UserAccess
}
