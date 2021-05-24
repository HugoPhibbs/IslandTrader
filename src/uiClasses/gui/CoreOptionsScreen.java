package uiClasses.gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextPane;

import coreClasses.GameEnvironment;
import uiClasses.GameUi;

public class CoreOptionsScreen extends Screen{

	private JPanel contentPanel;
	private JLabel contentPanelLabel;

	/**
	 * Create the application.
	 */
	public CoreOptionsScreen(GameEnvironment gameEnvironment) {
		// Make the parent of the setup screen to be null
		super("Core options screen", gameEnvironment);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 1100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JLabel welcomeLabel = new JLabel(String.format("Welcome %s to %s!", game.getPlayer().getName(), game.getCurrentIsland().getIslandName()));
		welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		welcomeLabel.setBounds(400, 20, 900, 30);
		frame.getContentPane().add(welcomeLabel);
		
		initializeOptionsPanel();
		//initializeContentPanel();
		initializeGameInfoPanel();
	}
	
	/** Create the panel containing all the buttons to interact with the game
	 * 
	 */
	private void initializeOptionsPanel() {
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(blackline);
		optionsPanel.setBounds(31, 196, 1000, 542);
		frame.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
//		JButton viewShipPropertiesButton = new JButton("View Ship Properties");
//		viewShipPropertiesButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				viewShipProperties();
//			}
//		});
//		viewShipPropertiesButton.setBounds(25, 24, 400, 100);
//		optionsPanel.add(viewShipPropertiesButton);
		
//		JButton viewBoughtItemsButton = new JButton("View Bought Items");
//		viewBoughtItemsButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				viewPrevItems();
//			}
//		});
//		viewBoughtItemsButton.setBounds(25, 151, 400, 100);
//		optionsPanel.add(viewBoughtItemsButton);
		
//		JButton visitStoreButton = new JButton("Visit <islandName> Store");
		JButton visitStoreButton = new JButton(String.format("<html>      Visit %s's Store<br> and view previously bought items</html>", game.getCurrentIsland().getIslandName()));
		visitStoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				visitStore();
			}
		});
		visitStoreButton.setBounds(12, 279, 425, 125);
		optionsPanel.add(visitStoreButton);
		
		// Use html to display text as two lines instead of one!
		JButton viewVisitOtherIslandsButton = new JButton("<html>      View other islands<br>and travel to another island</html>");
		viewVisitOtherIslandsButton.setBounds(25, 395, 425, 125);
		optionsPanel.add(viewVisitOtherIslandsButton);
		viewVisitOtherIslandsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewIsland();
			}
		});
	}
	
//	/** Create the panel to contain any information that is wished to be displayed, 
//	 * namely an table with Item descriptions and a description of a player's ship
//	 */
//	private void initializeContentPanel() {
//		this.contentPanel = new JPanel();
//		contentPanel.setBorder(blackline);
//		contentPanel.setBounds(512, 196, 500, 500);
//		frame.getContentPane().add(contentPanel);
//		contentPanel.setLayout(null);
//		
//		this.contentPanelLabel = new JLabel();
//		contentPanelLabel.setBounds(100, 10, 200, 20);
//		contentPanel.add(contentPanelLabel);
//	}
	
//	/** Create a table to hold descriptions of Items of a player that were previously bought
//	 * 
//	 * @param rows String[][] array contianing
//	 * @param columns
//	 */
//	private void createTable(String [][] rows, String[] columns) {
//		// Create a new table
//		clearPanel(contentPanel);
//		
//		JTable newTable = new JTable(rows, columns);
//		newTable.setBounds(0, 0, 500, 400);
//		
//		JScrollPane sp = new JScrollPane(newTable);
//		sp.setBounds(0, 20, 500, 400);
//		
//		contentPanel.add(sp);
//	}
	
	private void initializeGameInfoPanel() {
		JPanel gameFactsPanel = new JPanel();
		gameFactsPanel.setBorder(blackline);
		gameFactsPanel.setBounds(25, 40, 1000, 175);
		frame.getContentPane().add(gameFactsPanel);
		gameFactsPanel.setLayout(null);
		
		JLabel gameFactsLabel = new JLabel("Quick game facts!");
		gameFactsLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		gameFactsLabel.setBounds(24, 45, 400, 18);
		gameFactsPanel.add(gameFactsLabel);
		
		JLabel playerNameLabel = new JLabel(String.format("Player Name: %s", game.getPlayer().getName()));
		playerNameLabel.setBounds(24, 65, 400, 15);	
		gameFactsPanel.add(playerNameLabel);	
		
		JLabel playerCashLabel = new JLabel(String.format("Cash in possession: %s Pirate Bucks", game.getPlayer().getMoneyBalance()));
		playerCashLabel.setBounds(24, 80, 400, 15);
		gameFactsPanel.add(playerCashLabel);
		
		JLabel daysRemainingLabel = new JLabel(String.format("Days Remaining: %s", game.getDaysRemaining()));
		daysRemainingLabel.setBounds(24, 95, 400, 15);
		gameFactsPanel.add(daysRemainingLabel);
		
		JLabel currentIslandLabel = new JLabel(String.format("Current Island: %s", game.getCurrentIsland().getIslandName()));
		currentIslandLabel.setBounds(24, 110, 400, 15);
		gameFactsPanel.add(currentIslandLabel);
		
		JTextPane detailPane = new JTextPane();
		detailPane.setBounds(800, 10, 200, 200);
		detailPane.setText(game.getPlayer().getShip().getDescription());
		gameFactsPanel.add(detailPane);
	}
	
	private void visitStore() {
		Screen visitStoreScreen = new VisitStoreScreen(getGame());
		visitStoreScreen.show();
		quit();
	}
	
	private void viewIsland() {
		Screen viewIslands = new ViewIslandsScreen(getGame());
		viewIslands.show();
		quit();
	}
	
//	private void viewPrevItems(){
//		contentPanelLabel.setText("Previously bought items!");
//		
//		// Get the purchased of a Player and create an array containing column titles
//		String[][] purchasedItems = game.getPlayer().purchasedItemsToArray();
//		
//		if (purchasedItems == null){
//			clearPanel(contentPanel);
//			JLabel noPrevItemsLabel = new JLabel("You haven't bought any items yet, buy some at any store!");
//			noPrevItemsLabel.setBounds(33, 100, 500, 15);
//			contentPanel.add(noPrevItemsLabel);
//		}
//		else {
//			// Create the table containing all the previously bought items of a player
//			String[] columns = {"Name", "Purchase Price", "Consignment Price"};
//			createTable(purchasedItems, columns);
//		}
//	}
	
	private void viewShipProperties() {
		clearPanel(contentPanel);
		
		JTextPane detailPane = new JTextPane();
		detailPane.setBounds(168, 10, 200, 200);
		detailPane.setText(game.getPlayer().getShip().getDescription());
		contentPanel.add(detailPane);
	}
}
