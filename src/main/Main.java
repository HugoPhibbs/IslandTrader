package main;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import coreClasses.GameEnvironment;
import coreClasses.Island;
import coreClasses.Item;
import coreClasses.Player;
import coreClasses.Store;
import coreClasses.Route;
import coreClasses.Ship;
import uiClasses.*;


/** The entry point to the program, creates instances of the classes required 
 *  to start the game, then handles control to the class for the desired ui.
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 8/5/2021
 * @since 29/4/2021
 */
public class Main {
	
	public static void main(String[] args) {
		// Create objects required to initiate GameEnvironement
		
		// Create Ships to choose from, and an array to store
		Ship[] shipArray = createShips();
		
		// Create buy and sell catalogues
		ArrayList<HashMap<String, HashMap<String, Integer>>> buyCatalogues = createBuyCatalogues();
		ArrayList<HashMap<String, HashMap<String, Integer>>> sellCatalogues = createSellCatalogues();
		
		// Create an island array, required for game environment constructor
		Island[] islands = createIslands(buyCatalogues, sellCatalogues);
	
		// Create Routes
		Route[] routes = createRoutes(islands);
		
		for (Island island: islands) {
			List<Route> routesToIsland = new ArrayList<Route>();
			for(Route route: routes) {
				if (island == route.getIslands()[0] || island == route.getIslands()[1]) {
					routesToIsland.add(route);
				}
			}
			// Convert route list to route array
			Route[] routeArray = new Route[routesToIsland.size()];
			for (int i = 0; i < routesToIsland.size(); i++) {
				routeArray[i] = routesToIsland.get(i);
			}
			island.setRouteArray(routeArray);
		}
		
		// Initiate the UI and Game Environment
		GameUi ui;
		// TODO: needs to be changed later to allow both UIs to work.
		// THis is however easier for testing the command line user interface
		ui = new CmdLineUi();
		GameEnvironment gameEnrviron = new GameEnvironment(islands, shipArray, ui);
		ui.setup(gameEnrviron);
	}
	
	public static Island[] createIslands(ArrayList<HashMap<String, HashMap<String, Integer>>> buyCatalogues, ArrayList<HashMap<String, HashMap<String, Integer>>> sellCatalogues) {
		Store cyprusStore = new Store("War Goods", "Pies", buyCatalogues.get(0), sellCatalogues.get(0)); 
		Island cyprus = new Island("Cyprus", cyprusStore, "arb description");
		cyprusStore.setStoreIsland(cyprus);
		
		// Create the second island and its store
		Store sicilyStore = new Store("Pasta and Co", "WWW", buyCatalogues.get(0), sellCatalogues.get(0));
		Island sicily = new Island("Sicily", sicilyStore, "arb1"); 
		sicilyStore.setStoreIsland(sicily);
		
		// Create the third island and its store
		Store corsicaStore = new Store("Napoleans", "MMMMM", null, null);
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
		
		Island[] islands = new Island[] {cyprus, sicily, corsica, malta, ibiza};
		
		return islands;
	}
	
	public static Route[] createRoutes(Island[] islands) {
		Island cyprus = islands[0];
		Island sicily = islands[1];
		Island corsica = islands[2];
		Island malta = islands[3];
		Island ibiza = islands[4];
		
		Route cyprusAndSicily = new Route("firstRoute", 10, new Island[] {cyprus, sicily}, "test 1"); 
		cyprusAndSicily.constructProbabilityMap(50, 50, 50);
		
		Route cyprusAndCorsica = new Route("secondRoute", 20, new Island[] {cyprus, corsica}, "test 2"); 
		cyprusAndCorsica.constructProbabilityMap(50, 50, 50);
		
		Route cyprusAndMalta = new Route("firstRoute", 10, new Island[] {cyprus, malta}, "test 1"); 
		cyprusAndMalta.constructProbabilityMap(50, 50, 50);
		
		Route cyprusAndIbiza = new Route("secondRoute", 20, new Island[] {cyprus, ibiza}, "test 2"); 
		cyprusAndIbiza.constructProbabilityMap(50, 50, 50);
		
		Route sicilyAndIbiza = new Route("firstRoute", 10, new Island[] {sicily, ibiza}, "test 1"); 
		sicilyAndIbiza.constructProbabilityMap(50, 50, 50);
		
		Route sicilyAndCorsica = new Route("secondRoute", 20, new Island[] {sicily, corsica}, "test 2"); 
		sicilyAndCorsica.constructProbabilityMap(50, 50, 50);
		
		Route sicilyAndMalta = new Route("firstRoute", 10, new Island[] {sicily, malta}, "test 1"); 
		sicilyAndMalta.constructProbabilityMap(50, 50, 50);
		
		Route maltaAndCorsica = new Route("firstRoute", 10, new Island[] {malta, corsica}, "test 1"); 
		maltaAndCorsica.constructProbabilityMap(50, 50, 50);
		
		Route maltaAndIbiza = new Route("firstRoute", 10, new Island[] {malta, ibiza}, "test 1");
		maltaAndIbiza.constructProbabilityMap(50, 50, 50);
		
		Route ibizaAndCorsica = new Route("firstRoute", 10, new Island[] {ibiza, corsica}, "test 1"); 
		ibizaAndCorsica.constructProbabilityMap(50, 50, 50);
		
		Route[] routes = new Route[] {cyprusAndSicily, cyprusAndCorsica, cyprusAndMalta, cyprusAndIbiza, sicilyAndIbiza, sicilyAndCorsica, 
				sicilyAndMalta, maltaAndCorsica, maltaAndIbiza, ibizaAndCorsica};
		
		return routes;
	}
	
	public static ArrayList<HashMap<String, HashMap<String, Integer>>> createBuyCatalogues(){
		// NOTE stores do not buy upgrades!
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
		HashMap<String, Integer> canonProperties = new HashMap<String, Integer>();
		canonProperties.put("spaceTaken", 2);
		canonProperties.put("price", 2);
		canonProperties.put("defenseBoost", 2);
		buyCatalogue.put("Gold", goldProperties);
		buyCatalogue.put("Silver", silverProperties);
		buyCatalogue.put("Banana", bananaProperties);
		buyCatalogue.put("Canon(upgrade)", canonProperties);
		ArrayList<HashMap<String, HashMap<String, Integer>>> catalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();
		catalogues.add(buyCatalogue);
		
		// Create buy catalogue for sicily
		
		// Create buy catalogue for corsica
		
		// Create buy catalogue for malta
		
		// Create buy catalogue for ibiza
		return catalogues;
	}
	
	public static ArrayList<HashMap<String, HashMap<String, Integer>>> createSellCatalogues() {
		// Create sell catalogue for cyprus
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
		
		// Create sell catalogue for sicily
		
		// Create sell catalogue for corsica
		
		// Create sell catalogue for malta
		
		// Create sell catalogue for ibiza
		return catalogues;
	}
	
	public static Ship[] createShips() {
		// TODO messed up the numbers bellow, pls check again
		Ship ship1 = new Ship("Black Pearl", 100, 10, 30);
		Ship ship2 = new Ship("Thunder Bird", 80, 8, 40);
		Ship ship3 = new Ship("Batmobile", 70, 7, 50);
		Ship ship4 = new Ship("Apollo", 100, 6, 20);
		return new Ship[] {ship1, ship2, ship3, ship4};
	}
}











