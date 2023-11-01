package weather.sprorowski.io.presenter.response

import weather.sprorowski.io.presenter.http.response.UnauthorizedExceptionResponse
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UnauthorizedExceptionResponseTests {
    @Test
    fun `when exception is created then its code and error message are properly set`() {
        val exception = UnauthorizedExceptionResponse("The thing was not authorized")

        Assertions.assertEquals(HttpStatusCode.Unauthorized, exception.statusCode)
        Assertions.assertEquals("The thing was not authorized", exception.message)
    }
}
