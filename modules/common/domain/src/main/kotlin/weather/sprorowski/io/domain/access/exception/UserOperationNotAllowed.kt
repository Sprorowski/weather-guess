package weather.sprorowski.io.domain.access.exception

import weather.sprorowski.io.domain.exception.BusinessRuleException

class UserOperationNotAllowed : BusinessRuleException("Operation Not Allowed")
