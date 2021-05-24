package uiClasses.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import coreClasses.*;

/** Represents a Screen that a user can interact within "Island Trader" in order to do
 * operations with a Store
 * 
 * @author Hugo Phibbs
 * @version 24/5/2021
 * @since 10/5/2021
 */
public class VisitStoreScreen extends Screen {
	
	// Class Variables //
	/** JTextField that a user enters into how many of a particular item they would like to buy */
	private JTextField numItemsTextField;
	
	/** JTextPane to display the receipt of a buying or selling transaction */
	private JTextPane receiptTextPane;
	
	/** JLabel as a title for the table that holds items */
	private JLabel itemsJTableLabel;
	
	/** JLabel that tells a user the current money balance of a player */
	private JLabel balanceJLabel;
	
	/** JLabel to ask a use to enter an Integer for the number of numbers they would like to buy */
	private JLabel enterIntegerJLabel;
	
	/** JButton for a user to press if they would like to buy or sell items */
	private JButton buySellItemsButton;
	
	/** Panel to Store the buttons for high level interaction with a store, 
	 * (buying items, selling items, view previously bought items) */
	private JPanel mainStoreOptionsPanel;
	
	/** Panel to store components relating directly to buying and selling items with a store */
	private JPanel buySellOptionsPanel;
	
	/** Panel to store the table that displays Items to a user */
	private JPanel tablePanel;
	
	/** Label to ask user how many items they would like to buy */
	private JLabel howManyItemsLabel;
	
	/** The current name of the Item that a player has chosen from the table */
	private String chosenItemName = "";

	
	/** Constructor for the VisitStoreScreen
	 * 
	 * @param gameEnvironment GameEnvironment object for the current game 
	 */
	public VisitStoreScreen(GameEnvironment gameEnvironment) {
		super("Visit Store Screen ", gameEnvironment);
		initialize();
	}
	
