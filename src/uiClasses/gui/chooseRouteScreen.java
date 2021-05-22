package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class chooseRouteScreen {

	private JFrame frame;
	private JTextField txtRouteDescription;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the application.
	 */
	public chooseRouteScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTravelling = new JLabel("You are travelling to ");
		lblTravelling.setFont(new Font("Dialog", Font.BOLD, 16));
		lblTravelling.setBounds(121, 28, 187, 19);
		frame.getContentPane().add(lblTravelling);
		
		JLabel lblIslandName = new JLabel("<Island Name>");
		lblIslandName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblIslandName.setBounds(121, 61, 175, 24);
		frame.getContentPane().add(lblIslandName);
		
		JLabel lblSelectRoute = new JLabel("Please select a route to continue");
		lblSelectRoute.setFont(new Font("Dialog", Font.BOLD, 16));
		lblSelectRoute.setForeground(new Color(51, 51, 51));
		lblSelectRoute.setBounds(76, 146, 297, 19);
		frame.getContentPane().add(lblSelectRoute);
		
		JLabel lblIslandImage = new JLabel("This will be a picture of the island you are travelling to!");
		lblIslandImage.setBackground(Color.CYAN);
		lblIslandImage.setBounds(509, 7, 259, 137);
		frame.getContentPane().add(lblIslandImage);
		
		JPanel panelRouteSelection = new JPanel();
		panelRouteSelection.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelRouteSelection.setBounds(12, 177, 776, 308);
		frame.getContentPane().add(panelRouteSelection);
		panelRouteSelection.setLayout(null);
		
		JButton btnRoute1 = new JButton("New button");
		btnRoute1.setBounds(12, 12, 233, 284);
		panelRouteSelection.add(btnRoute1);
		
		JButton btnRoute2 = new JButton("New button");
		btnRoute2.setBounds(257, 12, 233, 284);
		panelRouteSelection.add(btnRoute2);
		
		JLabel lblRouteName = new JLabel("Select a route!");
		lblRouteName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblRouteName.setBounds(551, 12, 196, 40);
		panelRouteSelection.add(lblRouteName);
		
		txtRouteDescription = new JTextField();
		txtRouteDescription.setText("description of route here");
		txtRouteDescription.setBounds(551, 54, 203, 60);
		panelRouteSelection.add(txtRouteDescription);
		txtRouteDescription.setColumns(10);
		
		JLabel lblDistance = new JLabel("Distance:");
		lblDistance.setBounds(551, 126, 67, 15);
		panelRouteSelection.add(lblDistance);
		
		JLabel lblPirate = new JLabel("Pirate Attack:");
		lblPirate.setBounds(551, 193, 125, 15);
		panelRouteSelection.add(lblPirate);
		
		JLabel lblBadWeather = new JLabel("Bad Weather:");
		lblBadWeather.setBounds(551, 228, 172, 15);
		panelRouteSelection.add(lblBadWeather);
		
		JLabel lblStrandedSailor = new JLabel("Stranded Sailor:");
		lblStrandedSailor.setBounds(551, 265, 172, 15);
		panelRouteSelection.add(lblStrandedSailor);
		
		JLabel lblChances = new JLabel("Chance of...");
		lblChances.setBounds(605, 166, 85, 15);
		panelRouteSelection.add(lblChances);
		
		JLabel lblDistanceVal = new JLabel("-");
		lblDistanceVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDistanceVal.setBounds(698, 126, 49, 15);
		panelRouteSelection.add(lblDistanceVal);
		
		JLabel lblPirateOdds = new JLabel("-");
		lblPirateOdds.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPirateOdds.setBounds(698, 193, 49, 15);
		panelRouteSelection.add(lblPirateOdds);
		
		JLabel lblWeatherOdds = new JLabel("-");
		lblWeatherOdds.setHorizontalAlignment(SwingConstants.TRAILING);
		lblWeatherOdds.setBounds(698, 228, 49, 15);
		panelRouteSelection.add(lblWeatherOdds);
		
		JLabel lblRescueOdds = new JLabel("-");
		lblRescueOdds.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRescueOdds.setBounds(698, 265, 49, 15);
		panelRouteSelection.add(lblRescueOdds);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(626, 489, 162, 25);
		frame.getContentPane().add(btnConfirm);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("a" + textField.getText());
			}
		});
		textField.setBounds(353, 99, 114, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
