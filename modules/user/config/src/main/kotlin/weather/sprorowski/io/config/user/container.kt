package weather.sprorowski.io.config.user

import weather.sprorowski.io.application.user.access.connect.ConnectUserCommandHandler
import weather.sprorowski.io.application.user.access.refresh.RefreshTokenCommandHandler
import weather.sprorowski.io.application.user.access.revoke.RevokeTokenCommandHandler
import weather.sprorowski.io.domain.access.UserIdLoader
import weather.sprorowski.io.domain.user.Users
import weather.sprorowski.io.domain.user.access.UserAccesses
import weather.sprorowski.io.domain.user.access.token.AccessToken
import weather.sprorowski.io.domain.user.access.token.UserTokenCreator
import weather.sprorowski.io.domain.user.connect.Authenticator
import weather.sprorowski.io.domain.user.connect.UserRoles
import weather.sprorowski.io.infrastructure.user.data.repositories.OrmUsers
import weather.sprorowski.io.infrastructure.user.data.repositories.access.OrmUserAccesses
import weather.sprorowski.io.infrastructure.user.data.repositories.connect.InMemoryUserRoles
import weather.sprorowski.io.infrastructure.user.data.tables.UserAccessTable
import weather.sprorowski.io.infrastructure.user.data.tables.UserEmailTable
import weather.sprorowski.io.infrastructure.user.data.tables.UserTable
import weather.sprorowski.io.infrastructure.user.security.JwtUserIdLoader
import weather.sprorowski.io.infrastructure.user.security.google.GoogleAuthenticationProvider
import weather.sprorowski.io.infrastructure.user.security.token.KtorJwtAccessToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.ktor.server.config.*
import org.koin.dsl.module
import java.util.*

fun container(config: ApplicationConfig) = module {
    // configs
    val jwtConfig = KtorJwtAccessToken.Config(
        audience = config.property("security.jwt.audience").getString(),
        issuer = config.property("security.jwt.issuer").getString(),
        secret = config.property("security.jwt.secret").getString(),
    )

    val verifier = GoogleIdTokenVerifier
        .Builder(NetHttpTransport(), GsonFactory.getDefaultInstance())
        .setAudience(Collections.singletonList(config.property("google.oauth.clientId").getString()))
        .build()

    // infrastructure:orm:tables
    single { UserTable(get(), get()) }
    single { UserEmailTable(get()) }
    single { UserAccessTable(get()) }

    // domain:repositories
    single<Users> { OrmUsers(get(), get(), get()) }
    single<UserAccesses> { OrmUserAccesses(get()) }
    single<UserRoles> { InMemoryUserRoles() }

    // domain:services
    single<AccessToken> { KtorJwtAccessToken(jwtConfig, get()) }
    single<UserIdLoader> { JwtUserIdLoader(get()) }
    single { UserTokenCreator(get(), get(), get()) }

    single<Authenticator> { GoogleAuthenticationProvider(verifier) }

    // application:handlers
    single { ConnectUserCommandHandler(get(), get(), get()) }
    single { RefreshTokenCommandHandler(get(), get()) }
    single { RevokeTokenCommandHandler(get()) }
}
