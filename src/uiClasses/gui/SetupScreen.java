package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import coreClasses.GameEnvironment;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JList;

public class SetupScreen extends Screen {

	private JTextField textFieldName;

	/**
	 * Create the application.
	 */
	public SetupScreen(GameEnvironment game) {
		super("Setup Game", game, null);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chooseRouteScreen window = new chooseRouteScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
				
		frame.setBounds(100, 100, 1100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		completeScreenSetup(frame, "Setup Game");
		
		JPanel panelPickShip = new JPanel();
		panelPickShip.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPickShip.setBounds(12, 184, 1065, 250);
		frame.getContentPane().add(panelPickShip);
		panelPickShip.setLayout(null);
		
		JPanel panelPickIsland = new JPanel();
		panelPickIsland.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPickIsland.setBounds(12, 477, 1065, 250);
		frame.getContentPane().add(panelPickIsland);
		panelPickIsland.setLayout(null);
		
		createMainLabels();
		createOtherComponents();
		createPickShipComponents(panelPickShip);
		createPickIslandComponents(panelPickIsland);
	}
		
	public void createMainLabels() {
		JLabel lblWelcome = new JLabel("Welcome to Island Trader!");
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 22));
		lblWelcome.setBounds(321, 0, 332, 27);
		frame.getContentPane().add(lblWelcome);
		
		JLabel lblAskName = new JLabel("Please enter your name: ");
		lblAskName.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAskName.setBounds(26, 50, 225, 19);
		frame.getContentPane().add(lblAskName);
		
		JLabel lblAskDays = new JLabel("How many days would you like to play for?");
		lblAskDays.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAskDays.setBounds(26, 101, 377, 19);
		frame.getContentPane().add(lblAskDays);
		
		JLabel lblDisplayDays = new JLabel("30");
		lblDisplayDays.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDisplayDays.setFont(new Font("Dialog", Font.BOLD, 16));
		lblDisplayDays.setBounds(1008, 105, 44, 17);
		frame.getContentPane().add(lblDisplayDays);
		
		JLabel lblPickship = new JLabel("Pick a Ship");
		lblPickship.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPickship.setBounds(26, 153, 96, 19);
		frame.getContentPane().add(lblPickship);
		
		JLabel lblPickIsland = new JLabel("Pick a Starting Island");
		lblPickIsland.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPickIsland.setBounds(26, 446, 188, 19);
		frame.getContentPane().add(lblPickIsland);
		
