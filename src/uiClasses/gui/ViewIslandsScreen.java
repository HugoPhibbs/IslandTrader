package uiClasses.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import coreClasses.*;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Represents a screen for viewing Islands that a user can travel to from their
 * current Island
 * 
 * @author Jordan Vegar and Hugo Phibbs
 *
 */
public class ViewIslandsScreen extends Screen {
	
	/** Island selected by the player.*/
	private Island selectedIsland;
	
	/** Button that a user can press when they have chosen an Island that they want to travel to */
	private JButton btnTravel;

	/** Label that shows the current Island that a user has selected */
	private JLabel lblSelectedIsland;
	
	/** TextPane to hold detailed information on a Island, including what it buys and sells */
	private JTextPane paneFullIslandInfo;
	
	/** Panel to hold tables detailing the Items that the chosen Island store buys and sells */
	private JPanel panelTable;
	
	/** Panel to hold information on the current selected island */
	private JPanel panelIslandInfo;
	
	/** Constructor for ViewIslandsScreen class.
	 * 
	 * @param game GameEnvironment object for this current game 
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
		frame.setBounds(100, 100, 1144, 530);
		
		createSelectIslandComponents();
		createIslandInfoPanel();
		createTablePanel();
		createOtherComponenets();
	}
	
	/** Method called when confirm button is clicked. Hides this instance of screen and creates the 
	 * Screen used to select a route to the selected island. 
	 * 
	 */
	private void viewRoutes() {
		Screen chooseRoute = new ChooseRouteScreen(game, selectedIsland);
		quit();
		chooseRoute.show();
	}
	
	/** Creates the JPanel and its components used to select an Island.*/
	private void createSelectIslandComponents() {
		JPanel panelIslandSelection = new JPanel();
		panelIslandSelection.setBorder(blackline);
		panelIslandSelection.setBackground(new Color(0, 153, 255));
		panelIslandSelection.setBounds(12, 43, 584, 411);
		frame.getContentPane().add(panelIslandSelection);
		panelIslandSelection.setLayout(null);
		
		// All the other islands that a user can reach from their current island */
		Island[] islandsToView = game.otherIslands();
		
		/* Buttons that a user can press, when pressed, gives them info on an island, and
		 * enables btnTravel if not already enabled. 
		 */
		JButton btnIsland1 = new JButton(islandsToView[0].getIslandName());
		btnIsland1.setBounds(12, 12, 274, 187);
		btnIsland1.setBackground(new Color(153, 204, 255));
		btnIsland1.addActionListener(e -> changeIslandInfo(islandsToView[0]));
		panelIslandSelection.add(btnIsland1);
		
		JButton btnIsland2 = new JButton(islandsToView[1].getIslandName());
		btnIsland2.setBackground(new Color(153, 204, 255));
		btnIsland2.setBounds(298, 12, 274, 187);
		btnIsland2.addActionListener(e -> changeIslandInfo(islandsToView[1]));
		panelIslandSelection.add(btnIsland2);
		
		JButton btnIsland3 = new JButton(islandsToView[2].getIslandName());
		btnIsland3.setBackground(new Color(153, 204, 255));
		btnIsland3.setBounds(12, 212, 274, 187);
		btnIsland3.addActionListener(e -> changeIslandInfo(islandsToView[2]));
		panelIslandSelection.add(btnIsland3);
		
		JButton btnIsland4 = new JButton(islandsToView[3].getIslandName());
		btnIsland4.setBackground(new Color(153, 204, 255));
		btnIsland4.setBounds(298, 212, 274, 187);
		btnIsland4.addActionListener(e -> changeIslandInfo(islandsToView[3]));
		panelIslandSelection.add(btnIsland4);
	}
	
	/** Creates panel for holding information on the current island that has been selected */
	private void createIslandInfoPanel() {
		this.panelIslandInfo = new JPanel();
		panelIslandInfo.setBackground(new Color(0, 153, 255));
		panelIslandInfo.setBounds(608, 43, 524, 124);
		frame.getContentPane().add(panelIslandInfo);
		panelIslandInfo.setLayout(null);
		
		this.lblSelectedIsland = new JLabel("Select an island!");
		lblSelectedIsland.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedIsland.setBounds(12, 0, 184, 32);
		panelIslandInfo.add(lblSelectedIsland);
		
		this.paneFullIslandInfo = new JTextPane();
		paneFullIslandInfo.setText("");
		paneFullIslandInfo.setEditable(false);
		paneFullIslandInfo.setBounds(12, 30, 500, 82);
		panelIslandInfo.add(paneFullIslandInfo);
	}

