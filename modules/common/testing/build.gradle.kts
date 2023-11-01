val jUnitVersion: String by project
val mockkVersion: String by project
val ktorVersion: String by project
val jacksonVersion: String by project
val kotestVersion: String by project
val ormVersion: String by project
val koinVersion: String by project

plugins {
    id("api.module")
    id("api.application")
    id("api.presenter")
    id("api.serializer")
}

dependencies {
    implementation(project(":modules:common:config"))
    implementation(project(":modules:common:domain"))
    implementation(project(":modules:common:infrastructure"))
    implementation(project(":modules:common:presenter"))

    implementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
    implementation("io.mockk:mockk:$mockkVersion")
    implementation("io.kotest:kotest-assertions-json-jvm:$kotestVersion")
    implementation("io.kotest:kotest-assertions-core:$kotestVersion")
    implementation("io.insert-koin:koin-test-junit5:$koinVersion")

    implementation("io.tcds:orm:$ormVersion")
    implementation("io.ktor:ktor-client-core-jvm:2.3.2")
    implementation("io.ktor:ktor-client-mock-jvm:2.3.2")
    implementation("io.ktor:ktor-server-test-host-jvm:2.3.2")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.2")
}
