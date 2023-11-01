package weather.sprorowski.io.plugins

import weather.sprorowski.io.plugins.cors.cors
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig

fun Application.configureHttp(config: ApplicationConfig) {
    install(cors) {
        val allowedHosts = config.property("security.cors.hosts").getString().split(",")

        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Patch)
        method(HttpMethod.Options)
        method(HttpMethod.Delete)

        header(HttpHeaders.ContentType)
        header(HttpHeaders.Authorization)

        allowedHosts.forEach { host(it, listOf("http", "https")) }
    }
}
