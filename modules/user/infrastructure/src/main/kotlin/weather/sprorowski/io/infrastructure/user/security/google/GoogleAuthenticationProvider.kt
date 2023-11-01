package weather.sprorowski.io.infrastructure.user.security.google

import weather.sprorowski.io.domain.access.exception.UserUnauthorized
import weather.sprorowski.io.domain.extension.onNull
import weather.sprorowski.io.domain.user.connect.Authenticator
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier

class GoogleAuthenticationProvider(private val verifier: GoogleIdTokenVerifier) : Authenticator {
    override fun connect(idToken: String): Authenticator.User {
        val token = verifier.verify(idToken)
        val payload = token.payload.onNull { throw UserUnauthorized("Invalid Credentials") }

        return Authenticator.User(
            email = payload.email,
            name = (payload["name"] as String?)!!,
        )
    }
}