		JLabel lblNewLabel = new JLabel("Must be 3-15 characters, and have not special characters");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		lblNewLabel.setBounds(682, 77, 370, 14);
		frame.getContentPane().add(lblNewLabel);
	}
	
	public void createOtherComponents() {
		JButton btnNewButton = new JButton("Confirm Choices");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(910, 739, 178, 25);
		frame.getContentPane().add(btnNewButton);
		
		
		textFieldName = new JTextField();
		textFieldName.setToolTipText("your name here");
		textFieldName.setFont(new Font("Dialog", Font.PLAIN, 16));
		textFieldName.setBounds(693, 50, 359, 23);
		frame.getContentPane().add(textFieldName);
		textFieldName.setColumns(10);
		
		JSlider sliderDays = new JSlider();
		sliderDays.setPaintLabels(true);
		sliderDays.setMinorTickSpacing(1);
		sliderDays.setMinimum(20);
		sliderDays.setMaximum(50);
		sliderDays.setBounds(693, 103, 299, 19);
		frame.getContentPane().add(sliderDays);
	}
	
	public void createPickShipComponents(JPanel panelPickShip) {
		
		JLabel lblSelectedShip = new JLabel("Select a ship!");
		lblSelectedShip.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedShip.setBounds(869, 12, 196, 40);
		panelPickShip.add(lblSelectedShip);
		
		JLabel lblSpeed = new JLabel("Speed:");
		lblSpeed.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSpeed.setBounds(869, 64, 56, 17);
		panelPickShip.add(lblSpeed);
		
		JLabel lblCrewSize = new JLabel("Crew Size:");
		lblCrewSize.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCrewSize.setBounds(869, 107, 86, 17);
		panelPickShip.add(lblCrewSize);
		
		JLabel lblCargoCapacity = new JLabel("Cargo capacity:");
		lblCargoCapacity.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCargoCapacity.setBounds(869, 152, 123, 17);
		panelPickShip.add(lblCargoCapacity);
		
		JLabel lblMaxDefense = new JLabel("Max Defense:");
		lblMaxDefense.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMaxDefense.setBounds(869, 198, 86, 17);
		panelPickShip.add(lblMaxDefense);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(12, 12, 198, 226);
		panelPickShip.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("New button");
		btnNewButton_1_1.setBounds(222, 12, 198, 226);
		panelPickShip.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("New button");
		btnNewButton_1_2.setBounds(432, 12, 198, 226);
		panelPickShip.add(btnNewButton_1_2);
		
		JButton btnNewButton_1_1_1 = new JButton("New button");
		btnNewButton_1_1_1.setBounds(642, 12, 198, 226);
		panelPickShip.add(btnNewButton_1_1_1);
		
		JLabel lblSpeed_1 = new JLabel("-");
		lblSpeed_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSpeed_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSpeed_1.setBounds(1006, 64, 34, 17);
		panelPickShip.add(lblSpeed_1);
		
		JLabel lblSpeed_1_1 = new JLabel("-");
		lblSpeed_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSpeed_1_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSpeed_1_1.setBounds(1006, 108, 34, 17);
		panelPickShip.add(lblSpeed_1_1);
		
		JLabel lblSpeed_1_2 = new JLabel("-");
		lblSpeed_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSpeed_1_2.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSpeed_1_2.setBounds(1006, 153, 34, 17);
		panelPickShip.add(lblSpeed_1_2);
		
		JLabel lblSpeed_1_3 = new JLabel("-");
		lblSpeed_1_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSpeed_1_3.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSpeed_1_3.setBounds(1006, 199, 34, 17);
		panelPickShip.add(lblSpeed_1_3);
	}
	
	public void createPickIslandComponents(JPanel panelPickIsland) {
		
		JButton btnNewButton_1_3 = new JButton("New button");
		btnNewButton_1_3.setBounds(12, 12, 156, 226);
		panelPickIsland.add(btnNewButton_1_3);
		
		JLabel lblSelectAnIsland = new JLabel("Select an island!");
		lblSelectAnIsland.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectAnIsland.setBounds(869, 12, 196, 40);
		panelPickIsland.add(lblSelectAnIsland);
		
		JTextPane txtpnThisIslandSells = new JTextPane();
		txtpnThisIslandSells.setText("This island sells tomatos and buys gold. It has routes ot all other isalnds.");
		txtpnThisIslandSells.setBounds(869, 76, 184, 135);
		panelPickIsland.add(txtpnThisIslandSells);
		
		JLabel lblInfo = new JLabel("Info:");
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblInfo.setBounds(869, 52, 70, 15);
		panelPickIsland.add(lblInfo);
		
		JButton btnNewButton_1_3_1 = new JButton("New button");
		btnNewButton_1_3_1.setBounds(180, 12, 156, 226);
		panelPickIsland.add(btnNewButton_1_3_1);
		
		JButton btnNewButton_1_3_2 = new JButton("New button");
		btnNewButton_1_3_2.setBounds(348, 12, 156, 226);
		panelPickIsland.add(btnNewButton_1_3_2);
		
		JButton btnNewButton_1_3_3 = new JButton("New button");
		btnNewButton_1_3_3.setBounds(516, 12, 156, 226);
		panelPickIsland.add(btnNewButton_1_3_3);
		
		JButton btnNewButton_1_3_4 = new JButton("New button");
		btnNewButton_1_3_4.setBounds(684, 12, 156, 226);
		panelPickIsland.add(btnNewButton_1_3_4);
	}
}
