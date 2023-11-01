package weather.sprorowski.io.presenter.response

import weather.sprorowski.io.domain.exception.BusinessRuleException
import weather.sprorowski.io.presenter.http.response.UnprocessableEntityExceptionResponse
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UnprocessableEntityExceptionResponseTests {
    class TestException : BusinessRuleException("test failed")

    @Test
    fun `when exception is created then its code and error message are properly set`() {
        val exception = UnprocessableEntityExceptionResponse(TestException())

        Assertions.assertEquals(HttpStatusCode.UnprocessableEntity, exception.statusCode)
        Assertions.assertEquals("The server is unable to process this request.", exception.message)
    }
}
