package weather.sprorowski.io.domain.extension

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StringExtensionTest {
    companion object {
        private const val EXPECTED = "123456abcdef987654qwerty"
    }

    @Test
    fun `given a value with spaces when trim is called then return inline`() {
        val value = """
            123456
            abcdef
            987654
            qwerty
        """

        val trimmed = value.trimLines()

        Assertions.assertEquals(EXPECTED, trimmed)
    }
}
