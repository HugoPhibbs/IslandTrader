package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewislandsScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewislandsScreen window = new ViewislandsScreen();
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
	public ViewislandsScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panelIslandSelection = new JPanel();
		panelIslandSelection.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelIslandSelection.setBounds(12, 43, 776, 411);
		frame.getContentPane().add(panelIslandSelection);
		panelIslandSelection.setLayout(null);
		
		JLabel lblSelectAnIsland = new JLabel("Select an island!");
		lblSelectAnIsland.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectAnIsland.setBounds(593, 12, 184, 32);
		panelIslandSelection.add(lblSelectAnIsland);
		
		JLabel lblInfo = new JLabel("Info:");
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblInfo.setBounds(593, 43, 70, 15);
		panelIslandSelection.add(lblInfo);
		
		JTextPane txtpnThisIslandSells = new JTextPane();
		txtpnThisIslandSells.setText("This island sells tomatos and buys gold. It has routes ot all other isalnds.");
		txtpnThisIslandSells.setBounds(593, 71, 174, 328);
		panelIslandSelection.add(txtpnThisIslandSells);
		
		JButton btnIsland1 = new JButton("New button");
		btnIsland1.setBounds(12, 12, 274, 187);
		panelIslandSelection.add(btnIsland1);
		
		JButton btnIsland2 = new JButton("New button");
		btnIsland2.setBounds(298, 12, 274, 187);
		panelIslandSelection.add(btnIsland2);
		
		JButton btnIsland3 = new JButton("New button");
		btnIsland3.setBounds(12, 212, 274, 187);
		panelIslandSelection.add(btnIsland3);
		
		JButton btnIsland4 = new JButton("New button");
		btnIsland4.setBounds(298, 212, 274, 187);
		panelIslandSelection.add(btnIsland4);
		
		JButton btnBack = new JButton("GO BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnBack.setBounds(12, 466, 117, 25);
		frame.getContentPane().add(btnBack);
		
		JButton btnTravel = new JButton("TRAVEL TO SELECTED ISLAND");
		btnTravel.setBounds(554, 466, 234, 25);
		frame.getContentPane().add(btnTravel);
		
		JLabel lblInstructions = new JLabel("Select an Island to see info about that Island. ");
		lblInstructions.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInstructions.setBounds(12, 12, 412, 19);
		frame.getContentPane().add(lblInstructions);
	}

}
