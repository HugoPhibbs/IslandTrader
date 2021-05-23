package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import coreClasses.*;
import uiClasses.GameUi;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewIslandsScreen extends Screen {
	
	/** Island selected by the player.*/
	private Island selectedIsland;

	/**
	 * Create the application.
	 */
	public ViewIslandsScreen(GameEnvironment game) {
		super("View Islands", game);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 800, 530);
		
		createSelectIslandComponents();
		createOtherComponenets();
	}
	
	/**
	 * Method called when confirm button is clicked. Hides this instance of screen and creates the 
	 * Screen used to select a route to the selected island. 
	 */
	private void viewRoutes() {
		Screen chooseRoute = new ChooseRouteScreen(game, selectedIsland);
		this.hide();
		chooseRoute.show();
	}
	
	/** Creates the JPanel and its components used to select an Island.*/
	private void createSelectIslandComponents() {
		JPanel panelIslandSelection = new JPanel();
		panelIslandSelection.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelIslandSelection.setBounds(12, 43, 776, 411);
		frame.getContentPane().add(panelIslandSelection);
		panelIslandSelection.setLayout(null);
		
		JLabel lblSelectedIsland = new JLabel("Select an island!");
		lblSelectedIsland.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedIsland.setBounds(593, 12, 184, 32);
		panelIslandSelection.add(lblSelectedIsland);
		
		JLabel lblInfo = new JLabel("Info:");
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblInfo.setBounds(593, 43, 70, 15);
		panelIslandSelection.add(lblInfo);
		
		JTextPane textPaneIslandInfo = new JTextPane();
		textPaneIslandInfo.setText("This island sells tomatos and buys gold. It has routes ot all other isalnds.");
		textPaneIslandInfo.setBounds(593, 71, 174, 328);
		panelIslandSelection.add(textPaneIslandInfo);
		
		Island[] islandsToView = game.otherIslands();
		
		JButton btnIsland1 = new JButton(islandsToView[0].getIslandName());
		btnIsland1.setBounds(12, 12, 274, 187);
		btnIsland1.addActionListener(e -> changeIslandInfo(islandsToView[0] , lblSelectedIsland, textPaneIslandInfo));
		panelIslandSelection.add(btnIsland1);
		
		JButton btnIsland2 = new JButton(islandsToView[1].getIslandName());
		btnIsland2.setBounds(298, 12, 274, 187);
		btnIsland2.addActionListener(e -> changeIslandInfo(islandsToView[1] , lblSelectedIsland, textPaneIslandInfo));
		panelIslandSelection.add(btnIsland2);
		
		JButton btnIsland3 = new JButton(islandsToView[2].getIslandName());
		btnIsland3.setBounds(12, 212, 274, 187);
		btnIsland3.addActionListener(e -> changeIslandInfo(islandsToView[2] , lblSelectedIsland, textPaneIslandInfo));
		panelIslandSelection.add(btnIsland3);
		
		JButton btnIsland4 = new JButton(islandsToView[3].getIslandName());
		btnIsland4.setBounds(298, 212, 274, 187);
		btnIsland4.addActionListener(e -> changeIslandInfo(islandsToView[3] , lblSelectedIsland, textPaneIslandInfo));
		panelIslandSelection.add(btnIsland4);
	}

	/** Creates the miscellaneous components the screen requires.*/
	private void createOtherComponenets() {
		JButton btnBack = new JButton("Go Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CoreOptionsScreen coreOptionsScreen = new CoreOptionsScreen(game);
				coreOptionsScreen.show();
				quit();
			}
		});
		btnBack.setBounds(12, 466, 117, 25);
		frame.getContentPane().add(btnBack);
		
		JButton btnTravel = new JButton("Travel to selected island");
		btnTravel.setBounds(554, 466, 234, 25);
		btnTravel.addActionListener(e -> viewRoutes());
		frame.getContentPane().add(btnTravel);
		
		JLabel lblInstructions = new JLabel("Select an Island to see info about that Island. ");
		lblInstructions.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInstructions.setBounds(12, 12, 412, 19);
		frame.getContentPane().add(lblInstructions);
	}

	/**
	 * Changes the info displayed to be info on the selected island. Method is called each time an
	 * Island's button is clicked. 
	 * @param island Island
	 * @param name JLabe;
	 * @param islandInfo JTextPane
	 */
	private void changeIslandInfo(Island island, JLabel name, JTextPane islandInfo) {
		name.setText(island.getIslandName());
		islandInfo.setText(island.fullInfo(game.getCurrentIsland().possibleRoutes(island)));
		selectedIsland = island;
	}
}
