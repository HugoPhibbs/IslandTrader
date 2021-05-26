package uiClasses.gui;

import uiClasses.GameUi;
import coreClasses.GameEnvironment;
import coreClasses.Route;

/** Represent the core operating class for the GUI for Island Trader
 * 
 * @author Jordan Vegar
 */
public class Gui implements GameUi{
	
	
	/** GameEnvironment object for this game, used to store all necessary objects needed for the game */
	private GameEnvironment gameEnvironment;
	
	/** Creates and shows the setupScreen, in which the user enters their information and makes the
	 * choices required to start the game. 
	 */
	@Override
	public void setup(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
		Screen setupScreen = new SetupScreen(gameEnvironment);
		setupScreen.show();
	}
	/** Creates the coreOptionsScreen from which the player can select which action they would like to do.
	 */
	@Override
	public void playGame() {
		Screen optionsScreen = new CoreOptionsScreen(gameEnvironment);
		optionsScreen.show();	
	}
	/** Ends the game and creates the gameOverScreen which gives information on why the game ended and how
	 *  well the player did.
	 */
	@Override
	public void finishGame(String message) {
		Screen gameOverScreen = new GameOverScreen(gameEnvironment, message);
		gameOverScreen.show();
	}

	/** When a pirate attack occurs, creates a screen that allows the player to roll a dice to defend against
	 * the pirates. Displays the outcome of the attack.
	 */
	@Override
	public void pirateAttack(Route route) {
		Screen pirateScreen = new PirateScreen(gameEnvironment, route);
		pirateScreen.show();
	}
	/** When bad weather occurs, creates a screen that tells the player. Also tells the player how much damage the
	 * bad weather did to their ship.
	 */
	@Override
	public void badWeather(Route route, int damageDone) {
		Screen weatherScreen = new WeatherScreen(gameEnvironment, route, damageDone);
		weatherScreen.show();
	}
	/** When a sailor is rescued, this method creates a screen to tell the player, and tell them how much money
	 * they receive as thanks. 
	 */
	@Override
	public void rescueSailor(Route route) {
		Screen rescueScreen = new RescueScreen(gameEnvironment, route);
		rescueScreen.show();
	}
}
