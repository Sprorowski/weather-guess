package weather.sprorowski.io.testing

import io.ktor.server.config.MapApplicationConfig

val applicationConfig = MapApplicationConfig(

    "aws.s3.region" to "aws-testing-region",
    "aws.s3.accessKeyId" to "aws-testing-key",
    "aws.s3.secretAccessKey" to "aws-testing-secret",
    "aws.s3.bucket" to "aws-testing-bucket",
)
