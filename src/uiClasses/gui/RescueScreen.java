package uiClasses.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import coreClasses.GameEnvironment;
import coreClasses.Route;

public class RescueScreen extends Screen {

	/** The route the attack occurs on.*/
	private Route route;
	
	/**
	 * Create the screen by calling the parent's constructor and initializing the class variable route. 
	 */
	public RescueScreen(GameEnvironment game, Route route) {
		super("Stranded Sailor", game);
		this.route = route;
		initialize();	
	}

	/** Sets the bound/size of the frame and calls the methods that create and add the frame's components*/
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 600, 420);
		
		createContinueButton();
		createLabels();
	}
	
	/** Disposes of this Screen and creates the SailingScreen, to finish sailing to the next Island. */
	private void onContinueButton() {
		SailingScreen sailingScreen = new SailingScreen(game, game.getCurrentIsland(), route);
		sailingScreen.finishProgress();
		quit();
		sailingScreen.show();
	}
	
	/** Creates the JButton btnContinue.*/
	private void createContinueButton() {
		JButton btnContinue = new JButton("Continue");
		btnContinue.setBackground(new Color(153, 204, 255));
		btnContinue.addActionListener(e -> onContinueButton());
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 14));
		btnContinue.setBounds(242, 359, 117, 25);
		frame.getContentPane().add(btnContinue);
	}
	
	/** Creates the JLabels that give information to the player about the random event rescue sailor. */
	private void createLabels() {
		JLabel lblTitle = new JLabel("STRANDED SAILOR");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 32));
		lblTitle.setBounds(66, 12, 486, 38);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblInfo = new JLabel("<html> You spotted a stranded sailor through your telescope and <br> saved him! He has thanked you by giving you 50 Pirate Bucks  <html>");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInfo.setBounds(28, 200, 548, 38);
		frame.getContentPane().add(lblInfo);
	}
}
