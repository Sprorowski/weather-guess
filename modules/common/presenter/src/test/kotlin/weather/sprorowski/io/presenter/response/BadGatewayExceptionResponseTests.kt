package weather.sprorowski.io.presenter.response

import weather.sprorowski.io.presenter.http.response.BadGatewayExceptionResponse
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BadGatewayExceptionResponseTests {
    @Test
    fun `when exception is created then its code and error message are properly set`() {
        val exception = BadGatewayExceptionResponse()

        Assertions.assertEquals(HttpStatusCode.BadGateway, exception.statusCode)
        Assertions.assertEquals("There was a problem with an upstream server.", exception.message)
    }
}
