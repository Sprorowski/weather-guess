package weather.sprorowski.io.domain.user.access

interface UserAccesses {
    suspend fun loadNonRevoked(id: String): UserAccess
    suspend fun register(access: UserAccess)
    suspend fun revoke(access: UserAccess)
}
