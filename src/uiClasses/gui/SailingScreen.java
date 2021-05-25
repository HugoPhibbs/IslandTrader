package uiClasses.gui;

import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import coreClasses.*;

public class SailingScreen extends Screen {
	
	private Island desinatonIsland;
	private Route route;
	
	private JProgressBar progressBar;
	private int progressPercent = 0;
	private int delay;
	private JLabel lblIslandName;
	private Timer timer;
	private final PropertyChangeSupport pcs;
	
	/**
	 * Create the application.
	 */
	public SailingScreen(GameEnvironment game, Island island, Route route) {
		super("Sailing", game);
		this.desinatonIsland = island;
		this.route = route;
		pcs = new PropertyChangeSupport(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 450, 300);
		int routeDuration = game.calculateDaysSailing(route);
		delay = 200 * routeDuration;
		
		createProgressBar();
		createLabels();
	}
	
	public void startProgress() {
		timer = new Timer(delay, (event) -> firstHalfProgressBar());
	}
	

	private void firstHalfProgressBar() {
		progressPercent += 5;
		progressBar.setValue(progressPercent);
		
		if (progressPercent >= 50) {
			timer.stop();
			startSail();
		}
	} 
	
	/** Fills progress bar, time taken depends on how many days the route takes to sail.*/
	private void startSail() {
		boolean eventOccurred = game.sailToNewIsland(route, desinatonIsland);
		if (!eventOccurred) {
			endSail();
		} else {
			quit();
		}
	}
	
	protected void endSail() {
		Screen optionsScreen = new CoreOptionsScreen(game);
		quit();
		optionsScreen.show();
	}
	
	private void createProgressBar() {
		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(30, 167, 390, 39);
		progressBar.setValue(0);
		progressBar.setBorderPainted(true);
		progressBar.setStringPainted(true);

		frame.getContentPane().add(progressBar);
	}
	
	private void createLabels() {
		JLabel lblSailingTo = new JLabel("Sailing to");
		lblSailingTo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSailingTo.setBounds(185, 44, 80, 17);
		frame.getContentPane().add(lblSailingTo);
		
		lblIslandName = new JLabel("Island Name");
		lblIslandName.setHorizontalAlignment(SwingConstants.CENTER);
		lblIslandName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblIslandName.setBounds(150, 91, 150, 24);
		frame.getContentPane().add(lblIslandName);
	}
}
