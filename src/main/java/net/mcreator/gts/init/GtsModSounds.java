
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.gts.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.GtsMod;

public class GtsModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, GtsMod.MODID);
	public static final RegistryObject<SoundEvent> SPITSOUNDEFFECT = REGISTRY.register("spitsoundeffect", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "spitsoundeffect")));
	public static final RegistryObject<SoundEvent> TOKI_LICK = REGISTRY.register("toki.lick", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "toki.lick")));
	public static final RegistryObject<SoundEvent> TOKI_SWALLOW = REGISTRY.register("toki_swallow", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "toki_swallow")));
	public static final RegistryObject<SoundEvent> TOKI_STOMACH = REGISTRY.register("toki.stomach", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "toki.stomach")));
	public static final RegistryObject<SoundEvent> TOKI_BELCH = REGISTRY.register("toki.belch", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "toki.belch")));
	public static final RegistryObject<SoundEvent> TOKI_KISS = REGISTRY.register("toki.kiss", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "toki.kiss")));
	public static final RegistryObject<SoundEvent> TOKI_DISKS = REGISTRY.register("toki.disks", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "toki.disks")));
	public static final RegistryObject<SoundEvent> CANDY_BELCH = REGISTRY.register("candy.belch", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "candy.belch")));
	public static final RegistryObject<SoundEvent> CANDY_STOMACH = REGISTRY.register("candy.stomach", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "candy.stomach")));
	public static final RegistryObject<SoundEvent> CANDY_SWALLOW = REGISTRY.register("candy.swallow", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gts", "candy.swallow")));
}
