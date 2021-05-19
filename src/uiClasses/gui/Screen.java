package uiClasses.gui;
import coreClasses.GameEnvironment;


/** Represents a Screen object for GUI
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 19/5/21
 * @since 18/5/21
 * 
 */
public abstract class Screen {
	
	/** GameEnvironment object for this current game */
	private final GameEnvironment game;
	/** Screen that called this screen. Makes it easy to go back. CoreOptionsScreen and
	 * SetupScreen dont have parents
	 */
	private final Screen parent;
	
	/** Constructor for the Screen class
	 * 
	 * @param title 
	 * @param gameEvironment
	 * @param parent
	 */
	protected Screen(String title, GameEnvironment gameEvironment, Screen parent) {
		this.game = gameEvironment;
		this.parent = parent;
		initialize(title);
	}
	
	abstract protected void initialize(String title);
	
	
	protected GameEnvironment getGame() {
		return game;
	}
	
	protected void show() {
		
	}
	
	protected boolean confirmQuit() {
		return true;
	}
	
	public void quit() {
		
	}
	
	public void goBack() {
		
	}
	
	public void showError() {
		
	}
	
	public Screen getParent() {
		return parent;
	}
}
