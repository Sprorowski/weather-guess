package weather.sprorowski.io.presenter.plugins

import weather.sprorowski.io.domain.access.exception.UserOperationNotAllowed
import weather.sprorowski.io.domain.access.exception.UserUnauthorized
import weather.sprorowski.io.domain.exception.*
import io.kotest.assertions.json.shouldMatchJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.test.junit5.ClosingKoinTest
import org.slf4j.Logger

class ExceptionHandlingTest : ClosingKoinTest {
    companion object {
        private const val ENDPOINT = "/foo/bar"
    }

    enum class TestingCode : DomainException.DomainErrorCode { TEST }

    private val mockedLogger: Logger = mockk()

    class NotFound(value: String) : EntityNotFoundException("FooBar", mapOf("foo" to value), TestingCode.TEST)
    class NotImplemented(value: String) : NotImplementedException("Action $value not implemented")
    class ThirdPartyFailed(value: String) : BadGatewayException("Call to $value server failed")
    class Unprocessable(value: String) : BusinessRuleException(value)

    @Test
    fun `when InvalidRequest exception is thrown then return bad request response`() {
        val app: Application.() -> Unit = {
            routing { get(ENDPOINT) { throw InvalidRequest("Invalid Request") } }
        }

        testCoreApplication(app) {
            val response = client.get(ENDPOINT)

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            response.bodyAsText() shouldMatchJson """{"message":"Invalid Request"}"""
        }
    }

    @Test
    fun `when UnauthorizedException exception is thrown then return unauthorized response`() {
        val app: Application.() -> Unit = {
            routing { get(ENDPOINT) { throw UserUnauthorized("Unauthorized") } }
        }

        testCoreApplication(app) {
            val response = client.get(ENDPOINT)

            Assertions.assertEquals(HttpStatusCode.Unauthorized, response.status)
            response.bodyAsText() shouldMatchJson """{"message":"Unauthorized"}"""
        }
    }

    @Test
    fun `when EntityNotFoundException exception is thrown then return not implemented response`() {
        val app: Application.() -> Unit = {
            routing { get(ENDPOINT) { throw NotFound("bar") } }
        }

        testCoreApplication(app) {
            val response = client.get(ENDPOINT)

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
            response.bodyAsText() shouldMatchJson """
            {
                "code": "TEST",
                "message": "FooBar Not Found",
                "details": {
                    "foo":"bar"
                }
            }
            """.trimIndent()
        }
    }

    @Test
    fun `when NotImplementedException exception is thrown then return not implemented response`() {
        val message = "Action foo-bar not implemented"
        every { mockedLogger.error(message, any()) } just runs
        val app: Application.() -> Unit = {
            install(Koin) {
                modules(module { single { mockedLogger } })
                routing { get(ENDPOINT) { throw NotImplemented("foo-bar") } }
            }
        }

        testCoreApplication(app) {
            val response = client.get(ENDPOINT)

            Assertions.assertEquals(HttpStatusCode.NotImplemented, response.status)
            response.bodyAsText() shouldMatchJson """{"message":"$message"}"""
            verify(exactly = 1) { mockedLogger.error(message, any()) }
        }
    }

    @Test
    fun `when BadGatewayException exception is thrown then return bad gateway response`() {
        val message = "Call to windows server server failed"
        every { mockedLogger.error(message, any()) } just runs
        val app: Application.() -> Unit = {
            install(Koin) {
                modules(module { single { mockedLogger } })
                routing { get(ENDPOINT) { throw ThirdPartyFailed("windows server") } }
            }
        }

        testCoreApplication(app) {
            val response = client.get(ENDPOINT)

            Assertions.assertEquals(HttpStatusCode.BadGateway, response.status)
            response.bodyAsText() shouldMatchJson """{"message":"There was a problem with an upstream server."}"""
            verify(exactly = 1) { mockedLogger.error(message, any()) }
        }
    }

    @Test
    fun `when unhandled exceptions are thrown then return internal server error response`() {
        val message = "Error over there"
        every { mockedLogger.error(message, any()) } just runs
        val app: Application.() -> Unit = {
            install(Koin) {
                modules(module { single { mockedLogger } })
                routing { get(ENDPOINT) { throw Exception("Error over there") } }
            }
        }

        testCoreApplication(app) {
            val response = client.get(ENDPOINT)

            Assertions.assertEquals(HttpStatusCode.InternalServerError, response.status)
            response.bodyAsText() shouldMatchJson """
            {
              "message": "A problem happened in the server. Please, try again later."
            }
            """.trimIndent()
            verify(exactly = 1) { mockedLogger.error(message, any()) }
        }
    }

    @Test
    fun `when unprocessable exceptions are thrown then return unable to process response`() {
        val app: Application.() -> Unit = {
            install(Koin) {
                modules(module { single { mockedLogger } })
                routing { get(ENDPOINT) { throw Unprocessable("Unable to process the thing") } }
            }
        }
        testCoreApplication(app) {
            val response = client.get(ENDPOINT)

            Assertions.assertEquals(HttpStatusCode.UnprocessableEntity, response.status)
            response.bodyAsText() shouldMatchJson """
            {
                "message": "The server is unable to process this request.",
                "details": {
                    "description": "Unable to process the thing"
                }
            }
            """.trimIndent()
        }
    }

    @Test
    fun `when forbidden exceptions are thrown then return Forbidden`() {
        val app: Application.() -> Unit = {
            install(Koin) {
                modules(module { single { mockedLogger } })
                routing { get(ENDPOINT) { throw UserOperationNotAllowed() } }
            }
        }
        testCoreApplication(app) {
            val response = client.get(ENDPOINT)

            Assertions.assertEquals(HttpStatusCode.Forbidden, response.status)
            response.bodyAsText() shouldMatchJson """
            {
                "message": "You cannot access the requested resource."
            }
            """.trimIndent()
        }
    }
}
