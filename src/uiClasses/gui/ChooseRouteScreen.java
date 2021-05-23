package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import coreClasses.*;
import uiClasses.GameUi;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseRouteScreen extends Screen {

	/** the island the player to view routes to.*/
	private Island island;
	/** Route selected by the player.*/
	private Route selectedRoute;

	/**
	 * Create the application.
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
		frame.setBounds(100, 100, 800, 550);
		
		createMainLabels();
		createOtherComponents();
		createRouteChooseComponents();
	}
	
	private void onConfirm() {
		game.sailToNewIsland(selectedRoute, island);
		
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
		JButton btnConfirm = new JButton("Confirm");
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
	
	/** Creates the Jpanel and its components used to select a route.*/
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
		
		JTextField txtRouteDescription = new JTextField();
		txtRouteDescription.setText("description of route here");
		txtRouteDescription.setBounds(551, 54, 203, 60);
		txtRouteDescription.setEditable(false);
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
		
		ArrayList<Route> routeList = game.getCurrentIsland().possibleRoutes(island);
		
		JButton btnRoute1 = new JButton(routeList.get(0).getRouteName());
		btnRoute1.addActionListener(e -> changeRouteInfo(routeList.get(0), 
				lblSelectedRoute, txtRouteDescription, lblDistanceVal, lblPirateOdds, lblWeatherOdds, lblRescueOdds));
		btnRoute1.setBounds(12, 12, 233, 284);
		panelRouteSelection.add(btnRoute1);
		
		if (routeList.size() == 2) {
			JButton btnRoute2 = new JButton(routeList.get(1).getRouteName());
			btnRoute2.setBounds(257, 12, 233, 284);
			btnRoute2.addActionListener(e -> changeRouteInfo(routeList.get(1), 
					lblSelectedRoute, txtRouteDescription, lblDistanceVal, lblPirateOdds, lblWeatherOdds, lblRescueOdds));
			panelRouteSelection.add(btnRoute2);
		}
	}
	
	/**
	 * Changes the displayed route info to that of the selected route. Called each time a Route's button
	 * is clicked. 
	 * @param route Route
	 * @param name JLabel
	 * @param description JTextField
	 * @param distance JLabel
	 * @param pirate JLabel
	 * @param weather JLabel
	 * @param rescue JLabel
	 */
	private void changeRouteInfo(Route route, JLabel name, JTextField description, JLabel distance, JLabel pirate,  JLabel weather, JLabel rescue) {
		name.setText(route.getRouteName());
		description.setText(route.getDescription());
		distance.setText(String.valueOf(route.getDistance()));
		pirate.setText(String.valueOf(route.getPirateProb()));
		weather.setText(String.valueOf(route.getWeatherProb()));
		rescue.setText(String.valueOf(route.getRescueProb()));
		selectedRoute = route;
	}
}
