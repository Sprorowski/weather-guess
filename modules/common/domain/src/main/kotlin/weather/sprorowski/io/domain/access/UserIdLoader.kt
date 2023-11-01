package weather.sprorowski.io.domain.access

interface UserIdLoader {
    suspend fun loadByToken(token: String?): String
}
