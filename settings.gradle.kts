rootProject.name = "weather.sprorowski.io"

val modules = listOf("common", "game")

modules.forEach {
    include(
        "modules:$it:domain",
        "modules:$it:application",
        "modules:$it:infrastructure",
        "modules:$it:presenter",
        "modules:$it:config",
        "modules:$it:testing",
    )
}
