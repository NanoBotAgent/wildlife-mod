package com.wildlife.mod.client.model;

import com.wildlife.mod.WildlifeMod;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class WildlifeModelLayers {

    // Forest & Plains
    public static final ModelLayerLocation DEER = createMain("deer");
    public static final ModelLayerLocation BOAR = createMain("boar");
    public static final ModelLayerLocation FOX = createMain("fox");
    public static final ModelLayerLocation RACCOON = createMain("raccoon");
    public static final ModelLayerLocation BADGER = createMain("badger");

    // Jungle
    public static final ModelLayerLocation MONKEY = createMain("monkey");
    public static final ModelLayerLocation TAPIR = createMain("tapir");
    public static final ModelLayerLocation TOUCAN = createMain("toucan");

    // Desert & Savanna
    public static final ModelLayerLocation MEERKAT = createMain("meerkat");
    public static final ModelLayerLocation OSTRICH = createMain("ostrich");

    // Water & Wetlands
    public static final ModelLayerLocation OTTER = createMain("otter");
    public static final ModelLayerLocation BEAVER = createMain("beaver");
    public static final ModelLayerLocation DUCK = createMain("duck");

    // Mountains & Taiga
    public static final ModelLayerLocation MARMOT = createMain("marmot");
    public static final ModelLayerLocation GOAT = createMain("goat");

    // Snow & Ice
    public static final ModelLayerLocation PENGUIN = createMain("penguin");

    // Night
    public static final ModelLayerLocation OWL = createMain("owl");

    // Reptiles
    public static final ModelLayerLocation SNAKE = createMain("snake");

    // Insects
    public static final ModelLayerLocation BUTTERFLY = createMain("butterfly");
    public static final ModelLayerLocation BEE = createMain("bee");
    public static final ModelLayerLocation LADYBUG = createMain("ladybug");
    public static final ModelLayerLocation DRAGONFLY = createMain("dragonfly");
    public static final ModelLayerLocation FIREFLY = createMain("firefly");

    // Birds
    public static final ModelLayerLocation SPARROW = createMain("sparrow");
    public static final ModelLayerLocation ROBIN = createMain("robin");
    public static final ModelLayerLocation CROW = createMain("crow");
    public static final ModelLayerLocation CARDINAL = createMain("cardinal");
    public static final ModelLayerLocation BLUEJAY = createMain("bluejay");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(WildlifeMod.MOD_ID, name), "main");
    }

    public static void registerModelLayers() {
        // Forest & Plains
        EntityModelLayerRegistry.registerModelLayer(DEER, DeerModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BOAR, BoarModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FOX, FoxModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(RACCOON, RaccoonModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BADGER, BadgerModel::getTexturedModelData);

        // Jungle
        EntityModelLayerRegistry.registerModelLayer(MONKEY, MonkeyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TAPIR, TapirModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TOUCAN, ToucanModel::getTexturedModelData);

        // Desert & Savanna
        EntityModelLayerRegistry.registerModelLayer(MEERKAT, MeerkatModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(OSTRICH, OstrichModel::getTexturedModelData);

        // Water & Wetlands
        EntityModelLayerRegistry.registerModelLayer(OTTER, OtterModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BEAVER, BeaverModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DUCK, DuckModel::getTexturedModelData);

        // Mountains & Taiga
        EntityModelLayerRegistry.registerModelLayer(MARMOT, MarmotModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOAT, GoatModel::getTexturedModelData);

        // Snow & Ice
        EntityModelLayerRegistry.registerModelLayer(PENGUIN, PenguinModel::getTexturedModelData);

        // Night
        EntityModelLayerRegistry.registerModelLayer(OWL, OwlModel::getTexturedModelData);

        // Reptiles
        EntityModelLayerRegistry.registerModelLayer(SNAKE, SnakeModel::getTexturedModelData);

        // Insects
        EntityModelLayerRegistry.registerModelLayer(BUTTERFLY, ButterflyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BEE, BeeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LADYBUG, LadybugModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DRAGONFLY, DragonflyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FIREFLY, FireflyModel::getTexturedModelData);

        // Birds
        EntityModelLayerRegistry.registerModelLayer(SPARROW, SparrowModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ROBIN, RobinModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CROW, CrowModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CARDINAL, CardinalModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BLUEJAY, BluejayModel::getTexturedModelData);
    }
}
