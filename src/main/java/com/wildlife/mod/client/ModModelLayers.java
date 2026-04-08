package com.wildlife.mod.client;

import com.wildlife.mod.WildlifeMod;
import com.wildlife.mod.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WildlifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModModelLayers {
    // Forest/Plains
    public static final ModelLayerLocation DEER = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "deer"), "main");
    public static final ModelLayerLocation BOAR = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "boar"), "main");
    
    // Jungle
    public static final ModelLayerLocation MONKEY = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "monkey"), "main");
    public static final ModelLayerLocation TAPIR = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "tapir"), "main");
    public static final ModelLayerLocation TOUCAN = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "toucan"), "main");
    
    // Water/Wetlands
    public static final ModelLayerLocation OTTER = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "otter"), "main");
    public static final ModelLayerLocation BEAVER = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "beaver"), "main");
    public static final ModelLayerLocation DUCK = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "duck"), "main");
    
    // Desert/Savanna
    public static final ModelLayerLocation MEERKAT = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "meerkat"), "main");
    public static final ModelLayerLocation OSTRICH = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "ostrich"), "main");
    
    // Mountain
    public static final ModelLayerLocation MARMOT = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "marmot"), "main");
    
    // Night
    public static final ModelLayerLocation OWL = 
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "owl"), "main");
    
    // Reptiles
    public static final ModelLayerLocation SNAKE =
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "snake"), "main");
    
    // Forest/Woodland (new)
    public static final ModelLayerLocation FOX =
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "fox"), "main");
    public static final ModelLayerLocation RACCOON =
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "raccoon"), "main");
    public static final ModelLayerLocation BADGER =
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "badger"), "main");
    
    // Snow/Ice (new)
    public static final ModelLayerLocation PENGUIN =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "penguin"), "main");
    public static final ModelLayerLocation GOAT =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "goat"), "main");

    // Insects
    public static final ModelLayerLocation BUTTERFLY =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "butterfly"), "main");
    public static final ModelLayerLocation BEE =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "bee"), "main");
    public static final ModelLayerLocation LADYBUG =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "ladybug"), "main");
    public static final ModelLayerLocation DRAGONFLY =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "dragonfly"), "main");
    public static final ModelLayerLocation FIREFLY =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "firefly"), "main");

    // Birds
    public static final ModelLayerLocation SPARROW =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "sparrow"), "main");
    public static final ModelLayerLocation ROBIN =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "robin"), "main");
    public static final ModelLayerLocation CROW =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "crow"), "main");
    public static final ModelLayerLocation CARDINAL =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "cardinal"), "main");
    public static final ModelLayerLocation BLUEJAY =
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WildlifeMod.MOD_ID, "bluejay"), "main");

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DEER, DeerModel::createBodyLayer);
        event.registerLayerDefinition(BOAR, BoarModel::createBodyLayer);
        event.registerLayerDefinition(MONKEY, MonkeyModel::createBodyLayer);
        event.registerLayerDefinition(TAPIR, TapirModel::createBodyLayer);
        event.registerLayerDefinition(TOUCAN, ToucanModel::createBodyLayer);
        event.registerLayerDefinition(OTTER, OtterModel::createBodyLayer);
        event.registerLayerDefinition(BEAVER, BeaverModel::createBodyLayer);
        event.registerLayerDefinition(DUCK, DuckModel::createBodyLayer);
        event.registerLayerDefinition(MEERKAT, MeerkatModel::createBodyLayer);
        event.registerLayerDefinition(OSTRICH, OstrichModel::createBodyLayer);
        event.registerLayerDefinition(MARMOT, MarmotModel::createBodyLayer);
        event.registerLayerDefinition(OWL, OwlModel::createBodyLayer);
        event.registerLayerDefinition(SNAKE, SnakeModel::createBodyLayer);
        event.registerLayerDefinition(FOX, FoxModel::createBodyLayer);
        event.registerLayerDefinition(RACCOON, RaccoonModel::createBodyLayer);
        event.registerLayerDefinition(BADGER, BadgerModel::createBodyLayer);
        event.registerLayerDefinition(PENGUIN, PenguinModel::createBodyLayer);
        event.registerLayerDefinition(GOAT, GoatModel::createBodyLayer);
        event.registerLayerDefinition(BUTTERFLY, ButterflyModel::createBodyLayer);
        event.registerLayerDefinition(BEE, BeeModel::createBodyLayer);
        event.registerLayerDefinition(LADYBUG, LadybugModel::createBodyLayer);
        event.registerLayerDefinition(DRAGONFLY, DragonflyModel::createBodyLayer);
        event.registerLayerDefinition(FIREFLY, FireflyModel::createBodyLayer);
        event.registerLayerDefinition(SPARROW, SparrowModel::createBodyLayer);
        event.registerLayerDefinition(ROBIN, RobinModel::createBodyLayer);
        event.registerLayerDefinition(CROW, CrowModel::createBodyLayer);
        event.registerLayerDefinition(CARDINAL, CardinalModel::createBodyLayer);
        event.registerLayerDefinition(BLUEJAY, BluejayModel::createBodyLayer);
    }
}
