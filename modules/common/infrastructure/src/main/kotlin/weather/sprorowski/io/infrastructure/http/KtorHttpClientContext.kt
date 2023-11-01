package weather.sprorowski.io.infrastructure.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

class KtorHttpClientContext {
    companion object {
        fun create(): HttpClient {
            return HttpClient(CIO) {
                expectSuccess = true
            }
        }
    }
}
