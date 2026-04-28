package com.wildlife.mod.gametest;

import com.wildlife.mod.entity.WildlifeEntities;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

/**
 * GameTest class for Wildlife Mod.
 * Registered via the "fabric-gametest" entrypoint in fabric.mod.json.
 * These tests run automatically when mc-runtime-test executes /test runall.
 */
public class WildlifeGameTest {

    @GameTest(maxTicks = 100, setupTicks = 20)
    public void deerSpawnsAndExists(GameTestHelper helper) {
        var deer = WildlifeEntities.DEER.create(helper.getLevel());
        if (deer == null) {
            helper.fail("Failed to create deer entity — WildlifeEntities.DEER returned null");
            return;
        }
        var pos = helper.getAbsolutePos(1, 2, 1);
        deer.moveTo(pos.getX(), pos.getY(), pos.getZ());
        helper.getLevel().addFreshEntity(deer);
        helper.assertTrue(deer.isAlive(), "Deer should be alive after spawning");
        helper.succeed();
    }

    @GameTest(maxTicks = 100, setupTicks = 20)
    public void snakeSpawnsAndExists(GameTestHelper helper) {
        var snake = WildlifeEntities.SNAKE.create(helper.getLevel());
        if (snake == null) {
            helper.fail("Failed to create snake entity — WildlifeEntities.SNAKE returned null");
            return;
        }
        var pos = helper.getAbsolutePos(1, 2, 1);
        snake.moveTo(pos.getX(), pos.getY(), pos.getZ());
        helper.getLevel().addFreshEntity(snake);
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
