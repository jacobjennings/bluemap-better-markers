Merge.

Reviewed wave `mcs-2a-better-markers-26-3` for [the board task](http://huly.lan/workbench/hulyaccessevaluation/tracker/MCS-2).
Source branch: `codex/mcs-2a-better-markers-26-3`.
Reviewed commit: `3655bf51d48e0832af1beb907b03ac63096c76c1`.
Diff base: `origin/main` at `e34364bfc9654b0376c5f17676c3a52678a3a86a`.
Review branch: `review/mcs-2a-better-markers-26-3`.

## Acceptance

The change meets the original implementation brief.
Minecraft now targets 26.3 and the mod version is 1.2.0.
The Fabric stack and Kotlin plugins have published versions.
BlueMap API 2.8.1 also exists.
The source compiles without API edits.

The chosen Minecraft predicate is `>=26.1.2`.
The original brief explicitly permits this predicate.
I checked it with Fabric loader 0.19.5's version parser.
It accepts 26.3 and 26.3.1 and rejects 26.1.1.
It also accepts 27.1, so it does not limit compatibility to the 26.x series.
These checks establish version acceptance, not runtime compatibility.

The diff contains three permitted implementation files and the worker report.
No license text or headers changed.
No secrets, build artifacts, caches, or attribution were added.
No blocking findings.

## Finding

Suggestion: `docs/harness/reports/mcs-2a-better-markers-26-3/report.md:62`.
The report says BlueMap API 2.8.0 no longer matches BlueMap 5.28.
The cited shared publication date for 2.8.1 does not establish that claim.
Describe 2.8.1 as the selected published version unless there is compatibility evidence.
The 2.8.1 build passes, so this wording issue does not block acceptance.

## Independent checks

I read the original brief and recovered the worker report from the reviewed commit.
I built an isolated archive of that commit to preserve both worktrees.
Java was 25.0.4.1.
I inspected the command output and jar contents.

| Command | Result | Actionable tasks |
| --- | --- | --- |
| `./gradlew classes` | BUILD SUCCESSFUL | 3 executed |
| `./gradlew build` | BUILD SUCCESSFUL | 4 executed, 3 up-to-date |
| `./gradlew assemble` | BUILD SUCCESSFUL | 7 up-to-date |

Both modules report `test NO-SOURCE`.
There are no repository test sources.
The build reports native access and Gradle deprecation warnings.
The core jar also reports an undetermined Mixin version.
The mod declares no mixins and the jar is produced successfully.
No server or client was started and no lab host was contacted.
No load testing or deployment was performed.

Built jar: `/tmp/better-markers-review-26-3.rsJLvI/fabric/build/libs/bluemap-better-markers-1.2.0.jar`.
Its embedded metadata has mod version 1.2.0 and Minecraft predicate `>=26.1.2`.
The jar requires adapter version `>=1.14.1+kotlin.2.4.20`.
Its nested core jar is `META-INF/jars/core-1.2.0.jar`.
The jar stays local and is excluded from the review commit.

## Published versions checked

Every linked package description returned HTTP 200 and the expected version.
The registry checks were independent of the Gradle cache.

| Dependency | Version | Public evidence |
| --- | --- | --- |
| Minecraft | `26.3` | [Fabric game registry](https://meta.fabricmc.net/v2/versions/game) lists a stable release. |
| Fabric loader | `0.19.5` | [Loader POM](https://maven.fabricmc.net/net/fabricmc/fabric-loader/0.19.5/fabric-loader-0.19.5.pom) and [26.3 registry](https://meta.fabricmc.net/v2/versions/loader/26.3). |
| Fabric API | `0.161.0+26.3` | [API POM](https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/0.161.0+26.3/fabric-api-0.161.0+26.3.pom). |
| `Fabric Kotlin` | `1.14.1+kotlin.2.4.20` | [Adapter POM](https://maven.fabricmc.net/net/fabricmc/fabric-language-kotlin/1.14.1+kotlin.2.4.20/fabric-language-kotlin-1.14.1+kotlin.2.4.20.pom). |
| BlueMap API | `2.8.1` | [API POM](https://repo.bluecolored.de/releases/de/bluecolored/bluemap-api/2.8.1/bluemap-api-2.8.1.pom). |
| `Kotlin JVM plugin` | `2.4.20` | [Plugin POM](https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-gradle-plugin/2.4.20/kotlin-gradle-plugin-2.4.20.pom). |
| `Kotlin serialization plugin` | `2.4.20` | [Plugin POM](https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-serialization/2.4.20/kotlin-serialization-2.4.20.pom). |
| `Fabric Loom` | `1.17.21` | [Loom POM](https://maven.fabricmc.net/net/fabricmc/fabric-loom/1.17.21/fabric-loom-1.17.21.pom). |

The [Fabric API registry](https://api.modrinth.com/v2/project/fabric-api/version?game_versions=%5B%2226.3%22%5D) lists the chosen version for 26.3.
The [adapter registry](https://api.modrinth.com/v2/project/fabric-language-kotlin/version?game_versions=%5B%2226.3%22%5D) also lists 26.3 support.
The [BlueMap registry](https://api.modrinth.com/v2/project/bluemap/version?game_versions=%5B%2226.3%22%5D) includes `5.28-fabric` for 26.3.
This confirms publication, without establishing in-game behavior.

## RECOMMENDATIONS

Clarify the worker report's API compatibility claim at line 62.
Keep the open-ended Minecraft predicate distinct from tested runtime support.
The existing Java 21 release workflow remains a separate follow-up noted by the worker.
No merge, release, installation, or deployment was performed.
