package weather.sprorowski.io.presenter.http.response

import io.ktor.http.HttpStatusCode

class ForbiddenExceptionResponse : ExceptionResponse(
    statusCode = HttpStatusCode.Forbidden,
    message = "You cannot access the requested resource.",
)
