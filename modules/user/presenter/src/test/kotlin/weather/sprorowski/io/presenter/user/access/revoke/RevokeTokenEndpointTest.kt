package weather.sprorowski.io.presenter.user.access.revoke

import weather.sprorowski.io.application.user.access.revoke.RevokeTokenCommand
import weather.sprorowski.io.application.user.access.revoke.RevokeTokenCommandHandler
import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.testing.user.UserTestCase
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.koin.dsl.module

class RevokeTokenEndpointTest : UserTestCase {
    private val handler: RevokeTokenCommandHandler = mockk()

    init {
        coEvery { handler.handle(any()) } just runs
    }

    override fun module(): ApplicationModule = ApplicationModule(container = module { single { handler } })
    override fun endpoint(): String = "/users/access/revoke"
    override fun method(): HttpMethod = HttpMethod.Post

    override suspend fun verifyResponse(response: HttpResponse) {
        Assertions.assertEquals(HttpStatusCode.NoContent, response.status)
        coVerify { handler.handle(RevokeTokenCommand("user-access-token-xxx")) }
    }
}
