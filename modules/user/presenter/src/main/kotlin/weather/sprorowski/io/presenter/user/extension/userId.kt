package weather.sprorowski.io.presenter.user.extension

import weather.sprorowski.io.domain.access.UserIdLoader
import weather.sprorowski.io.presenter.inject
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

private val loader: UserIdLoader by inject()

suspend fun PipelineContext<Unit, ApplicationCall>.userId(): String {
    return loader.loadByToken(call.request.headers["Authorization"])
}
