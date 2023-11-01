package weather.sprorowski.io.presenter.plugins

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import java.text.DateFormat

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            dateFormat = DateFormat.getDateInstance()
            // enable(SerializationFeature.INDENT_OUTPUT)
            // disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)

            registerModules(
                JavaTimeModule(),
                kotlinModule(),
            )
        }
    }
}
