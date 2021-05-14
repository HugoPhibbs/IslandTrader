package uiClasses.gui;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

import coreClasses.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Color;
import javax.swing.JTable;

public class VisitStoreScreen {
	
	// Core Class objects
	private GameEnvironment gameEnvironment;
	
	// Swing objects
	private JFrame frame;
	private JTextField textField;
	
	private JLabel receiptJLabel;
	private JLabel itemsJListLabel;
	private JLabel balanceJLabel;
	private JLabel enterIntegerJLabel;
	
	private JButton buySellItemsButton;
	
	private JPanel mainStoreOptionsPanel;
	private JPanel buySellPanel;
	private JTable itemsTable;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisitStoreScreen window = new VisitStoreScreen();
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
	public VisitStoreScreen() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Initialize all labels and buttons that aren't in panels
		
		// Label for welcoming user to store
		//JLabel welcomeLabel = new JLabel(String.format("Hello %s, welcome to the %s store", gameEnvironment.getPlayer().getName(), gameEnvironment.getCurrentIsland().getIslandStore().getName()));
		JLabel welcomeLabel = new JLabel("Hello <playerName>, welcome to the <storeName> store");
		welcomeLabel.setBounds(301, 23, 555, 14);
		frame.getContentPane().add(welcomeLabel);
		
		// Label for j list. For buying.selling and showing previously bought items
		this.itemsJListLabel = new JLabel();
		itemsJListLabel.setBounds(641, 98, 248, 14);
		frame.getContentPane().add(itemsJListLabel);
		
		// need to have a listener here, because cash could change!
		this.balanceJLabel = new JLabel("Your current balance is <player.getBalance()>\r\n");
		balanceJLabel.setBounds(34, 89, 265, 23);
		updatePlayerBalance();
		frame.getContentPane().add(balanceJLabel);
		
