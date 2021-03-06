package uiClasses;

import coreClasses.*;   

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/** Represents Command Line UI for playing 'Island Trader' game
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 17/5/21
 * @since 26/4/21
 */
public class CmdLineUi implements GameUi {
	
	/** GameEnvironment object for this game, used to store all necessary objects needed for the game */
	private GameEnvironment gameEnvironment;
	/** Scanner object for taking input from a user */
	private Scanner scanner;
	/** Boolean value keeping track if a game is over, ends if true */
	private boolean finish = false;
	
	/** Constructor for CmdLineUi
	 * Initializes scanner object needed for running of UI
	 * 
	 */
	public CmdLineUi() {
		this.scanner = new Scanner(System.in);
	}
	
	/////////////////////////////////////////////////////////////////////////////
    ////////////////////////// GAME HANDLING METHODS ////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
	
	/** Gets the input required to create the player and ship objects, then passes them to gameEnvironment
	 * to complete its setup. 
	 * 
	 * @param gameEnvironment GameEnvironment object for the current object
	 */
	@Override
	public void setup(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
		// create the player and the ship
		// player first
		String playerName = getName("Enter a name for your player: ");
		int gameDuration = getDuration();
		Island startIsland = gameEnvironment.getIslandArray()[0];
		Player player = new Player(playerName, 1000);
		// and ship
		Ship ship = pickShip();
		ship.setOwner(player);
		
		System.out.println("Setup complete! Ready to play!");
		gameEnvironment.onSetupFinished(player, ship, gameDuration, startIsland);
	}
	
	/** While the game has not been finished, playGame calls methods to print the actions available
	 * to the player, take input from the player and handles that input to perform actions.
	 * 
	 */
	@Override
	public void playGame() {
		checkSufficientMoney();
		
		while (!finish) {
			String[] coreOptions = new String[] {
					"View your money and days remaining.", 
					"View the properties of your ship and any upgrades bought.", 
					"View the goods you have purchased.", 
					"View the properties of each Island.",
					String.format("Visit %s's store", gameEnvironment.getCurrentIsland().getIslandName()), 
					"Set sail to another island."
			        };
			printOptions(coreOptions, "Enter an action's number: ", false);
			int input = getInt(1, 6);
			
			handleCoreChoice(input);
		}
	}
	
	/** Method to handle the choice of a user for the main options of the game
	 * Based on the players input, calls the appropriate method to execute the action they have selected.
	 * 
	 * @param input Integer that corresponds with an action available to the player.
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
	
	/** Method for finishing a game, prints messages detailing the game just played. 
	 * I.e. displays days survived, profit made and the final score
	 * 
	 * @param message String detailing the reason why a game ended
	 */
	@Override
	public void finishGame(String message) {
		finish = true;
		System.out.format("\nGame Over %s!\n", gameEnvironment.getPlayer().getName());
		System.out.println(message);
		int selectedDays = gameEnvironment.getDaysSelected();
		System.out.format("You played for %d days out of a selected %d days.\n", 
				(selectedDays - gameEnvironment.getDaysRemaining()), selectedDays);
		System.out.format("You made $%d profit, and your final score was %d!\n", 
				(gameEnvironment.getPlayer().getMoneyBalance() -STARTING_MONEY), gameEnvironment.calculateScore());
	}
	
	/////////////////////////////////////////////////////////////////////////////
    //////////////////////////// STORE METHODS //////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

	/** Entry method for a store. Gets the choice of a user for further interaction
	 * Functionality is stretched from visitStorePreper() to getStoreVisitChoice(String)
	 * to allow a user to do further actions with a store, without getting a welcome message as if
	 * its their first time visiting. 
	 * 
	 */
	private void visitStorePreper() {
		/* twin method for the visitStore method from game environment
		 * is a bit special compared to the gui version, bc this actually prints out things
		 * we will see later on down the road with how to implement the GUI!
		 */
		String storeName = gameEnvironment.getCurrentIsland().getIslandStore().getName();
		String visitStoreMessage = String.format("Welcome to %s, please read options below for interacting with this store!", storeName);
		getStoreVisitChoice(visitStoreMessage);
	}
	
