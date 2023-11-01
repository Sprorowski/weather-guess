val koinVersion: String by project

plugins {
    id("api.module")
    application
}

dependencies {
    // koin
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
}
