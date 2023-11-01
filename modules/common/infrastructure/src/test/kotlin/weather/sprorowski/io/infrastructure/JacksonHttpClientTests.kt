package weather.sprorowski.io.infrastructure

import weather.sprorowski.io.infrastructure.http.HcHttpClient
import weather.sprorowski.io.infrastructure.http.HcJacksonHttpClient
import weather.sprorowski.io.testing.assertHttpRequest
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

private data class Auth(val token: String)

class JacksonHttpClientTests {
    companion object {
        private const val DOMAIN = "https://weather.sprorowski.dev"
        private const val ENDPOINT = "/foo-bar"
        private const val TOKEN = "token-xxx"
    }

    private val engine = MockEngine { respondOk("""{"token":"$TOKEN"}""") }
    private val headers = mutableMapOf("header" to "value")
    private val client = HcJacksonHttpClient(HcHttpClient(DOMAIN, HttpClient(engine)))
    private val expectedHeaders = mutableMapOf(
        "header" to listOf("value"),
        "Accept-Charset" to listOf("UTF-8"),
        "Accept" to listOf("application/json"),
    )

    @Test
    fun `run get request with query string and headers and parse response into given object T`() {
        val query = mapOf("query" to "params")

        val response = runBlocking { client.get(ENDPOINT, query, headers) }

        Assertions.assertEquals(Auth(token = TOKEN), response.body<Auth>())
        Assertions.assertEquals(1, engine.requestHistory.count())
        assertHttpRequest(
            request = engine.requestHistory[0],
            expectedMethod = HttpMethod.Get,
            expectedUrl = "$DOMAIN$ENDPOINT?query=params",
            expectedHeaders = (expectedHeaders + mutableMapOf("Accept" to listOf("*/*"))) as MutableMap<String, List<String>>,
            expectedOutgoingContent = EmptyContent,
        )
    }

    @Test
    fun `run post request with payload and headers and parse response into given object T`() {
        val body = mapOf("body" to "post content body")

        val response = runBlocking { client.post(ENDPOINT, body, headers) }

        Assertions.assertEquals(Auth(token = TOKEN), response.body<Auth>())
        Assertions.assertEquals(1, engine.requestHistory.count())
        assertHttpRequest(
            request = engine.requestHistory[0],
            expectedMethod = HttpMethod.Post,
            expectedUrl = "$DOMAIN$ENDPOINT",
            expectedHeaders = expectedHeaders,
            expectedOutgoingContent = TextContent("""{"body":"post content body"}""", ContentType.Application.Json),
        )
    }

    @Test
    fun `run patch request with payload and headers and parse response into given object T`() {
        val body = mapOf("body" to "patch content body")

        val response = runBlocking { client.patch(ENDPOINT, body, headers) }

        Assertions.assertEquals(Auth(token = TOKEN), response.body<Auth>())
        Assertions.assertEquals(1, engine.requestHistory.count())
        assertHttpRequest(
            request = engine.requestHistory[0],
            expectedMethod = HttpMethod.Patch,
            expectedUrl = "$DOMAIN$ENDPOINT",
            expectedHeaders = expectedHeaders,
            expectedOutgoingContent = TextContent("""{"body":"patch content body"}""", ContentType.Application.Json),
        )
    }

    @Test
    fun `run put request with payload and headers and parse response into given object T`() {
        val body = mapOf("body" to "put content body")

        val response = runBlocking { client.put(ENDPOINT, body, headers) }

        Assertions.assertEquals(Auth(token = TOKEN), response.body<Auth>())
        Assertions.assertEquals(1, engine.requestHistory.count())
        assertHttpRequest(
            request = engine.requestHistory[0],
            expectedMethod = HttpMethod.Put,
            expectedUrl = "$DOMAIN$ENDPOINT",
            expectedHeaders = expectedHeaders,
            expectedOutgoingContent = TextContent("""{"body":"put content body"}""", ContentType.Application.Json),
        )
    }
}
