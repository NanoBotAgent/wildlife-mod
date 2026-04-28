package com.wildlife.mod.gametest;

import com.wildlife.mod.entity.WildlifeEntities;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntitySpawnReason;

/**
 * GameTest class for Wildlife Mod.
 * Registered via the "fabric-gametest" entrypoint in fabric.mod.json.
 * These tests run automatically when mc-runtime-test executes /test runall.
 */
public class WildlifeGameTest {

    @GameTest(maxTicks = 100, setupTicks = 20)
    public void deerSpawnsAndExists(GameTestHelper helper) {
        var level = helper.getLevel();
        var deer = WildlifeEntities.DEER.create(level, EntitySpawnReason.COMMAND);
        if (deer == null) {
            helper.fail("Failed to create deer entity — WildlifeEntities.DEER returned null");
            return;
        }
        // Place the deer at the test structure's origin
        var origin = helper.getAbsolutePos(BlockPos.ZERO);
        deer.setPos(origin.getX() + 0.5, origin.getY() + 1.0, origin.getZ() + 0.5);
        level.addFreshEntity(deer);
        helper.assertTrue(deer.isAlive(), "Deer should be alive after spawning");
        helper.succeed();
    }

    @GameTest(maxTicks = 100, setupTicks = 20)
    public void snakeSpawnsAndExists(GameTestHelper helper) {
        var level = helper.getLevel();
        var snake = WildlifeEntities.SNAKE.create(level, EntitySpawnReason.COMMAND);
        if (snake == null) {
            helper.fail("Failed to create snake entity — WildlifeEntities.SNAKE returned null");
            return;
        }
        var origin = helper.getAbsolutePos(BlockPos.ZERO);
        snake.setPos(origin.getX() + 0.5, origin.getY() + 1.0, origin.getZ() + 0.5);
        level.addFreshEntity(snake);
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
