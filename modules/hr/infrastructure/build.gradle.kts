val ormVersion: String by project
val argon2Version: String by project
val ktorVersion: String by project
val googleOauthVersion = "1.34.1"

plugins {
    id("api.module")
    id("api.testing")
}

dependencies {
    // core
    implementation(project(":modules:common:domain"))
    implementation(project(":modules:common:infrastructure"))

    // user
    implementation(project(":modules:hr:domain"))

    // external
    implementation("io.tcds:orm:$ormVersion")
    implementation("de.mkammerer:argon2-jvm:$argon2Version")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("com.google.oauth-client:google-oauth-client:1.34.0")
    implementation("com.google.api-client:google-api-client:2.0.0")

    // testing
    testImplementation(project(":modules:common:testing"))
    testImplementation(project(":modules:hr:testing"))
}
