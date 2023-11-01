package weather.sprorowski.io.testing

import weather.sprorowski.io.infrastructure.http.HcHttpClient
import weather.sprorowski.io.infrastructure.http.HcJacksonHttpClient
import io.kotest.assertions.json.shouldMatchJson
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpMethod
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.util.toMap
import org.junit.jupiter.api.Assertions

const val testingDomain = "https://testing-domain.log.dev"
fun testClient(engine: MockEngine) = HcHttpClient(testingDomain, HttpClient(engine))
fun jacksonTestClient(engine: MockEngine) = HcJacksonHttpClient(testClient(engine))

fun assertHttpRequest(
    request: HttpRequestData,
    expectedMethod: HttpMethod,
    expectedUrl: String,
    expectedOutgoingContent: OutgoingContent,
    expectedHeaders: Map<String, List<String>>,
) {
    Assertions.assertEquals(expectedMethod, request.method)
    Assertions.assertEquals(expectedUrl, request.url.toString())
    Assertions.assertEquals(expectedOutgoingContent.toString(), request.body.toString())
    Assertions.assertEquals(expectedHeaders, request.headers.toMap())
}

fun assertHttpRequest(
    request: HttpRequestData,
    expectedMethod: HttpMethod,
    expectedUrl: String,
    expectedJsonBody: String,
    expectedHeaders: Map<String, List<String>>,
) {
    Assertions.assertEquals(expectedMethod, request.method, "Expected method failed")
    Assertions.assertEquals(expectedUrl, request.url.toString(), "Expected url failed")
    (request.body as TextContent).text shouldMatchJson expectedJsonBody
    Assertions.assertEquals(expectedHeaders, request.headers.toMap(), "Expected headers failed")
}
