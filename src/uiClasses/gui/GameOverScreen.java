	package uiClasses.gui;

import java.awt.Color; 
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import coreClasses.GameEnvironment;
import uiClasses.GameUi;

public class GameOverScreen extends Screen {
	
	/** JLabel that displays the reason the game has ended.*/
	private JLabel lblReason;
	
	/** JLabel that displays the player's score. */
	private JLabel lblScore;
	
	/** JLabel that displays the players profit.*/
	private JLabel lblProfit;
	
	/** JLabel that displays the days played for, and the total days selected to play for.*/ 
	private JLabel lblDaysPlayed;
	
	/** String a message that gives the reason for the game ending, put into html format with line
	 * breaks so it can be displayed by the JLabel lblReason.
	 */
	private String preppedMessage;

	/** Creates the Screen, calling the parent's constructor and then initializing the class variable preppedMessage.
	 * 
	 * @param game GameEvironment the instance of GameEnvironment for this particular game, tracks state. 
	 * @param message String describes the reason the game has ended.
	 */
	public GameOverScreen(GameEnvironment game, String message) {
		super("Game Over", game);
		preppedMessage = prepareMessage(message);
		initialize();
	}
	
	/**
	 * Set the frames bounds/size and call methods to create the screens's components.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 700, 500);
		
		createFinishButton();
		createImageLabel();
		createMainLabels();
	}
	
	/** Creates the JButton btnFinishGame, used to finish the game and exit.*/
	private void createFinishButton() {
		JButton btnFinishGame = new JButton("Finish Game");
		btnFinishGame.setBackground(new Color(153, 204, 255));
		btnFinishGame.addActionListener(e -> quit());
		btnFinishGame.setFont(new Font("Dialog", Font.BOLD, 16));
		btnFinishGame.setBounds(280, 405, 140, 29);
		frame.getContentPane().add(btnFinishGame);
	}
	
	/**Creates the JLabel that spans that displays an image. Spans the frame, and is behind all other components.*/
	private void createImageLabel() {
		JLabel lblBackgroundImage = new JLabel("");
		lblBackgroundImage.setFont(new Font("Dialog", Font.BOLD, 16));
		lblBackgroundImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackgroundImage.setBounds(0, 0, 700, 500);
		frame.getContentPane().add(lblBackgroundImage);
	}
	
	/** Creates the JLabels for this screen. */
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
		lblReason.setBounds(40, 73, 620, 80);
		frame.getContentPane().add(lblReason);
		
		lblScore = new JLabel("SCORE: " + game.calculateScore());
		lblScore.setFont(new Font("Dialog", Font.BOLD, 20));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(0, 189, 700, 24);
		frame.getContentPane().add(lblScore);
		
		lblProfit = new JLabel("PROFIT: " + (game.getPlayer().getMoneyBalance() - GameUi.STARTING_MONEY));
		lblProfit.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfit.setFont(new Font("Dialog", Font.BOLD, 20));
		lblProfit.setBounds(0, 250, 700, 24);
		frame.getContentPane().add(lblProfit);
		
		lblDaysPlayed = new JLabel(String.format("DAYS PLAYED: %d/%d", 
				(game.getDaysSelected() - game.getDaysRemaining()), game.getDaysSelected()));
		lblDaysPlayed.setHorizontalAlignment(SwingConstants.CENTER);
		lblDaysPlayed.setFont(new Font("Dialog", Font.BOLD, 20));
		lblDaysPlayed.setBounds(0, 313, 700, 24);
		frame.getContentPane().add(lblDaysPlayed);
	}
	
	/** Takes a string and converts it to html format with line breaks such that it can be 
	 * displayed by the JLabel lblReason. 
	 * @param message String that gives the reason for the game ending. 
	 * @return String a html formatted string for the JLabel lblReason to display.
	 */
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
