package uiClasses;

import coreClasses.*;

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
	// Indicates whether the cmd line should finish, leave here?
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
		Player player = new Player(playerName, 100, gameDuration, startIsland);
		// and ship
		Ship ship = pickShip();
		
		gameEnvironment.onSetupFinished(player, ship);
	}
	
	public void playGame() {
		
		while (!finish) {
			printOptions();
			int input = getInt(1, 6);
			
			
			
			
			
		}
		
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
	
	
	private void printOptions() {
		String options = "Enter an action's number:\n(1) View your money and days remaining.\n(2) View the propeties of your ship.\n"
				+ "(3) View the goods you have purchased.\n(4) View the properties of each Island.\n"
				+ "(5) Visit the store on " + gameEnvironment.getPlayer().getCurrentIsland() + " (current island).\n(6) Set sail to another Island.";
		
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
		}
	}
	
	/**
	 * Displays a list of ships and their qualities, and takes input to choose which will be used.
	 * @return myShip the ship you have chosen to use in this play through.
	 */
	private Ship pickShip() {
		// TODO implement this
	}
}
