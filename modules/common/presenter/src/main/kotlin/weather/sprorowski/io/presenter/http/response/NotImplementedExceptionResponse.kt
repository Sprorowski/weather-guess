package weather.sprorowski.io.presenter.http.response

import io.ktor.http.HttpStatusCode

class NotImplementedExceptionResponse(
    message: String,
) : ExceptionResponse(HttpStatusCode.NotImplemented, message)
