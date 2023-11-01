plugins {
    id("api.module")
    id("api.application")
    id("api.testing")
}

dependencies {
    implementation(project(":modules:common:domain"))
}
