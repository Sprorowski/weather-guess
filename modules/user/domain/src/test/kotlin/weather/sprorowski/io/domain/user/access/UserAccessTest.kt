package weather.sprorowski.io.domain.user.access

import weather.sprorowski.io.testing.user.spawner.UserAccessSpawner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserAccessTest {
    @Test
    fun `given an access token when revoke is called then revoked at is not null`() {
        val accessToken = UserAccessSpawner.arthurDentAccess()
        assert(accessToken.revokedAt == null)

        accessToken.revoke()

        Assertions.assertNotNull(accessToken.revokedAt)
    }
}
