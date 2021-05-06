package uiClasses;

import coreClasses.*;
import exceptions.InsufficientMoneyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 26/4/21
 * @since 26/4/21
 */
public class CmdLineUi implements GameUi {
	
	private GameEnvironment gameEnvironment;
	private Scanner scanner;
	private boolean finish = false;
	enum PlayOptions{
		// TODO add these
	}
	
	public CmdLineUi() {
		this.scanner = new Scanner(System.in);
	}
	
	/**
	 * Gets the input required to create the player and ship objects, then passes them to gameEnvironment
	 * to complete its setup. 
	 */
	public void setup(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
		// create the player and the ship
		// player first
		String playerName = getName("Enter a name for your player: ");
		int gameDuration = getDuration();
		Island startIsland = gameEnvironment.getIslandArray()[0];
		Player player = new Player(playerName, 100);
		// and ship
		Ship ship = pickShip();
		ship.setOwner(player);
		
		System.out.println("Setup complete! Ready to play!");
		gameEnvironment.onSetupFinished(player, ship, gameDuration, startIsland);
	}
	
	/**
	 * While the game has not been finished, playGame calls methods to print the actions available
	 * to the player, take input from the player and handles that input to perform actions.
	 */
	public void playGame() {
		
		while (!finish) {
			String[] coreOptions = new String[] {
					"View your money and days remaining.", 
					"View the propeties of your ship.", 
					"View the goods you have purchased.", 
					"View the properties of each Island.",
					String.format("Visit the store on %s (current island)", gameEnvironment.getCurrentIsland().getIslandName()), 
					"Set sail to another Island."
			        };
			printOptions(coreOptions, "Enter an action's number: ");
			int input = getInt(1, 6);
			
			handleCoreChoice(input);
		}
	}
	
	/**
	 * Based on the players input, calls the appropriate method to execute the action they have selected.
	 * @param input a valid integer that corresponds with an action available to the player.
	 */
	private void handleCoreChoice(int input) {
		switch (input) {
		case 1: 
			// option to view the amount of money and days remaining
			viewPlayerInfo();
			break;
		case 2:
			// option to view the properties of the ship
			viewShipProperties();
			break;
		case 3:
			// view the goods you have purchased
			viewGoodsPurchased();
			break;
		case 4:
			// option to view properties of each island
			viewOtherIslands();
			break;
		case 5:
			// option to visit the store on the current island
			visitStorePreper();
			break;
		case 6:
			// setting sail to another island
			travelToIsland();
			break;
	    }
	}

	// #################### VISITING STORE METHODS ######################## 

	private void visitStorePreper() {
		/* twin method for the visitStore method from game environment
		 * is a bit special compared to the gui version, bc this actually prints out things
		 * we will see later on down the road with how to implement the GUI!
		 */
		String storeName = gameEnvironment.getCurrentIsland().getIslandStore().getName();
		String visitStoreMessage = String.format("Welcome to %s, please read options below for interacting with this store!", storeName);
		getStoreVisitChoice(visitStoreMessage);
	}
	
	private void getStoreVisitChoice(String visitStoreMessage) {
		printOptions(Store.getVisitOptions(), visitStoreMessage);
		int input = getInt(1, 5);
		handleStoreChoice(input);
	}
	
	private void handleStoreChoice(int input) {
		String welcomeBackMsg = "Welcome back to the Store, please enter a number to further interact with this store!";
		switch (input) {
		case 1:
			//view and buy items that store sells
			HashMap<String, HashMap<String, Integer>> sellCatalogue = gameEnvironment.getCurrentIsland().getIslandStore().getSellCatalogue();
			
	    	String itemStoreToSellName = visitStoreBuySellPreper("buy", sellCatalogue);
	    	
	    	try {
	    		// TODO move bellow if statement into ge
	    		Item itemToSell = gameEnvironment.buyFromStore(itemStoreToSellName);
	    		if (!itemToSell.getWithPlayer()) {
	    			System.out.println(Store.sellItemChecker(gameEnvironment.getPlayer(), itemToSell));
	    		}
	    		System.out.format("You just bought %s for %s pirate bucks! \n", itemToSell.getName(), itemToSell.getPlayerBuyPrice());
	    	}
	    	catch (IllegalStateException ise) {
	    		System.out.print(ise.getMessage());
	    	}
	    	
	    	getStoreVisitChoice(welcomeBackMsg);
			break;
		case 2:
			// view and sell items that a store buys 
			HashMap<String, HashMap<String, Integer>> buyCatalogue = gameEnvironment.getCurrentIsland().getIslandStore().getBuyCatalogue();
			
			String itemStoreToBuyName = visitStoreBuySellPreper("sell", buyCatalogue);
			
	    	// Sell item from player to store
	    	try {
	    		Item itemToBuy = gameEnvironment.sellToStore(itemStoreToBuyName);
	    		if (itemToBuy == null) {
	    			System.out.println(Store.buyItemChecker(gameEnvironment.getPlayer(), itemToBuy));
	    		}
	    		else {
	    			System.out.format("You just sold %s for %s pirate bucks! \n", itemToBuy.getName(), itemToBuy.getPlayerSellPrice());
	    		}	
	    	}
	    	catch (IllegalStateException ise) {
	    		System.out.println(ise.getMessage());
	    	}
	    	getStoreVisitChoice(welcomeBackMsg);
			break;
		case 3:
			// view previously bought items
			viewGoodsPurchased();
			getStoreVisitChoice(welcomeBackMsg);
			break;
		case 4:
			// view the amount of money that you have
			System.out.println(gameEnvironment.getPlayer().moneyBalanceToString());
			getStoreVisitChoice(welcomeBackMsg);
			break;
		case 5:
			// exit store
			exitStore();
			return;
		}
	}
	
