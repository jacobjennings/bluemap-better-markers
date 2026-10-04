# MCS-2 (part 1 of 2): bluemap-better-markers build for Minecraft 26.3

Card: http://huly.lan/workbench/hulyaccessevaluation/tracker/MCS-2

Run started 11:40 pm on October 3, 2026 in America/Chicago.

## What changed

The build now targets Minecraft 26.3 and the mod version is 1.2.0. All versions
live in `gradle.properties` unless noted. No source files needed changes.

- `minecraftVersion`: 26.1.2 to 26.3.
  Checked: `meta.fabricmc.net/v2/versions/loader/26.3` lists loaders for 26.3.
  Loom resolved `com.mojang:minecraft:26.3` in the build.

- `fabricLoaderVersion`: 0.19.3 to 0.19.5.
  Checked: the stable loader entry at the same meta.fabricmc.net endpoint.

- `fabricApiVersion`: 0.153.0+26.1.2 to 0.161.0+26.3.
  Checked: Modrinth API project `fabric-api`, release `0.161.0+26.3` for 26.3,
  published 2026-09-18.

- `fabricKotlinVersion`: 1.13.11+kotlin.2.3.21 to 1.14.1+kotlin.2.4.20.
  Checked: Modrinth API project `fabric-language-kotlin`, release for 26.3,
  published 2026-09-07.

- `blueMapApiVersion`: 2.8.0 to 2.8.1.
  Checked: `repo.bluecolored.de` maven metadata, latest and release 2.8.1,
  updated 2026-09-25.

- `modVersion`: 1.1.0 to 1.2.0.
  A bump. No registry lookup needed.

`build.gradle.kts` changes:

- Kotlin plugin 2.3.21 to 2.4.20. This matches the Kotlin bundled in the
  `fabric-language-kotlin 1.14.1+kotlin.2.4.20` adapter.
- fabric-loom 1.17.12 to 1.17.21. That is the newest stable 1.17.x release in
  `maven.fabricmc.net/net/fabricmc/fabric-loom/maven-metadata.xml`. A 1.18.x
  line also exists; I stayed on the 1.17 line to keep the change minimal.

`fabric.mod.json` changes:

- `depends.minecraft` was `~${minecraftVersion}`. I changed it to the literal
  `>=26.1.2` and say why below.
- `depends.fabric-language-kotlin` still expands to `>=` plus the adapter
  version, now `>=1.14.1+kotlin.2.4.20`.

### Why `>=26.1.2` and not `~26.3`

With year-based versioning, a `~26.3` range means `>=26.3` and `<26.4`. The
next 26.x update would make the mod unloadable and force a rebuild. The mod
only touches the BlueMap API and stable marker interfaces, so `>=26.1.2`
keeps it loadable across the whole 26.x series. I chose `>=26.1.2`.

## BlueMap 5.28 check

BlueMap 5.28 exists on Modrinth for 26.3. I checked the `bluemap` project
versions with `game_versions=26.3`. The builds are published per loader
(sponge, spigot, folia, paper, purpur, neoforge, forge) on 2026-09-25.

BlueMap API 2.8.0 no longer matches it. Version 2.8.1 was published on the
maven on the same day as 5.28. I bumped `blueMapApiVersion` to 2.8.1.

## API changes I made

None. `core/src/main/kotlin/.../MarkerUpdates.kt` and
`fabric/src/main/kotlin/.../BlueMapBetterMarkersMod.kt` compile unchanged
against BlueMap API 2.8.1 and Minecraft 26.3. No source edits were needed.

## Checks

Both checks were run from the worktree, clean run first, then re-run:

- `./gradlew classes` - BUILD SUCCESSFUL. Clean run: 3 actionable tasks:
  3 executed.
- `./gradlew assemble` - BUILD SUCCESSFUL. Clean run: 7 actionable tasks:
  4 executed, 3 up-to-date.

The jar: `fabric/build/libs/bluemap-better-markers-1.2.0.jar`.

I verified the jar contents. The embedded `fabric.mod.json` says version
1.2.0, `minecraft` depends `>=26.1.2`, and `fabric-language-kotlin` depends
`>=1.14.1+kotlin.2.4.20`. The core module is embedded under
`META-INF/jars/core-1.2.0.jar`.

## Notes

- No sandbox blocked any host. I reached services.gradle.org,
  maven.fabricmc.net, repo.bluecolored.de, api.modrinth.com,
  meta.fabricmc.net, the public Maven repository, and the Mojang maven
  during the build.
- No load testing or in-game testing, as the brief says.
- `README.md` needed no change. It does not mention any versions.

## RECOMMENDATIONS

1. `.github/workflows/release.yml` sets up JDK 21, but the build compiles
   with `jvmTarget` and `release` 25. A CI run on `main` will fail at the
   compile step. The file is outside this brief's list, so I left it.
   Bump it to Java 25.

2. Loom 1.18.2 is the newest stable line on maven.fabricmc.net. Consider a
   follow-up bump if 1.17.21 shows issues.

3. The mc-server-spinner-upper role that installs this jar on `stlmc` should
   point at the new 1.2.0 jar once this branch merges. That is the part 2 or
   merge lane work.

4. BlueMap 5.28 itself ships on the server as a separate jar. Make sure the
   server's BlueMap install is 5.28 so it matches the API version we build
   against.
