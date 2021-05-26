package main;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;

import coreClasses.*;
import uiClasses.*;
import uiClasses.gui.Gui;

/**
 * The entry point to the program, creates instances of the classes required to
 * start the game, then handles control to the c
 * lass for the desired ui.
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
		Pirates pirates = new Pirates(50);

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
		
		if (args.length > 0 && (args[0].equals("cmd"))) {
			ui = new CmdLineUi();
			GameEnvironment gameEnrvironment = new GameEnvironment(islands, shipArray, ui, pirates, rescuedSailors);
			ui.setup(gameEnrvironment);
		} else {
			ui = new Gui();
			GameEnvironment gameEnrvironment = new GameEnvironment(islands, shipArray, ui, pirates, rescuedSailors);
			ui.setup(gameEnrvironment);
		}
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
		Ship ship1 = new Ship("Black Pearl",   100,  7,  30);
		Ship ship2 = new Ship("Queen Mary",     30,  8,  20);
		Ship ship3 = new Ship("HMNZS Aotearoa", 80,  5,  40);
		Ship ship4 = new Ship("Titanic",        50,  6,  10);
		return new Ship[] { ship1, ship2, ship3, ship4 };
	}

	/**
	 * Method for creating the islands for a game
	 * 
	 * @param buyCatalogues  ArrayList containing HashMaps for the buyCatalogues for every island to be
	 *                       created
	 * @param sellCatalogues ArrayList containing HashMaps for the sellCatalogues for every island to be
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
		Store sicilyStore = new Store("Pasta and Co", "tasty food", sellCatalogues.get(1), buyCatalogues.get(1));
		Island sicily = new Island("Sicily", sicilyStore, "Looks like a football");
		sicilyStore.setStoreIsland(sicily);

		// Create Corsica Island and it's Store
		Store corsicaStore = new Store("Napoleans", "a bit of everything", sellCatalogues.get(2), buyCatalogues.get(2));
		Island corsica = new Island("Corsica", corsicaStore, "Has cool beaches");
		corsicaStore.setStoreIsland(corsica);

		// Create Malta Island and it's Store
		Store maltaStore = new Store("Duty Free Store", "exotic goods", sellCatalogues.get(3), buyCatalogues.get(3));
		Island malta = new Island("Malta", maltaStore, "Good for the rich");
		maltaStore.setStoreIsland(malta);

		// Create Ibiza Island and it's Store
		Store ibizaStore = new Store("Party Store", "pirate apparel", sellCatalogues.get(4), buyCatalogues.get(4));
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
				"The most direct route between Cyprus and Sicily, but watch out - its stormy!");
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
				"Reasonably short. Reasonably risky.");
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
		Route maltaAndIbizaPirate = new Route("Piratey Pass", 200, new Island[] { malta, ibiza },
				"A calmer route, prefered by the pirates.");
		maltaAndIbizaPirate.constructProbabilityMap(60, 20, 50);

		Route ibizaAndCorsica = new Route("Mild Sail", 300, new Island[] { ibiza, corsica },
				"A hint of calm in the chaos.");
		ibizaAndCorsica.constructProbabilityMap(5, 5, 5);

		return new Route[] { cyprusAndSicilySafe, cyprusAndSicilyStormy, cyprusAndCorsicaFast, cyprusAndCorsicaSlow,
				cyprusAndMalta, cyprusAndIbiza, sicilyAndIbizaSwim, sicilyAndIbizaWobble, sicilyAndCorsica,
				sicilyAndMalta, maltaAndCorsicaPirate, maltaAndCorsicaSafe, maltaAndIbizaPirate, ibizaAndCorsica };

	}

	/**
	 * Method for creating all the sellCatalogues for a game
	 * 
	 * @return ArrayList of HashMaps for the
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

		ArrayList<HashMap<String, HashMap<String, Integer>>> sellCatalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();

		// every store buys and sells 5 items, for simplicity
		// Max item space in a ship is 100

		// Create Gold sell properties for each island store
		HashMap<String, Integer> goldPropertiesCyprus = new HashMap<String, Integer>();
		goldPropertiesCyprus.put("spaceTaken", 4);
		goldPropertiesCyprus.put("price", 20);
		HashMap<String, Integer> goldPropertiesMalta = new HashMap<String, Integer>();
		goldPropertiesMalta.put("spaceTaken", 4);
		goldPropertiesMalta.put("price", 10);
		HashMap<String, Integer> goldPropertiesSicily = new HashMap<String, Integer>();
		goldPropertiesSicily.put("spaceTaken", 4);
		goldPropertiesSicily.put("price", 15);
		
		// Create Teasure map sell properties for each island store
		HashMap<String, Integer> treasureMapPropertiesCyprus = new HashMap<String, Integer>();
		treasureMapPropertiesCyprus.put("spaceTaken", 5);
		treasureMapPropertiesCyprus.put("price", 50);
		HashMap<String, Integer> treasureMapPropertiesMalta = new HashMap<String, Integer>();
		treasureMapPropertiesMalta.put("spaceTaken", 5);
		treasureMapPropertiesMalta.put("price", 25);
		HashMap<String, Integer> treasureMapPropertiesCorsica = new HashMap<String, Integer>();
		treasureMapPropertiesCorsica.put("spaceTaken", 5);
		treasureMapPropertiesCorsica.put("price", 40);
		
		// Create tomato sauce sell properties for each store
		HashMap<String, Integer> tomatoSaucePropertiesSicily = new HashMap<String, Integer>();
		tomatoSaucePropertiesSicily.put("spaceTaken", 1);
		tomatoSaucePropertiesSicily.put("price", 5);
		HashMap<String, Integer> tomatoSaucePropertiesCorsica = new HashMap<String, Integer>();
		tomatoSaucePropertiesCorsica.put("spaceTaken", 1);
		tomatoSaucePropertiesCorsica.put("price", 10);
		
		// Create Bottle O Rum sell properties for each store
		HashMap<String, Integer> bottleORumPropertiesSicily = new HashMap<String, Integer>();
		bottleORumPropertiesSicily.put("spaceTaken", 2);
		bottleORumPropertiesSicily.put("price", 8);
		HashMap<String, Integer> bottleORumPropertiesIbiza = new HashMap<String, Integer>();
		bottleORumPropertiesIbiza.put("spaceTaken", 2);
		bottleORumPropertiesIbiza.put("price", 15);
		
		// Create turtle meat sell properties for each store
		HashMap<String, Integer> turtleMeatPropertiesSicily = new HashMap<String, Integer>();
		turtleMeatPropertiesSicily.put("spaceTaken", 1);
		turtleMeatPropertiesSicily.put("price", 12);
		HashMap<String, Integer> turtleMeatPropertiesCyprus = new HashMap<String, Integer>();
		turtleMeatPropertiesCyprus.put("spaceTaken", 1);
		turtleMeatPropertiesCyprus.put("price", 25);
		
		// Create special tea sell properties for each store
		HashMap<String, Integer> woodenLegPropertiesCorsica = new HashMap<String, Integer>();
		woodenLegPropertiesCorsica.put("spaceTaken", 3);
		woodenLegPropertiesCorsica.put("price", 20);
		HashMap<String, Integer> woodenLegPropertiesIbiza = new HashMap<String, Integer>();
		woodenLegPropertiesIbiza.put("spaceTaken", 3);
		woodenLegPropertiesIbiza.put("price", 5);
		HashMap<String, Integer> woodenLegPropertiesMalta = new HashMap<String, Integer>();
		woodenLegPropertiesMalta.put("spaceTaken", 3);
		woodenLegPropertiesMalta.put("price", 10);
		
		// Create Lime properties for each store
		HashMap<String, Integer> limePropertiesSicily = new HashMap<String, Integer>();
		limePropertiesSicily.put("spaceTaken", 1);
		limePropertiesSicily.put("price", 10);
		HashMap<String, Integer> limePropertiesIbiza = new HashMap<String, Integer>();
		limePropertiesIbiza.put("spaceTaken", 1);
		limePropertiesIbiza.put("price", 20);
		
		// Create large chest sell properties for each store
		HashMap<String, Integer> largeChestPropertiesMalta = new HashMap<String, Integer>();
		largeChestPropertiesMalta.put("spaceTaken", 8);
		largeChestPropertiesMalta.put("price", 10);
		HashMap<String, Integer> largeChestPropertiesIbiza = new HashMap<String, Integer>();
		largeChestPropertiesIbiza.put("spaceTaken", 8);
		largeChestPropertiesIbiza.put("price", 30);
		
		// Create bandages sell properties for each store
		HashMap<String, Integer> pirateHatPropertiesMalta = new HashMap<String, Integer>();
		pirateHatPropertiesMalta.put("spaceTaken", 2);
		pirateHatPropertiesMalta.put("price", 30);
		HashMap<String, Integer> pirateHatPropertiesIbiza = new HashMap<String, Integer>();
		pirateHatPropertiesIbiza.put("spaceTaken", 2);
		pirateHatPropertiesIbiza.put("price", 10);
		
		// Create eye patch sell properties for each store
		HashMap<String, Integer> eyePatchPropertiesCyprus = new HashMap<String, Integer>();
		eyePatchPropertiesCyprus.put("spaceTaken", 1);
		eyePatchPropertiesCyprus.put("price", 10);
		HashMap<String, Integer> eyePatchPropertiesIbiza = new HashMap<String, Integer>();
		eyePatchPropertiesIbiza.put("spaceTaken", 1);
		eyePatchPropertiesIbiza.put("price", 5);
		
		// Create Canon sell properties for each island store
		HashMap<String, Integer> canonPropertiesCyprus = new HashMap<String, Integer>();
		canonPropertiesCyprus.put("spaceTaken", 0);
		canonPropertiesCyprus.put("price", 300);
		canonPropertiesCyprus.put("defenseBoost", 5);
		HashMap<String, Integer> canonPropertiesCorsica = new HashMap<String, Integer>();
		canonPropertiesCorsica.put("spaceTaken", 0);
		canonPropertiesCorsica.put("price", 600);
		canonPropertiesCorsica.put("defenseBoost", 5);
		
		// Create armour sell properties for each store	
		HashMap<String, Integer> armourPropertiesCyprus = new HashMap<String, Integer>();
		armourPropertiesCyprus.put("spaceTaken", 0);
		armourPropertiesCyprus.put("price", 400);
		armourPropertiesCyprus.put("defenseBoost", 3);
		HashMap<String, Integer> armourPropertiesMalta = new HashMap<String, Integer>();
		armourPropertiesMalta.put("spaceTaken", 0);
		armourPropertiesMalta.put("price", 240);
		armourPropertiesMalta.put("defenseBoost", 3);
		
		// Create crows-nest sell properties or crows-nest for each store
		HashMap<String, Integer> crowsNestPropertiesCyprus = new HashMap<String, Integer>();
		crowsNestPropertiesCyprus.put("spaceTaken", 0);
		crowsNestPropertiesCyprus.put("price", 100);
		crowsNestPropertiesCyprus.put("defenseBoost", 1);
		
		// Create telescope sell properties for each store
		HashMap<String, Integer> telescopePropertiesCorsica = new HashMap<String, Integer>();
		telescopePropertiesCorsica.put("spaceTaken", 0);
		telescopePropertiesCorsica.put("price", 200);
		telescopePropertiesCorsica.put("defenseBoost", 2);

		// Create sell catalouge for Cyprus, specialises in upgrades
		HashMap<String, HashMap<String, Integer>> sellCatalogueCyprus = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueCyprus.put("Gold",                goldPropertiesCyprus);
		sellCatalogueCyprus.put("Treasure-Map",        treasureMapPropertiesCyprus);
		sellCatalogueCyprus.put("Canon(upgrade)",      canonPropertiesCyprus);
		sellCatalogueCyprus.put("Crows-Nest(upgrade)", crowsNestPropertiesCyprus);
		sellCatalogueCyprus.put("Armour(upgrade)",     armourPropertiesCyprus);
		sellCatalogueCyprus.put("Eye-Patch",           eyePatchPropertiesCyprus);
		sellCatalogueCyprus.put("Turtle-Meat",         turtleMeatPropertiesCyprus);
		sellCatalogues.add(sellCatalogueCyprus);

		// Create sell catalogue for Sicily specialises in food
		HashMap<String, HashMap<String, Integer>> sellCatalogueSicily = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueSicily.put("Tomato-Sauce", tomatoSaucePropertiesSicily);
		sellCatalogueSicily.put("Bottle-O-Rum", bottleORumPropertiesSicily);
		sellCatalogueSicily.put("Turtle-Meat",  turtleMeatPropertiesSicily);
		sellCatalogueSicily.put("Gold",         goldPropertiesSicily);
		sellCatalogueSicily.put("Lime",         limePropertiesSicily);
		sellCatalogues.add(sellCatalogueSicily);

		// Create sell catalogue for corsica specialises in everything
		HashMap<String, HashMap<String, Integer>> sellCatalogueCorsica = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueCorsica.put("Tomato-Sauce",       tomatoSaucePropertiesCorsica);
		sellCatalogueCorsica.put("Treasure-Map",       treasureMapPropertiesCorsica);
		sellCatalogueCorsica.put("Wooden-Leg",         woodenLegPropertiesCorsica);
		sellCatalogueCorsica.put("Canon(upgrade)",     canonPropertiesCorsica);
		sellCatalogueCorsica.put("Telescope(upgrade)", telescopePropertiesCorsica);
		sellCatalogues.add(sellCatalogueCorsica);

		// Create sell catalogue for malta specialises in Exotic stuff
		HashMap<String, HashMap<String, Integer>> sellCatalogueMalta = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueMalta.put("Large-Chest",     largeChestPropertiesMalta);
		sellCatalogueMalta.put("Treasure-Map",    treasureMapPropertiesMalta);
		sellCatalogueMalta.put("Gold",            goldPropertiesMalta);
		sellCatalogueMalta.put("Pirate-Hat",      pirateHatPropertiesMalta);
		sellCatalogueMalta.put("Armour(upgrade)", armourPropertiesMalta);
		sellCatalogueMalta.put("Wooden-Leg", woodenLegPropertiesMalta);
		sellCatalogues.add(sellCatalogueMalta);

		// Create sell catalogue for ibiza specialises pirate apparel
		HashMap<String, HashMap<String, Integer>> sellCatalogueIbiza = new HashMap<String, HashMap<String, Integer>>();
		sellCatalogueIbiza.put("Pirate-Hat",   pirateHatPropertiesIbiza);
		sellCatalogueIbiza.put("Eye-Patch",    eyePatchPropertiesIbiza);
		sellCatalogueIbiza.put("Wooden-Leg",   woodenLegPropertiesIbiza);
		sellCatalogueIbiza.put("Lime",         limePropertiesIbiza);
		sellCatalogueIbiza.put("Bottle-O-Rum", bottleORumPropertiesIbiza);
		sellCatalogueIbiza.put("Large-Chest",  largeChestPropertiesIbiza);
		sellCatalogues.add(sellCatalogueIbiza);

		return sellCatalogues;
	}

	/**
	 * Method for creating all the buyCatalgues for a game
	 * 
	 * @return ArrayList of HashMaps for all the
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
		
		// Max item space in a ship is 100
		/* Make it so a store buys back items at the exact same price that they sell them
		 * makes it easier to coordinate prices
		 * 
		 * or we could have a small penalty for this, say 10 bucks, just putting it out there, its all relative to
		 * how much we set the price, aim for around 10%, or just a small fee. 
		 */
		
		ArrayList<HashMap<String, HashMap<String, Integer>>> buyCatalogues = new ArrayList<HashMap<String, HashMap<String, Integer>>>();
		
		// Create buy catalogue properties for Gold for each store
		HashMap<String, Integer> goldBuyPropertiesCyprus = new HashMap<String, Integer>();
		goldBuyPropertiesCyprus.put("spaceTaken", 4);
		goldBuyPropertiesCyprus.put("price", 20);
		HashMap<String, Integer> goldBuyPropertiesMalta = new HashMap<String, Integer>();
		goldBuyPropertiesMalta.put("spaceTaken", 4);
		goldBuyPropertiesMalta.put("price", 10);
		HashMap<String, Integer> goldBuyPropertiesSicily = new HashMap<String, Integer>();
		goldBuyPropertiesSicily.put("spaceTaken", 4);
		goldBuyPropertiesSicily.put("price", 15);
		
		// Create buy catalogue properties for Treasure Map for each store
		HashMap<String, Integer> treasureMapBuyPropertiesCyprus = new HashMap<String, Integer>();
		treasureMapBuyPropertiesCyprus.put("spaceTaken", 5);
		treasureMapBuyPropertiesCyprus.put("price", 50);
		HashMap<String, Integer> treasureMapBuyPropertiesMalta = new HashMap<String, Integer>();
		treasureMapBuyPropertiesMalta.put("spaceTaken", 5);
		treasureMapBuyPropertiesMalta.put("price", 25);
		HashMap<String, Integer> treasureMapBuyPropertiesCorsica = new HashMap<String, Integer>();
		treasureMapBuyPropertiesCorsica.put("spaceTaken", 5);
		treasureMapBuyPropertiesCorsica.put("price", 40);
		
		// Create buy catalogue properties for Tomato Sauce for each store
		HashMap<String, Integer> tomatoSauceBuyPropertiesSicily = new HashMap<String, Integer>();
		tomatoSauceBuyPropertiesSicily.put("spaceTaken", 1);
		tomatoSauceBuyPropertiesSicily.put("price", 5);
		HashMap<String, Integer> tomatoSauceBuyPropertiesCorsica = new HashMap<String, Integer>();
		tomatoSauceBuyPropertiesCorsica.put("spaceTaken", 1);
		tomatoSauceBuyPropertiesCorsica.put("price", 10);
		
		// Create buy catalogue properties for bottle o rum for each store
		HashMap<String, Integer> bottleORumBuyPropertiesSicily = new HashMap<String, Integer>();
		bottleORumBuyPropertiesSicily.put("spaceTaken", 2);
		bottleORumBuyPropertiesSicily.put("price", 8);
		HashMap<String, Integer> bottleORumBuyPropertiesIbiza = new HashMap<String, Integer>();
		bottleORumBuyPropertiesIbiza.put("spaceTaken", 2);
		bottleORumBuyPropertiesIbiza.put("price", 15);
		
		// Create buy catalogue properties for Turtle meat for each store
		HashMap<String, Integer> turtleMeatBuyPropertiesSicily = new HashMap<String, Integer>();
		turtleMeatBuyPropertiesSicily.put("spaceTaken", 1);
		turtleMeatBuyPropertiesSicily.put("price", 12);
		HashMap<String, Integer> turtleMeatBuyPropertiesCyprus = new HashMap<String, Integer>();
		turtleMeatBuyPropertiesCyprus.put("spaceTaken", 1);
		turtleMeatBuyPropertiesCyprus.put("price", 25);
		
		// Create buy catalogue properties for wooden leg for each store
		HashMap<String, Integer> woodenLegBuyPropertiesCorsica = new HashMap<String, Integer>();
		woodenLegBuyPropertiesCorsica.put("spaceTaken", 3);
		woodenLegBuyPropertiesCorsica.put("price", 20);
		HashMap<String, Integer> woodenLegBuyPropertiesIbiza = new HashMap<String, Integer>();
		woodenLegBuyPropertiesIbiza.put("spaceTaken", 3);
		woodenLegBuyPropertiesIbiza.put("price", 5);
		HashMap<String, Integer> woodenLegBuyPropertiesMalta = new HashMap<String, Integer>();
		woodenLegBuyPropertiesMalta.put("spaceTaken", 3);
		woodenLegBuyPropertiesMalta.put("price", 10);
		
		// Create buy catalogue properties for lime for each store
		HashMap<String, Integer> limeBuyPropertiesSicily = new HashMap<String, Integer>();
		limeBuyPropertiesSicily.put("spaceTaken", 1);
		limeBuyPropertiesSicily.put("price", 10 );
		HashMap<String, Integer> limeBuyPropertiesIbiza = new HashMap<String, Integer>();
		limeBuyPropertiesIbiza.put("spaceTaken", 1);
		limeBuyPropertiesIbiza.put("price", 20);
		
		// Create buy catalogue properties for Large chest for each store
		HashMap<String, Integer> largeChestBuyPropertiesMalta = new HashMap<String, Integer>();
		largeChestBuyPropertiesMalta.put("spaceTaken", 8);
		largeChestBuyPropertiesMalta.put("price", 10);
		HashMap<String, Integer> largeChestBuyPropertiesIbiza = new HashMap<String, Integer>();
		largeChestBuyPropertiesIbiza.put("spaceTaken", 8);
		largeChestBuyPropertiesIbiza.put("price", 30);
		
		// Create buy catalogue properties for bandages for each store
		HashMap<String, Integer> pirateHatBuyPropertiesMalta = new HashMap<String, Integer>();
		pirateHatBuyPropertiesMalta.put("spaceTaken", 2);
		pirateHatBuyPropertiesMalta.put("price", 30);
		HashMap<String, Integer> pirateHatBuyPropertiesIbiza = new HashMap<String, Integer>();
		pirateHatBuyPropertiesIbiza.put("spaceTaken", 2);
		pirateHatBuyPropertiesIbiza.put("price", 10);
		
		// Create buy catalogue properties for eye patch for each store
		HashMap<String, Integer> eyePatchBuyPropertiesCyprus = new HashMap<String, Integer>();
		eyePatchBuyPropertiesCyprus.put("spaceTaken", 1);
		eyePatchBuyPropertiesCyprus.put("price", 10);
		HashMap<String, Integer> eyePatchBuyPropertiesIbiza = new HashMap<String, Integer>();
		eyePatchBuyPropertiesIbiza.put("spaceTaken", 1);
		eyePatchBuyPropertiesIbiza.put("price", 5);
		
		// Create sell catalogue for cyprus, becuase it only sells ugrades, can buy a bit of everything
		HashMap<String, HashMap<String, Integer>> buyCatalogueCyprus = new HashMap<String, HashMap<String, Integer>>();
		buyCatalogueCyprus.put("Gold",           goldBuyPropertiesCyprus);
		buyCatalogueCyprus.put("Treasure-Map",   treasureMapBuyPropertiesCyprus);
		buyCatalogueCyprus.put("Eye-Patch",      eyePatchBuyPropertiesCyprus);
		buyCatalogueCyprus.put("Turtle-Meat",    turtleMeatBuyPropertiesCyprus);
		buyCatalogues.add(buyCatalogueCyprus);
		
		// Create sell catalogue for sicily, specialises in food.
		HashMap<String, HashMap<String, Integer>> buyCatalogueSicily = new HashMap<String, HashMap<String, Integer>>();
		buyCatalogueSicily.put("Tomato-Sauce",  tomatoSauceBuyPropertiesSicily);
		buyCatalogueSicily.put("Bottle-O-Rum",  bottleORumBuyPropertiesSicily);
		buyCatalogueSicily.put("Turtle-Meat",   turtleMeatBuyPropertiesSicily);
		buyCatalogueSicily.put("Gold",          goldBuyPropertiesSicily);
		buyCatalogueSicily.put("Lime",          limeBuyPropertiesSicily);
		buyCatalogues.add(buyCatalogueSicily);

		// Create buy catalogue for corsica specialises in everything
		HashMap<String, HashMap<String, Integer>> buyCatalogueCorsica = new HashMap<String, HashMap<String, Integer>>();
		buyCatalogueCorsica.put("Tomato-Sauce", tomatoSauceBuyPropertiesCorsica);
		buyCatalogueCorsica.put("Treasure-Map", treasureMapBuyPropertiesCorsica);
		buyCatalogueCorsica.put("Wooden-Leg",   woodenLegBuyPropertiesCorsica);
		buyCatalogues.add(buyCatalogueCorsica);

		// Create sell catalogue for malta specialises in shiny things
		HashMap<String, HashMap<String, Integer>> buyCatalogueMalta = new HashMap<String, HashMap<String, Integer>>();
		buyCatalogueMalta.put("Large-Chest",  largeChestBuyPropertiesMalta);
		buyCatalogueMalta.put("Treasure-Map", treasureMapBuyPropertiesMalta);
		buyCatalogueMalta.put("Gold",         goldBuyPropertiesMalta);
		buyCatalogueMalta.put("Pirate-Hat",   pirateHatBuyPropertiesMalta);
		buyCatalogues.add(buyCatalogueMalta);

		// Create buy catalogue for ibiza specialises pirate apparel
		HashMap<String, HashMap<String, Integer>> buyCatalogueIbiza = new HashMap<String, HashMap<String, Integer>>();
		buyCatalogueIbiza.put("Pirate-Hat",   pirateHatBuyPropertiesIbiza);
		buyCatalogueIbiza.put("Wooden-Leg",   woodenLegBuyPropertiesIbiza);
		buyCatalogueIbiza.put("Lime",         limeBuyPropertiesIbiza);
		buyCatalogueIbiza.put("Bottle-O-Rum", bottleORumBuyPropertiesIbiza);
		buyCatalogueIbiza.put("Large-Chest",  largeChestBuyPropertiesIbiza);
		buyCatalogueIbiza.put("Eye-Patch",    eyePatchBuyPropertiesIbiza);
		buyCatalogues.add(buyCatalogueIbiza);

		return buyCatalogues;
	}
}