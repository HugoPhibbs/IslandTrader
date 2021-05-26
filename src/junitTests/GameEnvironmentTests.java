package junitTests;

import static org.junit.jupiter.api.Assertions.*;  
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import coreClasses.*;

/** JUnit tests for GameEnvironment Object
 * 
 * @author Jordan Vegar
 *
 */
class GameEnvironmentTests {
	
	private GameEnvironment testGame;
	private Island homeIsland;
	private Ship testShip;
	private Player testPlayer;

	private Island island1;
	private Island island2;
	
	@BeforeEach
	void createRequiredObjects() {
		// Main testing objects
		testShip = new Ship("Black Pearl", 50,  5, 30);
		testPlayer = new Player("Jack Sparrow", 1000);
		Store homeStore = new Store("home store", "nothing", null, null);
		homeIsland = new Island("home", homeStore, "my home island");
		// Other islands for the island array
		Store store1 = new Store("store one", "tests", null, null);
		island1 = new Island("island one", store1, "first other island for tests");
		Store store2 = new Store("store two", "tests", null, null);
		island2 = new Island("island two", store2, "first other island for tests");
		Island[] islands = new Island[] {homeIsland, island1, island2};
		// Make routes
		Route a = new Route("a", 50, new Island[] {homeIsland, island1}, null);
		Route b = new Route("b", 75, new Island[] {homeIsland, island2}, null);
		// Route c = new Route("c", 50, new Island[] {homeIsland, island1}, null);
		// Route d = new Route("d", 50, new Island[] {homeIsland, island1}, null);
		
		homeIsland.setRouteArray(new Route[] {a, b});
		
		testGame = new GameEnvironment(islands, null, null, null, null);
		testGame.setCurrentIsland(homeIsland);
		testGame.setPlayer(testPlayer);
		testGame.setShip(testShip);
	}
	
	// Not testing getCost as it literally just returns repairCost and routeWageCost,
	// which tested when ship is tested. 
	
	@Test
	void testOtherIslands() {
		assertEquals(island1, testGame.otherIslands()[0]);
		assertEquals(island2, testGame.otherIslands()[1]);
		
		testGame.setCurrentIsland(island1);
		assertEquals(homeIsland, testGame.otherIslands()[0]);
		assertEquals(island2, testGame.otherIslands()[1]);
	}
	
	// should we test shipDescriptionArrayList()?
	
	@Test
	void testCalculatecore() {
		
	}
	
	
	// Might not need to test, just adds the result of two other methods together, and those 
	// other methods are tested elsewhere.
	@Test
	void testMinMoneyRequired() {
		
	}
	
	@Test
	void testGetLiquidValue() {
		
	}
}

