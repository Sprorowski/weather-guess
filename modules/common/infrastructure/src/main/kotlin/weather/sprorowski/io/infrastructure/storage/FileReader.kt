package weather.sprorowski.io.infrastructure.storage

import aws.smithy.kotlin.runtime.content.ByteStream
import weather.sprorowski.io.domain.file.File
import java.io.File as JavaFile

class FileReader {
    fun read(file: File) = ByteStream.fromBytes(JavaFile(file.path).readBytes())
}
