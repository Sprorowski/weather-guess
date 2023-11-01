package weather.sprorowski.io.presenter.http.response

import io.ktor.http.HttpStatusCode

class UnauthorizedExceptionResponse(message: String) : ExceptionResponse(
    statusCode = HttpStatusCode.Unauthorized,
    message = message,
)
