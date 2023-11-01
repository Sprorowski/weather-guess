package weather.sprorowski.io.infrastructure.http

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.call.body
import io.ktor.client.call.body
import io.ktor.client.call.body
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.client.HttpClient as KtorHttpClient

class HcHttpClient(val domain: String, val client: KtorHttpClient) {
    suspend fun get(
        endpoint: String,
        query: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf(),
    ): HttpResponse = client.get("${domain}$endpoint") {
        query.forEach { parameter(it.key, it.value) }
        headers.forEach { header(it.key, it.value) }
    }

    suspend fun post(
        endpoint: String,
        payload: Any = emptyMap<String, Any>(),
        headers: Map<String, String> = mapOf(),
    ): HttpResponse = client.post("${domain}$endpoint") { configureJsonRequest(payload, headers) }

    suspend fun patch(
        endpoint: String,
        payload: Any = emptyMap<String, Any>(),
        headers: Map<String, String> = mapOf(),
    ): HttpResponse {
        return client.patch("${domain}$endpoint") { configureJsonRequest(payload, headers) }
    }

    suspend fun put(
        endpoint: String,
        payload: Any = emptyMap<String, Any>(),
        headers: Map<String, String> = mapOf(),
    ): HttpResponse {
        return client.put("${domain}$endpoint") { configureJsonRequest(payload, headers) }
    }

    private fun HttpRequestBuilder.configureJsonRequest(payload: Any, headers: Map<String, String> = mapOf()) {
        setBody(jacksonObjectMapper().writeValueAsString(payload))

        contentType(ContentType.Application.Json)
        headers {
            append(HttpHeaders.Accept, "application/json")
            append(HttpHeaders.ContentType, "application/json")
        }

        headers.forEach { header(it.key, it.value) }
    }
}
