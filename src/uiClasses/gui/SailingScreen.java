package uiClasses.gui;

import javax.swing.JProgressBar;  
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import coreClasses.*;

/** Represents a screen for Sailing between two islands
 * 
 * @author Jordan Vegar
 *
 */
public class SailingScreen extends Screen {
	// Class attributes //
	/** The Island that a user is trying to traveling to */
	private Island destinatonIsland;
	
	/** Route object that a user is currently traveling on to destinationIsland */
	private Route route;
	
	/** Progress bar reflecting the current progress of this journey to destinationIsland */
	private JProgressBar progressBar;
	
	/** Current progress in percent of traveling to another Island, influenced by the speed of a Ship and a Route's speed */
	private int progressPercent = 0;
	
	/** int The time in milliseconds to wait before each update of the progress bar. Based on the amount 
	 * of days sailing a route is with the player's Ship.
	 */
	private int delay;
	
	/** Label for the name of the Island that a user is currently traveling to */
	private JLabel lblIslandName;
	
	/** Timer which makes event calls to update the progress bar.*/
	private Timer timer;
	
	
	/** Constructor for SailingScreen
	 * 
	 * @param game GameEnvironment object for this current game
	 * @param island Island that a user is currently traveling to
	 * @param route Route object that a user is currently traveling on to an Island
	 */
	public SailingScreen(GameEnvironment game, Island island, Route route) {
		super("Sailing", game);
		this.destinatonIsland = island;
		this.route = route;
		initialize();
	}

	/**
	 * Sets the bounds/size of the frame, calculates and sets delay, and calls the methods to create and 
	 * add the components to the frame. 
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 450, 300);
		int routeDuration = game.calculateDaysSailing(route);
		delay = 100 * routeDuration;
		
		createProgressBar();
		createLabels();
	}
	
	/** Starts progress bar timer for the journey on the way to the Island */
	public void startProgress() {
		timer = new Timer(delay, (event) -> firstHalfProgressBar());
		timer.start();
	}
	
	/** Handles the first half of the progress bar
	 * Once the journey to a new Island has reached half way, it calls
	 * setSail() to handle the occurrence of any random events  
	 */
	private void firstHalfProgressBar() {
		progressPercent += 5;
		progressBar.setValue(progressPercent);
		
		if (progressPercent >= 50) {
			timer.stop();
			startSail();
		}
	} 
	
	
	/** Fills progress bar, time taken depends on how many days the route takes to sail.
	 * if a random event occurs, then this screen is quit, otherwise the sail is continued
	 */
	private void startSail() {
		boolean eventOccurred = game.sailToNewIsland(route, destinatonIsland);

		if (!eventOccurred) {
			finishProgress();
		} else {
			quit();
		}
	}
	
	/** Handles the finishing of the of the progress
	 * calls secondHalfProgress()
	 */
	public void finishProgress() {
		progressPercent = 50;
		timer = new Timer(delay, (event) -> secondHalfProgressBar());
		timer.start();
	}
	
	/** Handles the second half of the progress bar, once it is completed
	 * calls endSail() to finish traveling to an Island
	 */
	private void secondHalfProgressBar() {
		progressPercent += 5;
		progressBar.setValue(progressPercent);
		
		if (progressPercent >= 100) {
			timer.stop();
			endSail();
		}
	} 
	
	/** Handles the finish of a sail to new Island. */
	protected void endSail() {
		Screen coreOptionsScreen = new CoreOptionsScreen(game);
		coreOptionsScreen.show();
		quit();
	}
	
	/** Creates a progress bar to reflect the current progress of
	 * traveling between one island and another
	 */
	private void createProgressBar() {
		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(30, 167, 390, 39);
		progressBar.setValue(0);
		progressBar.setBorderPainted(true);
		frame.getContentPane().add(progressBar);
	}
	
	/** Creates the labels needed for this screen */
	private void createLabels() {
		JLabel lblSailingTo = new JLabel("Sailing to");
		lblSailingTo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSailingTo.setBounds(185, 44, 80, 17);
		frame.getContentPane().add(lblSailingTo);
		
		lblIslandName = new JLabel(destinatonIsland.getIslandName());
		lblIslandName.setHorizontalAlignment(SwingConstants.CENTER);
		lblIslandName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblIslandName.setBounds(150, 91, 150, 24);
		frame.getContentPane().add(lblIslandName);
	}
}
