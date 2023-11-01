package weather.sprorowski.io.presenter.game

import weather.sprorowski.io.application.game.employee.stipend.create.CreateStipendCommandHandler
import weather.sprorowski.io.application.game.employee.stipend.create.document.AttachExpenseDocumentCommandHandler
import weather.sprorowski.io.application.game.employee.stipend.get.GetStipendQueryHandler
import weather.sprorowski.io.application.game.employee.stipend.list.ListStipendsQueryHandler
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.gameRoutes() {
    val creator: CreateStipendCommandHandler by inject()
    val createDocument: AttachExpenseDocumentCommandHandler by inject()
    val getStipends: ListStipendsQueryHandler by inject()
    val getStipend: GetStipendQueryHandler by inject()

    route("/game") {
        get {
            call.respond(HttpStatusCode.OK, "hello")
        }
    }


}
