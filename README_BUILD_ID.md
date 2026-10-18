# Toki & More GTS — Forge 1.20.1 source package (stage 6)

This folder is a source/resource workspace intended for building locally. It is **not a compiled JAR** and has not been verified as a successful build or in-game test.

## Target environment
- Minecraft 1.20.1
- Minecraft Forge 47.2.20
- Java JDK 17 (use JDK 17 for Gradle; do not rely on Java 21 if Gradle toolchains are not configured)
- GeckoLib Forge 1.20.1, version 4.4.9
- Gradle 8.5 recommended

## Build on a PC
1. Extract this ZIP to a normal folder (not inside `.minecraft/mods`).
2. Install a JDK 17 and Gradle 8.5, or open the folder in IntelliJ IDEA and configure Gradle to use JDK 17.
3. Ensure the computer can access Gradle Plugin Portal, `maven.minecraftforge.net`, and the GeckoLib Maven repository. Gradle must download ForgeGradle, Forge, and GeckoLib the first time.
4. In this folder run:
   - Windows: `gradle.bat build` if you have a Gradle distribution, otherwise `gradle build`
   - Linux/macOS: `gradle build`
5. If the build succeeds, the JAR should appear under `build/libs/` with a name starting `gts-forge-1.20.1`.
6. Test in a separate Forge 1.20.1 instance with GeckoLib 4.4.9 installed. Check `logs/latest.log` if it fails to load.

## Important status
- `build.gradle` targets Forge 1.20.1 / Forge 47.2.20 / GeckoLib 4.4.9.
- `settings.gradle` does not use the Foojay toolchain resolver plugin; this removes one unnecessary plugin-resolution dependency. It does **not** remove the need to download ForgeGradle and game dependencies.
- The original reference is `gts-4.0.0.jar` (NeoForge 1.21.1). A real port requires API fixes beyond changing metadata. Compile errors may remain, and the build has not yet been confirmed.
- Resource/JSON syntax validation is not equivalent to a successful Java compile or an in-game test.
- Do not install this source ZIP as a mod. Only a successful JAR build can be placed in `mods/`.

## Files to retain
Keep the entire directory structure, including `src/main/java`, `src/main/resources`, `build.gradle`, `settings.gradle`, and `gradle.properties`. Do not copy only the Java files; textures, sounds, GeckoLib animation/model JSON, metadata, and data resources are also required.
