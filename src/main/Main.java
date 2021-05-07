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
		Store cyprusStore = new Store("War Goods", "Pies", catalogues.get(0), catalogues.get(0)); 
		Island cyprus = new Island("Cyprus", cyprusStore, "arb description");
		cyprusStore.setStoreIsland(cyprus);
		
		// Create the second island and its store
		Store sicilyStore = new Store("Pasta and Co", "WWW", catalogues.get(0), catalogues.get(0));
		Island sicily = new Island("Sicily", sicilyStore, "arb1"); 
		sicilyStore.setStoreIsland(sicily);
		
		// Create the third island and its store
		Store corsicaStore = new Store("Napolean's", "MMMMM", null, null);
		Island corsica = new Island("Corsica", corsicaStore, "arb2"); 
		corsicaStore.setStoreIsland(corsica);
		
		// Create the fourth island and its store
		Store maltaStore = new Store("Duty Free Store", "ppppppp", null, null);
		Island malta = new Island("Malta", maltaStore, "arb3"); 
		maltaStore.setStoreIsland(malta);
		
		// Create the fifth island and its store
		Store ibizaStore = new Store("Party Store", "pills and potions", null, null); 
		Island ibiza = new Island("Ibiza", ibizaStore, "arb3"); 
		ibizaStore.setStoreIsland(ibiza);
		
		
		// Create Routes for the first island
		Route firstRoute = new Route("firstRoute", 10, currentIsland, i1, "test 1"); firstRoute.constructProbabilityMap(100, 50, 50);
		Route secondRoute = new Route("secondRoute", 20, currentIsland, i1, "test 2"); secondRoute.constructProbabilityMap(50, 50, 50);
		Route[] currIslandRoutes = new Route[] {firstRoute, secondRoute};
		currentIsland.setRouteArray(currIslandRoutes);
		
		// Create Routes for the first island
		Route r1 = new Route("firstRoute", 10, currentIsland, i1, "test 1"); firstRoute.constructProbabilityMap(50, 50, 50);
		Route r2 = new Route("secondRoute", 20, currentIsland, i1, "test 2"); secondRoute.constructProbabilityMap(50, 50, 50);
		Route[] s2routes = new Route[] {firstRoute, secondRoute};
		currentIsland.setRouteArray(currIslandRoutes);
		
		// Create Routes for the first island
		Route r3 = new Route("firstRoute", 10, currentIsland, i1, "test 1"); firstRoute.constructProbabilityMap(50, 50, 50);
		Route r4 = new Route("secondRoute", 20, currentIsland, i1, "test 2"); secondRoute.constructProbabilityMap(50, 50, 50);
		Route[] s3routes = new Route[] {firstRoute, secondRoute};
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











