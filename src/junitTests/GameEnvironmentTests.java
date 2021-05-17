package junitTests;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import coreClasses.*;

/**
 * @author Jordan Vegar
 *
 */
class GameEnvironmentTests {
	
	private GameEnvironment testGame;
	
	@BeforeEach
	void createRequiredObjects() {
		// Main testing objects
		Ship testShip = new Ship("Black Pearl", 50,  5, 30);
		Player testPlayer = new Player("Jack Sparrow", 1000);
		Store homeStore = new Store("home store", "nothing", null, null);
		Island homeIsland = new Island("home", homeStore, "my home island");
		// Other islands for the island array
		Store store1 = new Store("store one", "tests", null, null);
		Island island1 = new Island("island one", store1, "first other island for tests");
		Store store2 = new Store("store two", "tests", null, null);
		Island island2 = new Island("island two", store2, "first other island for tests");
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
	
	@Test
	void testGetCost() {
		
		
	}
	
	@Test
	void testGetOtherIslands() {
		
	}
	
	// should we test getShipDescriptionArrayList()?
	
	@Test
	void testGetScore() {
		
	}
	
	@Test
	void testMinMoneyRequired() {
		
	}
	
	@Test
	void testGetLiquidValue() {
		
	}
	
	
	
	
}

