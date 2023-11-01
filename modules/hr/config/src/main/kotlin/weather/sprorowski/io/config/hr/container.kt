package weather.sprorowski.io.config.hr

import weather.sprorowski.io.application.hr.employee.stipend.create.CreateStipendCommandHandler
import weather.sprorowski.io.application.hr.employee.stipend.get.GetStipendQueryHandler
import weather.sprorowski.io.application.hr.employee.stipend.list.ListStipendsQueryHandler
import weather.sprorowski.io.application.hr.employee.stipend.create.document.AttachExpenseDocumentCommandHandler
import weather.sprorowski.io.domain.hr.employee.stipend.Stipends
import weather.sprorowski.io.infrastructure.hr.data.repositories.employee.stipend.InMemoryStipends
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
