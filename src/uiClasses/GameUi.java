package uiClasses;

import coreClasses.*;

public interface GameUi {
	
	// String describing the name requirements, to be displayed/printed if the name entered is invalid.
	String NAME_REQUIREMENTS = "Invalid name. Please enter a name with 3-15 letters, no numbers or special characters.";
	
	// String describing the valid range of days to be chosen to be played.
	String DURATION_REQUIREMENTS = "Invalid game duration. Please enter a number of days (integer) between 20 and 50";
	
	// The amount of money the player starts with.
	int STARTING_MONEY = 100;
	
	public void setup(GameEnvironment gameEnvironment);
	
	public void playGame();
	
	public void finishGame(String message);
}
