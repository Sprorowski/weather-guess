val ktorVersion: String by project
val ormVersion: String by project
val mysqlConnectorVersion: String by project
val jacksonVersion: String by project
val kotlinLoggingVersion: String by project
val logbackVersion: String by project
val logstashVersion: String by project
val kotestVersion: String by project
val jUnitVersion: String by project
val koinVersion: String by project
val s3Version: String by project
val tikaVersion: String by project

plugins {
    id("api.module")
    id("api.application")
    id("api.presenter")
    id("api.serializer")
    id("api.testing")
}

dependencies {
    implementation(project(":modules:common:domain"))
    implementation(project(":modules:common:application"))

    // external
    implementation("org.apache.tika:tika-parsers:$tikaVersion")

    implementation("io.ktor:ktor-client-core-jvm:2.3.2")
    implementation("io.ktor:ktor-server-status-pages-jvm:2.3.2")
    implementation("io.ktor:ktor-server-call-logging:2.3.2")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.2")
    implementation("io.ktor:ktor-serialization-jackson-jvm:2.3.2")
}
