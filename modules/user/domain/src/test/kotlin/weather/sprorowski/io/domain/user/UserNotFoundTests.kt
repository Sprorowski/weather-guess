package weather.sprorowski.io.domain.user

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserNotFoundTests {
    @Test
    fun `create not found exception`() {
        val exception = User.NotFound()

        Assertions.assertEquals("User Not Found", exception.message)
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, exception.code)
        Assertions.assertEquals(null, exception.details)
    }
}
