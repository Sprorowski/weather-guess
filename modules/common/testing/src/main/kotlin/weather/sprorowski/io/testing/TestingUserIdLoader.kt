package weather.sprorowski.io.testing

import weather.sprorowski.io.domain.extension.onNull
import weather.sprorowski.io.domain.access.UserIdLoader
import weather.sprorowski.io.domain.access.exception.UserUnauthorized

class TestingUserIdLoader : UserIdLoader {
    override suspend fun loadByToken(token: String?): String {
        token.onNull { throw UserUnauthorized("Missing Bearer Token") }

        return "user-xxx"
    }
}
