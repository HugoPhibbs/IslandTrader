package uiClasses.gui;

import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.Timer;

import coreClasses.*;

public class SailingScreen extends Screen {
	
	private Island desinatonIsland;
	private Route route;
	
	private JProgressBar progressBar;
	private JLabel lblIslandName;
	private Timer timer;
	
	/**
	 * Create the application.
	 */
	public SailingScreen(GameEnvironment game, Island island, Route route) {
		super("Sailing", game);
		System.out.println("1");
		this.desinatonIsland = island;
		this.route = route;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 450, 300);
		
		createProgressBar();
		createLabels();
		System.out.println("2");
	}
	

	
	/** Fills progress bar, time taken depends on how many days the route takes to sail.*/
	public void sail() {
		int routeDuration = game.calculateDaysSailing(route);
		int delay = 200 * routeDuration;
		System.out.println("3");
		
		fillProgressBar(delay);
		game.sailToNewIsland(route, desinatonIsland);
		fillProgressBar(delay);
		
		Screen optionsScreen = new CoreOptionsScreen(game);
		this.quit();
		optionsScreen.show();
	}
	
	private void fillProgressBar(int delay) {
		System.out.println("4");
		int progress = 0;
		// half full progress bar.
		timer = new Timer(delay, new ActionListener () {
			
			@Override
            public void actionPerformed(ActionEvent e) {
                if(progress <= 50) progressBar.setValue(progress);
            }
		});
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
