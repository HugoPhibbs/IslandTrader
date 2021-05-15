package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PiratesScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PiratesScreen window = new PiratesScreen();
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
	public PiratesScreen() {
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
		
		JLabel lblNewLabel = new JLabel("You have encountered pirates!");
		lblNewLabel.setBounds(34, 27, 268, 29);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 68, 426, 196);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Roll the dice!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(12, 12, 195, 52);
		panel.add(btnNewButton);
		// button should be set to disabled one it has been pressed
		
		JLabel lblYouRolled = new JLabel("You rolled: <num>");
		lblYouRolled.setBounds(238, 31, 162, 15);
		panel.add(lblYouRolled);
		
		JLabel lblNewLabel_1 = new JLabel("result label");
		lblNewLabel_1.setBounds(22, 93, 379, 80);
		panel.add(lblNewLabel_1);
	}

}
