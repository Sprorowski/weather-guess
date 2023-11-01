package weather.sprorowski.io.presenter.response

import weather.sprorowski.io.presenter.http.response.InternalServerErrorExceptionResponse
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class InternalServerErrorExceptionResponseTests {
    @Test
    fun `when exception is created then its code and error message are properly set`() {
        val exception = InternalServerErrorExceptionResponse()

        Assertions.assertEquals(HttpStatusCode.InternalServerError, exception.statusCode)
        Assertions.assertEquals("A problem happened in the server. Please, try again later.", exception.message)
    }
}
