package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class SailingScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SailingScreen window = new SailingScreen();
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
	public SailingScreen() {
		initialize();
	}
	
	// since rescued sailors and unfortunate weather events dont require any input from the player
	// it may just be good enough to have the event appear only in the progess text pane

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(28, 62, 396, 185);
		frame.getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
		
		JProgressBar sailingProgressBar = new JProgressBar();
		sailingProgressBar.setBounds(12, 152, 372, 21);
		infoPanel.add(sailingProgressBar);
		
		JTextPane sailingProgessTextPane = new JTextPane();
		sailingProgessTextPane.setText("Can show current progress of the sail\neg\nleaving <islandName>\non your sail\nyou have encountered <randomEvent>\nresult of randomEvent\ncontinuing with the sail\nyou have arrived at at <itemName>");
		sailingProgessTextPane.setBounds(12, 12, 372, 128);
		infoPanel.add(sailingProgessTextPane);
		
		JLabel descriptionLabel = new JLabel("Sailing to <islandName>");
		descriptionLabel.setBounds(26, 12, 241, 40);
		frame.getContentPane().add(descriptionLabel);
	}
}
