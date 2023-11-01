package weather.sprorowski.io.infrastructure.user.security.google

import weather.sprorowski.io.domain.access.exception.UserUnauthorized
import weather.sprorowski.io.domain.user.connect.Authenticator
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GoogleAuthenticationProviderTest {
    companion object {
        private const val ID_TOKEN = "json.web.token"
        private const val EMAIL = "arthur.dent@galaxy.org"
        private const val NAME = "Arthur Dent"
    }

    private val verifier: GoogleIdTokenVerifier = mockk()
    private val authenticator = GoogleAuthenticationProvider(verifier)
    private val token: GoogleIdToken = mockk()

    @Test
    fun `given the id token when token is verified then return a auth user`() {
        every { verifier.verify(ID_TOKEN) } returns token
        every { token.payload } returns GoogleIdToken.Payload()
            .set("name", NAME)
            .set("email", EMAIL)

        val user = authenticator.connect(ID_TOKEN)

        Assertions.assertEquals(Authenticator.User(email = "arthur.dent@galaxy.org", name = "Arthur Dent"), user)
    }

    @Test
    fun `given the id token when token is not verified then return throw unauthorized`() {
        every { verifier.verify(ID_TOKEN) } returns token
        every { token.payload } returns null

        val exception = assertThrows<UserUnauthorized> { authenticator.connect(ID_TOKEN) }

        Assertions.assertEquals("Invalid Credentials", exception.message)
    }
}
