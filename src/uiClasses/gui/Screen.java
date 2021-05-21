package uiClasses.gui;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	/**
	 * The frame for this screen. Holds all components and other containers. 
	 */
	private JFrame frame;
	
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
	
	
	/**
	 * Initializes the Screen
	 * @param title String the title of the Screen
	 */
	private void initialize(final String title) {
		frame = new JFrame();
		frame.setTitle(title);
		
		// Prevent the user from quiting immediately when quit is clicked.
		// Code copied from Rocket Manager Example
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmQuit();
			}
		});
		
	}
	
	/**
     * Creates and adds the required graphical components to the given container.
     *
     * @param container The container to add content to
     */
    protected abstract void initialise(Container container);
	
	
	protected GameEnvironment getGame() {
		return game;
	}
	
	/**
	 * Makes the screen visible to the user.
	 */
	protected void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Displays a Dialog to allow the user to confirm whether they would like to quit
	 * @return Boolean true if would like to quit, else false
	 */
	protected boolean confirmQuit() {
		int selection = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?",
                "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return selection == JOptionPane.YES_OPTION;
	}
	
	/**
	 * Disposes of the Screen's frame.
	 */
	public void quit() {
		frame.dispose();
	}
	
	/**
	 * Abstract method. Classes which extend screen will implement goBack() to re-create
	 * the screen of the prior (parent) screen. Will then close the current screen.
	 */
	public abstract void goBack();
	
	/**
	 * Displays any Error to the user.
	 * @param error String the error message.
	 */
	public void showError(String error) {
        JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);

	}
	
	public Screen getParent() {
		return parent;
	}
}
