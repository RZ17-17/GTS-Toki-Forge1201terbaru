GTS Toki & More — Forge 1.20.1 porting checkpoint v9
====================================================

SOURCE CHECKPOINT ONLY — NOT a playable JAR.

Target: Minecraft 1.20.1 / Forge 47.x / Java 17.
Java sources: 288. Assets: 39 PNG, 24 OGG, 18 JSON assets (JSON syntax validated).

New work in v9:
- Converted modern data-component usage for custom item names and anvil repair cost to 1.20.1 ItemStack APIs.
- Replaced ItemStack.isSameItemSameComponents with the 1.20.1 same-item/same-tags API.
- Updated the attribute modifier operation name to the 1.20.1 enum.
- Retained v8 custom scale attribute and RecordItem conversion, plus v7 SimpleChannel/capability groundwork.

Remaining caveats:
- No successful Gradle build or Minecraft launch test yet. Gradle is not installed and cannot be downloaded from this environment.
- The source still requires a real compile pass; other 1.21.1 API differences and Forge event/API mismatches may remain.
- The ZIP is not a mod JAR.
