package weather.sprorowski.io.infrastructure.storage

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import weather.sprorowski.io.domain.file.Storage
import weather.sprorowski.io.domain.file.File

class AwsStorage(
    private val reader: FileReader,
    private val s3: S3Client,
    private val s3bucket: String,
) : Storage {
    override suspend fun store(path: String, name: String, file: File): File {
        val location = "$path/$name.${file.extension()}"

        s3.putObject(
            PutObjectRequest {
                bucket = s3bucket
                key = location
                body = reader.read(file)
            },
        )

        return File(
            name = file.name,
            path = location,
            type = file.type,
            storage = File.Storage.AWS_S3,
        )
    }
}
