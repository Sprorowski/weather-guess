package weather.sprorowski.io.domain.user.access.token

import weather.sprorowski.io.domain.access.scopes
import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.domain.user.access.UserAccess
import weather.sprorowski.io.domain.user.access.UserAccesses
import weather.sprorowski.io.domain.user.connect.UserRoles

class UserTokenCreator(
    private val token: UserAccesses,
    private val roles: UserRoles,
    private val accessToken: AccessToken,
) {
    suspend fun createFor(user: User): String {
        val scopes = roles.of(user).scopes()
        val access = UserAccess.create(user)
        token.register(access)

        return accessToken.encode(access, User.Status.COMPLETED, scopes)
    }
}
