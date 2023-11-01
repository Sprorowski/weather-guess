package weather.sprorowski.io.presenter.user

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.domain.extension.trimLines
import weather.sprorowski.io.domain.user.access.UserAccesses
import weather.sprorowski.io.presenter.user.extension.userId
import weather.sprorowski.io.testing.user.spawner.UserAccessSpawner
import weather.sprorowski.io.testing.user.testUserApplication
import io.kotest.assertions.json.shouldMatchJson
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.test.junit5.ClosingKoinTest

class UserIdTest : ClosingKoinTest {
    companion object {
        private const val PATH = "/testing/getting-user-id-from-authorization"
    }

    private val userAccesses: UserAccesses = mockk()

    private val module = ApplicationModule(
        container = module {
            single { userAccesses }
        },
        application = {
            routing {
                get(PATH) {
                    call.respond(HttpStatusCode.OK, mapOf("id" to userId()))
                }
            }
        },
    )

    init {
        coEvery { userAccesses.loadNonRevoked(any()) } returns UserAccessSpawner.arthurDentAccess()
    }

    @Test
    fun `given the access token then return its userId`() = testUserApplication(module) {
        val jsonWebToken = """
            eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJod
            HRwOi8vbG9jYWxob3N0IiwiaXNzIjoibG9jYWxob3N0IiwibmFt
            ZSI6IkFydGh1ciBEZW50IiwiaWQiOiJ1c2VyLWFjY2Vzcy10b2t
            lbi14eHgifQ.AtV7JXkjVvAwWL-8Lol8TU4eAS_msGlyyQSBLNc
            64w0
        """.trimLines()

        val response = client.get(PATH) { header("Authorization", jsonWebToken) }

        Assertions.assertEquals(HttpStatusCode.OK, response.status)
        response.bodyAsText() shouldMatchJson """{"id": "user-xxx"}"""
    }
}
