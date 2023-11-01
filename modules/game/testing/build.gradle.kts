val ktorVersion: String by project
val koinVersion: String by project
val kotestVersion: String by project
val mockkVersion: String by project

plugins {
    id("api.module")
    id("api.application")
}

dependencies {
    implementation(project(":modules:common:config"))
    implementation(project(":modules:common:domain"))
    implementation(project(":modules:common:presenter"))
    implementation(project(":modules:common:testing"))

    implementation(project(":modules:game:config"))
    implementation(project(":modules:game:domain"))
    implementation(project(":modules:game:application"))
    implementation(project(":modules:game:presenter"))

    implementation("io.insert-koin:koin-test-junit5:$koinVersion")
    implementation("io.kotest:kotest-assertions-json-jvm:$kotestVersion")
    implementation("io.kotest:kotest-assertions-core:$kotestVersion")

    implementation("io.mockk:mockk:$mockkVersion")
    implementation("io.ktor:ktor-server-test-host-jvm:2.3.2")
}
