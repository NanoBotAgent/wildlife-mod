package com.wildlife.mod;

import com.wildlife.mod.entity.ModEntities;
import com.wildlife.mod.item.ModCreativeTabs;
import com.wildlife.mod.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("wildlife")
public class WildlifeMod {
    public static final String MOD_ID = "wildlife";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WildlifeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener(this::commonSetup);
        
        ModEntities.ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::clientSetup);
        });
        
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        
        LOGGER.info("Wildlife Mod initialized - Adding deer and monkeys to your world!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("Common setup complete for Wildlife Mod");
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup complete for Wildlife Mod");
    }

    private void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Wildlife Mod server starting!");
    }
}
