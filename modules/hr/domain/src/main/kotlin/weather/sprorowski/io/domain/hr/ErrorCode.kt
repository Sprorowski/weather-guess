package weather.sprorowski.io.domain.hr

import weather.sprorowski.io.domain.exception.DomainException

enum class ErrorCode : DomainException.DomainErrorCode {
    STIPEND_NOT_FOUND,
}