	private String visitStoreBuySellPreper(String operation, HashMap<String, HashMap<String, Integer>> catalogue) {
		String buySellMessage  = String.format("Enter the number corresponding to the Item that you want to %s! \n", operation);
		if (catalogue.isEmpty()) {
			System.out.format("%s catalogue for this store is empty!", operation);
			return null;
		}
		ArrayList<String> optionsArrayList = Store.catalogueToArrayList(catalogue);
		printOptions(optionsArrayList, buySellMessage);
		
		System.out.format("(%d) Go Back \n", catalogue.size()+1);
    	int itemNum = getInt(1, catalogue.size()+1);
    	if (itemNum == 4) {
    		getStoreVisitChoice("Welcome back to the Store, please enter a number to further interact with this store!");
    	}
    	return Store.getChosenItemName(optionsArrayList, itemNum);
	}
	
	private void exitStore() {
		String exitStoreMessage = "Are you sure you want to leave the store? \n"
				+ "Please enter action number:";
		printOptions(new String[] {"Do more actions with the store.", "Exit Store"}, exitStoreMessage);
		int input = getInt(1, 2);
		
		switch(input) {
		case 1:
			getStoreVisitChoice("Welcome back to the Store, please enter a number to further interact with this store!");
		case 2:
			System.out.println("You have exited the store!");
			return;
		}
	}
	
	// ############### SHIP METHODS #################
	
	/**
	 * Displays a list of ships and their qualities, and takes input to choose which will be used.
	 * @return myShip the ship you have chosen to use in this play through.
	 */
	private Ship pickShip() { 
		String pickShipMessage = "Please choose a ship, enter an action number corresponding to the ship that you want:\n";
		printOptions(gameEnvironment.getShipDescriptionArrayList(), pickShipMessage);
		int chosenShipNum = getInt(1, 4);
		return gameEnvironment.getShipArray()[chosenShipNum-1];
	}
	
	private void viewShipProperties() {
		System.out.println(gameEnvironment.getShip().getDescription());
	}	

	
	/**
	 * Prints information about the player and there position in the game. 
	 */
	private void viewPlayerInfo() {
		Player player = gameEnvironment.getPlayer();
		System.out.format("%s has $%d and %d days remaining.\n", player.getName(), player.getMoneyBalance(), gameEnvironment.getDaysRemaining());
	}

	
	private void viewGoodsPurchased() {
		System.out.print(gameEnvironment.getPlayer().purchasedItemsToString());
	}
	
	public void finishGame() {
		
	}
	
	// ############### ISLAND METHODS ###############
	/**
	 * Displays a list of all the islands the player can travel to (all except the player's current island), 
	 * then gives the player the option of seeing more detail on any of the islands. 
	 */
	private void viewOtherIslands() {
		
		Island[] otherIslands = gameEnvironment.getOtherIslands();
		
		while (true) {
			System.out.println("To view more information about an island, enter the number of the island.");
			printIslands(otherIslands);
			int islandInput = getInt(1, otherIslands.length+1);
			
			// if islandInput was to go back
			if (islandInput == otherIslands.length+1) {
				return;
			}
			else {
				Island selectedIsland = otherIslands[islandInput-1];
				viewIslandDetails(otherIslands, selectedIsland);
			}
		}
	}
	
	/**
	 * Prints the name and a brief description of each island in the list given. 
	 * @param islands list of islands that can be traveled to.
	 */
	private void printIslands(Island[] islands) {
		for (int i = 0; i < islands.length; i++) {
			System.out.format("(%d) %s\n", i+1, islands[i].toString());
		}
		System.out.format("(%d) %s\n", (islands.length+1), "Go back");
	}
	
	/**
	 * Gives more detail about a selected island, then asks the player if the would like to go back or travel to the
	 * island they are viewing info about.
	 * @param otherIslands array f all islands in the game except the current island (islands that can be traveled to).
	 * @param selectedIsland The island the player has chosen to see more info on.
	 */
	private void viewIslandDetails(Island[] otherIslands, Island selectedIsland) {
		// print full info of selected island
		ArrayList<Route> routes = gameEnvironment.getCurrentIsland().getPossibleRoutes(selectedIsland);
		System.out.println(selectedIsland.getFullInfo(routes));
		
		String[] proceedOptions = new String[] {"Travel to this island"};
		printOptions(proceedOptions, "Enter the number of the action you wish to take.");
		System.out.format("(%d) %s\n", (proceedOptions.length+1), "Go back");
		int proceedInput = getInt(1, otherIslands.length+1);
		// if input was to go back
		if (proceedInput == otherIslands.length) {
			return;
		}
		else {
			chooseRoute(selectedIsland); // will probably change this to call a helper of travelToisland()
		}
	}
	
