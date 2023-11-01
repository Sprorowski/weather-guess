package weather.sprorowski.io.testing

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.domain.access.Auditor
import io.kotest.assertions.json.shouldMatchJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.dsl.module

interface ScopedHttpTestCase : HttpTestCase {
    @Test
    fun whenRequestHasValidPayloadAndCallerIsNotAuthorizedThenReturnUnauthorized() = testHttpApplication(modules) {
        val response = client.request(endpoint()) {
            method = method()
            contentType(ContentType.Application.Json)
            body()?.let { setBody(it) }
            header("Authorization", createTestingToken(emptyList()))
            request()
        }

        Assertions.assertEquals(HttpStatusCode.Forbidden, response.status)
        response.bodyAsText() shouldMatchJson """{"message":"You cannot access the requested resource."}"""
    }

    private val modules: List<ApplicationModule>
        get() {
            return listOf(config(), module(), ApplicationModule(module { single<Auditor> { TestingAuditor() } }))
        }
}
