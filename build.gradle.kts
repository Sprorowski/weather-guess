val nettyVersion: String by project

group = "weather.sprorowski.io"
version = "1.0.0"

plugins {
    id("api.module")
    id("api.application")
}

application {
    mainClass.set("weather.sprorowski.io.ApplicationKt")
}

dependencies {
    implementation(project(":modules:common:presenter"))

    implementation(project(":modules:common:config"))
    implementation(project(":modules:hr:config"))
    // new-module-placeholder

    // external
    implementation("io.ktor:ktor-server-netty-jvm:$nettyVersion")
}
