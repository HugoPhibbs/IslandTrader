package uiClasses.gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import coreClasses.GameEnvironment;
import coreClasses.Route;

public class RescueScreen extends Screen {


	/** The route the attack occurs on.*/
	private Route route;
	/**
	 * Create the application.
	 */
	public RescueScreen(GameEnvironment game, Route route) {
		super("Stranded Sailor", game);
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
		sailingScreen.finishProgress();
		quit();
		sailingScreen.show();
	}
	
	private void createContinueButton() {
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(e -> onContinueButton());
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 14));
		btnContinue.setBounds(242, 359, 117, 25);
		frame.getContentPane().add(btnContinue);
	}
	
	private void createLabels() {
		JLabel lblTitle = new JLabel("STRANDED SAILOR");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 32));
		lblTitle.setBounds(66, 12, 486, 38);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblImage = new JLabel("IMAGE HERE");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(53, 82, 494, 227);
		frame.getContentPane().add(lblImage);
		
		JLabel lblInfo = new JLabel("<html> You spotted a stranded sailor through your telescope and <br> saved him! He has thanked you by giving you 50 Pirate Bucks  <html>");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInfo.setBounds(28, 309, 548, 38);
		frame.getContentPane().add(lblInfo);
	}
}
