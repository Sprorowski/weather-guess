package weather.sprorowski.io.domain.extension

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NullExtensionTest {
    @Test
    fun `given a null value then run lambda`() = runBlocking {
        var value = "foo"

        null.onNull { value = "bar" }

        Assertions.assertEquals("bar", value)
    }

    @Test
    fun `given onTrue extension when boolean is false then do not run lambda`() = runBlocking {
        var value = "foo"

        "".onNull { value = "bar" }

        Assertions.assertEquals("foo", value)
    }
}
