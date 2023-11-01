package weather.sprorowski.io.domain.file

import weather.sprorowski.io.domain.exception.EntityNotFoundException
import weather.sprorowski.io.domain.exception.ErrorCode
import java.time.LocalDateTime

class Document(
    val id: Id,
    val name: String,
    val file: File,
    val createdAt: LocalDateTime,
) {
    data class Id(val value: String)
    class NotFound(id: Id) :
        EntityNotFoundException("Document", mapOf("id" to id.value), ErrorCode.DOCUMENT_NOT_FOUND)

    companion object {
        fun create(id: String, name: String, file: File) = Document(
            id = Id(id),
            name = name,
            file = file,
            createdAt = LocalDateTime.now(),
        )
    }
}