	/** Method to get the choice to interact with a Store from a player
	 * 
	 * @param visitStoreMessage String welcoming a player to a store for the first time, or coming back for more interaction
	 */
	private void getStoreVisitChoice(String visitStoreMessage) {
		printOptions(Store.getVisitOptions(), visitStoreMessage, true);
		int input = getInt(1, 5);
		handleStoreChoice(input);
	}
	
	/** Method for handling the choice of a player to interact with a Store
	 * 
	 * @param input Integer for the action number that a player has chosen corresponding to the action that they want to perform
	 */
	private void handleStoreChoice(int input) {
		String welcomeBackMsg = "Welcome back to the Store, please enter a number to further interact with this store!";
		switch (input) {
		case 1:
			// Call recursive method for selling to a player
			visitStoreSellToPlayer();
			// Go back to main visit store choices
	    	getStoreVisitChoice(welcomeBackMsg);
			break;
		case 2:
			// Call recursive method for buying from a player
			visitStoreBuyFromPlayer();
			
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
			System.out.println("You have exited the store!");
			return;
		}
	}
	
	/** Recursive method for selling an Item to a player. Keeps recursing until the user asks to exit
	 * 
	 */
	private void visitStoreSellToPlayer() {
		// Get an array representation of the items that a store sells
    	String[] infoList = visitStoreBuySellHelper("sell");
    	
    	// if itemStoreToBuyName is null, then visitStoreBuySellHelper is handling case when user wants to go back menus
    	if (infoList != null) {
    		// in gui: check player wants to buy. if there is a string returned by method in stirng, have a pop up appear. 
    		// player will have to either choose continue, or go back. if go back is selected then exectution flow is diverted back to buying items screen
    		// if they want to continue, then the item is bought
    		// ie in gui if checkPlayerWantsToBuy(ge, itemName) != null: ask them, take input then do as above
        	try {
        		// Try to sell items to a player
        		// Get the name of the Item that a player has chosen
            	String itemStoreToSellName = infoList[0];
            	int numItems = Integer.parseInt(infoList[1]);
        		// Call Store to handle, will print result of transaction from Store
        		System.out.println(gameEnvironment.getCurrentIsland().getIslandStore().sellItemsToPlayer(gameEnvironment, itemStoreToSellName, numItems));
        	}
        	catch (IllegalStateException ise) {
        		System.out.print(ise.getMessage());
        	}
        	visitStoreSellToPlayer();// Recursively call function until user selects "Go back" in helper method bellow
    	}
    	return; // stops recursion
	}
	
	/** Recursive method for selling an Item to a store from a player. Keeps recursing until a user asks to exit
	 * 
	 */
	private void visitStoreBuyFromPlayer() {
		// Get Array for the chosen item, and the number of items requested
		String[] infoArray = visitStoreBuySellHelper("buy");
		
		// if itemStoreToBuyName is null, then visitStoreBuySellHelper is handling case when user wants to go back menus
		if (infoArray != null) {
	    	try {
	    		// Get necessary info from the infoArray 
	         	String itemStoreToBuyName = infoArray[0];
            	int numItems = Integer.parseInt(infoArray[1]);
	    		// Call Store to handle
	    		System.out.println(gameEnvironment.getCurrentIsland().getIslandStore().buyItemsFromPlayer(itemStoreToBuyName, gameEnvironment.getPlayer(), numItems));
	    	}
	    	catch (IllegalStateException ise) {
	    		System.out.println(ise.getMessage());
	    	}
	    	visitStoreBuyFromPlayer(); // Recursively call function until user selects "Go back" in helper method bellow
		}
		return; // stops recursion
	}
	
