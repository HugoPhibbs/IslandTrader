package uiClasses.gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import coreClasses.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseRouteScreen extends Screen {

	/** the island the player to view routes to.*/
	private Island island;
	
	/** Route selected by the player.*/
	private Route selectedRoute;
	
	/** Button pressed by user when they have chosen a Route they want to travel on, and want to confirm their selection */
	private JButton btnConfirm;

	
	/** Constructor for ChooseRouteScreen
	 * 
	 * @param game GameEnvironment object for this current game
	 * @param island Island object for which a user has to see routes from
	 */
	public ChooseRouteScreen(GameEnvironment game, Island island) {
		super("Choose Route", game);
		this.island = island;
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		System.out.println("route " + game.getPlayer().getMoneyBalance()); // TODO remove

		frame.setBounds(100, 100, 800, 550);
		
		createMainLabels();
		createOtherComponents();
		createRouteChooseComponents();
	}
	
	/** Creates the labels on the screen not in a sub container.*/
	private void createMainLabels() {
		
		JLabel lblIsland = new JLabel("You are travelling to ", SwingConstants.CENTER);
		lblIsland.setFont(new Font("Dialog", Font.BOLD, 16));
		lblIsland.setBounds(121, 28, 187, 19);
		frame.getContentPane().add(lblIsland);
		
		JLabel lblIslandName = new JLabel(island.getIslandName(), SwingConstants.CENTER);
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
	}
	
	/** Creates the miscellaneous components on the screen not in a sub container.*/
	private void createOtherComponents() {
		this.btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false); // set to disabled until a user picks a route
		btnConfirm.addActionListener(e -> onConfirm());
		btnConfirm.setBounds(626, 489, 162, 25);
		frame.getContentPane().add(btnConfirm);
		
		JButton btnBack = new JButton("Go Back");
		btnBack.setBounds(12, 489, 162, 25);
		frame.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewIslandsScreen viewIslandsScreen = new ViewIslandsScreen(game);
				viewIslandsScreen.show();
				quit();
			}
		});
	}
	
	/** Creates the JPanel and its components used to select a route.*/
	private void createRouteChooseComponents() {
		JPanel panelRouteSelection = new JPanel();
		panelRouteSelection.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelRouteSelection.setBounds(12, 177, 776, 308);
		frame.getContentPane().add(panelRouteSelection);
		panelRouteSelection.setLayout(null);
		
		JLabel lblSelectedRoute = new JLabel("Select a route!");
		lblSelectedRoute.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedRoute.setBounds(551, 12, 196, 40);
		panelRouteSelection.add(lblSelectedRoute);
		
		JTextPane txtRouteDescription = new JTextPane();
		txtRouteDescription.setText("description of route here");
		txtRouteDescription.setBounds(551, 54, 203, 60);
		txtRouteDescription.setEditable(false);
		panelRouteSelection.add(txtRouteDescription);
		
		JLabel lblDuration = new JLabel("Duration (days):");
		lblDuration.setBounds(551, 126, 150, 15);
		panelRouteSelection.add(lblDuration);
		
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
		
		JLabel lblDurationVal = new JLabel("-");
		lblDurationVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDurationVal.setBounds(698, 126, 49, 15);
		panelRouteSelection.add(lblDurationVal);
		
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
		
		ArrayList<Route> routeList = game.getCurrentIsland().possibleRoutes(island);
		
		JButton btnRoute1 = new JButton(routeList.get(0).getRouteName());
		btnRoute1.addActionListener(e -> changeRouteInfo(routeList.get(0), 
				lblSelectedRoute, txtRouteDescription, lblDurationVal, lblPirateOdds, lblWeatherOdds, lblRescueOdds));
		btnRoute1.setBounds(12, 12, 233, 284);
		panelRouteSelection.add(btnRoute1);
		
		if (routeList.size() == 2) {
			JButton btnRoute2 = new JButton(routeList.get(1).getRouteName());
			btnRoute2.setBounds(257, 12, 233, 284);
			btnRoute2.addActionListener(e -> changeRouteInfo(routeList.get(1), 
					lblSelectedRoute, txtRouteDescription, lblDurationVal, lblPirateOdds, lblWeatherOdds, lblRescueOdds));
			panelRouteSelection.add(btnRoute2);
		}
	}
	
	/** Changes the displayed route info to that of the selected route. Called each time a Route's button
	 * is clicked. 
	 * 
	 * @param route Route object that a user has selected to see information on
	 * @param lblName JLabel for the name of inputed Route object
	 * @param lblDescription JTextField  for the description of a Route
	 * @param lblDuration JLabel for the distance of a route
	 * @param lblPirate JLabel for the chance of a Pirate attack event on Route
	 * @param lblWeather JLabel for the chance of a unfortunate weather event on Route
	 * @param lblRescue JLabel for the chance of a Pirate attack event on Route
	 */
	private void changeRouteInfo(Route route, JLabel lblName, JTextPane lblDescription, JLabel lblDuration, JLabel lblPirate,  JLabel lblWeather, JLabel lblRescue) {
		btnConfirm.setEnabled(true);
		lblName.setText(route.getRouteName());
		lblDescription.setText(route.getDescription());
		lblDuration.setText(String.valueOf(game.getShip().calculateDaysSailing(route)));
		lblPirate.setText(String.valueOf(route.getPirateProb()));
		lblWeather.setText(String.valueOf(route.getWeatherProb()));
		lblRescue.setText(String.valueOf(route.getRescueProb()));
		selectedRoute = route;
	}
	
	/** Method to handle pressing of btnConfirm. 
	 * Checks if a user can pay the wages and any repair costs in order to travel on a Route
	 */
	private void onConfirm() {
		// Check player has enough money to make required payments
		int costToSail = game.getShip().repairCost() + game.getShip().routeWageCost(selectedRoute);
		int moneyBalance = game.getPlayer().getMoneyBalance();
		
		if (costToSail > game.getPlayer().getMoneyBalance()) {
			String notEnoughMoneyMessage = String.format("<html>Not enough money tosail this route! You need $%d to pay your crew wages and <br>"
					+ "repair your ship. Your current balance is $%d. Please sell some of your items.<html>", 
					costToSail, moneyBalance);
			JOptionPane.showMessageDialog(frame, notEnoughMoneyMessage, "Not Enough Money!", JOptionPane.ERROR_MESSAGE);
		} else {
			JDialog paymentsScreen = new MakePaymentsDialog(game, selectedRoute, this);
		}
	}
	
	/** Method to handle when a user pays wages for a route, called from MakePaymentsDialog
	 * Creates a new SailingScreen and closes this screen
	 * 
	 */
	protected void confirmSelection() {
		SailingScreen sailingScreen = new SailingScreen(game, island, selectedRoute);
		sailingScreen.show();
		sailingScreen.startProgress();
		this.quit();
	}
}
