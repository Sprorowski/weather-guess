val ormVersion: String by project
val jacksonVersion: String by project
val ktorVersion: String by project
val s3Version: String by project

plugins {
    id("api.module")
    id("api.serializer")
    id("api.testing")
}

dependencies {
    implementation(project(":modules:common:domain"))

    implementation("io.tcds:orm:$ormVersion")

    implementation("aws.sdk.kotlin:s3:$s3Version")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.2")
    implementation("io.ktor:ktor-client-core-jvm:2.3.2")
    implementation("io.ktor:ktor-client-serialization-jvm:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.2")
}
