package weather.sprorowski.io.presenter.user

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.domain.extension.trimLines
import weather.sprorowski.io.presenter.user.extension.accessId
import weather.sprorowski.io.testing.testHttpApplication
import io.kotest.assertions.json.shouldMatchJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.test.junit5.ClosingKoinTest

class AccessIdTest : ClosingKoinTest {
    companion object {
        private const val PATH = "/testing/getting-access-id-from-authorization"
    }

    private val module = ApplicationModule(
        container = module {
        },
        application = {
            routing {
                get(PATH) {
                    call.respond(HttpStatusCode.OK, mapOf("id" to accessId()))
                }
            }
        },
    )

    @Test
    fun `given the access token then return its accessId`() = testHttpApplication(module) {
        val jsonWebToken = """
            eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJod
            HRwOi8vbG9jYWxob3N0IiwiaXNzIjoibG9jYWxob3N0IiwibmFt
            ZSI6IkFydGh1ciBEZW50IiwiaWQiOiJ1c2VyLWFjY2Vzcy10b2t
            lbi14eHgifQ.AtV7JXkjVvAwWL-8Lol8TU4eAS_msGlyyQSBLNc
            64w0
        """.trimLines()

        val response = client.get(PATH) { header("Authorization", jsonWebToken) }

        Assertions.assertEquals(HttpStatusCode.OK, response.status)
        response.bodyAsText() shouldMatchJson """{"id": "user-access-token-xxx"}"""
    }
}
