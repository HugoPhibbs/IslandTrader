package coreClasses;


import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.*;
import uiClasses.GameUi;

/* TODO
 * how do we do message handling of errors, since they are all caught GameEnvironment, should we use
 * getMessage(), or create the messages themselves within GameEnvironment?
 */

/**
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 23/4/21
 * @since 2/4/21
 */


/** TODO 
 * the main thing to remember is that we want to jeeo this all modular. 
 * so all the UI should be kept to game environment
 */


public class GameEnvironment {
	
	// TODO fyi where do we create a new ship? -hp
	// also i think sailShip should be implemented by game environment-see comment in ship class

	private Player player;
	private Island[] islandArray;
	private GameUi ui;
	private Ship ship;
	
	public GameEnvironment(Island[] islandArray, uiClasses.GameUi ui) {
		this.islandArray = islandArray;
		this.ui = ui;
	}
	
	public Player getPlayer() {return player;}
	
	public Island[] getIslandArray() {return islandArray;}
	
	public GameUi getUi() {return ui;}
	
	public void onSetupFinished(Player player, Ship ship) {
		this.player = player;
		this.ship = ship;
		ui.playGame();
	}
	
	
	// TODO: if you enter a invalid input and an int outside the vlaid range, in sequence, prints both error messages. fix this. 
	/**
	 * Method that handles all calls required to take a turn in the game. Displays
	 * the player's options, takes input as to what the player wants to do, then calls the
	 * appropriate method.
	 */	
	public void takeTurn() {
		
		String options = "Enter an actions number:\n1: View your money and days remaining.\n2: View the propeties of your ship.\n"
				+ "3: View the goods you have purchased.\n4: View the properties of each Island.\n"
				+ "5: Visit the store on " + player.getCurrentIsland() + " (current island).\n6: Set sail to another Island.";
		
		Scanner scan = new Scanner(System.in);
		System.out.println(options);
		
		int input = getActionInt(scan);
		
		while (input < 1 || input > 6) {
			System.out.println("Number entered didn't correspond with an action. Please enter a number between 1 and 6 (inlcusive).");
			input = getActionInt(scan);
		}
		
		scan.close();
		
		switch (input) {
			case 1: 
				// option to view the amount of money and days remaining
				System.out.println(String.format("%s has $%d and %d days remaining.",
						player.getName(), player.getMoneyBalance(), player.getDaysRemaining()));
				takeTurn();
				break;
			case 2:
				// option to view the properties of the ship
				// calls the toString method of the player's ship.
				System.out.println(player.getShip());
				takeTurn();
				break;
			case 3:
				// view the goods you have purchased
				System.out.println(3);
				break;
			case 4:
				// option to view properties of each island
				viewOtherIslands();
				break;
			case 5:
				// option to visit the store on the current island
				System.out.println(5);
				break;
			case 6:
				// setting sail to another island
				System.out.println(6);
				break;
		}
	}
	
	public int getActionInt(Scanner scan) {
		// TODO rename this method, so it can be used elsewhere when ever we need to 
		boolean successful = false;
		while (!successful)
			try {
				int input = scan.nextInt();	
				return input;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer.");
				scan = new Scanner(System.in);
			}
		return getActionInt(scan);
	}
	
	/**
	 * Method to handle calls to chooseOtherIsland. If an Island is returned, get Route is called.
	 */
	public void getIsland() {
		// Gets a new island or goes back to take turn
		try {
			Island newIsland = chooseOtherIsland();
			getRoute(newIsland);
		} catch (GoBackException e) {
			takeTurn();
			return;
		}	
	}
	
	/**
	 * Method to handle calls to Island.chooseRoute. If a route is chosen, the player sets sail along that route. 
	 * Alternatively the player chooses to go back, and getIsland is called. 
	 * @param newIsland Island that has been selected to travel to.
	 */
	public void getRoute(Island newIsland) {
		// Gets route to new island or goes back to reselect island
		try {
			Route selectedRoute = player.getCurrentIsland().chooseRoute(newIsland);
		} catch (GoBackException e) {
			takeTurn();
			return;
		}
		// call Ship.setSail() once implemented
		System.out.println("worked");
	}

	
	/**
	 * Prints a string representation of each island that is not the current island.
	 */
	public void viewOtherIslands() {
		ArrayList<Island> otherIslands = getOtherIslandsList();
		
		for (Island otherIsland: otherIslands) {
				System.out.println(otherIsland);
		}
		
		String input = TakeInput.inputString("To view more information about an island, enter the name of the island.");
		
		for (Island island: otherIslands) {
			if (island.getIslandName().toLowerCase() == input) {
				String islandInfo = "The island " + island.getIslandName() + " can be reached from your current island by the following routes:\n";
				islandInfo += player.getCurrentIsland().viewRoutes(island);  // TODO: separate view routes from choose route.
				// TODO: add info string about what the island's store buys and sells. 
			}
		}
	}
	
