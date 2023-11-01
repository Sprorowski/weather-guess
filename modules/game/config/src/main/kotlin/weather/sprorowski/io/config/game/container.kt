package weather.sprorowski.io.config.game

import io.ktor.server.config.*
import org.koin.dsl.module

@Suppress("UNUSED_PARAMETER")
fun container(config: ApplicationConfig) = module {
    // configs
    // ...

    // infrastructure:orm:tables
    // ...

    // domain:repositories

    // domain:services
    // ...

    // application:handlers
}
