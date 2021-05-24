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

public class GameOverScren extends Screen {

	private JLabel lblReason;
	private JLabel lblScore;
	private JLabel lblProfit;
	private JLabel lblDaysPlayed;

	/**
	 * Create the application.
	 */
	public GameOverScren(GameEnvironment game) {
		super("Game Over", game);
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
		btnFinishGame.setFont(new Font("Dialog", Font.BOLD, 16));
		btnFinishGame.setBounds(292, 405, 140, 29);
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
		JLabel lblGameOver = new JLabel("GAME OVER");
		lblGameOver.setForeground(Color.RED);
		lblGameOver.setFont(new Font("Dialog", Font.BOLD, 32));
		lblGameOver.setBounds(243, 12, 215, 38);
		frame.getContentPane().add(lblGameOver);
		
		lblReason = new JLabel("REASON");
		lblReason.setHorizontalAlignment(SwingConstants.CENTER);
		lblReason.setBounds(38, 73, 616, 57);
		frame.getContentPane().add(lblReason);
		
		lblScore = new JLabel("SCORE: ");
		lblScore.setFont(new Font("Dialog", Font.BOLD, 20));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(243, 189, 236, 24);
		frame.getContentPane().add(lblScore);
		
		lblProfit = new JLabel("PROFIT:");
		lblProfit.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfit.setFont(new Font("Dialog", Font.BOLD, 20));
		lblProfit.setBounds(243, 250, 236, 24);
		frame.getContentPane().add(lblProfit);
		
		lblDaysPlayed = new JLabel("DAYS PLAYED: ");
		lblDaysPlayed.setHorizontalAlignment(SwingConstants.CENTER);
		lblDaysPlayed.setFont(new Font("Dialog", Font.BOLD, 20));
		lblDaysPlayed.setBounds(243, 313, 236, 24);
		frame.getContentPane().add(lblDaysPlayed);
	}
}
