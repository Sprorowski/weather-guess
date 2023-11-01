package weather.sprorowski.io.presenter.response

import weather.sprorowski.io.domain.exception.DomainException
import weather.sprorowski.io.presenter.http.response.NotFoundExceptionResponse
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NotFoundExceptionResponseTests {
    enum class TestingCode : DomainException.DomainErrorCode { TEST }

    @Test
    fun `when exception is created then its code and error message are properly set`() {
        val exception =
            NotFoundExceptionResponse(TestingCode.TEST, "The requested resource was not found.", mapOf("id" to "123"))

        Assertions.assertEquals(HttpStatusCode.NotFound, exception.statusCode)
        Assertions.assertEquals("The requested resource was not found.", exception.message)
    }
}
