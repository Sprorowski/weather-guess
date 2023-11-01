package weather.sprorowski.io.presenter.user.access.refresh

import weather.sprorowski.io.application.user.access.refresh.RefreshTokenCommand
import weather.sprorowski.io.application.user.access.refresh.RefreshTokenCommandHandler
import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.testing.user.UserTestCase
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions

class RefreshTokenEndpointTest : UserTestCase {
    private val handler: RefreshTokenCommandHandler = mockk()

    init {
        coEvery { handler.handle(any()) } returns RefreshTokenCommand.Response("json.web.token")
    }

    override fun module(): ApplicationModule = ApplicationModule(container = org.koin.dsl.module { single { handler } })
    override fun endpoint(): String = "/users/access/refresh"
    override fun method(): HttpMethod = HttpMethod.Post
    override fun body(): String = """
        {
            "token": "user-access-token-xxx"
        }
        """.trimIndent()

    override suspend fun verifyResponse(response: HttpResponse) {
        Assertions.assertEquals(HttpStatusCode.OK, response.status)
        coVerify { handler.handle(RefreshTokenCommand("user-xxx")) }
    }
}
