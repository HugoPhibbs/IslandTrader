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

	private JPanel tablePanel;
	private JLabel tablePanelLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment ge = new GameEnvironment(null, null, null, null, null);
					CoreOptionsScreen window = new CoreOptionsScreen(ge);
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
	public CoreOptionsScreen(GameEnvironment gameEnvironment, GameUi ui) {
		// Make the parent of the setup screen to be null
		super("Core options screen", gameEnvironment, null, ui);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 1100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JLabel welcomeLabel = new JLabel(String.format("Welcome to %s, select an option to interact with!", game.getCurrentIsland().getIslandName()));
		welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		welcomeLabel.setBounds(150, 20, 900, 30);
		frame.getContentPane().add(welcomeLabel);
		
		initializeOptionsPanel();
		initializeTablePanel();
		initializeGameInfoPanel();
	}
	
	private void initializeOptionsPanel() {
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBounds(31, 196, 469, 542);
		frame.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		JButton viewShipPropertiesButton = new JButton("View Ship Properties");
		viewShipPropertiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewShipProperties();
			}
		});
		viewShipPropertiesButton.setBounds(25, 24, 400, 100);
		optionsPanel.add(viewShipPropertiesButton);
		
		JButton viewBoughtItemsButton = new JButton("View Bought Items");
		viewBoughtItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewPrevItems();
			}
		});
		viewBoughtItemsButton.setBounds(25, 151, 400, 100);
		optionsPanel.add(viewBoughtItemsButton);
		
//		JButton visitStoreButton = new JButton("Visit <islandName> Store");
		JButton visitStoreButton = new JButton(String.format("Visit %s's Store", game.getCurrentIsland().getIslandName()));
		visitStoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				visitStore();
			}
		});
		visitStoreButton.setBounds(25, 279, 400, 100);
		optionsPanel.add(visitStoreButton);
		
		// Use html to display text as two lines instead of one!
		JButton viewVisitOtherIslandsButton = new JButton("<html>      View other islands<br>and travel to another island</html>");
		viewVisitOtherIslandsButton.setBounds(25, 395, 400, 122);
		optionsPanel.add(viewVisitOtherIslandsButton);
		viewVisitOtherIslandsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewIsland();
			}
		});
	}
	
	private void initializeTablePanel() {
		this.tablePanel = new JPanel();
		tablePanel.setBounds(512, 196, 546, 542);
		frame.getContentPane().add(tablePanel);
		tablePanel.setLayout(null);
		
		this.tablePanelLabel = new JLabel();
		tablePanelLabel.setBounds(100, 10, 200, 20);
		tablePanel.add(tablePanelLabel);
	}
	
	private void createTable(String [][] rows, String[] columns) {
		// Create a new table
		tablePanel.removeAll();
		
		JTable newTable = new JTable(rows, columns);
		newTable.setBounds(0, 100, 200, 200);
		
		JScrollPane sp = new JScrollPane(newTable);
		sp.setSize(200, 200);s
		
		tablePanel.add(sp);
	}
	
	private void initializeGameInfoPanel() {
		JPanel gameFactsPanel = new JPanel();
		gameFactsPanel.setBounds(31, 40, 1026, 145);
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
	}
	
	private void visitStore() {
		 // TODO implement
		hide();
		Screen visitStoreScreen = new VisitStoreScreen(getGame(), this);
		visitStoreScreen.show();
		
	}
	
	private void viewIsland() {
		 // TODO implement
		hide();
		Screen viewIslands = new ViewIslandsScreen(getGame(), ui);
		viewIslands.show();
		
	}
	
	private void viewPrevItems(){
		tablePanelLabel.setText("Previously bought items!");
		
		// Get the purchased of a Player and create an array containing column titles
		String[][] purchasedItems = game.getPlayer().purchasedItemsToArray();
		
		if (purchasedItems == null){
			tablePanel.removeAll();
			JLabel noPrevItemsLabel = new JLabel("You haven't bought any items yet, buy some at any store!");
			noPrevItemsLabel.setBounds(33, 100, 200, 150);
			tablePanel.add(noPrevItemsLabel);
		}
		else {
			String[] columns = {"Name", "Purchase Price", "Consignment Price"};
			
			// Create the table containing all the previously bought items of a player
			createTable(purchasedItems, columns);
		}
	}
	
	private void viewShipProperties() {
		// TODO implement
		// Show ship details in the info box opposite
//		String[][] rows = { 
//				{"1", "3", "5"},
//				{"4", "6", "7"}
//		};
//		String[] columns ={"Test", "Test", "Test"};
//		createTable(rows, columns);
		tablePanel.removeAll();
		
		JTextPane detailPane = new JTextPane();
		detailPane.setBounds(168, 10, 200, 200);
		detailPane.setText(game.getPlayer().getShip().getDescription());
		//detailPane.setText("This is just some test text \n with a line break!");
		tablePanel.add(detailPane);
	}
}