	public ArrayList<Island> getOtherIslandsList() {
		ArrayList<Island> otherIslands = new ArrayList<Island>();
		for (Island island: islandArray) {
			if (island != player.getCurrentIsland()) {
				otherIslands.add(island);
			}
		}
		return otherIslands;
	}
	
	public Island chooseOtherIsland() {
		
		ArrayList<Island> otherIslands = getOtherIslandsList();
		
		// delete this once viewOtherIslands is working
		for (Island otherIsland: otherIslands) {
				System.out.println(otherIsland);
		}
		
		String inputStr = TakeInput.inputString("To travel to an Island, enter the island's name. To go back please type \"back\"");
		
		if (inputStr.equals("back")) {
			throw new GoBackException();
		}
		else {
			for (Island otherIsland: otherIslands) {
				if (inputStr.equals(otherIsland.getIslandName().toLowerCase())) {
					return otherIsland;
				}
			}
		}
		
		System.out.println("Invalid Input");
		return chooseOtherIsland();
	}
	
	/**
	 * Called by takeTurn, presents the list of things the player can do in the store, takes input from the player as to 
	 * what they would like to do, then calls the appropriate method of the current island's store.
	 */
	public void visitTheStore() {
		/* TODO 
		 * implement error handling for buying and selling items
		 */
		String options = "Enter the action number:\n "
				+ "1. View and buy items that the store sells. \n " 
				+ "2. View and sell Items that the store buys. \n "
				+ "3. View previously bought items. \n"
				+ "4. View the amount of money that you have. \n"
				+ "5. Exit store.";
				
		Store currentStore = player.getCurrentIsland().getIslandStore();
				
		Scanner scanner = new Scanner(System.in);
		System.out.println(options);
				
		int entryInput = getActionInt(scanner);
				
		// If you add more options, make sure that you have accounted for this in while loop!
		while (!CheckValidInput.actionIntIsValid(entryInput, 1, 5)) {
			System.out.println("Invalid input, please enter a number between 1 and 5");
			entryInput = getActionInt(scanner);
		}
		
		// As a convention across the project, all printing is done by GameEnvironment
				
		switch (entryInput) {
		    case 1:
		    	// Get sellCatalogue and its size
		    	HashMap<String, HashMap<String, Integer>> sellCatalogue = currentStore.getSellCatalogue();
		    	int sellCatalogueSize = sellCatalogue.size();
		    	
		    	// Display on sale items
		    	System.out.println("Enter the number corresponding to the Item that you want to buy!");
		    	String sellDisplayString = Store.getDisplayString(sellCatalogue);
		    	System.out.println(sellDisplayString);
		    
		    	
		    	// number for chosen item
		    	int itemToSellNum= getActionInt(scanner);
		    	
		    	// Check that input of itemToSellNum is valid
		    	while (!CheckValidInput.actionIntIsValid(itemToSellNum, 1, sellCatalogueSize)) {
		    		System.out.println(String.format("Invalid input, please enter a number between 1 and %d.",sellCatalogueSize));
		    		itemToSellNum = getActionInt(scanner);
		    	}
		    	
		    	// Get name of chosen item
		    	String splitLine1 = (String) Array.get(sellDisplayString.split("\n"), itemToSellNum-1);
		    	String itemOnSaleName = (String) Array.get(splitLine1.split(" "), 1); // get name

		    	// Try to sell item from store to player (may throw exception)
		    	try {
		    		currentStore.sellItem(itemOnSaleName, player);
		    	}
		    	catch (InsufficientMoneyException ime) {
		    		System.out.println(ime.getMessage());
		    		/* TODO
		    		 * what should we do here, ie give player options to get cash?
		    		 * create a method for this i think
		    		 */
		    	}
		    	catch (IllegalStateException ise) {
		    		System.out.print(ise.getMessage());
		    	}
				break;
		    case 2:
		    	// Get buyCatalogue and its size
		    	HashMap<String, HashMap<String, Integer>> buyCatalogue = currentStore.getBuyCatalogue();
		    	int itemsToBuyCount = buyCatalogue.size();
		    	
		    	// Display items that can be bought by store
		    	System.out.println("Enter the number corresponding to the Item that you want to sell!.");
		    	String buyDisplayString = Store.getDisplayString(buyCatalogue);
		    	System.out.println(buyDisplayString);
		    	
		    	// number for chosen item
		    	int itemToBuyNum = getActionInt(scanner);
		    	
		    	// Check that input of itemToBuyNum is valid
		    	while (!CheckValidInput.actionIntIsValid(itemToBuyNum, 1, itemsToBuyCount)) {
		    		System.out.println(String.format("Invalid input, please enter a number between 1 and %d.",itemsToBuyCount));
		    		itemToBuyNum = getActionInt(scanner);
		    	}
		    	
		    	// Get name of chosen item
		    	String splitLine2 = (String) Array.get(buyDisplayString.split("\n"), itemToBuyNum-1);
		    	String itemToBuyName = (String) Array.get(splitLine2.split(" "), 1);
		    	
		    	
		    	// Sell item from player to store
		    	try {
		    		currentStore.buyItem(itemToBuyName, player);
		    	}
		    	catch (IllegalStateException ise) {
		    		System.out.println(ise.getMessage());
		    	}
				break;
			case 3:
				// Display items bought, and where they have been sold, if they have been sold
				System.out.println(player.getShip().displayAllTimeItems());
				break;
		    case 4:
				// View the amount of cash that a player has
				System.out.println(String.format("Player has a balance of: %d Pirate Bucks", player.getMoneyBalance()));
				break;
		    case 5:
		    	System.out.println("You have exited the store!");
				// exit store
		        return;
		}
		
		visitTheStoreHelper();
	}
	
