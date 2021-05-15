package main;

import java.util.ArrayList;  
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import coreClasses.*;
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
		initializeObjects();
	}
	
	private static void initializeObjects() {
		// Initializes all the objects needed to run a game
		
		// called by main
        Ship[] shipArray = createShips();
        
		// Create pirates object to be used throughout game
		Pirates pirates = new Pirates(350);;
		
		// Create Rescued Sailors object to be used throughout game
		RescuedSailors rescuedSailors = new RescuedSailors(50);
		
		// Create buy and sell catalogues
		ArrayList<HashMap<String, HashMap<String, Integer>>> buyCatalogues = createBuyCatalogues();
		ArrayList<HashMap<String, HashMap<String, Integer>>> sellCatalogues = createSellCatalogues();
		
		// Create an island array, required for GameEnvironment constructor
		Island[] islands = createIslands(buyCatalogues, sellCatalogues);
	
		// Create Routes
		Route[] routes = createRoutes(islands);
		
		// Set the Routes for every island
		islands = setIslandRoutes(routes, islands);
		
		// Initiate the UI and Game Environment
		GameUi ui;
		ui = new CmdLineUi();
		// ui = new Gui();
		GameEnvironment gameEnrvironment = new GameEnvironment(islands, shipArray, ui, pirates, rescuedSailors);
		// TODO: needs to be changed later to allow both UIs to work.
		// THis is however easier for testing the command line user interface
		
		// Setup UI with gameEnvironment object that was just created
		ui.setup(gameEnrvironment);
	}
	
	public static Island[] setIslandRoutes(Route[] routes, Island[] islands) {
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
		return islands;
	}
	
	public static Ship[] createShips() {
		// TODO messed up the numbers bellow, pls check again
		Ship ship1 = new Ship("Black Pearl", 100, 10, 30);
		Ship ship2 = new Ship("Thunder Bird", 80, 8, 40);
		Ship ship3 = new Ship("Batmobile", 70, 7, 50);
		Ship ship4 = new Ship("Apollo", 100, 6, 20);
		return new Ship[] {ship1, ship2, ship3, ship4};
	}
	
	public static Island[] createIslands(ArrayList<HashMap<String, HashMap<String, Integer>>> buyCatalogues, ArrayList<HashMap<String, HashMap<String, Integer>>> sellCatalogues) {
		Store cyprusStore = new Store("War Goods", "Ship upgrades", buyCatalogues.get(0), sellCatalogues.get(0)); 
		Island cyprus = new Island("Cyprus", cyprusStore, "arb description");
		cyprusStore.setStoreIsland(cyprus);
		
		// Create the second island and its store
		Store sicilyStore = new Store("Pasta and Co", "Specialty food", buyCatalogues.get(0), sellCatalogues.get(0));
		Island sicily = new Island("Sicily", sicilyStore, "arb1"); 
		sicilyStore.setStoreIsland(sicily);
		
		// Create the third island and its store
		Store corsicaStore = new Store("Napoleans", "A bit of everything", null, null);
		Island corsica = new Island("Corsica", corsicaStore, "arb2"); 
		corsicaStore.setStoreIsland(corsica);
		
		// Create the fourth island and its store
		Store maltaStore = new Store("Duty Free Store", "Exotic goods", null, null);
		Island malta = new Island("Malta", maltaStore, "arb3"); 
		maltaStore.setStoreIsland(malta);
		
		// Create the fifth island and its store
		Store ibizaStore = new Store("Party Store", "Medicines", null, null); 
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
		/* Items that can be sold, keep the number small for simplicity
		 * 3 types of goods for simplicity, makes types of stores easy to to.
		 * Goods:,
		 * Gold,          spaceTaken = 5  /exotic
		 * Large Chest,   spaceTaken = 10 /exotic
		 * Treasure Map,  spaceTaken = 5  /exotic
		 * Lime           spaceTaken = 1  /med
		 * Bandages       spaceTaken = 2  /med
		 * Special Tea    spaceTaken = 3  /med
		 * Tomato Sauce,  spaceTaken = 1  /foods
		 * Turtle Meat    spaceTaken = 2  /foods
		 * Bottle o' Rum, spaceTaken = 2  /foods
		 * 
		 * Upgrades:
		 * Cannon         defenseBoost = 10
		 * CrowsNest      defenseBoost = 5
		 * Armour         defenseBoost = 7
		 * Telescope      defenseBoost = 2
		 * 
		 */
		// NOTE stores do not buy upgrades!
		
		//TODO need to balance out all the prices!!
		
		ArrayList<HashMap<String, HashMap<String, Integer>>> buyCatalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();
		
		// every store should sell 5 items, for simplicity
		
		// Create buy catalouge for Cyprus, specialises in upgrades
		HashMap<String, HashMap<String, Integer>> buyCatalogueCyprus = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> goldPropertiesCyprus = new HashMap<String, Integer>();
		goldPropertiesCyprus.put("spaceTaken", 2);
		goldPropertiesCyprus.put("price", 3);
		HashMap<String, Integer> treasureMapPropertiesCyprus = new HashMap<String, Integer>();
		treasureMapPropertiesCyprus.put("spaceTaken", 3);
		treasureMapPropertiesCyprus.put("price", 4);
		HashMap<String, Integer> canonPropertiesCyprus = new HashMap<String, Integer>();
		canonPropertiesCyprus.put("spaceTaken", 0);
		canonPropertiesCyprus.put("price", 50); 
		canonPropertiesCyprus.put("defenseBoost", 10);
		HashMap<String, Integer> armourPropertiesCyprus = new HashMap<String, Integer>();
		armourPropertiesCyprus.put("spaceTaken", 0);
		armourPropertiesCyprus.put("price", 30);
		armourPropertiesCyprus.put("defenseBoost", 7);
		HashMap<String, Integer> crowsNestPropertiesCyprus = new HashMap<String, Integer>();
		crowsNestPropertiesCyprus.put("spaceTaken", 0);
		crowsNestPropertiesCyprus.put("price", 10);
		crowsNestPropertiesCyprus.put("defenseBoost", 2);
		buyCatalogueCyprus.put("Gold", goldPropertiesCyprus);
		buyCatalogueCyprus.put("Treasure Map", treasureMapPropertiesCyprus);
		buyCatalogueCyprus.put("Canon(upgrade)", canonPropertiesCyprus);
		buyCatalogueCyprus.put("Crows-Nest(upgrade)", crowsNestPropertiesCyprus);
		buyCatalogueCyprus.put("Armour(upgrade)", armourPropertiesCyprus);
		buyCatalogues.add(buyCatalogueCyprus);
		
		// Create buy catalogue for Sicily specialises in food
		HashMap<String, HashMap<String, Integer>> buyCatalogueSicily = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> tomatoSaucePropertiesSicily = new HashMap<String, Integer>();
		tomatoSaucePropertiesSicily.put("spaceTaken", 1);
		tomatoSaucePropertiesSicily.put("price", 3);
		HashMap<String, Integer> bottleORumPropertiesSicily = new HashMap<String, Integer>();
		bottleORumPropertiesSicily.put("spaceTaken", 3);
		bottleORumPropertiesSicily.put("price", 4);
		HashMap<String, Integer> turtleMeatPropertiesSicily = new HashMap<String, Integer>();
		turtleMeatPropertiesSicily.put("spaceTaken", 0);
		turtleMeatPropertiesSicily.put("price", 50); 
		HashMap<String, Integer> goldPropertiesSicily = new HashMap<String, Integer>();
		goldPropertiesSicily.put("spaceTaken", 0);
		goldPropertiesSicily.put("price", 30);
		HashMap<String, Integer> limePropertiesSicily = new HashMap<String, Integer>();
		limePropertiesSicily.put("spaceTaken", 0);
		limePropertiesSicily.put("price", 10);
		buyCatalogueSicily.put("Tomato Sauce", tomatoSaucePropertiesSicily);
		buyCatalogueSicily.put("Bottle O' Rum", bottleORumPropertiesSicily);
		buyCatalogueSicily.put("Turtle Meat", turtleMeatPropertiesSicily);
		buyCatalogueSicily.put("Gold", goldPropertiesSicily);
		buyCatalogueSicily.put("Lime", limePropertiesSicily);
		buyCatalogues.add(buyCatalogueSicily);
		
		// Create buy catalogue for corsica specialises in everything
		HashMap<String, HashMap<String, Integer>> buyCatalogueCorsica = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> tomatoSaucePropertiesCorsica = new HashMap<String, Integer>();
		tomatoSaucePropertiesCorsica.put("spaceTaken", 1);
		tomatoSaucePropertiesCorsica.put("price", 3);
		HashMap<String, Integer> treasureMapPropertiesCorsica = new HashMap<String, Integer>();
		treasureMapPropertiesCorsica.put("spaceTaken", 3);
		treasureMapPropertiesCorsica.put("price", 4);
		HashMap<String, Integer> specialTeaPropertiesCorsica = new HashMap<String, Integer>();
		specialTeaPropertiesCorsica.put("spaceTaken", 0);
		specialTeaPropertiesCorsica.put("price", 50); 
		HashMap<String, Integer> cannonPropertiesCorsica = new HashMap<String, Integer>();
		cannonPropertiesCorsica.put("spaceTaken", 0);
		cannonPropertiesCorsica.put("price", 30);
		cannonPropertiesCorsica.put("defenseBoost", 7);
		HashMap<String, Integer> telescopePropertiesCorsica = new HashMap<String, Integer>();
		telescopePropertiesCorsica.put("spaceTaken", 0);
		telescopePropertiesCorsica.put("price", 10);
		cannonPropertiesCorsica.put("defenseBoost", 7);
		buyCatalogueCorsica.put("Tomato Sauce", tomatoSaucePropertiesCorsica);
		buyCatalogueCorsica.put("Treasure Map", treasureMapPropertiesCorsica);
		buyCatalogueCorsica.put("Special Tea", specialTeaPropertiesCorsica);
		buyCatalogueCorsica.put("Canon(upgrade)", cannonPropertiesCorsica);
		buyCatalogueCorsica.put("Telescope(upgrade)", telescopePropertiesCorsica);
		buyCatalogues.add(buyCatalogueCorsica);
		
		// Create buy catalogue for malta specialises in Exotic stuff
		HashMap<String, HashMap<String, Integer>> buyCatalogueMalta = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> largeChestPropertiesMalta = new HashMap<String, Integer>();
		largeChestPropertiesMalta.put("spaceTaken", 1);
		largeChestPropertiesMalta.put("price", 3);
		HashMap<String, Integer> treasureMapPropertiesMalta = new HashMap<String, Integer>();
		treasureMapPropertiesMalta.put("spaceTaken", 3);
		treasureMapPropertiesMalta.put("price", 4);
		HashMap<String, Integer> goldPropertiesMalta = new HashMap<String, Integer>();
		goldPropertiesMalta.put("spaceTaken", 0);
		goldPropertiesMalta.put("price", 50); 
		HashMap<String, Integer> bandagesPropertiesMalta = new HashMap<String, Integer>();
		bandagesPropertiesMalta.put("spaceTaken", 0);
		bandagesPropertiesMalta.put("price", 30);
		HashMap<String, Integer> armourPropertiesMalta = new HashMap<String, Integer>();
		armourPropertiesMalta.put("spaceTaken", 0);
		armourPropertiesMalta.put("price", 10);
		armourPropertiesMalta.put("defenseBoost", 7);
		buyCatalogueMalta.put("Large Chest", largeChestPropertiesMalta);
		buyCatalogueMalta.put("Treasure Map", treasureMapPropertiesMalta);
		buyCatalogueMalta.put("Gold", goldPropertiesMalta);
		buyCatalogueMalta.put("Bandages", bandagesPropertiesMalta);
		buyCatalogueMalta.put("Armour(upgrade)", armourPropertiesMalta);
		buyCatalogues.add(buyCatalogueMalta);
		
		// Create buy catalogue for ibiza
		HashMap<String, HashMap<String, Integer>> buyCatalogueIbiza = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> bandagesPropertiesIbiza = new HashMap<String, Integer>();
		bandagesPropertiesIbiza.put("spaceTaken", 0);
		bandagesPropertiesIbiza.put("price", 30);
		HashMap<String, Integer> specialTeaPropertiesIbiza = new HashMap<String, Integer>();
		specialTeaPropertiesIbiza.put("spaceTaken", 3);
		specialTeaPropertiesIbiza.put("price", 4);
		HashMap<String, Integer> limePropertiesIbiza = new HashMap<String, Integer>();
		limePropertiesIbiza.put("spaceTaken", 0);
		limePropertiesIbiza.put("price", 50); 
		HashMap<String, Integer> bottleORumPropertiesIbiza = new HashMap<String, Integer>();
		bottleORumPropertiesIbiza.put("spaceTaken", 0);
		bottleORumPropertiesIbiza.put("price", 30);
		HashMap<String, Integer> largeChestPropertiesIbiza = new HashMap<String, Integer>();
		largeChestPropertiesIbiza.put("spaceTaken", 0);
		largeChestPropertiesIbiza.put("price", 10);
		largeChestPropertiesIbiza.put("defenseBoost", 7);
		buyCatalogueIbiza.put("Bandages", bandagesPropertiesIbiza);
		buyCatalogueIbiza.put("Special Tea", specialTeaPropertiesIbiza);
		buyCatalogueIbiza.put("Lime", limePropertiesIbiza);
		buyCatalogueIbiza.put("BottleORum", bottleORumPropertiesIbiza);
		buyCatalogueIbiza.put("Large Chest", largeChestPropertiesIbiza);
		buyCatalogues.add(buyCatalogueIbiza);
		
		return buyCatalogues;
	}
	
	public static ArrayList<HashMap<String, HashMap<String, Integer>>> createSellCatalogues() {
		/* Items that can be sold, keep the number small for simplicity
		 * Goods:,
		 * Gold,          spaceTaken = 5  /exotic
		 * Large Chest,   spaceTaken = 10 /exotic
		 * Treasure Map,  spaceTaken = 5  /exotic
		 * Lime           spaceTaken = 1  /med
		 * Bandages       spaceTaken = 2  /med
		 * Special Tea    spaceTaken = 3  /med
		 * Tomato Sauce,  spaceTaken = 1  /foods
		 * Turtle Meat    spaceTaken = 2  /foods
		 * Bottle o' Rum, spaceTaken = 2  /foods
		 * 
		 */
		
		
		ArrayList<HashMap<String, HashMap<String, Integer>>> sellCatalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();
		
		
		// Create sell catalogue for cyprus, becuase it only sells ugrades, can buy a bit of everything
		HashMap<String, HashMap<String, Integer>> sellCatalogueCyprus = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> goldPropertiesCyprus = new HashMap<String, Integer>();
		goldPropertiesCyprus.put("spaceTaken", 5);
		goldPropertiesCyprus.put("price", 10);
		HashMap<String, Integer> silverPropertiesCyprus = new HashMap<String, Integer>();
		silverPropertiesCyprus.put("spaceTaken", 3);
		silverPropertiesCyprus.put("price", 4);
		HashMap<String, Integer> bananaPropertiesCyprus = new HashMap<String, Integer>();
		bananaPropertiesCyprus.put("spaceTaken", 1);
		bananaPropertiesCyprus.put("price", 1);
		HashMap<String, Integer> bandagesPropertiesCyprus = new HashMap<String, Integer>();
		bandagesPropertiesCyprus.put("spaceTaken", 3);
		bandagesPropertiesCyprus.put("price", 4);
		HashMap<String, Integer> limePropertiesCyprus = new HashMap<String, Integer>();
		limePropertiesCyprus.put("spaceTaken", 1);
		limePropertiesCyprus.put("price", 1);
		sellCatalogueCyprus.put("Gold", goldPropertiesCyprus);
		sellCatalogueCyprus.put("Silver", silverPropertiesCyprus);
		sellCatalogueCyprus.put("Banana", bananaPropertiesCyprus);
		sellCatalogueCyprus.put("Silver", bandagesPropertiesCyprus);
		sellCatalogueCyprus.put("Banana", limePropertiesCyprus);
		sellCatalogues.add(sellCatalogueCyprus);
		
		// Create sell catalogue for sicily, specialises in food. 
		HashMap<String, HashMap<String, Integer>> sellCatalogueSicily = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> tomatoSaucePropertiesSicily = new HashMap<String, Integer>();
		tomatoSaucePropertiesSicily.put("spaceTaken", 1);
		tomatoSaucePropertiesSicily.put("price", 3);
		HashMap<String, Integer> bottleORumPropertiesSicily = new HashMap<String, Integer>();
		bottleORumPropertiesSicily.put("spaceTaken", 3);
		bottleORumPropertiesSicily.put("price", 4);
		HashMap<String, Integer> turtleMeatPropertiesSicily = new HashMap<String, Integer>();
		turtleMeatPropertiesSicily.put("spaceTaken", 0);
		turtleMeatPropertiesSicily.put("price", 50); 
		HashMap<String, Integer> goldPropertiesSicily = new HashMap<String, Integer>();
		goldPropertiesSicily.put("spaceTaken", 0);
		goldPropertiesSicily.put("price", 30);
		HashMap<String, Integer> limePropertiesSicily = new HashMap<String, Integer>();
		limePropertiesSicily.put("spaceTaken", 0);
		limePropertiesSicily.put("price", 10);
		sellCatalogueSicily.put("Tomato-Sauce", tomatoSaucePropertiesSicily);
		sellCatalogueSicily.put("Bottle-O'-Rum", bottleORumPropertiesSicily);
		sellCatalogueSicily.put("Turtle-Meat", turtleMeatPropertiesSicily);
		sellCatalogueSicily.put("Gold", goldPropertiesSicily);
		sellCatalogueSicily.put("Lime", limePropertiesSicily);
		sellCatalogues.add(sellCatalogueSicily);
		
		// Create sell catalogue for corsica
		// Create buy catalogue for corsica specialises in everything
		HashMap<String, HashMap<String, Integer>> sellCatalogueCorsica = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> tomatoSaucePropertiesCorsica = new HashMap<String, Integer>();
		tomatoSaucePropertiesCorsica.put("spaceTaken", 1);
		tomatoSaucePropertiesCorsica.put("price", 3);
		HashMap<String, Integer> treasureMapPropertiesCorsica = new HashMap<String, Integer>();
		treasureMapPropertiesCorsica.put("spaceTaken", 3);
		treasureMapPropertiesCorsica.put("price", 4);
		HashMap<String, Integer> specialTeaPropertiesCorsica = new HashMap<String, Integer>();
		specialTeaPropertiesCorsica.put("spaceTaken", 0);
		specialTeaPropertiesCorsica.put("price", 50); 
		HashMap<String, Integer> cannonPropertiesCorsica = new HashMap<String, Integer>();
		cannonPropertiesCorsica.put("spaceTaken", 0);
		cannonPropertiesCorsica.put("price", 30);
		HashMap<String, Integer> telescopePropertiesCorsica = new HashMap<String, Integer>();
		telescopePropertiesCorsica.put("spaceTaken", 0);
		telescopePropertiesCorsica.put("price", 10);
		sellCatalogueCorsica.put("Tomato-Sauce", tomatoSaucePropertiesCorsica);
		sellCatalogueCorsica.put("Treasure-Map", treasureMapPropertiesCorsica);
		sellCatalogueCorsica.put("Special-Tea", specialTeaPropertiesCorsica);
		sellCatalogueCorsica.put("Canon(upgrade)", cannonPropertiesCorsica);
		sellCatalogueCorsica.put("Telescope(upgrade)", telescopePropertiesCorsica);
		sellCatalogues.add(sellCatalogueCorsica);
		
		// Create sell catalogue for malta
		HashMap<String, HashMap<String, Integer>> sellCatalogueMalta = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> largeChestPropertiesMalta = new HashMap<String, Integer>();
		largeChestPropertiesMalta.put("spaceTaken", 1);
		largeChestPropertiesMalta.put("price", 3);
		HashMap<String, Integer> treasureMapPropertiesMalta = new HashMap<String, Integer>();
		treasureMapPropertiesMalta.put("spaceTaken", 3);
		treasureMapPropertiesMalta.put("price", 4);
		HashMap<String, Integer> goldPropertiesMalta = new HashMap<String, Integer>();
		goldPropertiesMalta.put("spaceTaken", 0);
		goldPropertiesMalta.put("price", 50); 
		HashMap<String, Integer> bandagesPropertiesMalta = new HashMap<String, Integer>();
		bandagesPropertiesMalta.put("spaceTaken", 0);
		bandagesPropertiesMalta.put("price", 30);
		HashMap<String, Integer> armourPropertiesMalta = new HashMap<String, Integer>();
		armourPropertiesMalta.put("spaceTaken", 0);
		armourPropertiesMalta.put("price", 10);
		sellCatalogueCyprus.put("Large-Chest", largeChestPropertiesMalta);
		sellCatalogueMalta.put("Treasure-Map", treasureMapPropertiesMalta);
		sellCatalogueMalta.put("Gold", goldPropertiesMalta);
		sellCatalogueMalta.put("Bandages", bandagesPropertiesMalta);
		sellCatalogueMalta.put("Armour(upgrade)", armourPropertiesMalta);
		sellCatalogues.add(sellCatalogueMalta);
		
		// Create buy catalogue for ibiza
		HashMap<String, HashMap<String, Integer>> sellCatalogueIbiza = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> bandagesPropertiesIbiza = new HashMap<String, Integer>();
		bandagesPropertiesIbiza.put("spaceTaken", 0);
		bandagesPropertiesIbiza.put("price", 30);
		HashMap<String, Integer> specialTeaPropertiesIbiza = new HashMap<String, Integer>();
		specialTeaPropertiesIbiza.put("spaceTaken", 3);
		specialTeaPropertiesIbiza.put("price", 4);
		HashMap<String, Integer> limePropertiesIbiza = new HashMap<String, Integer>();
		limePropertiesIbiza.put("spaceTaken", 0);
		limePropertiesIbiza.put("price", 50); 
		HashMap<String, Integer> bottleORumPropertiesIbiza = new HashMap<String, Integer>();
		bottleORumPropertiesIbiza.put("spaceTaken", 0);
		bottleORumPropertiesIbiza.put("price", 30);
		HashMap<String, Integer> largeChestPropertiesIbiza = new HashMap<String, Integer>();
		largeChestPropertiesIbiza.put("spaceTaken", 0);
		largeChestPropertiesIbiza.put("price", 10);
		sellCatalogueIbiza.put("Bandages", bandagesPropertiesIbiza);
		sellCatalogueIbiza.put("Special-Tea", specialTeaPropertiesIbiza);
		sellCatalogueIbiza.put("Lime", limePropertiesIbiza);
		sellCatalogueIbiza.put("BottleORum", bottleORumPropertiesIbiza);
		sellCatalogueIbiza.put("Large-Chest", largeChestPropertiesIbiza);
		sellCatalogues.add(sellCatalogueIbiza);
		
		return sellCatalogues;
	}
}











