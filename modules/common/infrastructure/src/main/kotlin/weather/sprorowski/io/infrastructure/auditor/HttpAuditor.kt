package weather.sprorowski.io.infrastructure.auditor

import weather.sprorowski.io.domain.access.exception.UserOperationNotAllowed
import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.domain.access.Auditor
import weather.sprorowski.io.infrastructure.table.AccessAuditTable
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.Claim
import java.time.LocalDateTime

class HttpAuditor(private val table: AccessAuditTable) : Auditor {
    override suspend fun audit(token: String, scope: Scope, method: String, path: String) {
        val jwt = JWT.decode(token.replace("Bearer ", ""))
        val id = jwt.claims["id"]?.asString()
        val scopes: List<String> = jwt.claims["scopes"]?.scopes() ?: emptyList()
        val access = when (scopes.contains(scope.scope)) {
            true -> AccessAuditTable.Access.ALLOWED
            else -> AccessAuditTable.Access.DENIED
        }

        table.insert(
            AccessAuditTable.Model(
                accessId = id,
                access = access,
                scope = scope,
                method = method,
                path = path,
                createdAt = LocalDateTime.now(),
            ),
        )

        if (access == AccessAuditTable.Access.DENIED) {
            throw UserOperationNotAllowed()
        }
    }

    private fun Claim.scopes(): List<String> = asList(String::class.java)
}
