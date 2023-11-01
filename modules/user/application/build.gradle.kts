plugins {
    id("api.module")
    id("api.testing")
}

dependencies {
    // common
    implementation(project(":modules:common:domain"))
    implementation(project(":modules:common:application"))

    // user
    implementation(project(":modules:user:domain"))

    // testing
    testImplementation(project(":modules:common:testing"))
    testImplementation(project(":modules:user:testing"))
}
