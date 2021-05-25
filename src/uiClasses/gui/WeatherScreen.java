package uiClasses.gui;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import coreClasses.GameEnvironment;
import coreClasses.Route;

import javax.swing.JButton;

/** Represents a random weather event
 * 
 * @author Jordan Vegar
 * @version 24/5/21
 * @since 18/5/21
 */
public class WeatherScreen extends Screen {

	/** The route the attack occurs on.*/
	private Route route;
	private int damageDone;

	/**
	 * Create the application.
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
	
	/** Method to handle the pressing of the continue button */
	private void onContinueButton() {
		SailingScreen sailingScreen = new SailingScreen(game, game.getCurrentIsland(), route);
		quit();
		sailingScreen.endSail();
	}
	
	private void createContinueButton() {
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(e -> onContinueButton());
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 14));
		btnContinue.setBounds(242, 359, 117, 25);
		frame.getContentPane().add(btnContinue);
	}
	
	private void createLabels() {
		JLabel lblTitle = new JLabel("UNFORTUNATE WEATHER");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 32));
		lblTitle.setBounds(66, 12, 486, 38);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblImage = new JLabel("IMAGE HERE");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(53, 82, 494, 227);
		frame.getContentPane().add(lblImage);
		
		JLabel lblInfo = new JLabel(String.format("<html> Oh no! You have sailed into a storm and the <br>"
				+ "wild weather did %d damage to your ship. <html>", damageDone));
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInfo.setBounds(12, 310, 576, 38);
		frame.getContentPane().add(lblInfo);
	}
}
