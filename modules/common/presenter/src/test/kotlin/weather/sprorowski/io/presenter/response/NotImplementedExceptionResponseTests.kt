package weather.sprorowski.io.presenter.response

import weather.sprorowski.io.presenter.http.response.NotImplementedExceptionResponse
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NotImplementedExceptionResponseTests {
    @Test
    fun `when exception is created then its code and error message are properly set`() {
        val exception = NotImplementedExceptionResponse("The requested resource is not implemented.")

        Assertions.assertEquals(HttpStatusCode.NotImplemented, exception.statusCode)
        Assertions.assertEquals("The requested resource is not implemented.", exception.message)
    }
}
