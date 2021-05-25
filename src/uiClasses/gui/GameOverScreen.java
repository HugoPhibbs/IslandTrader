package uiClasses.gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.SwingConstants;

import coreClasses.GameEnvironment;

public class GameOverScreen extends Screen {

	private JLabel lblReason;
	private JLabel lblScore;
	private JLabel lblProfit;
	private JLabel lblDaysPlayed;
	private String preppedMessage;

	/**
	 * Create the application.
	 */
	public GameOverScreen(GameEnvironment game, String message) {
		super("Game Over", game);
		preppedMessage = prepareMessage(message);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 700, 500);
		
		createFinishButton();
		createImageLabel();
		createMainLabels();
	}
	
	private void createFinishButton() {
		JButton btnFinishGame = new JButton("Finish Game");
		btnFinishGame.addActionListener(e -> quit());
		btnFinishGame.setFont(new Font("Dialog", Font.BOLD, 16));
		btnFinishGame.setBounds(280, 405, 140, 29);
		frame.getContentPane().add(btnFinishGame);
	}
	
	private void createImageLabel() {
		JLabel lblBackgroundImage = new JLabel("");
		lblBackgroundImage.setFont(new Font("Dialog", Font.BOLD, 16));
		lblBackgroundImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackgroundImage.setBounds(0, 0, 700, 476);
		frame.getContentPane().add(lblBackgroundImage);
	}
	
	private void createMainLabels() {
		JLabel lblGameOver = new JLabel("GAME OVER " + game.getPlayer().getName().toUpperCase());
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setForeground(Color.RED);
		lblGameOver.setFont(new Font("Dialog", Font.BOLD, 32));
		lblGameOver.setBounds(0, 12, 700, 38);
		frame.getContentPane().add(lblGameOver);
		
		lblReason = new JLabel(String.format(preppedMessage));
		lblReason.setFont(new Font("Dialog", Font.BOLD, 18));
		lblReason.setHorizontalAlignment(SwingConstants.CENTER);
		lblReason.setBounds(20, 73, 660, 57);
		frame.getContentPane().add(lblReason);
		
		lblScore = new JLabel("SCORE: " + game.calculateScore());
		lblScore.setFont(new Font("Dialog", Font.BOLD, 20));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(0, 189, 700, 24);
		frame.getContentPane().add(lblScore);
		
		lblProfit = new JLabel("PROFIT: " + (game.getPlayer().getMoneyBalance() - game.getUi().STARTING_MONEY));
		lblProfit.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfit.setFont(new Font("Dialog", Font.BOLD, 20));
		lblProfit.setBounds(0, 250, 700, 24);
		frame.getContentPane().add(lblProfit);
		
		lblDaysPlayed = new JLabel("DAYS PLAYED: " + (game.getDaysSelected() - game.getDaysRemaining()));
		lblDaysPlayed.setHorizontalAlignment(SwingConstants.CENTER);
		lblDaysPlayed.setFont(new Font("Dialog", Font.BOLD, 20));
		lblDaysPlayed.setBounds(0, 313, 700, 24);
		frame.getContentPane().add(lblDaysPlayed);
	}
	
	private String prepareMessage(String message) {
		if (message.length() <= 50) {
			return message;
		}
		int splitPoint = 50;
		while (message.charAt(splitPoint) != ' ')
			splitPoint++;
		return String.format("<html>%s<br>%s<html>", message.substring(0, splitPoint), message.substring(splitPoint));
	}
}
