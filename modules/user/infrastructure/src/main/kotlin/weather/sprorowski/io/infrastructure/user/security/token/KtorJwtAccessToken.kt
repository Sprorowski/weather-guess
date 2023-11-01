package weather.sprorowski.io.infrastructure.user.security.token

import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.domain.user.access.UserAccess
import weather.sprorowski.io.domain.user.access.UserAccesses
import weather.sprorowski.io.domain.user.access.token.AccessToken
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator.Builder
import com.auth0.jwt.algorithms.Algorithm

class KtorJwtAccessToken(
    private val config: Config,
    private val userAccesses: UserAccesses,
) : AccessToken {
    data class Config(
        val audience: String,
        val issuer: String,
        val secret: String,
    )

    override suspend fun encode(
        access: UserAccess,
        status: User.Status,
        scopes: List<Scope>,
    ): String {
        return JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withClaim("id", access.id)
            .withClaim("status", status.name)
            .withClaim(scopes)
            .sign(Algorithm.HMAC256(config.secret))
    }

    override suspend fun getUserAccess(token: String): UserAccess {
        val jwt = JWT.decode(token.replace("Bearer ", ""))
        val id = jwt.claims["id"]?.asString() ?: throw Exception("Missing property")

        return userAccesses.loadNonRevoked(id)
    }

    private fun Builder.withClaim(scopes: List<Scope>): Builder {
        if (scopes.isEmpty()) return this

        return withClaim("scopes", scopes.map { it.scope })
    }
}
