val mockkVersion: String by project
val jUnitVersion: String by project
val koinVersion: String by project
val kotestVersion: String by project
val ktorVersion: String by project

plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(project(":modules:common:testing"))

    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")

    testImplementation("io.kotest:kotest-assertions-json-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
}