	/** Initialize the contents of the frame for VisitStoreScreen
	 * 
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 1100, 600);
		
		// Initialize all other necessary components
		initializeDirectFrameComponents();
		initializeMainStoreOptions();
		initializeBuySellOptions();
		initializeTablePanel();
	}
	
	/** Create components that are directly added to the frame
	 * 
	 */
	private void initializeDirectFrameComponents() {
		// Label for welcoming user to store
		JLabel welcomeLabel = new JLabel(String.format("Hello %s, welcome to the %s Store!", game.getPlayer().getName(), game.getCurrentIsland().getIslandStore().getName()));
		welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		welcomeLabel.setBounds(200, 23, 900, 30);
		frame.getContentPane().add(welcomeLabel);	
		
		// Create label for a Player's balance, and update it's value
		this.balanceJLabel = new JLabel();
		balanceJLabel.setBounds(34, 89, 300, 23);
		frame.getContentPane().add(balanceJLabel);
		updatePlayerBalance();
		
		// Button for going back
		JButton goBackButton = new JButton("Go Back");
		goBackButton.setBounds(950, 525, 115, 23);
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a new coreOptions screen, and delete this current screen
				CoreOptionsScreen coreOptionsScreen = new CoreOptionsScreen(game);
				coreOptionsScreen.show();
				quit();
			}
		});
		frame.getContentPane().add(goBackButton);
	}

	/** Initialize the panel containing all the high level options that a user has to interact with a store
	 * 
	 */
	private void initializeMainStoreOptions() {
		// Create the Panel
		this.mainStoreOptionsPanel = new JPanel();
		mainStoreOptionsPanel.setBorder(blackline);
		mainStoreOptionsPanel.setBounds(35, 125, 500, 392);
		frame.getContentPane().add(mainStoreOptionsPanel);
		mainStoreOptionsPanel.setLayout(null);
		
		// Button to view Items that are for sale
		JButton viewItemsForSaleButton = new JButton("View Items that the store sells");
		viewItemsForSaleButton.setBounds(12, 12, 475, 113);
		mainStoreOptionsPanel.add(viewItemsForSaleButton);
		
		// Button to view Items that a store buys
		JButton viewItemsStoreBuysButton = new JButton("View Items that the store buys");
		viewItemsStoreBuysButton.setBounds(12, 137, 475, 113);
		mainStoreOptionsPanel.add(viewItemsStoreBuysButton);
		
		// Button to view previously bought items
		JButton viewPreviousItemsButton = new JButton("View previously bought Items");
		viewPreviousItemsButton.setBounds(12, 262, 475, 113);
		mainStoreOptionsPanel.add(viewPreviousItemsButton);
		
		// Add Action listeners to all the buttons above, this guides the flow of this screen
		viewItemsForSaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buySellItemsStart("sell");
			}
		});
		viewItemsStoreBuysButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buySellItemsStart("buy");
			}
		});
		viewPreviousItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPrevItems();
			}
		});
	}
	
	/** Method to initialize the panel that contains all the options for buying and selling items.
	 * Panel is set to invisible until a user chooses an Item from the JTable
	 */
	public void initializeBuySellOptions() {
		
		// Create the panel holding everything necessary
		this.buySellOptionsPanel = new JPanel();
		buySellOptionsPanel.setBorder(blackline);
		buySellOptionsPanel.setBounds(560, 327, 500, 190);
		frame.getContentPane().add(buySellOptionsPanel);
		buySellOptionsPanel.setLayout(null);
		
		// Set Panel to invisible until a user selects buy or sell items in the main options
		buySellOptionsPanel.setVisible(false);
		
		// Label to ask user how many items they want to buy
		this.howManyItemsLabel = new JLabel();
		howManyItemsLabel.setBounds(12, 12, 393, 35);
		buySellOptionsPanel.add(howManyItemsLabel);
		
		/* Label to tell user to enter an integer, is only visible if they
		 * input an invalid integer in numItemsTextField
		 */
		this.enterIntegerJLabel = new JLabel("Please enter an integer above 1!");
		enterIntegerJLabel.setForeground(Color.RED);
		enterIntegerJLabel.setBounds(12, 53, 300, 15);
		buySellOptionsPanel.add(enterIntegerJLabel);
		enterIntegerJLabel.setVisible(false);
		
		// TextField for inputting number of items that you want to buy
		this.numItemsTextField = new JTextField();
		numItemsTextField.setBounds(417, 20, 46, 20);
		buySellOptionsPanel.add(numItemsTextField);
		numItemsTextField.setColumns(10);
		
		/* Add a listener to numItemsTextField to check if the 
		 * inputed integer is valid. Calls another method to handle
		 * the checking itself
		 */
		numItemsTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				handleNumItems(numItemsTextField.getText());
			  }
			  public void removeUpdate(DocumentEvent e) {
				handleNumItems(numItemsTextField.getText());
			  }
			  public void insertUpdate(DocumentEvent e) {
				handleNumItems(numItemsTextField.getText());
			  }
		});
		
		// Label to contain the receipt a transaction with a store
		this.receiptTextPane = new JTextPane();
		receiptTextPane.setBounds(12, 78, 325, 100);
		buySellOptionsPanel.add(receiptTextPane);
		
		/* Button to press to buy or sell Items.
		 * Default is set to disabled until a user inputs a valid integer
		 * for the number of Items that they want to buy/sell
		 */
		this.buySellItemsButton = new JButton();
		buySellItemsButton.setBounds(350, 78, 139, 100);
		buySellOptionsPanel.add(buySellItemsButton);
		buySellItemsButton.setEnabled(false);
	}
	
	/** Initialize the panel for a table holding all the items that are being displayed to a user
	 * 
	 */
	private void initializeTablePanel() {
		// Create the panel
		this.tablePanel = new JPanel();
		tablePanel.setBorder(blackline);
		tablePanel.setBounds(560, 125, 500, 190);
		frame.getContentPane().add(tablePanel);
		tablePanel.setLayout(null);
		
		// Label for JTable. For buying, selling and showing previously bought items
		this.itemsJTableLabel = new JLabel();
		itemsJTableLabel.setBounds(673, 80, 248, 50);
		frame.getContentPane().add(itemsJTableLabel);
	}
	
	
	///////////////////////// GENERAL HELPER METHODS ////////////////////////////////
	
	/** Create a table to display information about Items that a user has either bought,
	 * items that they can buy or sell. 
	 * 
	 * @param rows String[][] nested array containing all the data to be stored in the table
	 * @param columns String[] array containing the titles for each column in the table
	 * has been initialized to sell or buy items, NOT to show previously bought items
	 * @param viewPrevItems boolean value if a user is using createTable to view previously bought Items
	 * @return JTable that was created
	 */
	private JTable createTable(String [][] rows, String[] columns, boolean viewPrevItems){
		// Remove content from panel from last use
		// bellow code from https://stackoverflow.com/questions/38349445/how-to-delete-all-components-in-a-jpanel-dynamically
		clearPanel(tablePanel);
		
		// Create table
		JTable itemsTable = new JTable(rows, columns);
		itemsTable.setBounds(0, 0, 475, 325);
		
		/* Create a ScrollPane that contains the Table, this is done to
		 * ensure column titles are shown 
		 */
		JScrollPane sp  = new JScrollPane(itemsTable);
		
		/* Adjust table if a user wants to see previous items that they have bought.
		 * This is done to extend the table downwards to reclaim real estate that would 
		 * be taken up with buySellOptionsPanel
		 */
		if (viewPrevItems) {
			sp.setBounds(12, 12, 475, 368);
			tablePanel.setBounds(560, 125, 500, 392);
		}
		else {
			sp.setBounds(12, 12, 475, 160);
			tablePanel.setBounds(560, 125, 500, 190);
		}
		tablePanel.add(sp);
		
		// Return table that was created
		return itemsTable;
	}
	
	//////////////////////// VIEWING PREVOUSLY BOUGHT ITEMS  /////////////////////////////////////	
	
	/** Method to handle the displaying of previously bought items of a player 
	 * in a table opposite.
	 */
	private void viewPrevItems() {
		
		/* Adjust components that may have been adjusted from buying or selling items
		 * basically resets affected components to what they were before user selected anything
		 */
		buySellOptionsPanel.setVisible(false);
		itemsJTableLabel.setText("Previously bought items");
		howManyItemsLabel.setText("");
		
		// Get the purchased of a Player and create an array containing column titles
		String[][] purchasedItems = game.getPlayer().purchasedItemsToArray();
		if (purchasedItems == null){
			clearPanel(tablePanel);
			JLabel noPrevItemsLabel = new JLabel("You haven't bought any items yet, you can buy some at any store!");
			noPrevItemsLabel.setBounds(12, 90, 500, 15);
			tablePanel.add(noPrevItemsLabel);
		}
		else {
			String[] columns = {"Name", "Purchase Price", "Consignment Price"};
			
			// Create the table containing all the previously bought items of a player
			createTable(purchasedItems, columns, true);
		}
	}
	
   //////////////////////// BUYING AND SELLING ITEMS  /////////////////////////////////////
	
	/** Method to begin a transaction between a player and a store
	 * 
	 * @param buyOrSell String for what a user wants to do with a Store, either "buy" or "sell"
	 */
	private void buySellItemsStart(String buyOrSell) {
		// Reset components from the last use of this function
		resetBuySellComponents();
		
		// Create table with descriptions of the Items that a player can buy or sell
		JTable itemsTable = setupBuySellTable(buyOrSell);
		
		// Create a selection model for itemsTable
		addItemsTableSelectionModel(itemsTable, buyOrSell);
		
		// Create listener for buying and selling items
		addBuySellItemsButtonListener(buyOrSell);
	}
	
	/** Method to handle the reseting of components relating to the buying and selling of Items 
	 * Basically creates a new state for things to be bought in, without concern of past conditions 
	 * of relevant components.
	 */
	private void resetBuySellComponents() {
		howManyItemsLabel.setText("");
		numItemsTextField.setText("");
		receiptTextPane.setText("");
		buySellOptionsPanel.setVisible(true);
		numItemsTextField.setVisible(false);
		buySellItemsButton.setEnabled(false);
		enterIntegerJLabel.setVisible(false);
		// Remove Excess action listeners for buySellItemsButton
		if (buySellItemsButton.getActionListeners().length > 0) {
			buySellItemsButton.removeActionListener(buySellItemsButton.getActionListeners()[0]);
		}
	}
	
	/** Method to create a selection model for a created itemsTable, for user
	 * to select Items from
	 * 
	 * @param itemsTable JTable containing descriptions of the Items that a player can buy or sell
	 * @param buyOrSell String for what a user wants to do with a Store, either "buy" or "sell"
	 */
	private void addItemsTableSelectionModel(JTable itemsTable, String buyOrSell) {
		// Create model and action listener for user to choose an Item from the table
		ListSelectionModel selectionModel = itemsTable.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		    	// Bellow lines ensures that changing selection of a row counts as one change, not two
		    	if (!e.getValueIsAdjusting()) {
			    	chosenItemName = chosenItemName(itemsTable);
			    	buySellItemsStartHelper(buyOrSell, chosenItemName);
		    	}
		    }
		});
	}
	
	/** Method to add a listener for the button to buy and sell items
	 * 
	 * @param buyOrSell String for what a user wants to do with a Store, either "buy" or "sell"
	 */
	private void addBuySellItemsButtonListener(String buyOrSell) {
		buySellItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buySellItemsButtonHandler(buyOrSell, chosenItemName);
			}
		});
	}
	
	/** Method to handle the creation of a table for buying and selling Items along with relevant supporting
	 * components.
	 * 
	 * @param buyOrSell String describing what a user wants to do with a Store, either "buy" or "sell"
	 * @return JTable table containing descriptions of the Items that a player can buy or sell with a Store
	 */
	private JTable setupBuySellTable(String buyOrSell) {
		/* Use html to display multi-line button 
		 * from https://stackoverflow.com/questions/15746970/how-to-add-a-multi-line-text-to-a-jbutton-with-the-line-unknown-dynamically
		 */
		itemsJTableLabel.setText(String.format("<html>Items that the store %ss<br>Select one to continue</html>", buyOrSell));
		
		/* Get the catalogue for the particular operation that is being done with a store
		 * and column names to match!
		 */
		HashMap<String, HashMap<String, Integer>> catalogue =  game.getCurrentIsland().getIslandStore().getCatalogue(buyOrSell);
		String[][] catalogueNestedArray = game.getCurrentIsland().getIslandStore().catalogueToNestedArray(catalogue);
		String[] colNames = {"Name", "Price", "Space Taken", "Defense Boost"};
		
		// Create table holding all the information from catalogueArray
		return createTable(catalogueNestedArray, colNames, false);
	}
	
	/** Helper method for buySellItemsStart. Adjusts relevant components to do with buying and selling Items
	 * Has no real functional use, just a method to format components.
	 * 
	 * @param buyOrSell String for what a user wants to do with a store, either "buy" or "sell"
	 * @param itemName String for the name of an Item that a user wants to buy or sell
	 */
	private void buySellItemsStartHelper(String buyOrSell, String itemName) {
		
		numItemsTextField.setText("");
	
		String buyOrSellAdj = "";
		
		if (buyOrSell.equals("buy")) {
			// Switch the meaning of operation around, as within the store class, it is in the perspective of the store!
			// So it is now in the perspective of the player!, just for components to diplay
			buyOrSellAdj = "sell";
		}
		else {
			buyOrSellAdj = "buy";
		}
		String buyOrSellCap = buyOrSellAdj.substring(0, 1).toUpperCase() + buyOrSellAdj.substring(1);
		buySellItemsButton.setText(String.format("%s Items", buyOrSellCap));
		howManyItemsLabel.setText(String.format("How many %s would you like to %s>", itemName, buyOrSellAdj));
		numItemsTextField.setVisible(true);
	}
	
	/** Method to handle the choosing between either buying or selling from a store. 
	 * Rolls functionality that would otherwise be split into 2 methods for buying and 
	 * selling into one.
	 * 
	 * @param buyOrSell String for what a user wants to do with a store, either "buy" or "sell"
	 * @param itemName String for the name of an Item that a user wants to buy or sell
	 * @throws IllegalStateException if operation is neither "buy" or "sell"
	 */
	private void buySellItemsButtonHandler(String buyOrSell, String chosenItemName) throws IllegalStateException {
		// Check if the chosenItem name is empty, checks exceptional program flow
		if (chosenItemName.equals("")) {
			throw new IllegalArgumentException("Chosen item is empty!!");
		}
		
		int numItems = Integer.parseInt(numItemsTextField.getText());
		
		String receipt = "";
		// buyOrSell is in the perspective of the store
		// Buy or sell items with a store
		if (buyOrSell.equals("buy")) {
			receipt = buyItemsFromPlayer(chosenItemName, numItems);
		}
		else if (buyOrSell.equals("sell")) {
			receipt = sellItemsToPlayer(chosenItemName, numItems);
		}
		else {
			throw new IllegalStateException("BUG invalid operation parameter!");
		}
		buySellFinish(receipt);
	}
	
	/** Method to handle the selling of Items from a Store to a Player
	 * 
	 * @param itemName String for the name of the item that is about to be sold to a player
	 * @param numItems Integer for the numbers of Items that a user wants to buy with the name 'itemName'
	 * @return String for the receipt of a transaction
	 */
	private String sellItemsToPlayer(String itemName, int numItems) {
		// Return Receipt from buying an item given by item name from the store
		return game.getCurrentIsland().getIslandStore().sellItemsToPlayer(game, itemName, numItems);
	}
	
	/** Method to handle the selling of Items from a Player to a Store
	 * 
	 * @param itemName String for the name of the item that is about to be sold to a Store
	 * @param numItems Integer for the numbers of Items that a user wants to sell with the name 'itemName'
	 * @return String for the receipt of a transaction
	 */
	private String buyItemsFromPlayer(String itemName, int numItems) {
		// Get receipt from selling an item to a Store
		return game.getCurrentIsland().getIslandStore().buyItemsFromPlayer(itemName, game.getPlayer(), numItems);
	}
	
	/** Method to handle the finishing of a transaction between a Player and a Store. 
	 * 
	 * @param receipt String for the receipt of a transaction between a Player and a Store
	 */
	private void buySellFinish(String receipt) {
		// Set the label for the receipt of transaction
		receiptTextPane.setText(receipt);
		// Update the balance of a player
		updatePlayerBalance();
	}
	
	/** Method to return the name of an Item has selected from the table.
	 * 
	 * @param itemsTable Table containing descriptions of the Items that a user can buy or sell
	 * @return String for the name of an Item that a user has chosen from the table
	 */
	private String chosenItemName(JTable itemsTable) {
		// Take selection from table and extract name of an Item
		int chosenRow = itemsTable.getSelectedRow();
		// Name of an Item is always stored at column 1 in a table
		String itemName = itemsTable.getModel().getValueAt(chosenRow, 0).toString();
		return itemName;
	}
	
	/** Method to handle the input of how many Items a user would like to buy
	 * Checks if a number is valid, and takes action to notify user that the number is invalid
	 * 
	 * @param num String representation of the number that a user has inputed that is to be checked. 
	 */
	private void handleNumItems(String num) {
		// Check if number is valid
		if (!checkValidInt(num)) {
			enterIntegerJLabel.setVisible(true);
			buySellItemsButton.setEnabled(false);
		}
		// If it isnt, then display the label describing the requirements of an inputed integer. 
		
		else {
			enterIntegerJLabel.setVisible(false);
			buySellItemsButton.setEnabled(true);
		}
	}
	
	/** Method to check if an inputed integer, (in the form of a String) from a player. 
	 * Helper method for handleNumItem(String)
	 * 
	 * @param num String representation of number from a player
	 * @return boolean value if the inputed number is valid
	 */
	private boolean checkValidInt(String num) {
		try {
			// Try parse String representation of the number
			Integer newNum = Integer.parseInt(num);
			if (newNum < 0 ) {
				return false;
			}
		}
		// This is thrown if the inputted String is not a valid Integer
		catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	
    }
	
	/** Method to update the balance of a player that is shown at the top of the frame
	 * Is called every time Item(s) are sold or bought between a store and a player
	 */
	private void updatePlayerBalance() {
		//balanceJLabel.setText(String.format("Player has a balance of : %d", gameEnvironment.getPlayer().getMoneyBalance()));
		balanceJLabel.setText(String.format("Your current balance is %s Pirate Bucks", game.getPlayer().getMoneyBalance()));
	}
}


