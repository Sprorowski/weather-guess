package weather.sprorowski.io.infrastructure.http

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class HcJacksonHttpClient(val client: HcHttpClient) {
    class Response(val status: HttpStatusCode, val raw: String) {
        inline fun <reified T> body(): T = mapper.readValue(raw, jacksonTypeRef<T>())
    }

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        fun create(domain: String): HcJacksonHttpClient = HcJacksonHttpClient(
            HcHttpClient(domain, KtorHttpClientContext.create()),
        )
    }

    suspend inline fun get(
        endpoint: String,
        query: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf(),
    ): Response = parseResponse(client.get(endpoint, query, headers))

    suspend inline fun post(
        endpoint: String,
        payload: Any = emptyMap<String, Any>(),
        headers: Map<String, String> = mapOf(),
    ): Response = parseResponse(client.post(endpoint, payload, headers))

    suspend inline fun patch(
        endpoint: String,
        payload: Any = emptyMap<String, Any>(),
        headers: Map<String, String> = mapOf(),
    ): Response = parseResponse(client.patch(endpoint, payload, headers))

    suspend inline fun put(
        endpoint: String,
        payload: Any = emptyMap<String, Any>(),
        headers: Map<String, String> = mapOf(),
    ): Response = parseResponse(client.put(endpoint, payload, headers))

    suspend inline fun parseResponse(
        response: HttpResponse,
    ): Response = Response(status = response.status, raw = response.bodyAsText())
}
