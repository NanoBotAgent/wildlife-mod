package com.wildlife.mod.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

/**
 * GameTest class for Wildlife Mod.
 * Registered via the "fabric-gametest" entrypoint in fabric.mod.json.
 *
 * Note: MC 26.1.x GameTest uses a block-based TestInstance system.
 * Tests registered here are placed in the TEST_INSTANCE registry by
 * Fabric's gametest-api-v1, but mc-runtime-test's allNearby finder
 * only discovers TestInstanceBlockEntity blocks already placed in the world.
 * For CI, we set McRuntimeGameTestMinExpectedGameTests=0 since the mod
 * loading itself is the primary verification.
 */
public class WildlifeGameTest {

	@GameTest(maxTicks = 60, setupTicks = 10)
	public void modLoadedTest(GameTestHelper helper) {
		// Minimal test — verify the GameTest framework is working and mod is loaded
		// If this test runs at all, the mod was loaded successfully
		helper.succeed();
	}

	@GameTest(maxTicks = 100, setupTicks = 20)
	public void deerSpawnsAndExists(GameTestHelper helper) {
		var deer = helper.spawn(com.wildlife.mod.entity.WildlifeEntities.DEER, 1, 2, 1);
		helper.assertTrue(deer.isAlive(), "Deer should be alive after spawning");
		helper.succeed();
	}

	@GameTest(maxTicks = 100, setupTicks = 20)
	public void snakeSpawnsAndExists(GameTestHelper helper) {
		var snake = helper.spawn(com.wildlife.mod.entity.WildlifeEntities.SNAKE, 1, 2, 1);
		helper.assertTrue(snake.isAlive(), "Snake should be alive after spawning");
		helper.succeed();
	}
}
