package weather.sprorowski.io.domain.user

import weather.sprorowski.io.domain.exception.DomainException

enum class ErrorCode : DomainException.DomainErrorCode {
    USER_NOT_FOUND,
}
