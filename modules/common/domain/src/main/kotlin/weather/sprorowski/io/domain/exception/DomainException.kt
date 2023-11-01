package weather.sprorowski.io.domain.exception

open class DomainException(
    message: String,
    val details: Map<String, String>? = null,
) : Exception(message) {
    interface DomainErrorCode
}
