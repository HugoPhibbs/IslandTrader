package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import coreClasses.GameEnvironment;
import coreClasses.Item;
import coreClasses.Pirates;
import coreClasses.Route;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PirateScreen extends Screen {

	private Pirates pirate;
	private JLabel lblDiceOutcome;
	private JLabel lblAttackOutcome;
	private String outcome;
	/** The route the attack occurs on.*/
	private Route route;
	
	/**
	 * Create the application.
	 */
	public PirateScreen(GameEnvironment game, Route route) {
		super("Pirate Attack", game);
		pirate = game.getPirates();
		this.route = route;
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
	
	private void onDiceRoll() {
		if (lblDiceOutcome.getText() == "") {
			int diceOutcome = pirate.rollDice();
			lblDiceOutcome.setText(String.format("You got a %d", diceOutcome));
			outcome = pirate.attackShip(diceOutcome, game.getShip());
			
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
	
	private void onContinueButton() {
		if (outcome.equals("game_over")) {
			game.getUi().finishGame("You have less goods than what the pirates demand. \n"
        			+ "You and your crew have to walk the plank!");
			quit();
		}
		else {
			SailingScreen sailingScreen = new SailingScreen(game, game.getCurrentIsland(), route);
			quit();
			sailingScreen.endSail();
		}
	}
	
	

	private void createRollDiceButton( ) {
		JButton btnRollDice = new JButton("Roll the Dice");
		btnRollDice.addActionListener(e -> onDiceRoll());
		btnRollDice.setFont(new Font("Dialog", Font.BOLD, 18));
		btnRollDice.setBounds(213, 148, 210, 160);
		frame.getContentPane().add(btnRollDice);
	}
	
	private void createContinueButton() {
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(e -> onContinueButton());
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 14));
		btnContinue.setBounds(521, 439, 117, 25);
		frame.getContentPane().add(btnContinue);
	}
	
	private void createLabels() {
		JLabel lblPirateAttack = new JLabel("PIRATE ATTACK!");
		lblPirateAttack.setFont(new Font("Dialog", Font.BOLD, 32));
		lblPirateAttack.setBounds(167, 12, 297, 38);
		frame.getContentPane().add(lblPirateAttack);
		
		JLabel lblInstructions = new JLabel("<html> Oh no, pirates are attacking your ship. They will try <br>"
				+ "to steal your loot, roll the dice to defend. </html>");
		lblInstructions.setFont(new Font("Dialog", Font.BOLD, 17));
		lblInstructions.setBounds(81, 76, 483, 60);
		frame.getContentPane().add(lblInstructions);
		
		lblDiceOutcome = new JLabel("");
		lblDiceOutcome.setFont(new Font("Dialog", Font.BOLD, 17));
		lblDiceOutcome.setBounds(269, 320, 118, 20);
		frame.getContentPane().add(lblDiceOutcome);
		
		lblAttackOutcome = new JLabel("");
		lblAttackOutcome.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAttackOutcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttackOutcome.setBounds(23, 352, 600, 75);
		frame.getContentPane().add(lblAttackOutcome);
	}
}
