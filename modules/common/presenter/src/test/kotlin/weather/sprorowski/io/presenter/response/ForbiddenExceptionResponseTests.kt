package weather.sprorowski.io.presenter.response

import weather.sprorowski.io.presenter.http.response.ForbiddenExceptionResponse
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ForbiddenExceptionResponseTests {
    @Test
    fun `when exception is created then its code and error message are properly set`() {
        val exception = ForbiddenExceptionResponse()

        Assertions.assertEquals(HttpStatusCode.Forbidden, exception.statusCode)
        Assertions.assertEquals("You cannot access the requested resource.", exception.message)
    }
}
