package weather.sprorowski.io.domain.access

interface Auditor {
    suspend fun audit(token: String, scope: Scope, method: String, path: String)
}
