package uiClasses;

import coreClasses.*;

import java.util.ArrayList;
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
	
	public void playGame() {
		
		while (!finish) {
			printOptions();
			int input = getInt(1, 6);
			
			handleChoice(input);
		}
		
	}
	
	private void printOptions() {
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
	public int getInt(int lowerBound, int upperBound) {
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
	
	private void handleChoice(int input) {
		switch (input) {
		case 1: 
			// option to view the amount of money and days remaining
			viewPlayerInfo(gameEnvironment.getPlayer());
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

	
	// needs to have methods from gameEnvironment that 'twin' the method
	// so all the methods in ge need to be turned into returning things
	
	// so for a visitStore method in main it would basically be printing things that are outputted from the visitStore method in gameEnvirionemt
	
	public void visitStore() {
		/* twin method for the visitStore method from game environment
		 * is a bit special compared to the gui version, bc this actually prints out things
		 * we will see later on down the road with how to implement the GUI!
		 */
		
	}
	
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
	
	private void viewPlayerInfo(Player player) {
		System.out.format("%s has $%d and %d days remaining.\n", player.getName(), player.getMoneyBalance(), gameEnvironment.getDaysRemaining());
	}
	
	private void viewShipProperties() {
		
	}
	
	private void viewGoodsPurchased() {
		
	}
	
	private void viewOtherIslands() {
		ArrayList<Island> otherIslands = getOtherIslandsList();
		
		for (Island otherIsland: otherIslands) {
				System.out.println(otherIsland);
		}
		
		String input = TakeInput.inputString("To view more information about an island, enter the name of the island.");
		
		for (Island island: otherIslands) {
			if (island.getIslandName().toLowerCase() == input) {
				String islandInfo = "The island " + island.getIslandName() + " can be reached from your current island by the following routes:\n";
				islandInfo += gameEnvironment.getCurrentIsland().viewRoutes(island);  // TODO: separate view routes from choose route.
				// TODO: add info string about what the island's store buys and sells. 
				// TODO : restructure player
			}
		}
	}
	
	private void travelToIsland() {
		
	}
	
}
