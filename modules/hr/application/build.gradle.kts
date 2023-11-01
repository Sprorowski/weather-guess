plugins {
    id("api.module")
    id("api.testing")
}

dependencies {
    // common
    implementation(project(":modules:common:domain"))
    implementation(project(":modules:common:application"))

    // user
    implementation(project(":modules:hr:domain"))

    // testing
    testImplementation(project(":modules:common:testing"))
    testImplementation(project(":modules:hr:testing"))
}
