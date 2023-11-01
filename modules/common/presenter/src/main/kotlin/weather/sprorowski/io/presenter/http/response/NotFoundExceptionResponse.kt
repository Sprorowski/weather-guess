package weather.sprorowski.io.presenter.http.response

import weather.sprorowski.io.domain.exception.DomainException
import io.ktor.http.HttpStatusCode

class NotFoundExceptionResponse(
    val code: DomainException.DomainErrorCode,
    message: String,
    details: Map<String, String>? = null,
) : ExceptionResponse(HttpStatusCode.NotFound, message, details)
