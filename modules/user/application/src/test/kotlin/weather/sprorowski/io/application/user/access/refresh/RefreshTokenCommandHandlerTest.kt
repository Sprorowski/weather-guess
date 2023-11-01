package weather.sprorowski.io.application.user.access.refresh

import weather.sprorowski.io.domain.user.Users
import weather.sprorowski.io.domain.user.access.token.UserTokenCreator
import weather.sprorowski.io.testing.TestingUuid
import weather.sprorowski.io.testing.freezeClock
import weather.sprorowski.io.testing.user.spawner.UserSpawner
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RefreshTokenCommandHandlerTest {
    private val users: Users = mockk()
    private val userTokenCreator: UserTokenCreator = mockk()

    private val handler = RefreshTokenCommandHandler(users, userTokenCreator)
    private val command = RefreshTokenCommand("user-xxx")
    private val arthurDent = UserSpawner.arthurDent()

    @Test
    fun `given an id when token is registered then return refresh token`() = freezeClock {
        TestingUuid.stack("user-access-token-xxx")
        coEvery { users.loadById(any()) } returns arthurDent
        coEvery { userTokenCreator.createFor(any()) } returns "json.web.token"

        val refresh = runBlocking { handler.handle(command) }

        Assertions.assertEquals(RefreshTokenCommand.Response("json.web.token"), refresh)
        coVerify(exactly = 1) { users.loadById("user-xxx") }
        coVerify(exactly = 1) { userTokenCreator.createFor(arthurDent) }
    }
}