	/** Creates the miscellaneous components the screen requires.*/
	private void createOtherComponenets() {
		JButton btnBack = new JButton("Go Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// If a user decides to go back, this screen is discarded and another CoreOptionsScreen is created 
				CoreOptionsScreen coreOptionsScreen = new CoreOptionsScreen(game);
				coreOptionsScreen.show();
				quit();
			}
		});
		btnBack.setBounds(12, 466, 117, 25);
		btnBack.setBackground(new Color(153, 204, 255));
		frame.getContentPane().add(btnBack); 
		
		this.btnTravel = new JButton("Travel to selected island");
		btnTravel.setEnabled(false); // set to disabled until a user chooses an island to travel to
		btnTravel.setBounds(730, 466, 234, 25);
		btnTravel.setBackground(new Color(153, 204, 255));
		btnTravel.addActionListener(e -> viewRoutes());
		frame.getContentPane().add(btnTravel);
		
		JLabel lblInstructions = new JLabel("Select an Island to see info about that Island. ");
		lblInstructions.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInstructions.setBounds(12, 12, 412, 19);
		frame.getContentPane().add(lblInstructions);
	}
	
	/** Method to create panel containing tables with information on the chosen Island's store buys and sells */
	private void createTablePanel() {
		// Create the panel
		this.panelTable = new JPanel();
		panelTable.setBorder(blackline);
		panelTable.setBackground(new Color(0, 153, 255));
		panelTable.setBounds(608, 177, 524, 277);
		frame.getContentPane().add(panelTable);
		panelTable.setLayout(null);
	}
	
	/** Creates the labels for the table panel, called after has been before adding tables to panelTable */
	private void createTablePanelLabels() {
		
		// Label for the title of the table containing items that the store sells
		JLabel labelStoreSellsTitle= new JLabel("This Island's store sells");
		labelStoreSellsTitle.setBounds(100, 12, 200, 20);
		panelTable.add(labelStoreSellsTitle);
		
		JLabel labelStoreBuysTitle = new JLabel("This Island's store buys");
		labelStoreBuysTitle.setBounds(100, 140, 200, 20);
		panelTable.add(labelStoreBuysTitle);
	}
	
	
	private void createTables(Store islandStore) {
		
		// Clear panelTabel and add labels back on
		clearPanel(panelTable);
		createTablePanelLabels();
		
		// Create table containing items that a store sells
		String[][] itemsStoreSellsRows= islandStore.catalogueToNestedArray(islandStore.getSellCatalogue(), "sell");
		String[] itemsstoreSellsColumns = new String[] {"Name", "Price", "Space Taken", "Defense Boost"};
		JTable tableItemsStoreSells = new JTable(itemsStoreSellsRows, itemsstoreSellsColumns);
		tableItemsStoreSells.setBounds(0, 0, 100, 100);
		
		JScrollPane scrollPaneItemsStoreSells = new JScrollPane(tableItemsStoreSells);
		scrollPaneItemsStoreSells.setBounds(12, 36, 500, 100);
		
		panelTable.add(scrollPaneItemsStoreSells);
		
		// Create table containing items that a store buys
		String[][] itemsStoreBuysRows = islandStore.catalogueToNestedArray(islandStore.getBuyCatalogue(), "buy");
		String[] itemsStoreBuysColumns = new String[] {"Name", "Price", "Space Taken"};
		JTable tableItemsStoreBuys = new JTable(itemsStoreBuysRows, itemsStoreBuysColumns);
		tableItemsStoreBuys.setBounds(0, 0, 100, 100);
		
		JScrollPane scrollPaneItemsStoreBuys = new JScrollPane(tableItemsStoreBuys);
		scrollPaneItemsStoreBuys.setBounds(12, 165, 500, 100);
		
		panelTable.add(scrollPaneItemsStoreBuys);
	}

	/**	Changes the info displayed to be info on the selected island. Method is called each time an
	 * Island's button is clicked. 
	 * 
	 * @param island Island object that has been chosen from clicking one of the Island buttons
	 */
	private void changeIslandInfo(Island island) {
		// Create the tables needed to display the items that can be bought from an Island's store
		createTables(island.getIslandStore());
		
		btnTravel.setEnabled(true); // Has now been set to enabled since a island button has been pressed
		lblSelectedIsland.setText(String.format("Info on %s", island.getIslandName()));
		paneFullIslandInfo.setText(island.fullInfo(game.getCurrentIsland().possibleRoutes(island)));
		selectedIsland = island;
	}
}
