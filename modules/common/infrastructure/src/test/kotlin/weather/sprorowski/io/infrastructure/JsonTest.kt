package weather.sprorowski.io.infrastructure

import io.kotest.assertions.json.shouldMatchJson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class JsonTest {
    data class FooBar(val foo: String, val bar: Int)

    companion object {
        private val JSON_CONTENT = """
            {
              "foo": "Foo Value",
              "bar": 10
            }
        """.trimIndent()
    }

    @Test
    fun `given a json then decode into an object`() {
        val content = JSON_CONTENT

        val fooBar = Json.decode<FooBar>(content)

        Assertions.assertEquals(FooBar("Foo Value", 10), fooBar)
    }

    @Test
    fun `given a data class then encode into an json string`() {
        val fooBar = FooBar("Foo Value", 10)

        val content = Json.encode(fooBar)

        content shouldMatchJson JSON_CONTENT
    }
}
