plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("fabric-loom")
}

val minecraftVersion: String by project
val blueMapApiVersion: String by project

dependencies {
    // Minecraft & Mappings
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())

    // BlueMap API
    implementation("de.bluecolored:bluemap-api:$blueMapApiVersion")
}
