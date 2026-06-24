plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("net.fabricmc.fabric-loom")
}

val minecraftVersion: String by project
val blueMapApiVersion: String by project

dependencies {
    // Minecraft (26.1+ ships unobfuscated — no mappings line needed)
    minecraft("com.mojang:minecraft:$minecraftVersion")

    // BlueMap API
    implementation("de.bluecolored:bluemap-api:$blueMapApiVersion")
}
