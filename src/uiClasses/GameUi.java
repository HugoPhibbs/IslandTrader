package uiClasses;

import coreClasses.*;

/** Interface for ui classes for Island trader
 * 
 * @author Jordan Vegar
 *
 */
public interface GameUi {
	
	/** String describing the name requirements, to be displayed/printed if the name entered is invalid.*/
	String NAME_REQUIREMENTS = "Invalid name. Please enter a name with 3-15 letters, no numbers or special characters.";
	
	/** String describing the valid range of days to be chosen to be played. */
	String DURATION_REQUIREMENTS = "Invalid game duration. Please enter a number of days (integer) between 20 and 50";
	
	/** The amount of money the player starts with. */
	int STARTING_MONEY = 1000;
	
	/** Gets the input required to create the player and ship objects, then passes them to gameEnvironment
	 * to complete its setup. 
	 * 
	 * @param gameEnvironment GameEnvironment object for the current object
	 */
	public void setup(GameEnvironment gameEnvironment);
	
	/** Allows the user to pick which of the main actions of the game to perform, and calls
	 * methods/classes required to perform them.
	 */
	public void playGame();
	
	/** Called when the game ends, displays output telling the player why the game has ended, the profit they
	 * made, and the score they achieved.
	 * @param message String A message that gives a reason for the game ending, to be displayed to the player. 
	 */
	public void finishGame(String message);
	
	/** Method to perform a Pirate attack on a user. Prompts user to roll a dice, then tells them the outcome
	 * of the attack. 
	 * @param route Route The route the player was sailing along when the pirate attack occurred.
	 */
	public void pirateAttack(Route route);
	
	/** Method to display output describing a bad weather random event to the user. 
	 * 
	 * @param route Route The route the player was sailing along when the bad weather occurred
	 * @param damageDone int The amount of damage the weather dealt to your ship.
	 */
	public void badWeather(Route route, int damageDone);
	
	/** Method to display output describing the random event of rescuing a stranded sailor to the user. 
	 * 
	 * @param route Route The route the player was sailing along when they rescued a sailor occurred
	 */
	public void rescueSailor(Route route);
}
