package weather.sprorowski.io.config.game

import weather.sprorowski.io.application.game.employee.stipend.create.CreateStipendCommandHandler
import weather.sprorowski.io.application.game.employee.stipend.get.GetStipendQueryHandler
import weather.sprorowski.io.application.game.employee.stipend.list.ListStipendsQueryHandler
import weather.sprorowski.io.application.game.employee.stipend.create.document.AttachExpenseDocumentCommandHandler
import weather.sprorowski.io.domain.game.employee.stipend.Stipends
import weather.sprorowski.io.infrastructure.game.data.repositories.employee.stipend.InMemoryStipends
import io.ktor.server.config.*
import org.koin.dsl.module

@Suppress("UNUSED_PARAMETER")
fun container(config: ApplicationConfig) = module {
    // configs
    // ...

    // infrastructure:orm:tables
    // ...

    // domain:repositories
    single<Stipends> { InMemoryStipends() }

    // domain:services
    // ...

    // application:handlers
    single { CreateStipendCommandHandler(get()) }
    single { ListStipendsQueryHandler(get()) }
    single { GetStipendQueryHandler(get()) }
    single { AttachExpenseDocumentCommandHandler(get(), get()) }
}
