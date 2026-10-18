# GTS Toki & More — Forge 1.20.1 port audit (stage 5)

## Reference and target
- Reference: supplied original `gts-4.0.0.jar` (NeoForge 1.21.1).
- Target: Forge 1.20.1 / Forge 47.2.20 / Java 17 / GeckoLib 4.4.9.
- Candidate behavior reference: `TokiGTS-1.1.2.7-spawn-rollback-fix.jar`.

## Changes in this stage
- Preserved the restored original assets from stage 4.
- Removed NeoForge-only biome modifier JSON from the Forge resource tree.
- Added a Forge 1.20.1 `BiomeLoadingEvent` handler in `GtsMod` to add the original Toki and Candy monster spawners at weight 1, 1–1 per spawn group. Entity spawn-placement checks remain registered through `GtsModEntities`.

## Static checks
- Java sources: 288.
- Java source imports referencing `net.neoforged`: none found in the previous audit; recheck after edits.
- JSON syntax validation: run separately before release.

## Known unverified / release-blocking items
- No successful Gradle build has yet run: ForgeGradle and/or plugin dependencies are not available in the current offline environment.
- This means the new event handler has not been compiler-verified against Forge 47.2.20.
- No launch test in Minecraft has occurred. Do not label this archive a playable JAR.
- The source port still does not prove full parity with the 290-class original. Entity registration, missing original entity classes, GUI/network sync, animations, attacks, AI, save/reload and dedicated server behavior require a real build and game test.
- The candidate JAR's own README says its hitscan patch bypasses normal damage-source behavior and was not tested in Minecraft. Do not copy that patch into the full port without replacing it with a normal Forge damage pipeline and testing it.
