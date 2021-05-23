package uiClasses.gui;

import uiClasses.GameUi;
import coreClasses.GameEnvironment;

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
		// TODO Auto-generated method stub	
	}


	@Override
	public void pirateAttack() {
		// TODO Auto-generated method stub
		
	}
}
