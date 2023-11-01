package weather.sprorowski.io.presenter.plugins

import weather.sprorowski.io.domain.access.Auditor
import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.presenter.inject
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

private val auditor: Auditor by inject()

private suspend fun audit(scope: Scope, call: ApplicationCall) {
    val request = call.request
    val token = (request.headers.get("Authorization") ?: "").replace("Bearer", "").trim()

    auditor.audit(token, scope, request.httpMethod.value, request.path())
}

fun Route.get(scope: Scope, run: suspend PipelineContext<Unit, ApplicationCall>.() -> Any): Route {
    return get {
        audit(scope, call)
        run()
    }
}

fun Route.get(path: String, scope: Scope, run: suspend PipelineContext<*, ApplicationCall>.() -> Any): Route {
    return get(path) {
        audit(scope, call)
        run()
    }
}

fun Route.post(scope: Scope, run: suspend PipelineContext<Unit, ApplicationCall>.() -> Any): Route {
    return post {
        audit(scope, call)
        run()
    }
}

fun Route.post(path: String, scope: Scope, run: suspend PipelineContext<Unit, ApplicationCall>.() -> Any): Route {
    return post(path) {
        audit(scope, call)
        run()
    }
}

fun Route.patch(scope: Scope, run: suspend PipelineContext<Unit, ApplicationCall>.() -> Any): Route {
    return patch {
        audit(scope, call)
        run()
    }
}

fun Route.patch(path: String, scope: Scope, run: suspend PipelineContext<Unit, ApplicationCall>.() -> Any): Route {
    return patch(path) {
        audit(scope, call)
        run()
    }
}

fun Route.put(scope: Scope, run: suspend PipelineContext<*, ApplicationCall>.() -> Any): Route {
    return put {
        audit(scope, call)
        run()
    }
}

fun Route.put(path: String, scope: Scope, run: suspend PipelineContext<*, ApplicationCall>.() -> Any): Route {
    return put(path) {
        audit(scope, call)
        run()
    }
}

fun Route.delete(scope: Scope, run: suspend PipelineContext<Unit, ApplicationCall>.() -> Any): Route {
    return delete {
        audit(scope, call)
        run()
    }
}

fun Route.delete(path: String, scope: Scope, run: suspend PipelineContext<Unit, ApplicationCall>.() -> Any): Route {
    return delete(path) {
        audit(scope, call)
        run()
    }
}
