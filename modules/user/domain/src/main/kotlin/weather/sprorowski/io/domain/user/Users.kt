package weather.sprorowski.io.domain.user

interface Users {
    suspend fun loadById(id: String): User
    suspend fun findByEmail(email: String): User?
    suspend fun exists(email: String): Boolean
    suspend fun register(user: User)
}
