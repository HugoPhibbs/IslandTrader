package uiClasses.gui;
import java.awt.Color; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import coreClasses.GameEnvironment;

/** Represents a Screen object for GUI
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 25/5/21
 * @since 18/5/21
 * 
 */
public abstract class Screen {
	
	// Class Variables //
	/** GameEnvironment object for this current game. */
	public final GameEnvironment game;
	
	/** The frame for this screen. Holds all components and other containers. */
	public JFrame frame;
	
	/** Border object that creates black borders that can be used by all implementations of this class. */
	public Border blackline = BorderFactory.createLineBorder(Color.black);
	
	
	/** Constructor for the Screen class
	 * 
	 * @param title String for the title of this Screen
	 * @param gameEvironment GameEnvironment object for this current "Island Trader" game
	 */
	protected Screen(String title, GameEnvironment gameEvironment) {
		this.game = gameEvironment;
		this.frame = new JFrame();
		frame.setTitle(title);
		
		setFrameCharacteristics();
	}
	
	/** Sets the characteristics of the frame that are common to all screens.
	 * 
	 */
	private void setFrameCharacteristics() {
		// Prevent the user from quiting immediately when quit is clicked.
		// Code copied from Rocket Manager Example
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmQuit();
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	/** This is where the bounds of your frame will be set and methods which create the components
	 * will be called. Is called at the end of the constructor method.
	 * 
	 */
	protected abstract void initialize();
	
	/** Disposes of the Screen's frame.
	 * 
	 */
	public void quit() {
		frame.dispose();
	}
	
	/** Makes the screen visible to the user.
	 * 
	 */
	protected void show() {
		frame.setVisible(true);
	}
	
	/** Makes the screen invisible to a user
	 * 
	 */
	protected void hide() {
		frame.setVisible(false);
	}
	
	/** Displays a Dialog to allow the user to confirm whether they would like to quit
	 * 
	 * @return Boolean true if would like to quit, else false
	 */
	protected boolean confirmQuit() {
		int selection = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?",
                "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return selection == JOptionPane.YES_OPTION;
	}

	
	/** Displays any Error to the user.
	 * 
	 * @param error String the error message.
	 */
	public void showError(String error) {
        JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/** Removes and refreshes all the components of a panel
	 * Useful for clearing a panel to prepare it for other uses
	 * 
	 * @param panel JPanel that is to be cleared
	 */
	public void clearPanel(JPanel panel) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}
	
	/** Getter method for the current game of this screen
	 * 
	 * @return GameEnvironment object for this current game
	 */
	protected GameEnvironment getGame() {
		return game;
	}
}