	public void visitTheStoreHelper() {
		
		System.out.println("Is that all you wanted to do at the store today? \n Enter action number:");
		
		String exitOptions = "1. Do more actions with the store. \n"
				+ "2. Exit Store.";
		
		System.out.println(exitOptions);
		
		Scanner scanner = new Scanner(System.in);
		
		int exitInput = getActionInt(scanner);
		
		while (!CheckValidInput.actionIntIsValid(exitInput, 1, 2)) {
			System.out.println("Please enter a number between 1 and 2.");
			exitInput = getActionInt(scanner);
		}
		
		switch (exitInput) {
		    case 1:
		    	// View options again
		    	visitTheStore();
		    case 2:
		    	// Exit store
		    	return;
		}
		
				
	    /* ask user if they would like to do any other action or just leave the store,
		* only if they didnt ask for exit store, in the first place
		*/
	}
	
	
	/**
	 * Note that this is an informal test environment, when i write the proper Junit tests ill use actual varaible names and what not.
	 * Still keep it organised tho so its readable. 5th island coming to a game near you soon.
	 */
	
	// TODO migrate this entire method into the main class
	public static void main(String[] args) {
		Store s1 = new Store(); Store s2 = new Store(); Store s3 = new Store(); 
		Island i1 = new Island("other 1", s1, "arb1"); 
		Island i2 = new Island("other 2", s2, "arb2"); 
		Island i3 = new Island("other 3", s3, "arb3"); 
		
		/* TODO
		 * when creating a new store the store needs to reference the island that it belongs to
		 * a two way relationship like player and ship, method setIslandStore in store allows this
		 */
		// Creating current instance of Island
		Store currStore = new Store(); 
		Island currentIsland = new Island("current", currStore, "arb description");
		// creating routes from the current island
		Route r11 = new Route("r11", 3, currentIsland, i1, "wavy route, but with storms come sailors to save");
		Route r12 = new Route("r12", 2, currentIsland, i1, "easy sailing, which pirates also enjoy");
		Route r21 = new Route("r21", 2, currentIsland, i2, "safe route, low risk, low reward.");
		Route r22 = new Route("r22", 2, currentIsland, i2, "wavy route, but with storms come sailors to save");
		Route r31 = new Route("r31", 4, currentIsland, i3, "long route, takes a detour to find calmer waters");
		Route r32 = new Route("r32", 1, currentIsland, i3, "Direct route with high winds, fast but lots of bad weather. ");
		// create route array and pass to current island
		Route[] routesFromCurrent = new Route[] {r11, r12, r21, r22, r31, r32};
		currentIsland.setRouteArray(routesFromCurrent);
		// Create a player
		Player player1 = new Player("ben", 100, 30, currentIsland);
		// Create a game environment
		Island[] islands = new Island[] {currentIsland, i1, i2, i3};
		GameEnvironment game1 = new GameEnvironment(player1, islands);
		// test viewOtherIslands
		game1.chooseOtherIsland();
		
		// note from me: fyi there needs to be 5 islands
		// game1.takeTurn();
	}
	
	public void chooseShip() {
		// TODO implement
		
	}
}
