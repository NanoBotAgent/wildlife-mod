package com.wildlife.mod.item;

import com.wildlife.mod.WildlifeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WildlifeMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WILDLIFE_TAB =
        CREATIVE_MODE_TABS.register("wildlife_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.wildlife"))
            .icon(() -> new ItemStack(ModItems.DEER_SPAWN_EGG.get()))
            .displayItems((parameters, output) -> {
                // Spawn Eggs
                output.accept(ModItems.DEER_SPAWN_EGG.get());
                output.accept(ModItems.BOAR_SPAWN_EGG.get());
                output.accept(ModItems.MONKEY_SPAWN_EGG.get());
                output.accept(ModItems.OTTER_SPAWN_EGG.get());
                output.accept(ModItems.OWL_SPAWN_EGG.get());
                output.accept(ModItems.BEAVER_SPAWN_EGG.get());
                output.accept(ModItems.MEERKAT_SPAWN_EGG.get());
                output.accept(ModItems.DUCK_SPAWN_EGG.get());
                output.accept(ModItems.TOUCAN_SPAWN_EGG.get());
                output.accept(ModItems.OSTRICH_SPAWN_EGG.get());
                output.accept(ModItems.TAPIR_SPAWN_EGG.get());
                output.accept(ModItems.MARMOT_SPAWN_EGG.get());
                
                // Food
                output.accept(ModItems.VENISON.get());
                output.accept(ModItems.COOKED_VENISON.get());
                output.accept(ModItems.BOAR_MEAT.get());
                output.accept(ModItems.COOKED_BOAR_MEAT.get());
                output.accept(ModItems.DUCK_MEAT.get());
                output.accept(ModItems.COOKED_DUCK.get());
                output.accept(ModItems.OSTRICH_MEAT.get());
                output.accept(ModItems.COOKED_OSTRICH.get());
                
                // Materials
                output.accept(ModItems.DEER_HIDE.get());
                output.accept(ModItems.MONKEY_FUR.get());
                output.accept(ModItems.OTTER_FUR.get());
                output.accept(ModItems.BEAVER_FUR.get());
                output.accept(ModItems.FEATHER.get());
                output.accept(ModItems.OWL_FEATHER.get());
                output.accept(ModItems.TOUCAN_FEATHER.get());
            })
            .build());
}
