[![java8](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/built-with/java8_vector.svg)](https://adoptium.net/temurin/releases/?version=8)
[![forge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/supported/forge_vector.svg)](http://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.12.2.html)
[![curseforge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg)](https://legacy.curseforge.com/minecraft/mc-mods/brokenwings-game-stages-edition)
[![modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/brokenwings-game-stages-edition)

[![github](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/available/github_vector.svg)](https://github.com/JP-HellParadise/BrokenWings)
[![kofi-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/donate/kofi-singular_vector.svg)](https://ko-fi.com/korewalidesu)

## Broken Wings - Game Stages Edition

Broken Wings is a creative flight blocker. It works per-dimension.

The default settings block flight in dimension 7, the Twilight Forest. It can be configured as a whitelist or a blacklist for dimensions to allow flight in, or to blanket-ban all creative flight in all dimensions. You can also choose to block elytra flight.

This fork features a implementation for GameStages (as long as fix if required).

**This work is dedicated to the public domain.**

### Original mod info:
Please check out the original repo at [here](https://github.com/quat1024/BrokenWings).

### Additional:
- New config to disable bypass keys.
- Support GameStages, CraftTweaker (and GroovyScript in Java way).

## Compiling

1. Clone this repository you have created with this template.
2. In the local repository, run the command `gradlew setupDecompWorkspace`
3. Open the project folder in IDEA.
4. Right-click in IDEA `build.gradle` of your project, and select `Link Gradle Project`, after completion, hit `Refresh All` in the gradle tab on the right.
5. Run `gradlew runClient` and `gradlew runServer`, or use the auto-imported run configurations in IntelliJ like `1. Run Client`.
