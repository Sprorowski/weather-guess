package weather.sprorowski.io.presenter.response

import weather.sprorowski.io.presenter.http.response.BadRequestExceptionResponse
import io.ktor.http.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BadRequestExceptionResponseTests {
    @Test
    fun `when no message is given then create with a default message`() {
        val exception = BadRequestExceptionResponse(null)

        Assertions.assertEquals(HttpStatusCode.BadRequest, exception.statusCode)
        Assertions.assertEquals("Invalid request", exception.message)
    }

    @Test
    fun `when a message is given then create with the given message`() {
        val exception = BadRequestExceptionResponse("The field `foo bar` is invalid")

        Assertions.assertEquals(HttpStatusCode.BadRequest, exception.statusCode)
        Assertions.assertEquals("The field `foo bar` is invalid", exception.message)
    }
}
