package weather.sprorowski.io.application.user.access.revoke

import weather.sprorowski.io.domain.user.access.UserAccesses
import weather.sprorowski.io.testing.user.spawner.UserAccessSpawner
import io.kotest.common.runBlocking
import io.mockk.*
import org.junit.jupiter.api.Test

class RevokeTokenCommandHandlerTest {
    private val userAccess: UserAccesses = mockk()
    private val handler = RevokeTokenCommandHandler(userAccess)
    private val command = RevokeTokenCommand(accessId = "user-access-token-xxx")

    private val arthurDentAccess = UserAccessSpawner.arthurDentAccess()

    @Test
    fun `given an access id then revoke this access`() {
        coEvery { userAccess.loadNonRevoked(any()) } returns arthurDentAccess
        coEvery { userAccess.revoke(any()) } just runs

        runBlocking { handler.handle(command) }

        coVerify { userAccess.loadNonRevoked("user-access-token-xxx") }
        coVerify { userAccess.revoke(match { it.revokedAt !== null }) }
    }
}
