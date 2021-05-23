package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import coreClasses.GameEnvironment;
import coreClasses.Pirates;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PirateScreen extends Screen {

	private Pirates pirate;
	private JLabel lblDiceOutcome;
	private JLabel lblAttackOutcome;
	
	/**
	 * Create the application.
	 */
	public PirateScreen(GameEnvironment game) {
		super("Pirate Attack", game);
		pirate = game.getPirates();
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 650, 450);
		
		createRollDiceButton();
		createContinueButton();
		createLabels();
	}
	
	private void onDiceRoll() {
		if (lblDiceOutcome.getText() == "") {
			int diceOutcome = Pirates.rollDice();
			lblDiceOutcome.setText(String.format("You got a %d", diceOutcome));
			String outcome = pirate.attackShip(diceOutcome, game.getShip());
			lblAttackOutcome.setText(outcome);
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
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 14));
		btnContinue.setBounds(521, 389, 117, 25);
		frame.getContentPane().add(btnContinue);
	}
	
	private void createLabels() {
		JLabel lblPirateAttack = new JLabel("PIRATE ATTACK!");
		lblPirateAttack.setFont(new Font("Dialog", Font.BOLD, 32));
		lblPirateAttack.setBounds(167, 12, 297, 38);
		frame.getContentPane().add(lblPirateAttack);
		
		JLabel lblInstructions = new JLabel("<html> Oh no, pirates are attacking your ship. They will try <br>        to steal your loot, roll the dice to defend. </html>");
		lblInstructions.setFont(new Font("Dialog", Font.BOLD, 17));
		lblInstructions.setBounds(81, 76, 483, 60);
		frame.getContentPane().add(lblInstructions);
		
		lblDiceOutcome = new JLabel("");
		lblDiceOutcome.setFont(new Font("Dialog", Font.BOLD, 17));
		lblDiceOutcome.setBounds(269, 320, 118, 20);
		frame.getContentPane().add(lblDiceOutcome);
		
		lblAttackOutcome = new JLabel("");
		lblAttackOutcome.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAttackOutcome.setBounds(23, 352, 600, 25);
		frame.getContentPane().add(lblAttackOutcome);
	}
}
