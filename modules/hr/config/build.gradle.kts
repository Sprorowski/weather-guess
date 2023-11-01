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

    // user
    implementation(project(":modules:hr:application"))
    implementation(project(":modules:hr:domain"))
    implementation(project(":modules:hr:infrastructure"))
    implementation(project(":modules:hr:presenter"))

    // external
    implementation("io.tcds:orm:$ormVersion")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.2")
    implementation("com.google.oauth-client:google-oauth-client:1.34.0")
    implementation("com.google.api-client:google-api-client:2.0.0")

    // testing
    testImplementation(project(":modules:hr:testing"))
}
