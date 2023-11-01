package weather.sprorowski.io.config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import weather.sprorowski.io.domain.file.Storage
import weather.sprorowski.io.domain.access.Auditor
import weather.sprorowski.io.infrastructure.auditor.HttpAuditor
import weather.sprorowski.io.infrastructure.storage.AwsStorage
import weather.sprorowski.io.infrastructure.storage.FileReader
import weather.sprorowski.io.infrastructure.table.AccessAuditTable
import io.ktor.server.config.ApplicationConfig
import io.tcds.orm.connection.Connection
import io.tcds.orm.connection.NestedTransactionConnection
import mu.KotlinLogging
import org.koin.dsl.module
import org.slf4j.Logger
import java.sql.DriverManager

fun container(config: ApplicationConfig) = module {
    val jdbcReadUrl = config.property("database.orm.jdbcReadUrl").getString()
    val jdbcWriteUrl = config.property("database.orm.jdbcWriteUrl").getString()
    val read = DriverManager.getConnection(jdbcReadUrl)
    val write = DriverManager.getConnection(jdbcWriteUrl)

    val s3Client = S3Client {
        region = config.property("aws.s3.region").getString()
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = config.property("aws.s3.accessKeyId").getString()
            secretAccessKey = config.property("aws.s3.secretAccessKey").getString()
        }
    }
    val s3Bucket = config.property("aws.s3.bucket").getString()

    single<Logger> { KotlinLogging.logger { } }
    single<Connection> { NestedTransactionConnection(read, write, get()) }

    single<Storage> { AwsStorage(get(), s3Client, s3Bucket) }
    single<Auditor> { HttpAuditor(get()) }

    single { FileReader() }
    single { AccessAuditTable(get()) }
}
