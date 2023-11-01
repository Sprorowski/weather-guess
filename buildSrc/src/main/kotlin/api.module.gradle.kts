import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion = "1.8"

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
}

// JVM target applied to all Kotlin tasks across all subprojects
// Kotlin DSL
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        languageVersion = kotlinVersion
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("started", "skipped", "passed", "failed")

        showCauses = true
        showStackTraces = true
        showExceptions = true
        exceptionFormat = TestExceptionFormat.FULL
    }
}

tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }
tasks.withType<Tar> { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }
tasks.withType<Zip> { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }
