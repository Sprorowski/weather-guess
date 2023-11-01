package weather.sprorowski.io.presenter.http

import weather.sprorowski.io.domain.file.File
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import org.apache.tika.Tika
import java.io.File as JavaFile

suspend fun ApplicationCall.uploadFile(key: String): File {
    val parts = receiveMultipart().readAllParts()
    val part = parts.firstOrNull { it.name == key }

    if (part !is PartData.FileItem) {
        throw BadRequestException("Request is missing `$key` file")
    }

    val name = part.originalFileName!!
    val bytes = part.streamProvider().readBytes()
    val path = "/tmp/$name"
    val file = JavaFile(path)
    file.writeBytes(bytes)

    parts.forEach { it.dispose() }

    return File(
        name = name,
        path = path,
        type = Tika().detect(file),
        storage = File.Storage.LOCAL,
    )
}
