package weather.sprorowski.io.domain.exception

abstract class BusinessRuleException(
    description: String,
) : DomainException("Unable to Continue", mapOf("description" to description))
