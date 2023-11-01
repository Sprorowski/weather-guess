package weather.sprorowski.io.testing

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.config.container
import weather.sprorowski.io.domain.access.Scope
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

val testingModule = ApplicationModule(
    container = container(applicationConfig),
    application = {},
)

fun createTestingToken(scopes: List<Scope>): String = JWT.create()
    .withClaim("scopes", scopes.map { it.scope })
    .withClaim("id", "user-access-token-xxx")
    .sign(Algorithm.HMAC256("jwt-test-secret"))
