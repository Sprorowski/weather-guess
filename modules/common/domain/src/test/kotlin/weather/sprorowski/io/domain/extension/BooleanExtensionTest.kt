package weather.sprorowski.io.domain.extension

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BooleanExtensionTest {
    @Test
    fun `given onFalse extension when boolean is false then run lambda`() {
        var value = "foo"

        false.onFalse { value = "bar" }

        Assertions.assertEquals("bar", value)
    }

    @Test
    fun `given onFalse extension when boolean is true then do not run lambda`() {
        var value = "foo"

        true.onFalse { value = "bar" }

        Assertions.assertEquals("foo", value)
    }

    @Test
    fun `given onTrue extension when boolean is true then run lambda`() {
        var value = "foo"

        true.onTrue { value = "bar" }

        Assertions.assertEquals("bar", value)
    }

    @Test
    fun `given onTrue extension when boolean is false then do not run lambda`() {
        var value = "foo"

        false.onTrue { value = "bar" }

        Assertions.assertEquals("foo", value)
    }
}
