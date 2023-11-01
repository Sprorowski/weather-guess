package weather.sprorowski.io.domain.exception

abstract class EntityNotFoundException : DomainException {
    val code: DomainErrorCode

    constructor(entity: String, details: Map<String, String>?, code: DomainErrorCode) : super("$entity Not Found", details) {
        this.code = code
    }

    constructor(message: String, code: DomainErrorCode) : super(message, emptyMap()) {
        this.code = code
    }
}
