package uiClasses.gui;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import coreClasses.GameEnvironment;
import coreClasses.Pirates;
import coreClasses.Route;

import javax.swing.JButton;

public class PirateScreen extends Screen {
	// Class Variables //
	/** Pirates object for this pirates attack */
	private Pirates pirates;
	
	/** Label to show the outcome of the dice roll within Pirates class */
	private JLabel lblDiceOutcome;
	
	/** Label to show the outcome of an attack on a ship */
	private JLabel lblAttackOutcome;
	
	/** String for the result of an attack by pirates on a ship */
	private String outcome;
	
	/** The route the attack occurs on.*/
	private Route route;
	
	/** Button that a user presses after a pirate attack has occurred */
	private JButton btnContinue;
	
	/** Button that a user presses to roll a dice */
	private JButton btnRollDice;
	
	
	/** Constructor for a PiratesScreen class
	 * 
	 * @param game GameEnvironment object for this current game
	 * @param route Route object that this player is currently traveling on
	 */
	public PirateScreen(GameEnvironment game, Route route) {
		super("Pirate Attack", game);
		this.route = route;
		pirates = game.getPirates();
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 650, 500);
		
		createRollDiceButton();
		createContinueButton();
		createLabels();
	}
	
	/** Method to create a button that a user presses when they want to roll a dice to play their chances
	 * against a pirate attack.
	 */
	private void createRollDiceButton( ) {
		this.btnRollDice = new JButton("Roll the Dice");
		btnRollDice.addActionListener(e -> onDiceRoll());
		btnRollDice.setFont(new Font("Dialog", Font.BOLD, 18));
		btnRollDice.setBounds(213, 148, 210, 160);
		frame.getContentPane().add(btnRollDice);
	}
	
	/** Method to create a button that a user presses when they want to continue to the next screen */
	private void createContinueButton() {
		this.btnContinue = new JButton("Continue");
		btnContinue.setEnabled(false);
		btnContinue.addActionListener(e -> onContinueButton());
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 14));
		btnContinue.setBounds(521, 439, 117, 25);
		frame.getContentPane().add(btnContinue);
	}
	
	/** Method to create the labels describing a pirate attack */
	private void createLabels() {
		JLabel lblPirateAttack = new JLabel("PIRATE ATTACK!");
		lblPirateAttack.setFont(new Font("Dialog", Font.BOLD, 32));
		lblPirateAttack.setBounds(167, 12, 297, 38);
		frame.getContentPane().add(lblPirateAttack);
		
		// Use html to display multiple text lines on the button
		JLabel lblInstructions = new JLabel("<html> Oh no, pirates are attacking your ship. They will try <br>"
				+ "to steal your loot, roll the dice to defend. </html>");
		lblInstructions.setFont(new Font("Dialog", Font.BOLD, 17));
		lblInstructions.setBounds(81, 76, 483, 60);
		frame.getContentPane().add(lblInstructions);
		
		this.lblDiceOutcome = new JLabel("");
		lblDiceOutcome.setFont(new Font("Dialog", Font.BOLD, 17));
		lblDiceOutcome.setBounds(269, 320, 118, 20);
		frame.getContentPane().add(lblDiceOutcome);
		
		this.lblAttackOutcome = new JLabel("");
		lblAttackOutcome.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAttackOutcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttackOutcome.setBounds(23, 352, 600, 75);
		frame.getContentPane().add(lblAttackOutcome);
	}
	
	/** Method to handle the outcome of a dice roll by a user */
	private void onDiceRoll() {	
		btnContinue.setEnabled(true);
		btnRollDice.setEnabled(false);
		if (lblDiceOutcome.getText() == "") {
			int diceOutcome = pirates.rollDice();
			lblDiceOutcome.setText(String.format("You got a %d", diceOutcome));
			outcome = pirates.attackShip(diceOutcome, game.getShip());
			
			if (outcome.equals("game_over")) {
				lblAttackOutcome.setText("<html> The pirates boarded your ship and you don't have enough goods <br>"
	        									+ "to satisfy them. You and your crew have to walk the plank! <html>");
			} else if (outcome.equals("attack_successful")) {
				lblAttackOutcome.setText("The pirate's boarded yur ship and stole all your goods!");
			} else {
				lblAttackOutcome.setText("You successful fended of the pirates!");
			}
		}
	}
	
	/** Method to handle the pressing of the continue button after a pirate attack has occurred
	 * Closes this screen and handles flow to either game over or traveling to another island.
	 */
	private void onContinueButton() {
		// Close this screen
		quit();
		if (outcome.equals("game_over")) {
			game.getUi().finishGame("You have less goods than what the pirates demand. \n"
        			+ "You and your crew have to walk the plank!");
			quit();
		}
		else {
			// User had enough goods to satisfy the Pirates, continue traveling to the next island
			SailingScreen sailingScreen = new SailingScreen(game, game.getCurrentIsland(), route);
			sailingScreen.finishProgress();
			quit();
			sailingScreen.show();
		}
	}
}
