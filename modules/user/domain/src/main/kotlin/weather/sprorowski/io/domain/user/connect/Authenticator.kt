package weather.sprorowski.io.domain.user.connect

interface Authenticator {
    data class User(val email: String, val name: String)

    fun connect(idToken: String): User
}
