package weather.sprorowski.io.domain.user

import weather.sprorowski.io.domain.access.Role
import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.domain.user.access.UserAccesses
import weather.sprorowski.io.domain.user.access.token.AccessToken
import weather.sprorowski.io.domain.user.access.token.UserTokenCreator
import weather.sprorowski.io.domain.user.connect.UserRoles
import weather.sprorowski.io.testing.TestingUuid
import weather.sprorowski.io.testing.freezeClock
import weather.sprorowski.io.testing.matchEquals
import weather.sprorowski.io.testing.user.spawner.UserAccessSpawner
import weather.sprorowski.io.testing.user.spawner.UserSpawner
import io.kotest.common.runBlocking
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserTokenCreatorTest {
    private val token: UserAccesses = mockk()
    private val roles: UserRoles = mockk()
    private val accessToken: AccessToken = mockk()

    private val userTokenCreator = UserTokenCreator(token, roles, accessToken)

    private val arthurDent = UserSpawner.arthurDent()
    private val arthurDentAccess = UserAccessSpawner.arthurDentAccess()
    private val status = User.Status.COMPLETED
    private val scopes = Role.EMPLOYEE.scopes

    @Test
    fun `given a user then return its encoded token`() = freezeClock {
        TestingUuid.stack("user-access-token-xxx")
        coEvery { roles.of(any()) } returns listOf(Role.EMPLOYEE)
        coEvery { token.register(any()) } just runs
        coEvery { accessToken.encode(any(), any(), any()) } returns "json.web.token"

        val userTokenCreator = runBlocking { userTokenCreator.createFor(arthurDent) }

        Assertions.assertEquals("json.web.token", userTokenCreator)
        coVerify(exactly = 1) { roles.of(arthurDent) }
        coVerify(exactly = 1) { token.register(matchEquals { arthurDentAccess }) }
        coVerify(exactly = 1) { accessToken.encode(matchEquals { arthurDentAccess }, status, scopes) }
    }
}
