package uiClasses;

import coreClasses.*;

public class CmdLineUi implements GameUi{
	
	enum PlayOptions{
		// TODO add these
	}
	private GameEnvironment gameEnvironment;
	private Scanner scanner;
	
	// Indicates whether the cmd line should finish, leave here?
	private boolean finish = false;
	
	public void setup(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
		// TODO implement
	}
	
	public void play() {
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
}