	/** Helper method for buying and selling from/to a store
	 *  takes code that was originally being used by both buying and selling, but was made general with parameter
	 *  'operation' to work with both. 
	 *  
	 * @param operation String for what operation is happening "buy" or "sell"
	 * @param catalogue HashMap containing the items that a store buys or sells
	 * @return String name of the chosen item that is being sold or bought
	 */
	private String[] visitStoreBuySellHelper(String operation) {
		// Switch operation to be in perspective of user, was previously in the perspective of the store
		String operationAdj;
		if (operation.equals("buy")) {
			operationAdj = "sell";
		}
		else {
			operationAdj = "buy";
		}
		
		String buySellMessage  = String.format("Enter the number corresponding to the Item that you want to %s! \n", operationAdj);
		
		// Get the catalogue assosiatted with this operation, and parse it into an Array form
		HashMap<String, HashMap<String, Integer>> catalogue = gameEnvironment.getCurrentIsland().getIslandStore().getCatalogue(operation);
		String [] optionsArray = gameEnvironment.getCurrentIsland().getIslandStore().catalogueToArray(catalogue);
		
		// Print the items that a player can buy or sell
		printOptions(optionsArray, buySellMessage, true);
		
		// Get the action number that the user wants
    	int itemNum = getInt(1, catalogue.size()+1);
    	if (itemNum == catalogue.size()+1) {
    		return null; // user wants to go back menus
    	}
  
    	// Get the name of the Item that a user has chosen
    	String itemName =  Store.chosenItemName(optionsArray, itemNum);
    	
    	String howManyMessage = String.format("How many %s would you like to %s? ", itemName, operation);
    	System.out.println(howManyMessage);
    	int numItems = getUnboundedInt();
    	
    	// Return an array containing the information needed for other methods.
    	return new String[] {itemName, Integer.toString(numItems)};
	}
	
	
	/** Method to handle exiting from a store. Prints queries and then asks user for input
	 * 
	 */
	private void exitStore() {
		String exitStoreMessage = "Are you sure you want to leave the store? \n"
				+ "Please enter action number:";
		printOptions(new String[] {"Do more actions with the store."}, exitStoreMessage, true);
		int input = getInt(1, 2);
		
		switch(input) {
		case 1:
			getStoreVisitChoice("Welcome back to the Store, please enter a number to further interact with this store!");
		case 2:
			return;
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////
    //////////////////////////// SHIP METHODS ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
	
	/** Displays a list of ships and their qualities, and takes input to choose which will be used.
	 * 
	 * @return myShip the ship you have chosen to use in this play through.
	 */
	private Ship pickShip() { 
		String pickShipMessage = "Please choose a ship, enter an action number corresponding to the ship that you want:\n";
		printOptions(gameEnvironment.shipDescriptionArrayList(), pickShipMessage, false);
		int chosenShipNum = getInt(1, 4);
		return gameEnvironment.getShipArray()[chosenShipNum-1];
	}
	
	/** View the properties of the Ship that a user has chosen for a game.
	 * And then asks if they would like to see the upgrades equipped. This is done to avoid
	 * cluttering up the command line with information that a user may not want to see.
	 * 
	 */
	private void viewShipProperties() {
		System.out.println(gameEnvironment.getShip().getDescription());
		String viewShipMessage = "Would you like to see the upgrades that you equipped for this ship? \nEnter an action number";
		printOptions(new String[] {"View upgrades for this ship."}, viewShipMessage, true);
		int input = getInt(1, 2);
		
		switch(input) {
		case 1:
			System.out.println(gameEnvironment.getShip().upgradesToString());
		case 2:
			return;
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////
    //////////////////////////// PLAYER METHODS /////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
	
	/** Prints information about the player and their position in the game. 
	 * 
	 */
	private void viewPlayerInfo() {
		Player player = gameEnvironment.getPlayer();
		System.out.format("%s has $%d and %d days remaining.\n", player.getName(), player.getMoneyBalance(), gameEnvironment.getDaysRemaining());
	}

	/** Display all the goods purchased by a Player in a game
	 * 
	 */
	private void viewGoodsPurchased() {
		System.out.print(gameEnvironment.getPlayer().purchasedItemsToString());
	}
	
	/////////////////////////////////////////////////////////////////////////////
    //////////////////////////// ISLAND METHODS /////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
	
	/** Displays a list of all the islands the player can travel to (all except the player's current island), 
	 * then gives the player the option of seeing more detail on any of the islands. 
	 * 
	 */
	private void viewOtherIslands() {
		
		Island[] otherIslands = gameEnvironment.otherIslands();
		
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
	
	/** Prints the name and a brief description of each island in the list given. 
	 * 
	 * @param islands Island[] array of islands that can be traveled to.
	 */
	private void printIslands(Island[] islands) {
		for (int i = 0; i < islands.length; i++) {
			System.out.format("(%d) %s\n", i+1, islands[i].toString());
		}
		System.out.format("(%d) %s\n", (islands.length+1), "Go back");
	}
	
	/** Gives more detail about a selected island, then asks the player if the would like to go back or travel to the
	 * island they are viewing info about.
	 * 
	 * @param otherIslands Island[] array of all islands in the game except the current island (islands that can be traveled to).
	 * @param selectedIsland Island that the player has chosen to see more info on.
	 */
	private void viewIslandDetails(Island[] otherIslands, Island selectedIsland) {
		// print full info of selected island
		ArrayList<Route> routes = gameEnvironment.getCurrentIsland().possibleRoutes(selectedIsland);
		System.out.println(selectedIsland.fullInfo(routes));
		
		String[] proceedOptions = new String[] {"Travel to this island"};
		printOptions(proceedOptions, "Enter the number of the action you wish to take.", true);
		int proceedInput = getInt(1, otherIslands.length+1);
		// if input was to go back
		if (proceedInput == 2) {
			return;
		}
		else {
			chooseRoute(selectedIsland); // will probably change this to call a helper of travelToisland()
		}
	}
	
	/** Makes the necessary calls and takes input to choose an island to travel to, before calling chooseRoute
	 * so that the player can choose a route to the island they selected.
	 *  
	 */
	private void travelToIsland() {
		while(finish == false) {
			Island[] otherIslands = gameEnvironment.otherIslands();
			
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
	
	/////////////////////////////////////////////////////////////////////////////
    //////////////////////////// ROUTE METHODS //////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
	
	/** Allows the user to see the routes to the island they have chosen (and info about each) and pick
	 * one to travel along. It then checks the player has enough money to do the necessary action before
	 * sailing along the chosen route, and calls set sail to travel along the route.
	 * 
	 * @param island The island the player wants to travel to. 
	 */
	private void chooseRoute(Island island) {
		ArrayList<Route> routes = gameEnvironment.getCurrentIsland().possibleRoutes(island);
		
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
			// Following 3 lines make player press enter to continue.
			int sailCost = gameEnvironment.getShip().repairCost() + gameEnvironment.getShip().routeWageCost(chosenRoute);
			System.out.format("Press Enter to repair your ship pay your crew for $%d, and set sail!\n", sailCost);
			scanner.nextLine();
			scanner.nextLine();
			
			gameEnvironment.sailToNewIsland(chosenRoute, island);
			if (finish == false) {
				System.out.println("You have arrived at " + gameEnvironment.getCurrentIsland().getIslandName());
				playGame();
			}
		}
	}
	
	/** Prints the name and a short description of each route in the parameter routes.
	 * 
	 * @param routes ArrayList<Route> contains all of the routes to print out.
	 */
	private void printRoutes(ArrayList<Route> routes) {
		for (int i = 0; i < routes.size(); i++) {
			System.out.format("(%d) %s\n", i+1, routes.get(i).toString());
		}
		System.out.format("(%d) %s\n", (routes.size()+1), "Go back");
	}
	
	/////////////////////////////////////////////////////////////////////////////
    //////////////////////// GENERAL HELPER METHODS /////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
	
	/** Prints the options that a player can choose.
	 * 
	 * @param optionsArrayList ArrayList<String> containing all the options that a player can perform
	 * @param message String a message from a other functions asking them for input for a specific purpose
	 * @param canGoBack boolean value to dictate if a user can go back. If they can then it prints an extra line, 
	 * with the number to press in order to go back
	 */
	private void printOptions(ArrayList<String> optionsArrayList, String message, boolean canGoBack) {
		System.out.print(message);
		for (int i = 0; i < optionsArrayList.size(); i++) {
			System.out.format("(%d) %s \n", (i+1), optionsArrayList.get(i));
    	}
		if (canGoBack) {
			System.out.format("(%d) Go back \n", optionsArrayList.size()+1);
		}
	}
	
	/** Prints the options that a player can choose.
	 * 
	 * @param optionsArrayList String[] containing all the options that a player can perform
	 * @param message String a message from a other functions asking them for input for a specific purpose
	 * @param canGoBack boolean value to dictate if a user can go back. If they can then it prints an extra line, 
	 * with the number to press in order to go back
	 */
	private void printOptions(String[] optionsArray, String message, boolean canGoBack) {
		System.out.println(message); //header
		for (int i = 0; i < optionsArray.length; i++) {
			System.out.format("(%d) %s \n", (i+1), optionsArray[i]);
		}
		if (canGoBack) {
			System.out.format("(%d) Go back \n", optionsArray.length+1);
		}
	}
	
	/** Reads user input and ensures it is an integer within a specified range.
	 * 
	 * @param lowerBound Integer for the minimum acceptable number for input
	 * @param upperBound Integer for the maximum acceptable number for input
	 * @return input Integer between the lower and upper bounds, representing the action number that a player has chosen
	 */
	private int getInt(int lowerBound, int upperBound) {
		while (true) {
			try {
				int input = scanner.nextInt();
				if (lowerBound <= input && input <= upperBound) { 
					return input;
				}
				System.out.format("Please enter a number between %d and %d (inclusive). \n", lowerBound, upperBound);
				
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
		}
	}
	
	/** Gets a number with no bounds from a player. Useful for getting numbers
	 * that have no theoretical limit. Eg asking a user how many Items they would like to
	 * buy/sell to/from a store
	 * 
	 * @return Integer representing the number that a user has chosen
	 */
	private int getUnboundedInt() {
		while (true) {
			try {
				int input = scanner.nextInt();
			    return input;
				
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
		}
	}
	
	/** Method for getting the name of a user upon the beginning of a game
	 * 
	 * @param message message to be printed to tell the user what the name is for.
	 * @return String name that a user has inputed and is valid. 
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
	
	/** Prompts the user to enter a game duration and reads the user input. Returns days if the input meets
	 * the criteria. 
	 * 
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
	
	/** Method to perform a Pirate attack onto a user. Prints a stream of information
	 * detailing the current progress of the attack
	 * 
	 */
	@Override
	public void pirateAttack(Route route) {
		Pirates pirates = gameEnvironment.getPirates();
		// Roll die
		System.out.println(Pirates.getDescription());
		System.out.println("(Press enter to roll)");
		scanner.nextLine();
		int roll = pirates.rollDice();
		System.out.format("You got a %d!\n", roll);
		// Pirate Attack 
		
		String pirateOutcome = pirates.attackShip(roll, gameEnvironment.getShip());
		if (pirateOutcome.equals("attack_failed")) {
			System.out.println("You successful fended of the pirates!");
		}
		else if (pirateOutcome.equals("attack_successful")) {
			System.out.println("The pirate's boarded yur ship and stole your goods!");
		}
		else {
			finishGame("You have less goods than what the pirates demand. \n"
        			+ "You and your crew have to walk the plank!");
		}
	}
	
	/** Method to check if a player has enough money in order to continue a game
	 * If they dont have enough money, then the finishGame(String) method if called to 
	 * end the current game
	 * 
	 */
	private void checkSufficientMoney() {
		gameEnvironment.minMoneyRequired();
		int balance = gameEnvironment.getPlayer().getMoneyBalance();
		if (balance < gameEnvironment.calculateLiquidValue()) {
			finishGame("You don't have enough money and can't sell enough goods to repair your ship and pay your crew wages. You are stranded!");
		}
		// If player has less money than min money required to travel, prints a message warning them.
		else if (balance < gameEnvironment.getMinMoneyToTravel()) {
			System.out.format("You currently don't have enough money to repair your ship and pay your crew. This requires $%d\n",
					gameEnvironment.getMinMoneyToTravel());
		}
	}

	@Override
	public void badWeather(Route route, int damageDone) {;
		System.out.format("Oh no! You have encountered bad weather and your ship has suffered %d damamged.\n", damageDone);
	}

	@Override
	public void rescueSailor(Route route) {
		System.out.println("You have encountered a stranded sailor and saved him. To show his gradiuted he has \n given you $50");
	}
}









