package weather.sprorowski.io.testing

import weather.sprorowski.io.domain.access.exception.UserOperationNotAllowed
import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.domain.access.Auditor
import weather.sprorowski.io.domain.access.exception.UserUnauthorized
import com.auth0.jwt.JWT

class TestingAuditor : Auditor {
    override suspend fun audit(token: String, scope: Scope, method: String, path: String) {
        if (token == "") throw UserUnauthorized("Missing Bearer Token")

        JWT.decode(token)
            .getClaim("scopes")
            .asList(String::class.java)
            .firstOrNull { it == scope.scope } ?: throw UserOperationNotAllowed()
    }
}

// class TestingUserLoader : UserIdLoader {
//    override suspend fun loadByToken(token: String?): String {
//        token.onNull { throw UnauthorizedException("Missing Bearer Token") }
//
//        return "user-xxx"
//    }
// }
