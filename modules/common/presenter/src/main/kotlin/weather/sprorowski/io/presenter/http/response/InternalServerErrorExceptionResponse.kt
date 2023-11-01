package weather.sprorowski.io.presenter.http.response

import io.ktor.http.HttpStatusCode

class InternalServerErrorExceptionResponse : ExceptionResponse(
    statusCode = HttpStatusCode.InternalServerError,
    message = "A problem happened in the server. Please, try again later.",
)
