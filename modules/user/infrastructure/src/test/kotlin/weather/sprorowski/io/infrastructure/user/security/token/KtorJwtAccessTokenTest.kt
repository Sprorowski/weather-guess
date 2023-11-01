package weather.sprorowski.io.infrastructure.user.security.token

import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.domain.extension.trimLines
import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.domain.user.access.UserAccesses
import weather.sprorowski.io.testing.user.spawner.UserAccessSpawner
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class KtorJwtAccessTokenTest {
    companion object {
        private const val USER_ACCESS_ID = "user-access-token-xxx"
        private const val AUDIENCE = "http://localhost"
        private const val ISSUER = "localhost"
        private const val SECRET = "aaa-bbb"
        private val VALID_TOKEN = """
            eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwOi8vbG9jYWxob3N0IiwiaXNzIjoi
            bG9jYWxob3N0IiwiaWQiOiJ1c2VyLWFjY2Vzcy10b2tlbi14eHgiLCJzdGF0dXMiOiJDT01QTEVURUQiL
            CJzY29wZXMiOlsiaHIvZXhwZW5zZXMubGlzdCIsImhyL2V4cGVuc2VzLnZpZXciXX0.K7hmjRLqPzUYKa
            2xNOwTVYYKvECu3kGSCWYZKsEpQwk
        """.trimLines()

        private val INVALID_TOKEN = """
            eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL2hjLmxvZy5icikiLCJpc3Mi
            OiJXaGVlbiBQYWdhbWVudG9zIiwibmFtZSI6Ikp1bGlhbmEgU2FyYW4ifQ.XTMGCTIA8hAq3Ei7-NhFH9
            LfDnGVbEjz-Ks9R1rLS3w
        """.trimLines()
    }

    private val userAccesses: UserAccesses = mockk()
    private val jwtAccessToken = KtorJwtAccessToken(
        KtorJwtAccessToken.Config(
            audience = AUDIENCE,
            issuer = ISSUER,
            secret = SECRET,
        ),
        userAccesses,
    )

    private val userAccess = UserAccessSpawner.arthurDentAccess()
    private val status = User.Status.COMPLETED
    private val scopes = listOf(Scope.EMPLOYEE_EXPENSE_LIST, Scope.EMPLOYEE_EXPENSE_VIEW)

    @Test
    fun `given a jwt token when its encoded then verify the token is valid `() = runBlocking {
        val valid = jwtAccessToken.encode(userAccess, status, scopes)

        Assertions.assertEquals(VALID_TOKEN, valid)
    }

    @Test
    fun `given a token when it is invalid then throw an exception`() = runBlocking {
        // Arrange
        coEvery { userAccesses.loadNonRevoked(any()) } returns userAccess

        // Act
        val exception = assertThrows<Exception> { jwtAccessToken.getUserAccess(INVALID_TOKEN) }

        // Assert
        Assertions.assertEquals("Missing property", exception.message)
        coVerify(exactly = 0) { userAccesses.loadNonRevoked(any()) }
    }

    @Test
    fun `given a token when it is valid then load the user access`() = runBlocking {
        coEvery { userAccesses.loadNonRevoked(any()) } returns userAccess

        val loaded = jwtAccessToken.getUserAccess(VALID_TOKEN)

        Assertions.assertSame(userAccess, loaded)
        coVerify(exactly = 1) { userAccesses.loadNonRevoked(USER_ACCESS_ID) }
    }
}
