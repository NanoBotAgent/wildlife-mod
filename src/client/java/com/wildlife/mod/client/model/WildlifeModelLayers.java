package com.wildlife.mod.client.model;

import com.wildlife.mod.WildlifeMod;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class WildlifeModelLayers {

 // Forest & Plains
 public static final ModelLayerLocation DEER = createMain("deer");
 public static final ModelLayerLocation BOAR = createMain("boar");
 // Fox, Bee, Goat removed — already exist in vanilla MC 26.1.2
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

 // Snow & Ice
 public static final ModelLayerLocation PENGUIN = createMain("penguin");

 // Night
 public static final ModelLayerLocation OWL = createMain("owl");

 // Reptiles
 public static final ModelLayerLocation SNAKE = createMain("snake");

 // Insects
 public static final ModelLayerLocation BUTTERFLY = createMain("butterfly");
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
 ModelLayerRegistry.registerModelLayer(DEER, DeerModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(BOAR, BoarModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(RACCOON, RaccoonModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(BADGER, BadgerModel::createLayerDefinition);

 // Jungle
 ModelLayerRegistry.registerModelLayer(MONKEY, MonkeyModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(TAPIR, TapirModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(TOUCAN, ToucanModel::createLayerDefinition);

 // Desert & Savanna
 ModelLayerRegistry.registerModelLayer(MEERKAT, MeerkatModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(OSTRICH, OstrichModel::createLayerDefinition);

 // Water & Wetlands
 ModelLayerRegistry.registerModelLayer(OTTER, OtterModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(BEAVER, BeaverModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(DUCK, DuckModel::createLayerDefinition);

 // Mountains & Taiga
 ModelLayerRegistry.registerModelLayer(MARMOT, MarmotModel::createLayerDefinition);

 // Snow & Ice
 ModelLayerRegistry.registerModelLayer(PENGUIN, PenguinModel::createLayerDefinition);

 // Night
 ModelLayerRegistry.registerModelLayer(OWL, OwlModel::createLayerDefinition);

 // Reptiles
 ModelLayerRegistry.registerModelLayer(SNAKE, SnakeModel::createLayerDefinition);

 // Insects
 ModelLayerRegistry.registerModelLayer(BUTTERFLY, ButterflyModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(LADYBUG, LadybugModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(DRAGONFLY, DragonflyModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(FIREFLY, FireflyModel::createLayerDefinition);

 // Birds
 ModelLayerRegistry.registerModelLayer(SPARROW, SparrowModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(ROBIN, RobinModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(CROW, CrowModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(CARDINAL, CardinalModel::createLayerDefinition);
 ModelLayerRegistry.registerModelLayer(BLUEJAY, BluejayModel::createLayerDefinition);
 }
}