	/**
	 * Makes the necessary calls and takes input to choose an island to travel to, before calling chooseRoute
	 * so that the player can choose a route to the island they selected. 
	 */
	private void travelToIsland() {
		while(true) {
			Island[] otherIslands = gameEnvironment.getOtherIslands();
			
			System.out.println("Enter the number of the island you would like to travel to.");
			
			printIslands(otherIslands);
			int islandInput = getInt(1, otherIslands.length+1);
			// if input was to go back
			if (islandInput == otherIslands.length+1) {
				return;
			}
			Island destinationIsland = otherIslands[islandInput-1];
			chooseRoute(destinationIsland);
		}
	}
	
	// ################## ROUTE METHODS ####################
	
	/**
	 * Allows the user to see the routes to the island they have chosen (and info about each) and pick
	 * one to travel along. It then checks the player has enough money to do the necessary action before
	 * sailing along the chosen route, and calls set sail to travel along the route.
	 * 
	 * @param island The island the player wants to travel to. 
	 */
	private void chooseRoute(Island island) {
		ArrayList<Route> routes = gameEnvironment.getCurrentIsland().getPossibleRoutes(island);
		
		System.out.println("Enter a number to choose a route to travel along.");
		printRoutes(routes);
		int routeInput = getInt(1, routes.size()+1);
		// if input was to go back
		if (routeInput == routes.size()+1) {
			return;
		}
		else {
			Route chosenRoute = routes.get(routeInput-1);
			// Check you have enough money to repair ship and pay wages
			if (gameEnvironment.getPlayer().getMoneyBalance() <= gameEnvironment.getCost(chosenRoute)) {
				System.out.println("Not enough money to repair your ship and pay your crew wages for this Route.\n"
						+ "Please sell some items or choose a shorter route.");
				return;
			}
			System.out.println("Repairing ship and paying wages");
			gameEnvironment.setSail(chosenRoute);
			System.out.println("Sail complete.");
		}
	}
	
	/**
	 * Prints the name and a short description of each route in the parameter routes.
	 * 
	 * @param routes list of routes to print out.
	 */
	// TODO move this to methods bellow!
	private void printRoutes(ArrayList<Route> routes) {
		for (int i = 0; i < routes.size(); i++) {
			System.out.format("(%d) %s\n", i+1, routes.get(i).toString());
		}
		System.out.format("(%d) %s\n", (routes.size()+1), "Go back");
	}
	
	//################### GENERAL HELPER METHODS ########################
	
	private void printOptions(ArrayList<String> optionsArrayList, String message) {
		System.out.print(message);
		for (int i = 0; i < optionsArrayList.size(); i++) {
			System.out.format("(%d) %s \n", (i+1), optionsArrayList.get(i));
    	}
	}
	
	private void printOptions(String[] optionsArray, String message) {
		System.out.println(message); //header
		for (int i = 0; i < optionsArray.length; i++) {
			System.out.format("(%d) %s \n", (i+1), optionsArray[i]);
		}
	}
	
	/**
	 * Reads user input and ensures it is an integer within a specified range. 
	 * 
	 * @param lowerBound - minimum acceptable number for input
	 * @param upperBound - maximum acceptable number for input
	 * @return input - an integer between the lower and upper bounds. 
	 */
	private int getInt(int lowerBound, int upperBound) {
		while (true) {
			try {
				int input = scanner.nextInt();
				if (lowerBound <= input && input <= upperBound) { 
					return input;
				}
				System.out.format("Please enter a number between %d and %d (inlcusive). \n", lowerBound, upperBound);
				
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
		}
	}
	
	// ################## GETTER METHODS #########################
	/**
	 * 
	 * @param message message to be printed to tell the user what the name is for.
	 * @return 
	 */
	private String getName(String message) {
		System.out.println(message);
		while (true) {
			String name = scanner.nextLine();
			if (CheckValidInput.nameIsValid(name)) {
				return name;
			}
			System.out.println(NAME_REQUIREMENTS);
		}
	}
	
	/**
	 * Prompts the user to enter a game duration and reads the user input. Returns days if the input meets
	 * the criteria. 
	 * @return days the number of days the game will last if it is completed.
	 */
	private int getDuration() {
		System.out.println("Enter the days to play for (must be between 20 and 50): ");
		while (true) {
			try {
				int days = scanner.nextInt();
				if (20 <= days && days <= 50) {
					return days;
				}
				System.out.println(DURATION_REQUIREMENTS);
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
			scanner.nextLine();
		}
	}
}









