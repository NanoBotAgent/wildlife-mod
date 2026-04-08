package com.wildlife.mod.world;

import com.wildlife.mod.WildlifeMod;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = WildlifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatapackRegistry {
    
    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BIOME_MODIFIERS, helper -> {
            ModBiomeModifiers.bootstrap(new net.minecraft.data.worldgen.BootstrapContext<>() {
                @Override
                public <T> void register(ResourceKey<T> key, T value) {
                    helper.register((ResourceKey) key, value);
                }
                
                @Override
                public net.minecraft.core.RegistryAccess.Frozen lookup() {
                    return event.getRegistryAccess();
                }
            });
        });
    }
}
