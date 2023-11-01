package weather.sprorowski.io.presenter.http.response

import io.ktor.http.HttpStatusCode

class BadRequestExceptionResponse(message: String?) : ExceptionResponse(
    statusCode = HttpStatusCode.BadRequest,
    message = message ?: "Invalid request",
)
