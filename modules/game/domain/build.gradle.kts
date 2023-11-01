plugins {
    id("api.module")
    id("api.testing")
}

dependencies {
    // core
    implementation(project(":modules:common:domain"))

    // user
    // ...

    // testing
    testImplementation(project(":modules:common:testing"))
    testImplementation(project(":modules:game:testing"))
}
