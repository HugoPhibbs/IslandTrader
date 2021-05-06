package main;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.HashMap;

import coreClasses.GameEnvironment;
import coreClasses.Island;
import coreClasses.Item;
import coreClasses.Player;
import coreClasses.Store;
import coreClasses.Route;
import coreClasses.Ship;
import uiClasses.*;


/**
 * The entry point to the program, creates instances of the classes required 
 * to start the game, then handles control to the class for the desired ui.
 * 
 * @author Jordan Vegar and Hugo Phibbs
 *
 */
public class Main {
	
	public static void main(String[] args) {
		// Create objects required to initiate GameEnvironement
		// Create Ships to choose from, and an array to store
		Ship[] shipArray = createShips();
		
		// Create the first island and its store
		ArrayList<HashMap<String, HashMap<String, Integer>>> catalogues = createBuyCatalogues();
		Store currStore = new Store("MY SHACK", "Pies", catalogues.get(0), catalogues.get(0)); 
		Island currentIsland = new Island("current", currStore, "arb description");
		currStore.setStoreIsland(currentIsland);
		
		// Create the second island and its store
		Store s1 = new Store("AAA", "WWW", catalogues.get(0), catalogues.get(0));
		Island i1 = new Island("otherOne", s1, "arb1"); 
		s1.setStoreIsland(i1);
		
		// Create the third island and its store
		Store s2 = new Store(); 
		Island i2 = new Island("otherTwo", s2, "arb2"); 
		s2.setStoreIsland(i2);
		
		// Create the fourth island and its store
		Store s3 = new Store(); 
		Island i3 = new Island("otherThree", s3, "arb3"); 
		s3.setStoreIsland(i3);
		
		// Create the fourth island and its store
		Store s4 = new Store(); 
		Island i4 = new Island("otherThree", s3, "arb3"); 
		s4.setStoreIsland(i4);
		
		
		// Create Routes for the first store
		Route firstRoute = new Route("firstRoute", 10, currentIsland, i1, "test 1"); firstRoute.constructProbabilityMap(50, 50, 50);
		Route secondRoute = new Route("secondRoute", 20, currentIsland, i1, "test 2"); secondRoute.constructProbabilityMap(50, 50, 50);
		Route[] currIslandRoutes = new Route[] {firstRoute, secondRoute};
		currentIsland.setRouteArray(currIslandRoutes);
		
		// Create Routes for the first store
		Route r1 = new Route("firstRoute", 10, currentIsland, i1, "test 1"); firstRoute.constructProbabilityMap(50, 50, 50);
		Route r2 = new Route("secondRoute", 20, currentIsland, i1, "test 2"); secondRoute.constructProbabilityMap(50, 50, 50);
		Route[] s2routes = new Route[] {firstRoute, secondRoute};
		currentIsland.setRouteArray(currIslandRoutes);
		
	
		// Create an island array, required for game environment constructor
		Island[] islands = new Island[] {currentIsland, i1, i2, i3};
		
		// Initiate the UI and Game Environment
		GameUi ui;
		// TODO: needs to be changed later to allow both UIs to work.
		// THis is however easier for testing the command line user interface
		ui = new CmdLineUi();
		GameEnvironment gameEnrviron = new GameEnvironment(islands, shipArray, ui);
		ui.setup(gameEnrviron);
	}
	
	public static ArrayList<HashMap<String, HashMap<String, Integer>>> createBuyCatalogues(){
		// Find a way to directly initialize these!!!
		HashMap<String, HashMap<String, Integer>> buyCatalogue = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> goldProperties = new HashMap<String, Integer>();
		goldProperties.put("spaceTaken", 2);
		goldProperties.put("price", 3);
		HashMap<String, Integer> silverProperties = new HashMap<String, Integer>();
		silverProperties.put("spaceTaken", 3);
		silverProperties.put("price", 4);
		HashMap<String, Integer> bananaProperties = new HashMap<String, Integer>();
		bananaProperties.put("spaceTaken", 1);
		bananaProperties.put("price", 1);
		buyCatalogue.put("Gold", goldProperties);
		buyCatalogue.put("Silver", silverProperties);
		buyCatalogue.put("Banana", bananaProperties);
		ArrayList<HashMap<String, HashMap<String, Integer>>> catalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();
		catalogues.add(buyCatalogue);
		return catalogues;
	}
	
	public static ArrayList<HashMap<String, HashMap<String, Integer>>> createSellCatalogues() {
		HashMap<String, HashMap<String, Integer>> sellCatalogue = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> goldProperties = new HashMap<String, Integer>();
		goldProperties.put("spaceTaken", 2);
		goldProperties.put("price", 3);
		HashMap<String, Integer> silverProperties = new HashMap<String, Integer>();
		silverProperties.put("spaceTaken", 3);
		silverProperties.put("price", 4);
		HashMap<String, Integer> bananaProperties = new HashMap<String, Integer>();
		bananaProperties.put("spaceTaken", 1);
		bananaProperties.put("price", 1);
		sellCatalogue.put("Gold", goldProperties);
		sellCatalogue.put("Silver", silverProperties);
		sellCatalogue.put("Banana", bananaProperties);
		ArrayList<HashMap<String, HashMap<String, Integer>>> catalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();
		catalogues.add(sellCatalogue);
		return catalogues;
	}
	
	public static Ship[] createShips() {
		Ship ship1 = new Ship("Black Pearl", 100, 100, 20, 50);
		Ship ship2 = new Ship("Thunder Bird", 80, 80, 30, 40);
		Ship ship3 = new Ship("Batmobile", 70, 120, 10, 70);
		Ship ship4 = new Ship("Apollo", 100, 100, 30, 40);
		return new Ship[] {ship1, ship2, ship3, ship4};
	}
}











