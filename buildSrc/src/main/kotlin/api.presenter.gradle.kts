val kotlinLoggingVersion: String by project
val logbackVersion: String by project
val logstashVersion: String by project

plugins {
    kotlin("jvm")
}

dependencies {
    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
}
