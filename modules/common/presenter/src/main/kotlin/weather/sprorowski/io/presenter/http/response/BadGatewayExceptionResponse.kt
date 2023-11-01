package weather.sprorowski.io.presenter.http.response

import io.ktor.http.HttpStatusCode

class BadGatewayExceptionResponse : ExceptionResponse(
    statusCode = HttpStatusCode.BadGateway,
    message = "There was a problem with an upstream server.",
)
