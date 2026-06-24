plugins {
    kotlin("jvm") version "2.3.21" apply false
    kotlin("plugin.serialization") version "2.3.21" apply false
    // New unobfuscated Loom plugin (Minecraft 26.1+). Does NOT remap MC or mods.
    id("net.fabricmc.fabric-loom") version "1.17.12" apply false
}

allprojects {
    group = "com.machinepeople.bluemapbettermarkers"
    version = property("modVersion") as String

    repositories {
        mavenCentral()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://repo.bluecolored.de/releases/") { name = "BlueMap" }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    dependencies {
        val implementation by configurations
        implementation(kotlin("stdlib"))
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
        }
    }

    tasks.withType<JavaCompile> {
        options.release.set(25)
        sourceCompatibility = "25"
        targetCompatibility = "25"
    }
}

