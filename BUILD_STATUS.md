# Build status — stage 6

Status: source package prepared for user-side build; no JAR generated here.

Changes in this stage:
- Retained the full stage 5 source/resource tree.
- Removed the Foojay resolver plugin from `settings.gradle`, because the project targets Java 17 and does not need dynamic JDK downloading through that plugin.
- Added `README_BUILD_ID.md` with environment requirements and local build instructions.

Still unverified:
- Full Java compilation against Forge 1.20.1 and GeckoLib 4.4.9.
- Client/server launch.
- Entity registration, spawn rules, models, animation, sounds, attack behavior, items, and gameplay.

This package must not be described as a playable mod until the build succeeds and in-game checks pass.
