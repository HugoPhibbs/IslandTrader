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

	/**
	 * Create the application.
	 */
	public WeatherScreen(GameEnvironment game, Route route) {
		super("Weather", game);
		this.route = route;
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
		
		JLabel lblInfo = new JLabel("<html> Oh no! You have sailed into a storm <br>and your ship has been damaged. <html>");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInfo.setBounds(127, 310, 368, 38);
		frame.getContentPane().add(lblInfo);
	}
}
