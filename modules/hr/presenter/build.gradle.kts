val ormVersion: String by project
val ktorVersion: String by project

plugins {
    id("api.module")
    id("api.application")
    id("api.presenter")
    id("api.testing")
}

dependencies {
    // core
    implementation(project(":modules:common:config"))
    implementation(project(":modules:common:domain"))
    implementation(project(":modules:common:application"))
    implementation(project(":modules:common:presenter"))

    // user
    implementation(project(":modules:hr:application"))
    implementation(project(":modules:hr:domain"))

    // external
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")

    // testing
    testImplementation(project(":modules:common:testing"))
    testImplementation(project(":modules:hr:testing"))
}
