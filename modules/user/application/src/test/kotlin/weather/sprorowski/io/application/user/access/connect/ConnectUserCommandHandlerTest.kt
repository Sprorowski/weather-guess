package weather.sprorowski.io.application.user.access.connect

import weather.sprorowski.io.domain.user.Users
import weather.sprorowski.io.domain.user.access.token.UserTokenCreator
import weather.sprorowski.io.domain.user.connect.Authenticator
import weather.sprorowski.io.testing.TestingUuid
import weather.sprorowski.io.testing.freezeClock
import weather.sprorowski.io.testing.matchEquals
import weather.sprorowski.io.testing.user.spawner.UserSpawner
import io.kotest.common.runBlocking
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ConnectUserCommandHandlerTest {
    private val authenticator: Authenticator = mockk()
    private val users: Users = mockk()
    private val userTokenCreator: UserTokenCreator = mockk()

    private val handler = ConnectUserCommandHandler(authenticator, users, userTokenCreator)
    private val command = ConnectUserCommand(idToken = "provided.id.token")

    private val connected = Authenticator.User("arthur.dent@galaxy.org", "Arthur Dent")
    private val user = UserSpawner.arthurDent()

    @Test
    fun `given the id token when user does not exist then create a new user and return the calculated token`() = freezeClock {
        TestingUuid.stack("user-xxx", "user-access-token-xxx")
        coEvery { authenticator.connect(any()) } returns connected
        coEvery { users.findByEmail(any()) } returns null
        coEvery { users.register(any()) } just runs
        coEvery { userTokenCreator.createFor(any()) } returns "json.web.token"

        val authorization = runBlocking { handler.handle(command) }

        Assertions.assertEquals(ConnectUserCommand.Response("json.web.token"), authorization)
        coVerify(exactly = 1) { authenticator.connect("provided.id.token") }
        coVerify(exactly = 1) { users.findByEmail("arthur.dent@galaxy.org") }
        coVerify(exactly = 1) { users.register(matchEquals { user }) }
        coVerify(exactly = 1) { userTokenCreator.createFor(matchEquals { user }) }
    }
}
