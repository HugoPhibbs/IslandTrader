package uiClasses;

import coreClasses.*;

import java.util.InputMismatchException;
import java.util.Scanner;

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
		
		// TODO: implement
		// this is just another use of the takeTurn method from gameEnvironment class
		// So it will have a 
	}
	
	// needs to have methods from gameEnvironment that 'twin' the method
	// so all the methods in ge need to be turned into returning things
	
	// so for a visitStore method in main it would basically be printing things that are outputted from the visitStore method in gameEnvirionemt
	
	public void visitStore() {
		/* twin method for the visitStore method from game environment
		 * 
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
