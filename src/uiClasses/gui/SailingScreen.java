package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(30, 167, 390, 39);
		frame.getContentPane().add(progressBar);
		
		JLabel lblNewLabel = new JLabel("Sailing to");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel.setBounds(185, 44, 80, 17);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblIslandName = new JLabel("Island Name");
		lblIslandName.setHorizontalAlignment(SwingConstants.CENTER);
		lblIslandName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblIslandName.setBounds(150, 91, 150, 24);
		frame.getContentPane().add(lblIslandName);
	}
}
