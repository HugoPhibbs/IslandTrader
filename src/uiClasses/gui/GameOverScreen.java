package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JButton;

public class GameOverScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameOverScreen window = new GameOverScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameOverScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Game Over!");
		titleLabel.setForeground(Color.RED);
		titleLabel.setBounds(160, 12, 133, 14);
		frame.getContentPane().add(titleLabel);
		
		JTextPane summaryTextPane = new JTextPane();
		summaryTextPane.setBounds(24, 58, 401, 155);
		frame.getContentPane().add(summaryTextPane);
		summaryTextPane.setText("here can go a summary of the game\n\nreason: <reason>\ndays played: <daysPlayed>\nfinal cash amount: <cash>\nfinal score: <finalScore>");
		
		JButton continueButton = new JButton("Continue");
		continueButton.setBounds(308, 225, 117, 25);
		frame.getContentPane().add(continueButton);
	}
}
