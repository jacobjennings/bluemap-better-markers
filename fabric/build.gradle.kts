plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("net.fabricmc.fabric-loom")
}

val minecraftVersion: String by project
val fabricLoaderVersion: String by project
val fabricApiVersion: String by project
val fabricKotlinVersion: String by project
val blueMapApiVersion: String by project

dependencies {
    // Core module. The unobfuscated Loom no longer remaps subprojects, so the
    // old `namedElements` configuration is gone — use a plain project dependency.
    implementation(project(":core"))
    include(project(":core"))
    
    // Minecraft & Fabric (26.1+ is unobfuscated — no mappings, and the new
    // Loom does not remap mods, so use `implementation` not `modImplementation`)
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")

    // Kotlin language adapter
    implementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")

    // BlueMap API
    implementation("de.bluecolored:bluemap-api:$blueMapApiVersion")
}

tasks.processResources {
    val modId: String by project
    val modName: String by project
    val modVersion: String by project
    val modDescription: String by project
    val modAuthor: String by project
    val modLicense: String by project

    inputs.property("modId", modId)
    inputs.property("modName", modName)
    inputs.property("modVersion", modVersion)
    inputs.property("modDescription", modDescription)
    inputs.property("modAuthor", modAuthor)
    inputs.property("modLicense", modLicense)
    inputs.property("minecraftVersion", minecraftVersion)
    inputs.property("fabricKotlinVersion", fabricKotlinVersion)

    filesMatching("fabric.mod.json") {
        expand(
            "modId" to modId,
            "modName" to modName,
            "modVersion" to modVersion,
            "modDescription" to modDescription,
            "modAuthor" to modAuthor,
            "modLicense" to modLicense,
            "minecraftVersion" to minecraftVersion,
            "fabricKotlinVersion" to fabricKotlinVersion
        )
    }
}

// Note: the unobfuscated Loom (26.1+) does not remap, so there is no
// `remapJar` task — `jar` produces the final, loadable mod artifact.
tasks.jar {
    val modId: String by project
    archiveBaseName.set(modId)
    from("../LICENSE") {
        rename { "${it}_${project.name}" }
    }
}
