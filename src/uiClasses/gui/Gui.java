package uiClasses.gui;

import uiClasses.GameUi;
import coreClasses.GameEnvironment;

public class Gui implements GameUi{
	private GameEnvironment gameEnvironment;
	private Screen screen; // Screen currently interacting with GUI
	
	public void setup(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
		// TODO implement
	}
	
	public void play() {
		// TODO implement
	}
}
