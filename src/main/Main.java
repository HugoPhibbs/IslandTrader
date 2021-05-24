package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import coreClasses.*;
import uiClasses.*;
import uiClasses.gui.Gui;

/**
 * The entry point to the program, creates instances of the classes required to
 * start the game, then handles control to the class for the desired ui.
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 15/5/2021
 * @since 29/4/2021
 */
public class Main {

	/**
	 * Main method Entry Point into the Island Trader Game Initializing all the
	 * objects needed for a game Most of the functionality is handled by Main's
	 * other methods`
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Initialize all the objects needed to run a game
		Ship[] shipArray = createShips();

		// Create pirates object to be used throughout game
		Pirates pirates = new Pirates(350, 50);
		;

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
		ui = new Gui();
		GameEnvironment gameEnvironment = new GameEnvironment(islands, shipArray, ui, pirates, rescuedSailors);
		ui.setup(gameEnvironment);

//		if (args.length > 0 && (args[0].equals("cmd"))) {
//			ui = new CmdLineUi();
//			GameEnvironment gameEnrvironment = new GameEnvironment(islands, shipArray, ui, pirates, rescuedSailors);
//			ui.setup(gameEnrvironment);
//		} else {
//			ui = new Gui();
//			GameEnvironment gameEnrvironment = new GameEnvironment(islands, shipArray, ui, pirates, rescuedSailors);
//			ui.setup(gameEnrvironment);
//		}
	}

	/**
	 * Method for Setting the routes between every pair of Islands
	 * 
	 * @param routes  Route[] containing all the routes for a game
	 * @param islands Island[] containing all the routes for a game
	 * @return Island[] an adjusted array from input, containing all the islands,
	 *         but with routes added between them
	 */
	public static Island[] setIslandRoutes(Route[] routes, Island[] islands) {
		for (Island island : islands) {
			List<Route> routesToIsland = new ArrayList<Route>();
			for (Route route : routes) {
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

	/**
	 * Method for creating the Ships for a game. Player chooses only one of them
	 * 
	 * @return Ship[] contains all the Ships created for a game
	 */
	public static Ship[] createShips() {
		// TODO messed up the numbers bellow, pls check again
		Ship ship1 = new Ship("Black Pearl", 100, 10, 30);
		Ship ship2 = new Ship("Thunder Bird", 80, 8, 40);
		Ship ship3 = new Ship("Batmobile", 70, 7, 50);
		Ship ship4 = new Ship("Apollo", 100, 6, 20);
		return new Ship[] { ship1, ship2, ship3, ship4 };
	}

	/**
	 * Method for creating the islands for a game
	 * 
	 * @param buyCatalogues  ArrayList<HashMap<String, HashMap<String, Integer>>>
	 *                       contains all the buyCatalogues for every island to be
	 *                       created
	 * @param sellCatalogues ArrayList<HashMap<String, HashMap<String, Integer>>>
	 *                       contains all the sellCatalogues for every island to be
	 *                       created
	 * @return Island[] containing all the Islands that were created
	 */
	public static Island[] createIslands(ArrayList<HashMap<String, HashMap<String, Integer>>> buyCatalogues,
			ArrayList<HashMap<String, HashMap<String, Integer>>> sellCatalogues) {

		// Create Cyprus Island and it's Store
		Store cyprusStore = new Store("War Goods", "Ship upgrades", sellCatalogues.get(0), buyCatalogues.get(0));
		Island cyprus = new Island("Cyprus", cyprusStore, "Nice and sunny");
		cyprusStore.setStoreIsland(cyprus);

		// Create Sicily Island and it's Store
		Store sicilyStore = new Store("Pasta and Co", "Specialty food", sellCatalogues.get(1), buyCatalogues.get(1));
		Island sicily = new Island("Sicily", sicilyStore, "Looks like a football");
		sicilyStore.setStoreIsland(sicily);

		// Create Corsica Island and it's Store
		Store corsicaStore = new Store("Napoleans", "A bit of everything", sellCatalogues.get(2), buyCatalogues.get(2));
		Island corsica = new Island("Corsica", corsicaStore, "Has cool beaches");
		corsicaStore.setStoreIsland(corsica);

		// Create Malta Island and it's Store
		Store maltaStore = new Store("Duty Free Store", "Exotic goods", sellCatalogues.get(3), buyCatalogues.get(3));
		Island malta = new Island("Malta", maltaStore, "Good for the rich");
		maltaStore.setStoreIsland(malta);

		// Create Ibiza Island and it's Store
		Store ibizaStore = new Store("Party Store", "Medicines", sellCatalogues.get(4), buyCatalogues.get(4));
		Island ibiza = new Island("Ibiza", ibizaStore, "Party central");
		ibizaStore.setStoreIsland(ibiza);

		// Return Islands packed into an Array
		return new Island[] { cyprus, sicily, corsica, malta, ibiza };
	}

	/**
	 * Method for creating all the routes for a game
	 * 
	 * @param islands Island[] Array containing all the islands for a game
	 * @return Route[] containing all the routes created inbetween the game Island's
	 */
	public static Route[] createRoutes(Island[] islands) {
		Island cyprus = islands[0];
		Island sicily = islands[1];
		Island corsica = islands[2];
		Island malta = islands[3];
		Island ibiza = islands[4];

		Route cyprusAndSicilySafe = new Route("Flat Water Detour", 350, new Island[] { cyprus, sicily },
				"A long, sheltered route.");
		cyprusAndSicilySafe.constructProbabilityMap(10, 10, 30);
		Route cyprusAndSicilyStormy = new Route("Stormy Seas", 175, new Island[] { cyprus, sicily },
				"The most direct route between Cyprus and Sicily, but watch out - its Stormy!");
		cyprusAndSicilyStormy.constructProbabilityMap(5, 70, 80);

		Route cyprusAndCorsicaFast = new Route("Speedy Route", 50, new Island[] { cyprus, corsica },
				"A very short route, but its dangerous!");
		cyprusAndCorsicaFast.constructProbabilityMap(70, 70, 70);
		Route cyprusAndCorsicaSlow = new Route("Slow Route", 270, new Island[] { cyprus, corsica },
				"A long but very safe route.");
		cyprusAndCorsicaSlow.constructProbabilityMap(5, 5, 25);

		Route cyprusAndMalta = new Route("Windy waters", 100, new Island[] { cyprus, malta },
				"A quick route, but the weather forecast isn't sunny!");
		cyprusAndMalta.constructProbabilityMap(20, 80, 100);

		Route cyprusAndIbiza = new Route("Standard Sail", 400, new Island[] { cyprus, ibiza },
				"A chance to take a break form the perils of the seas.");
		cyprusAndIbiza.constructProbabilityMap(0, 0, 10);

		Route sicilyAndIbizaSwim = new Route("Swimmers Seas", 300, new Island[] { sicily, ibiza },
				"Stranded sailors galore! Maybe you can rescue one.");
		sicilyAndIbizaSwim.constructProbabilityMap(15, 15, 100);
		Route sicilyAndIbizaWobble = new Route("Wobbly Waters", 170, new Island[] { sicily, ibiza },
				"REasonably short. Reasonably risky.");
		sicilyAndIbizaWobble.constructProbabilityMap(35, 40, 50);

		Route sicilyAndCorsica = new Route("Average Pass", 20, new Island[] { sicily, corsica },
				"The odd pirate, a storm here and there.");
		sicilyAndCorsica.constructProbabilityMap(30, 25, 30);

		Route sicilyAndMalta = new Route("Pirate Pass", 10, new Island[] { sicily, malta },
				"Flat water and a short travel are loved by pirates too!");
		sicilyAndMalta.constructProbabilityMap(75, 0, 35);

		Route maltaAndCorsicaPirate = new Route("Pirate Coast", 50, new Island[] { malta, corsica },
				"Many a pirates favourite route!");
		maltaAndCorsicaPirate.constructProbabilityMap(100, 0, 0);
		Route maltaAndCorsicaSafe = new Route("Boring Detour", 10, new Island[] { malta, corsica },
				"Scared of the pirates? Avoid them by taking a lengthy detour.");
		maltaAndCorsicaSafe.constructProbabilityMap(0, 5, 10);

		Route maltaAndIbizaWavy = new Route("Wavy waters", 200, new Island[] { malta, ibiza },
				"A wavy route, with a decent chance of storms and the odd brave pirate.");
		maltaAndIbizaWavy.constructProbabilityMap(20, 60, 50);
		Route maltaAndIbizaPirate = new Route("Wavy waters", 200, new Island[] { malta, ibiza },
				"A calmer route, prefered by the pirates.");
		maltaAndIbizaPirate.constructProbabilityMap(60, 20, 50);

		Route ibizaAndCorsica = new Route("Mild Sail", 300, new Island[] { ibiza, corsica },
				"A hintof calm in the chaos.");
		ibizaAndCorsica.constructProbabilityMap(5, 5, 5);

		return new Route[] { cyprusAndSicilySafe, cyprusAndSicilyStormy, cyprusAndCorsicaFast, cyprusAndCorsicaSlow,
				cyprusAndMalta, cyprusAndIbiza, sicilyAndIbizaSwim, sicilyAndIbizaWobble, sicilyAndCorsica,
				sicilyAndMalta, maltaAndCorsicaPirate, maltaAndCorsicaSafe, maltaAndIbizaPirate, ibizaAndCorsica };

	}

	/**
	 * Method for creating all the sellCatalogues for a game
	 * 
	 * @return ArrayList<HashMap<String, HashMap<String, Integer>>> contains all the
	 *         sellCatalogues for every Island in the game
	 */
	public static ArrayList<HashMap<String, HashMap<String, Integer>>> createSellCatalogues() {
		/*
		 * Items that can be sold, keep the number small for simplicity 3 types of goods
		 * for simplicity, makes types of stores easy to to. Goods:, Gold, spaceTaken =
		 * 5 /exotic Large Chest, spaceTaken = 10 /exotic Treasure Map, spaceTaken = 5
		 * /exotic Lime spaceTaken = 1 /med Bandages spaceTaken = 2 /med Special Tea
		 * spaceTaken = 3 /med Tomato Sauce, spaceTaken = 1 /foods Turtle Meat
		 * spaceTaken = 2 /foods Bottle o' Rum, spaceTaken = 2 /foods
		 * 
		 * Upgrades: Cannon defenseBoost = 10 CrowsNest defenseBoost = 5 Armour
		 * defenseBoost = 7 Telescope defenseBoost = 2
		 * 
		 */
		// NOTE stores do not buy upgrades!

		// TODO need to balance out all the prices!!

		ArrayList<HashMap<String, HashMap<String, Integer>>> sellCatalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();

		// every store buys and sells 5 items, for simplicity
		
		// Create Gold sell properties for each island store
		HashMap<String, Integer> goldPropertiesCyprus = new HashMap<String, Integer>();
		goldPropertiesCyprus.put("spaceTaken", 2);
		goldPropertiesCyprus.put("price", 25);
		HashMap<String, Integer> goldPropertiesMalta = new HashMap<String, Integer>();
		goldPropertiesMalta.put("spaceTaken", 0);
		goldPropertiesMalta.put("price", 35);
		HashMap<String, Integer> goldPropertiesSicily = new HashMap<String, Integer>();
		goldPropertiesSicily.put("spaceTaken", 2);
		goldPropertiesSicily.put("price", 45);
		
		// Create Teasure map sell properties for each island store
		HashMap<String, Integer> treasureMapPropertiesCyprus = new HashMap<String, Integer>();
		treasureMapPropertiesCyprus.put("spaceTaken", 3);
		treasureMapPropertiesCyprus.put("price", 4);
		HashMap<String, Integer> treasureMapPropertiesMalta = new HashMap<String, Integer>();
		treasureMapPropertiesMalta.put("spaceTaken", 3);
		treasureMapPropertiesMalta.put("price", 4);
		HashMap<String, Integer> treasureMapPropertiesCorsica = new HashMap<String, Integer>();
		treasureMapPropertiesCorsica.put("spaceTaken", 3);
		treasureMapPropertiesCorsica.put("price", 4);

		// Create Canon sell properties for each island store
		HashMap<String, Integer> canonPropertiesCyprus = new HashMap<String, Integer>();
		canonPropertiesCyprus.put("spaceTaken", 0);
		canonPropertiesCyprus.put("price", 50);
		canonPropertiesCyprus.put("defenseBoost", 10);
		HashMap<String, Integer> canonPropertiesCorsica = new HashMap<String, Integer>();
		canonPropertiesCorsica.put("spaceTaken", 0);
		canonPropertiesCorsica.put("price", 30);
		canonPropertiesCorsica.put("defenseBoost", 7);
		
		// Create armour sell properties for each store	
		HashMap<String, Integer> armourPropertiesCyprus = new HashMap<String, Integer>();
		armourPropertiesCyprus.put("spaceTaken", 0);
		armourPropertiesCyprus.put("price", 30);
		armourPropertiesCyprus.put("defenseBoost", 7);
		HashMap<String, Integer> armourPropertiesMalta = new HashMap<String, Integer>();
		armourPropertiesMalta.put("spaceTaken", 0);
		armourPropertiesMalta.put("price", 10);
		armourPropertiesMalta.put("defenseBoost", 7);
		
		// Create tomato sauce sell properties for each store
		HashMap<String, Integer> tomatoSaucePropertiesSicily = new HashMap<String, Integer>();
		tomatoSaucePropertiesSicily.put("spaceTaken", 1);
		tomatoSaucePropertiesSicily.put("price", 3);
		HashMap<String, Integer> tomatoSaucePropertiesCorsica = new HashMap<String, Integer>();
		tomatoSaucePropertiesCorsica.put("spaceTaken", 1);
		tomatoSaucePropertiesCorsica.put("price", 3);
		
		// Create crows-nest sell properties or crows-nest for each store
		HashMap<String, Integer> crowsNestPropertiesCyprus = new HashMap<String, Integer>();
		crowsNestPropertiesCyprus.put("spaceTaken", 0);
		crowsNestPropertiesCyprus.put("price", 10);
		crowsNestPropertiesCyprus.put("defenseBoost", 2);
		
		// Create Bottle O Rum sell properties for each store
		HashMap<String, Integer> bottleORumPropertiesSicily = new HashMap<String, Integer>();
		bottleORumPropertiesSicily.put("spaceTaken", 3);
		bottleORumPropertiesSicily.put("price", 4);
		HashMap<String, Integer> bottleORumPropertiesIbiza = new HashMap<String, Integer>();
		bottleORumPropertiesIbiza.put("spaceTaken", 0);
		bottleORumPropertiesIbiza.put("price", 30);
		
		// Create turtle meat sell properties for each store
		HashMap<String, Integer> turtleMeatPropertiesSicily = new HashMap<String, Integer>();
		turtleMeatPropertiesSicily.put("spaceTaken", 0);
		turtleMeatPropertiesSicily.put("price", 50);
		
		// Create special tea sell properties for each store
		HashMap<String, Integer> specialTeaPropertiesCorsica = new HashMap<String, Integer>();
		specialTeaPropertiesCorsica.put("spaceTaken", 0);
		specialTeaPropertiesCorsica.put("price", 50);
		HashMap<String, Integer> specialTeaPropertiesIbiza = new HashMap<String, Integer>();
		specialTeaPropertiesIbiza.put("spaceTaken", 3);
		specialTeaPropertiesIbiza.put("price", 4);
		
		// Create Lime properties for each store
		HashMap<String, Integer> limePropertiesSicily = new HashMap<String, Integer>();
		limePropertiesSicily.put("spaceTaken", 0);
		limePropertiesSicily.put("price", 10);
		HashMap<String, Integer> limePropertiesIbiza = new HashMap<String, Integer>();
		limePropertiesIbiza.put("spaceTaken", 0);
		limePropertiesIbiza.put("price", 50);
		
		// Create large chest sell properties for each store
		HashMap<String, Integer> largeChestPropertiesMalta = new HashMap<String, Integer>();
		largeChestPropertiesMalta.put("spaceTaken", 1);
		largeChestPropertiesMalta.put("price", 3);
		HashMap<String, Integer> largeChestPropertiesIbiza = new HashMap<String, Integer>();
		largeChestPropertiesIbiza.put("spaceTaken", 0);
		largeChestPropertiesIbiza.put("price", 10);
		largeChestPropertiesIbiza.put("defenseBoost", 7);
		
		// Create bandages sell properties for each store
		HashMap<String, Integer> bandagesPropertiesMalta = new HashMap<String, Integer>();
		bandagesPropertiesMalta.put("spaceTaken", 0);
		bandagesPropertiesMalta.put("price", 30);
		HashMap<String, Integer> bandagesPropertiesIbiza = new HashMap<String, Integer>();
		bandagesPropertiesIbiza.put("spaceTaken", 0);
		bandagesPropertiesIbiza.put("price", 30);
		
		// Create telescope sell properties for each store
		HashMap<String, Integer> telescopePropertiesCorsica = new HashMap<String, Integer>();
		telescopePropertiesCorsica.put("spaceTaken", 0);
		telescopePropertiesCorsica.put("price", 10);
		telescopePropertiesCorsica.put("defenseBoost", 3);
		
		
		// Create buy catalouge for Cyprus, specialises in upgrades
		HashMap<String, HashMap<String, Integer>> sellCatalogueCyprus = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueCyprus.put("Gold", goldPropertiesCyprus);
		sellCatalogueCyprus.put("Treasure-Map", treasureMapPropertiesCyprus);
		sellCatalogueCyprus.put("Canon(upgrade)", canonPropertiesCyprus);
		sellCatalogueCyprus.put("Crows-Nest(upgrade)", crowsNestPropertiesCyprus);
		sellCatalogueCyprus.put("Armour(upgrade)", armourPropertiesCyprus);
		sellCatalogues.add(sellCatalogueCyprus);

		// Create sell catalogue for Sicily specialises in food
		HashMap<String, HashMap<String, Integer>> sellCatalogueSicily = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueSicily.put("Tomato-Sauce", tomatoSaucePropertiesSicily);
		sellCatalogueSicily.put("Bottle-O-Rum", bottleORumPropertiesSicily);
		sellCatalogueSicily.put("Turtle-Meat", turtleMeatPropertiesSicily);
		sellCatalogueSicily.put("Gold", goldPropertiesSicily);
		sellCatalogueSicily.put("Lime", limePropertiesSicily);
		sellCatalogues.add(sellCatalogueSicily);

		// Create sell catalogue for corsica specialises in everything
		HashMap<String, HashMap<String, Integer>> sellCatalogueCorsica = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueCorsica.put("Tomato-Sauce", tomatoSaucePropertiesCorsica);
		sellCatalogueCorsica.put("Treasure-Map", treasureMapPropertiesCorsica);
		sellCatalogueCorsica.put("Special-Tea", specialTeaPropertiesCorsica);
		sellCatalogueCorsica.put("Canon(upgrade)", canonPropertiesCorsica);
		sellCatalogueCorsica.put("Telescope(upgrade)", telescopePropertiesCorsica);
		sellCatalogues.add(sellCatalogueCorsica);

		// Create sell catalogue for malta specialises in Exotic stuff
		HashMap<String, HashMap<String, Integer>> sellCatalogueMalta = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueMalta.put("Large-Chest", largeChestPropertiesMalta);
		sellCatalogueMalta.put("Treasure-Map", treasureMapPropertiesMalta);
		sellCatalogueMalta.put("Gold", goldPropertiesMalta);
		sellCatalogueMalta.put("Bandages", bandagesPropertiesMalta);
		sellCatalogueMalta.put("Armour(upgrade)", armourPropertiesMalta);
		sellCatalogues.add(sellCatalogueMalta);

		// Create sell catalogue for ibiza
		HashMap<String, HashMap<String, Integer>> sellCatalogueIbiza = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueIbiza.put("Bandages", bandagesPropertiesIbiza);
		sellCatalogueIbiza.put("Special-Tea", specialTeaPropertiesIbiza);
		sellCatalogueIbiza.put("Lime", limePropertiesIbiza);
		sellCatalogueIbiza.put("Bottle-O-Rum", bottleORumPropertiesIbiza);
		sellCatalogueIbiza.put("Large-Chest", largeChestPropertiesIbiza);
		sellCatalogues.add(sellCatalogueIbiza);

		return sellCatalogues;
	}

	/**
	 * Method for creating all the buyCatalgues for a game
	 * 
	 * @return ArrayList<HashMap<String, HashMap<String, Integer>>> contains all the
	 *         buyCatalogues for every Island in the game
	 */
	public static ArrayList<HashMap<String, HashMap<String, Integer>>> createBuyCatalogues() {
		/*
		 * Items that can be sold, keep the number small for simplicity Goods:, Gold,
		 * spaceTaken = 5 /exotic Large Chest, spaceTaken = 10 /exotic Treasure Map,
		 * spaceTaken = 5 /exotic Lime spaceTaken = 1 /med Bandages spaceTaken = 2 /med
		 * Special Tea spaceTaken = 3 /med Tomato Sauce, spaceTaken = 1 /foods Turtle
		 * Meat spaceTaken = 2 /foods Bottle o' Rum, spaceTaken = 2 /foods
		 * 
		 */

		ArrayList<HashMap<String, HashMap<String, Integer>>> buyCatalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();

		// Create sell catalogue for cyprus, becuase it only sells ugrades, can buy a
		// bit of everything
		HashMap<String, HashMap<String, Integer>> buyCatalogueCyprus = new HashMap<String, HashMap<String, Integer>>();
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
		buyCatalogueCyprus.put("Gold", goldPropertiesCyprus);
		buyCatalogueCyprus.put("Silver", silverPropertiesCyprus);
		buyCatalogueCyprus.put("Banana", bananaPropertiesCyprus);
		buyCatalogueCyprus.put("Bandages", bandagesPropertiesCyprus);
		buyCatalogueCyprus.put("Lime", limePropertiesCyprus);
		buyCatalogues.add(buyCatalogueCyprus);

		// Create sell catalogue for sicily, specialises in food.
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
		buyCatalogueSicily.put("Tomato-Sauce", tomatoSaucePropertiesSicily);
		buyCatalogueSicily.put("Bottle-O'-Rum", bottleORumPropertiesSicily);
		buyCatalogueSicily.put("Turtle-Meat", turtleMeatPropertiesSicily);
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
		buyCatalogueCorsica.put("Tomato-Sauce", tomatoSaucePropertiesCorsica);
		buyCatalogueCorsica.put("Treasure-Map", treasureMapPropertiesCorsica);
		buyCatalogueCorsica.put("Special-Tea", specialTeaPropertiesCorsica);
		buyCatalogues.add(buyCatalogueCorsica);

		// Create sell catalogue for malta
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
		buyCatalogueMalta.put("Large-Chest", largeChestPropertiesMalta);
		buyCatalogueMalta.put("Treasure-Map", treasureMapPropertiesMalta);
		buyCatalogueMalta.put("Gold", goldPropertiesMalta);
		buyCatalogueMalta.put("Bandages", bandagesPropertiesMalta);
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
		buyCatalogueIbiza.put("Bandages", bandagesPropertiesIbiza);
		buyCatalogueIbiza.put("Special-Tea", specialTeaPropertiesIbiza);
		buyCatalogueIbiza.put("Lime", limePropertiesIbiza);
		buyCatalogueIbiza.put("Bottle-O-Rum", bottleORumPropertiesIbiza);
		buyCatalogueIbiza.put("Large-Chest", largeChestPropertiesIbiza);
		buyCatalogues.add(buyCatalogueIbiza);

		return buyCatalogues;
	}
}
