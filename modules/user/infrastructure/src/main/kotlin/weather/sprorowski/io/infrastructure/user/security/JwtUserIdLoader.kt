package weather.sprorowski.io.infrastructure.user.security

import weather.sprorowski.io.domain.user.access.token.AccessToken
import weather.sprorowski.io.domain.access.UserIdLoader
import weather.sprorowski.io.domain.access.exception.UserUnauthorized

class JwtUserIdLoader(private val accessToken: AccessToken) : UserIdLoader {
    override suspend fun loadByToken(token: String?): String {
        val plainJwt = token?.replace("Bearer ", "") ?: throw UserUnauthorized("Missing Bearer Token")
        val access = accessToken.getUserAccess(plainJwt)

        return access.userId
    }
}
