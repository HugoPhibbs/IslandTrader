package uiClasses.gui;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import coreClasses.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class VisitStoreScreen {

	private JFrame frame;
	private JTextField textField;
	private JLabel itemsJListLabel;
	private JList itemsJList;
	private GameEnvironment gameEnvironment;
	private JLabel balanceJLabel;

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
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Label for welcoming user to store
		JLabel welcomeLabel = new JLabel("Hello <playerName>, welcome to the <storeName> store");
		welcomeLabel.setBounds(208, 22, 307, 14);
		frame.getContentPane().add(welcomeLabel);
		
		// Label for j list. For buying.selling and showing previouslt bought items
		this.itemsJListLabel = new JLabel();
		itemsJListLabel.setBounds(424, 63, 248, 14);
		frame.getContentPane().add(itemsJListLabel);
		
		// need to have a listener here, because cash could change!
		this.balanceJLabel = new JLabel("Your current balance is <player.getBalance()>\r\n");
		updatePlayerBalance();
		balanceJLabel.setBounds(34, 89, 265, 23);
		frame.getContentPane().add(balanceJLabel);
		
		// Button to view Items that a store buys
		JButton viewItemsStoreBuysButton = new JButton("View Items that the store buys");
		viewItemsStoreBuysButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// call method to display items in j list opposite
				displayItems("buy");
			}
		});
		viewItemsStoreBuysButton.setBounds(34, 220, 292, 23);
		frame.getContentPane().add(viewItemsStoreBuysButton);
		
		// Button to view Items that are for sale
		JButton viewItemsForSaleButton = new JButton("View Items that the store sells");
		viewItemsForSaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayItems("sell");
			}
		});
		viewItemsForSaleButton.setBounds(34, 164, 292, 23);
		frame.getContentPane().add(viewItemsForSaleButton);
		
		// Button to view previously bought items
		JButton viewPreviousItemsButton = new JButton("View Items that you have previously bought");
		viewPreviousItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayItems("previous");
			}
		});
		viewPreviousItemsButton.setBounds(34, 273, 292, 23);
		frame.getContentPane().add(viewPreviousItemsButton);
		
		// Button for going back
		JButton goBackButton = new JButton("Go Back");
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// call go back screen to handle
			}
		});
		goBackButton.setBounds(608, 431, 89, 23);
		frame.getContentPane().add(goBackButton);
		
		
		// use a jlist becuase it is variable size, so if we wanted to put more items in the game
		// we could
		this.itemsJList = new JList();
		itemsJList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});
		
		
		itemsJList.setBounds(361, 88, 348, 232);
		frame.getContentPane().add(itemsJList);
		

		
		buySellStart(); // for getting alignment right
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
			itemsJList.setListData(new String []{"this is where items should be"});
		}
		else {
			//String[] purchasedItemsArray = gameEnvironment.getPlayer().purchasedItemsToArray();
			//itemsJList.setListData(purchasedItemsArray);
			
			itemsJListLabel.setText("Previously bought items");
			itemsJList.setListData(new String []{"shakas"});
		}
	}
	
	private void buySellFinish() {
		/* called after a buying or selling transaction has been finished
		 */
		
		// TODO implement 
		// Show reciept from buying something, gets reciept from store
		JLabel lblNewLabel_3 = new JLabel("<receipt from buying something>");
		lblNewLabel_3.setBounds(405, 392, 210, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		updatePlayerBalance();
	}
	
	private void buySellStart() {
		/* starts buying or selling transaction with a store
		 * 
		 */
		/* Label for seeing the reciept of what you have bought/sold 
		 * should only appear after buying an item, and stay visible until another item
		 * is bought, 
		 * Perhaps it can be created by another method?
		 */
		JLabel lblNewLabel_4 = new JLabel("How many <itemName> would you like to buy/sell>");
		lblNewLabel_4.setBounds(371, 331, 265, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		// Textfield for inputting number of items that you want to buy
		textField = new JTextField();
		textField.setBounds(663, 331, 46, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		/* Label to tell user that current inputted number is invalid
		 * perhaps this could use the same sort of code when inputting an int 
		 * for game length?
		 */
		//TODO make colour red
		JLabel lblNewLabel_5 = new JLabel("Please enter an integer!");
		lblNewLabel_5.setBounds(548, 360, 149, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		
		// have a listener to check if the inputted number is valid, look at rocketmanagaer setupscreen for eg
		
		/* Button to buy or sell items, should be created alongside label above.
		 * 
		 * Button should only be visible/clickable when the inputted integer is valid
		 * much like what is done in rocketManager on the main screen
		 * otherwise there should be some text that says that current integer is invalid
		 */
		JButton btnNewButton = new JButton("<buy/sell> items");
		btnNewButton.setBounds(370, 356, 124, 23);
		frame.getContentPane().add(btnNewButton);
		
		buySellFinish();
	}
}
