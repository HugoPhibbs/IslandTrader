package uiClasses.gui;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

import coreClasses.GameEnvironment;
import coreClasses.Route;

import javax.swing.JButton;

/** Represents a Random Weather event
 * 
 * @author Jordan Vegar
 * @version 24/5/21
 * @since 18/5/21
 */
public class WeatherScreen extends Screen {

	/** The route the attack occurs on.*/
	private Route route;
	
	/** Damage done by an UnfortunateWeather event onto a ship */
	private int damageDone;

	/** Constructor for WeatherScreen class
	 * 
	 * @param game GameEnvironment object for this current game
	 * @param route Route object that a user is currently sailing on. 
	 * @param damageDone Integer for how much damage done by UnfortunateWeather from core classes
	 */
	public WeatherScreen(GameEnvironment game, Route route, int damageDone) {
		super("Weather", game);
		this.route = route;
		this.damageDone = damageDone;
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 600, 420);
		
		createContinueButton();
		createLabels();
	}
	
	/** Method to handle the pressing of the continue button
	 * Closes the screen and creates as new sailing screen. 
	 */
	private void onContinueButton() {
		SailingScreen sailingScreen = new SailingScreen(game, game.getCurrentIsland(), route);
		sailingScreen.finishProgress();
		sailingScreen.show();
		quit();
	}
	
	/** Creates a continue to be pressed by user when they want to
	 * continue arriving at an island
	 */
	private void createContinueButton() {
		JButton btnContinue = new JButton("Continue");
		btnContinue.setBackground(new Color(153, 204, 255));
		btnContinue.addActionListener(e -> onContinueButton());
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 14));
		btnContinue.setBounds(242, 359, 117, 25);
		frame.getContentPane().add(btnContinue);
	}
	
	/** Method to create the labels for this screen
	 * 
	 */
	private void createLabels() {
		JLabel lblTitle = new JLabel("UNFORTUNATE WEATHER");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 32));
		lblTitle.setBounds(66, 12, 486, 38);
		frame.getContentPane().add(lblTitle);
	
		
		// Use html to display text in Labels with multiple lines
		JLabel lblInfo = new JLabel(String.format("<html> Oh no! You have sailed into a storm and the <br>"
				+ "wild weather did %d damage to your ship. <html>", damageDone));
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInfo.setBounds(12, 200, 576, 38);
		frame.getContentPane().add(lblInfo);
	}
}
