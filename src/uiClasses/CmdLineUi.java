package uiClasses;

import coreClasses.*;

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
public class CmdLineUi implements GameUi{
	
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
		
		System.out.println("Setup complete! Ready to play!");
		gameEnvironment.onSetupFinished(player, ship, gameDuration, startIsland);
	}
	
	/**
	 * While the game has not been finished, playGame calls methods to print the actions available
	 * to the player, take input from the player and handles that input to perform actions.
	 */
	public void playGame() {
		
		while (!finish) {
			printCoreOptions();
			int input = getInt(1, 6);
			
			handleCoreChoice(input);
		}
	}
	
	/**
	 * Prints a list of the actions available to the player. 
	 */
	private void printCoreOptions() {
		String options = "Enter an action's number:\n(1) View your money and days remaining.\n(2) View the propeties of your ship.\n"
				+ "(3) View the goods you have purchased.\n(4) View the properties of each Island.\n"
				+ "(5) Visit the store on " + gameEnvironment.getCurrentIsland() + " (current island).\n(6) Set sail to another Island.";
		System.out.println(options);
		
	}
	
	/**
	 * Reads user input and ensures it is an integer within a specified range. 
	 * 
	 * @param lowerBound - minimum acceptable number for input
	 * @param upperBound - maximum acceptable number for input
	 * @return input - an integer between the lower and upper bounds. 
	 */
	private int getInt(int lowerBound, int upperBound) {
		boolean successful = false;
		while (true) {
			try {
				int input = scanner.nextInt();
				if (lowerBound <= input && input <= upperBound) { 
					return input;
				}
				System.out.format("Please enter a number between %d and %d (inlcusive).", lowerBound, upperBound);
				
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
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
			visitStore();
			break;
		case 6:
			// setting sail to another island
			travelToIsland();
			break;
	}
	}

	// #################### VISITING STORE METHODS ######################## 
	
	// needs to have methods from gameEnvironment that 'twin' the method
	// so all the methods in ge need to be turned into returning things
	
	// so for a visitStore method in main it would basically be printing things that are outputted from the visitStore method in gameEnvirionemt
	
	private void visitStore() {
		/* twin method for the visitStore method from game environment
		 * is a bit special compared to the gui version, bc this actually prints out things
		 * we will see later on down the road with how to implement the GUI!
		 */
		String storeName = gameEnvironment.getCurrentIsland().getIslandStore().getName();
		System.out.println(String.format("Welcome to %s, please read options below for interacting with this store!", storeName));
		printStoreOptions();
		int input = getInt(1, 5);
		handleStoreChoice(input);
	}
	
	private void printArrayOptions(String[] optionsArray, String message) {
		System.out.println(message); //header
		for (int i = 0; i < optionsArray.length; i++) {
			System.out.format("(%d) %s\n", (i+1), optionsArray[i]);
		}
		System.out.format("(%d) %s\n", (optionsArray.length+1), "Go back");
	}
	
	private void printStoreOptions() {
		String[] optionsArray = Store.getVisitOptions();
		System.out.println("Please enter an action number corresponding to the action that you want to do!"); //header
		for (int i = 0; i < optionsArray.length; i++) {
			//TODO how to change an int into a string?
			System.out.println((String.valueOf(i+1)) + optionsArray[i]);
		}
	}
	
	private void handleStoreChoice(int input) {
		switch (input) {
		case 1:
			//view and buy items that store sells
			HashMap<String, HashMap<String, Integer>> sellCatalogue = gameEnvironment.getCurrentIsland().getIslandStore().getSellCatalogue();
			System.out.println("Enter the number corresponding to the Item that you want to buy!");
			ArrayList<String> displayArrayList = Store.getDisplayArrayList(sellCatalogue);
	    	for (int i = 0; i < displayArrayList.size(); i++) {
	    		System.out.println(i.toString() + displayArrayList.get(i));
	    	}
	    	
	    	int itemToSellNum = getInt(1, sellCatalogue.size());
	    	String itemToSellName = Store.getChosenItemName(displayArrayList, itemToSellNum);
	    	
	    	// HOW TO SELL ITEMS?
			exitStore();
			break;
		case 2:
			// view and sell items that a store buys 
			exitStore();
			break;
		case 3:
			// view previously bought items
			exitStore();
			break;
		case 4:
			// view the amount of money that you have
			System.out.println(gameEnvironment.getPlayer().getMoneyBalance());
			exitStore();
			break;
		case 5:
			System.out.println("You have exited the store!");
			// exit store
			return;
		}
	}
	
	private void exitStore() {
		System.out.println("Is that all you wanted to do at this store today? \n Please enter action number:");
		
		// TODO should we use the same system with the visiting options as with exiting options, although it may be overkill
		// just because it really doesnt save that much code reuse for only 2 options!
		String exitOptions = "1. Do more actions with the store. \n"
				+ "2. Exit Store.";
		System.out.println(exitOptions);
		int input = getInt(1, 2);
		
		switch(input) {
		case 1:
			visitStore();
		case 2:
			return;
		}
	}
	// #####################################################################
	
	public void finishGame() {
		
	}
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
	
	/**
	 * Displays a list of ships and their qualities, and takes input to choose which will be used.
	 * @return myShip the ship you have chosen to use in this play through.
	 */
	private Ship pickShip() {
		// TODO implement this
		// Temporary implementation for testing delete once actual implementation added. 
		return new Ship("Row Boat", 10, 10, 5, 10);
	}
	
	/**
	 * Prints information about the player and there position in the game. 
	 */
	private void viewPlayerInfo() {
		Player player = gameEnvironment.getPlayer();
		System.out.format("%s has $%d and %d days remaining.\n", player.getName(), player.getMoneyBalance(), gameEnvironment.getDaysRemaining());
	}
	
	private void viewShipProperties() {
		
	}
	
	private void viewGoodsPurchased() {
		
	}
	
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
		printArrayOptions(proceedOptions, "Enter the number of the action you wish to take.");
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
			gameEnvironment.getShip().setSail(chosenRoute, gameEnvironment);
			System.out.println("Sail complete.");
		}
	}
	
	/**
	 * Prints the name and a short description of each route in the parameter routes.
	 * 
	 * @param routes list of routes to print out.
	 */
	private void printRoutes(ArrayList<Route> routes) {
		for (int i = 0; i < routes.size(); i++) {
			System.out.format("(%d) %s\n", i+1, routes.get(i).toString());
		}
		System.out.format("(%d) %s\n", (routes.size()+1), "Go back");
	}
}









