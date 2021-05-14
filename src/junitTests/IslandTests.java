/**
 * 
 */
package junitTests;

import coreClasses.*; 
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jordan Vegar
 *
 */
class IslandTests {
	
	private Island mainIsland;
	private Island testIsland1;
	private Island testIsland2;
	private Route mainAndTest1Fun;
	private Route mainAndTest1Scary;
	private Route mainAndTest2;
	
	

	
	// Note will probably transfer the code that sets up the stores islands and routes to a before each/all method. 
	@BeforeAll
	void createRequiredObjects() {
		/*
		 * Create the buy and sell catalogues for the main island's store, required so Island.getFullInfo() can be
		 * throughly tested.
		 */
		// Sell catalogue
		HashMap<String, HashMap<String, Integer>> testSellCatalogue = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> tomatoProperties = new HashMap<String, Integer>();
		tomatoProperties.put("spaceTaken", 1);
		tomatoProperties.put("price", 3);
		HashMap<String, Integer> rumProperties = new HashMap<String, Integer>();
		rumProperties.put("spaceTaken", 3);
		rumProperties.put("price", 4);
		testSellCatalogue.put("Tomato", tomatoProperties);
		testSellCatalogue.put("Rum", rumProperties);
		// buy cataogue
		HashMap<String, HashMap<String, Integer>> testBuyCatalogue = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> goldProperties = new HashMap<String, Integer>();
		goldProperties.put("spaceTaken", 0);
		goldProperties.put("price", 30);
		HashMap<String, Integer> limeProperties = new HashMap<String, Integer>();
		limeProperties.put("spaceTaken", 0);
		limeProperties.put("price", 10);
		testBuyCatalogue.put("Gold", goldProperties);
		testBuyCatalogue.put("Lime", limeProperties);
		
		
		Store mainStore = new Store("Main Store", "This store is sells and buys things!", testSellCatalogue, testBuyCatalogue);
		mainIsland = new Island("Main Island", mainStore, "An island with a nice store and even nicer tests");
		
		// Create other islands so the main isalnd t be tested has routes.
		Store testStore1 = new Store("TestIsland1", "sorry, we are closed", null, null);
		testIsland1 = new Island("Test1", testStore1, "Island exists only for its routes :(");
		
		Store testStore2 = new Store("TestIsland1", "sorry, we are closed", null, null);
		testIsland2 = new Island("Test1", testStore2, "Island exists only for its routes :(");
		
		// Create routes between the main island and the test islands.
		Island[] islands1 = new Island[] {mainIsland, testIsland1};
		mainAndTest1Fun = new Route("fun route", 100, islands1, "fun!");
		mainAndTest1Scary = new Route("scary route", 150, islands1, "scary!");
		mainAndTest2 = new Route("only route", 200, new Island[] {testIsland2, mainIsland}, "ur only choice");
		mainIsland.setRouteArray(new Route[] {mainAndTest1Fun, mainAndTest1Scary, mainAndTest2});
	}
	
	@Test
	void testToString() {
		assertEquals("Main Island: An island with a nice store and even nicer tests", mainIsland.toString());
	}
	
	@Test
	void testGetPossibleRoutes() {
		ArrayList<Route> expectedResult1 = new ArrayList<Route>(List.of(mainAndTest1Fun, mainAndTest1Scary));
		assertEquals(expectedResult1, mainIsland.getPossibleRoutes(testIsland1));
		
		ArrayList<Route> expectedResult2 = new ArrayList<Route>(List.of(mainAndTest2));
		assertEquals(expectedResult1, mainIsland.getPossibleRoutes(testIsland2));
	}
	
	@Test
	void testViewRoutes() {
		ArrayList<Route> routesToView1 = new ArrayList<Route>(List.of(mainAndTest1Fun, mainAndTest1Scary));
		String expectedResult1 = "Routes to Test1:\n - fun route: Is 100 km long. fun!\n - scary route: Is 150 km long. scary!";
		assertEquals(expectedResult1, mainIsland.getPossibleRoutes(testIsland1));
	}
	
	@Test
	void testGetFullInfo() {
		ArrayList<Route> routesFromTest1toMain = new ArrayList<Route>(List.of(mainAndTest1Fun, mainAndTest1Scary));
		String expectedMainInfoString = "About Main Island: An island with a nice store and even nicer tests\n"
				+ "Routes to Main Island:\n - fun route: Is 100 km long. fun!\n - scary route: Is 150 km long. scary!"
				+ "The store on this island sells:\nTomato; Rum; \n"
				+ "The store on this island buys:\nGold; Lime; \n";
		
		assertEquals(expectedMainInfoString, mainIsland.getFullInfo(routesFromTest1toMain));
	}
}








