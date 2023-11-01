package weather.sprorowski.io.presenter.http.response

import weather.sprorowski.io.domain.exception.BusinessRuleException
import io.ktor.http.HttpStatusCode

class UnprocessableEntityExceptionResponse(cause: BusinessRuleException) : ExceptionResponse(
    statusCode = HttpStatusCode.UnprocessableEntity,
    message = "The server is unable to process this request.",
    details = cause.details,
)
