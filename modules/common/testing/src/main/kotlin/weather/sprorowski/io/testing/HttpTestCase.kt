package weather.sprorowski.io.testing

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.domain.access.UserIdLoader
import io.kotest.assertions.json.shouldMatchJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.test.junit5.ClosingKoinTest

interface HttpTestCase : ClosingKoinTest {
    fun config(): ApplicationModule
    fun module(): ApplicationModule
    fun endpoint(): String
    fun method(): HttpMethod
    fun scopes(): List<Scope> = emptyList()
    suspend fun verifyResponse(response: HttpResponse)

    fun body(): String? = null
    fun request(): suspend ApplicationTestBuilder.() -> Unit = {}

    @Test
    fun whenHeaderDoesNotContainTheAuthorizationDataThenReturnUnauthorized() = testHttpApplication(modules) {
        val response = client.request(endpoint()) {
            method = method()
            contentType(ContentType.Application.Json)
            body()?.let { setBody(it) }
            request()
        }
        Assertions.assertEquals(HttpStatusCode.Unauthorized, response.status)
        response.bodyAsText() shouldMatchJson """{"message":"Missing Bearer Token"}"""
    }

    @Test
    fun whenRequestHasValidPayloadAndCallerIsAuthorizedThenHandleTheRequest() = testHttpApplication(modules) {
        val response = client.request(endpoint()) {
            method = method()
            contentType(ContentType.Application.Json)
            body()?.let { setBody(it) }
            header("Authorization", createTestingToken(scopes()))
            request()
        }

        verifyResponse(response)
    }

    private val modules: List<ApplicationModule>
        get() {
            return listOf(
                config(),
                module(),
                ApplicationModule(module { single<UserIdLoader> { TestingUserIdLoader() } }),
            )
        }
}
