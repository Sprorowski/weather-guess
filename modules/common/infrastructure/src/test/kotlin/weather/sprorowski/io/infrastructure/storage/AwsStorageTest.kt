package weather.sprorowski.io.infrastructure.storage

import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.content.ByteStream
import weather.sprorowski.io.testing.FileSpawner
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AwsStorageTest {
    private val s3: S3Client = mockk()
    private val bucket = "aaa-bbb"
    private val fileReader: FileReader = mockk()
    private val bytes: ByteStream = mockk()

    private val awsStorage = AwsStorage(fileReader, s3, bucket)

    private val notaFiscalFile = FileSpawner.notaFiscalFile()

    @Test
    fun `given a file stored in local when putObject is called then return a file stored in AWS_S3`() = runBlocking {
        coEvery { s3.putObject(any()) } returns mockk()
        coEvery { fileReader.read(any()) } returns bytes

        awsStorage.store(path = "/foo/bar", name = "aaa-bbb-ccc", file = notaFiscalFile)

        coVerify(exactly = 1) {
            s3.putObject(
                match {
                    Assertions.assertEquals(
                        listOf(bucket, "/foo/bar/aaa-bbb-ccc.pdf", bytes),
                        listOf(it.bucket, it.key, it.body),
                    )

                    true
                },
            )
        }
        coVerify(exactly = 1) { fileReader.read(notaFiscalFile) }
    }
}
