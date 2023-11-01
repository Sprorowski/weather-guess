package weather.sprorowski.io.presenter.user.access.connect

import weather.sprorowski.io.application.user.access.connect.ConnectUserCommand
import weather.sprorowski.io.application.user.access.connect.ConnectUserCommandHandler
import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.testing.user.testUserApplication
import io.kotest.assertions.json.shouldMatchJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.test.junit5.ClosingKoinTest

class ConnectUserEndpointTest : ClosingKoinTest {
    companion object {
        private const val PATH = "/users/access/connect"
    }

    private val handler: ConnectUserCommandHandler = mockk()
    private val module = ApplicationModule(container = module { single { handler } })

    init {
        coEvery { handler.handle(any()) } returns ConnectUserCommand.Response("json.web.token")
    }

    @Test
    fun `given the credentials when handler returns the id then return the id`() = testUserApplication(module) {
        val response = client.request(PATH) {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                    "idToken": "json.id.token"
                }
                """.trimIndent(),
            )
        }

        Assertions.assertEquals(HttpStatusCode.OK, response.status)
        response.bodyAsText() shouldMatchJson """
        {
           "accessToken": "json.web.token"
        }
        """.trimIndent()

        coVerify { handler.handle(ConnectUserCommand("json.id.token")) }
    }
}
