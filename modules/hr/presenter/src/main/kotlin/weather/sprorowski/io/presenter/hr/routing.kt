package weather.sprorowski.io.presenter.hr

import weather.sprorowski.io.application.hr.employee.stipend.create.CreateStipendCommand
import weather.sprorowski.io.application.hr.employee.stipend.create.CreateStipendCommandHandler
import weather.sprorowski.io.application.hr.employee.stipend.create.document.AttachExpenseDocumentCommand
import weather.sprorowski.io.application.hr.employee.stipend.create.document.AttachExpenseDocumentCommandHandler
import weather.sprorowski.io.application.hr.employee.stipend.get.GetStipendQuery
import weather.sprorowski.io.application.hr.employee.stipend.get.GetStipendQueryHandler
import weather.sprorowski.io.application.hr.employee.stipend.list.ListStipendsQuery
import weather.sprorowski.io.application.hr.employee.stipend.list.ListStipendsQueryHandler
import weather.sprorowski.io.domain.hr.employee.stipend.Stipend
import weather.sprorowski.io.presenter.http.uploadFile
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.hrRoutes() {
    val creator: CreateStipendCommandHandler by inject()
    val createDocument: AttachExpenseDocumentCommandHandler by inject()
    val getStipends: ListStipendsQueryHandler by inject()
    val getStipend: GetStipendQueryHandler by inject()

    route("/hr") {
        route("/employees") {
            route("/stipends") {
                get {
                    val query = ListStipendsQuery(call.request.queryParameters["page"]?.toInt() ?: 1)
                    val stipends = getStipends.handle(query)

                    call.respond(HttpStatusCode.OK, stipends)
                }

                route("/{stipendId}") {
                    get {
                        val query = GetStipendQuery(Stipend.Id(call.parameters["stipendId"] ?: ""))
                        val stipend = getStipend.handle(query)

                        call.respond(HttpStatusCode.OK, stipend)
                    }
                    post("/documents") {
                        val command = AttachExpenseDocumentCommand(call.uploadFile("file"), Stipend.Id(call.parameters["stipendId"] ?: ""))
                        createDocument.handle(command)
                        call.respond(HttpStatusCode.OK)
                    }
                }

                post {
                    val command = call.receive<CreateStipendCommand>()
                    creator.handle(command)

                    call.respond(status = HttpStatusCode.Created, "")
                }
            }
        }
    }
}
