package uiClasses.gui;

import uiClasses.GameUi;
import coreClasses.GameEnvironment;
import coreClasses.Route;

public class Gui implements GameUi{
	
	private GameEnvironment gameEnvironment;

	public void setup(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
		Screen setupScreen = new SetupScreen(gameEnvironment);
		setupScreen.show();
	}

	@Override
	public void playGame() {
		
		Screen optionsScreen = new CoreOptionsScreen(gameEnvironment);
		optionsScreen.show();	
	}

	@Override
	public void finishGame(String message) {
		Screen gameOverScreen = new GameOverScreen(gameEnvironment);
		gameOverScreen.show();
	}


	@Override
	public void pirateAttack(Route route) {
		Screen pirateScreen = new PirateScreen(gameEnvironment, route);
		pirateScreen.show();
	}

	@Override
	public void badWeather(Route route) {
		Screen weatherScreen = new WeatherScreen(gameEnvironment, route);
		weatherScreen.show();
	}

	@Override
	public void rescueSailor(Route route) {
		Screen rescueScreen = new RescueScreen(gameEnvironment, route);
		rescueScreen.show();
	}
}
