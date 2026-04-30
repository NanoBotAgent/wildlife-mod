package com.wildlife.mod.gametest;

import com.wildlife.mod.entity.WildlifeEntities;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

/**
 * GameTest class for Wildlife Mod.
 * Registered via the "fabric-gametest" entrypoint in fabric.mod.json.
 * Uses Fabric's @GameTest annotation which requires the fabric-api.gametest
 * system property to be set for test registration during initialization.
 */
public class WildlifeGameTest {

    @GameTest(maxTicks = 100, setupTicks = 20)
    public void deerSpawnsAndExists(GameTestHelper helper) {
        var deer = helper.spawn(WildlifeEntities.DEER, 1, 2, 1);
        helper.assertTrue(deer.isAlive(), "Deer should be alive after spawning");
        helper.succeed();
    }

    @GameTest(maxTicks = 100, setupTicks = 20)
    public void snakeSpawnsAndExists(GameTestHelper helper) {
        var snake = helper.spawn(WildlifeEntities.SNAKE, 1, 2, 1);
        helper.assertTrue(snake.isAlive(), "Snake should be alive after spawning");
        helper.succeed();
    }

    @GameTest(maxTicks = 60, setupTicks = 10)
    public void modLoadedTest(GameTestHelper helper) {
        // Minimal test — verify the GameTest framework is working and mod is loaded
        // If this test runs at all, the mod was loaded successfully
        helper.succeed();
    }
}