		// Button for going back
		JButton goBackButton = new JButton("Go Back");
		goBackButton.setBounds(949, 716, 118, 23);
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// call go back screen to handle
			}
		});
		frame.getContentPane().add(goBackButton);
		
		
		// Initialize main store options and buying and selling options
		initializeMainStoreOptions();
		initializeBuySellOptions();
		initializeTable();
	}
	
	private void initializeMainStoreOptions() {
		/* Panel containing core options with interacting with a store
		 * Ie, buy items, sell items, previously bought items
		 */
		this.mainStoreOptionsPanel = new JPanel();
		mainStoreOptionsPanel.setBounds(35, 124, 504, 352);
		frame.getContentPane().add(mainStoreOptionsPanel);
		mainStoreOptionsPanel.setLayout(null);
		
		mainStoreOptionsPanel.setVisible(true);
		
		// Button to view Items that are for sale
		JButton viewItemsForSaleButton = new JButton("View Items that the store sells");
		viewItemsForSaleButton.setBounds(12, 12, 475, 100);
		mainStoreOptionsPanel.add(viewItemsForSaleButton);
		
		// Button to view Items that a store buys
		JButton viewItemsStoreBuysButton = new JButton("View Items that the store buys");
		viewItemsStoreBuysButton.setBounds(12, 124, 475, 100);
		mainStoreOptionsPanel.add(viewItemsStoreBuysButton);
		
		// Button to view previously bought items
		JButton viewPreviousItemsButton = new JButton("View Items that you have previously bought");
		viewPreviousItemsButton.setBounds(12, 236, 475, 100);
		mainStoreOptionsPanel.add(viewPreviousItemsButton);
		
		viewPreviousItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPrevItems();
				displayItems("previous");
			}
		});
		viewItemsStoreBuysButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// call method to display items in j list opposite
				buySellStart();
				displayItems("buy");
			}
		});
		viewItemsForSaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buySellStart();
				displayItems("sell");
			}
		});
	}
	
	public void initializeBuySellOptions() {
		
		this.buySellPanel = new JPanel();
		buySellPanel.setBounds(290, 507, 507, 242);
		frame.getContentPane().add(buySellPanel);
		buySellPanel.setLayout(null);
		
		// Set Panel to invisible until prev items button is clicked
		buySellPanel.setVisible(false);
		
		// Label to ask user how many items they want to buy
		JLabel howManyItemsLabel = new JLabel("How many <itemName> would you like to buy/sell>");
		howManyItemsLabel.setBounds(12, 12, 393, 35);
		buySellPanel.add(howManyItemsLabel);
		//TODO make colour red
		
		// Label to tell user to enter an integer
		this.enterIntegerJLabel = new JLabel("Please enter an integer above 1!");
		enterIntegerJLabel.setForeground(Color.RED);
		enterIntegerJLabel.setBounds(12, 53, 174, 15);
		buySellPanel.add(enterIntegerJLabel);
		
		enterIntegerJLabel.setVisible(false);
		
		// Textfield for inputting number of items that you want to buy
		JTextField numItemsTextField = new JTextField();
		numItemsTextField.setBounds(417, 20, 46, 20);
		buySellPanel.add(numItemsTextField);
		numItemsTextField.setColumns(10);
		
		// Check if the current inputted int is valid
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
		
		// Receipt from buying a store
		this.receiptJLabel = new JLabel("<receipt from buying something>");
		receiptJLabel.setBounds(22, 95, 202, 91);
		buySellPanel.add(receiptJLabel);
		
		// Button for buying and selling items
		this.buySellItemsButton = new JButton("<buy/sell> n items");
		buySellItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		buySellItemsButton.setBounds(267, 85, 227, 114);
		buySellPanel.add(buySellItemsButton);
		
		buySellItemsButton.setEnabled(false);
	}
	

	
	private void initializeTable(){
		
		// Create Panel for items table
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(584, 89, 504, 384);
		frame.getContentPane().add(tablePanel);
		tablePanel.setLayout(null);
		
		// Create table to hold items
		this.itemsTable = new JTable();
		itemsTable.setBounds(33, 30, 447, 342);
		
		// HOW TF do i get table column titles???
		//panel.add(new JScrollPane(itemsTable));
		tablePanel.add(itemsTable);
	}
	
	private void viewPrevItems() {
		// Set buy sell items to be invisible because it wont be needed
		
		// need to handle case where player has no items bought, table should be left blank with just a message
		
		buySellPanel.setVisible(false);
		
		String [] prevItemsColLabels = {"Name", "Buy price", "Sell Price"};
		
		String[][] testArray = {
				{"Hello", "This", "Is"},
				{"A", "test"}
		};
		
		DefaultTableModel tableModel = (DefaultTableModel) itemsTable.getModel();
		
		for (int i = 0; i < testArray.length; i++) {
			tableModel.addRow(testArray[i]);
		}
		
		itemsTable.getColumnModel().getColumn(0).setHeaderValue("New Name");
		itemsTable.getTableHeader().resizeAndRepaint();
		
		// need to reset table after adding items
				
		//ArrayList<String> previousBoughtItems = gameEnvironment.getPlayer().getPurchasedItemsArrayList();
		
		
		// JTable should be set to empty after
	}
	
	private void updatePlayerBalance() {
		// TODO can this be done automatically???
		
		//balanceJLabel.setText(String.format("Player has a balance of : %d", gameEnvironment.getPlayer().getMoneyBalance()));
		balanceJLabel.setText(String.format("Player has a balance of : %d", 420));
	}
	
	private void displayItems(String operation) {
		// TODO implement
		
		if (operation != "previous") {
			//String[] catalogueArrayList = gameEnvironment.getCurrentIsland().getIslandStore().catalogueToArray(operation);
			//itemsJList.setListData(catalogueArray);
			
			//TEST
			itemsJListLabel.setText(String.format("Items that the store %ss", operation));
		}
		else {
			//String[] purchasedItemsArray = gameEnvironment.getPlayer().purchasedItemsToArray();
			//itemsJList.setListData(purchasedItemsArray);
			
			itemsJListLabel.setText("Previously bought items");
		}
	}

	private void buySellStart() {
		buySellPanel.setVisible(true);		
		
		DefaultTableModel tableModel = (DefaultTableModel) itemsTable.getModel();
	
		// clear the items in the table 
		
		/* starts buying or selling transaction with a store
		 * 
		 */
		/* Label for seeing the reciept of what you have bought/sold 
		 * should only appear after buying an item, and stay visible until another item
		 * is bought, 
		 * Perhaps it can be created by another method?
		 */
		
		/* Label to tell user that current inputted number is invalid
		 * perhaps this could use the same sort of code when inputting an int 
		 * for game length?
		 */
		
		
		// have a listener to check if the inputted number is valid, look at rocketmanagaer setupscreen for eg
		
		/* Button to buy or sell items, should be created alongside label above.
		 * 
		 * Button should only be visible/clickable when the inputted integer is valid
		 * much like what is done in rocketManager on the main screen
		 * otherwise there should be some text that says that current integer is invalid
		 */
		
		buySellFinish("filler");
	}
	
	private void sellItemsToPlayer(String itemName, int numItems) {
		// called when button pressed, condensed into a method
		
		// have a text show up for the curr item and number if they want to buy, in the form of red text like entering in a name or number
		// this can be in the form of a pop up
		
		// Get Receipt from buying an item given by item name from the store
		String receipt = gameEnvironment.getCurrentIsland().getIslandStore().sellItemsToPlayer(gameEnvironment, itemName, numItems);
		
		buySellFinish(receipt);
	}
	
	private void buyItemsFromPlayer(String itemName, int numItems) {
		
		// Get receipt from selling an item to 
		String receipt = gameEnvironment.getCurrentIsland().getIslandStore().buyItemsFromPlayer(itemName, gameEnvironment.getPlayer(), numItems);
		buySellFinish(receipt);
	}
	
	
	private void buySellFinish(String receipt) {
		/* called after a buying or selling transaction has been finished
		 */	
		
		// Set the label for the receipt of transaction
		receiptJLabel.setText(receipt);
		updatePlayerBalance();
	}
	
	private void handleNumItems(String num) {
		if (!checkValidInt(num)) {
			enterIntegerJLabel.setVisible(true);
			buySellItemsButton.setEnabled(false);
		}
		else {
			enterIntegerJLabel.setVisible(false);
			buySellItemsButton.setEnabled(true);
		}
	}
	
	private boolean checkValidInt(String num) {
		// Check valid integer for choosing the number of items that you want
		if (num==null) {
			return false;
		}
		try {
			Integer newNum = Integer.parseInt(num);
			if (newNum < 0 ) {
				return false;
			}
		}
		catch (NumberFormatException iie) {
			return false;
		}
		return true;
	}
}
