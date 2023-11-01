package weather.sprorowski.io.plugins.cors

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class CorsConfig {
    companion object {
        private val numberRegex = "[0-9]+".toRegex()
    }

    val hosts: MutableSet<String> = HashSet()
    val headers: MutableSet<String> = mutableSetOf(HttpHeaders.ContentType, HttpHeaders.Authorization)
    val methods: MutableSet<HttpMethod> = mutableSetOf(HttpMethod.Get, HttpMethod.Post)

    fun host(host: String, schemes: List<String> = listOf("http")) = schemes.forEach { hosts.add("$it://$host") }
    fun header(header: String) = headers.add(header)
    fun method(method: HttpMethod) = methods.add(method)

    fun normalizeHost(origin: String) = when (origin) {
        "null", "*" -> origin
        else -> StringBuilder(origin.length).apply {
            append(origin)

            if (!origin.substringAfterLast(":", "").matches(numberRegex)) {
                when (origin.substringBefore(':')) {
                    "http" -> append(":80")
                    "https" -> append(":443")
                }
            }
        }.toString()
    }
}

val cors = createRouteScopedPlugin("CORS", ::CorsConfig) {
    val normalizedHosts = HashSet<String>(pluginConfig.hosts.map { pluginConfig.normalizeHost(it) })

    onCall { call ->
        // Note: Cors setup gets skipped whenever request has no origin
        val origin = call.request.headers.getAll(HttpHeaders.Origin)?.singleOrNull() ?: return@onCall
        val validOrigin = pluginConfig.normalizeHost(origin) in normalizedHosts

        when {
            !validOrigin -> {
                // val allowed = normalizedHosts.joinToString(", ")
                // logger.warn("CORS: origin `$origin` is not within the allowed list: $allowed")
                call.respond(HttpStatusCode.Forbidden, mapOf("message" to "Unknown origin"))
            }

            else -> {
                val headers = call.response.headers

                headers.append(HttpHeaders.AccessControlAllowOrigin, origin)
                headers.append(HttpHeaders.AccessControlAllowCredentials, "true")
                headers.append(HttpHeaders.AccessControlAllowHeaders, pluginConfig.headers.joinToString(", "))
                headers.append(
                    HttpHeaders.AccessControlAllowMethods,
                    pluginConfig.methods.joinToString(", ") { it.value })
                headers.append(HttpHeaders.AccessControlMaxAge, "600")

                if (call.request.httpMethod == HttpMethod.Options) {
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}
