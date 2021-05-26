package uiClasses.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

import coreClasses.GameEnvironment;

/** Represents the screen for high level interaction with the "Island Trader" game
 * Displays the current progress of the game. And gives a user options to visit a store
 * or travel to another island
 * 
 * @author Hugo Phibbs
 * @version 24/5/2021
 * @since 10/5/21
 */
public class CoreOptionsScreen extends Screen{

	/**
	 * Create the application.
	 */
	public CoreOptionsScreen(GameEnvironment gameEnvironment) {
		super("Core options screen", gameEnvironment);
		// Check player has enough money to continue with the game
		if (game.calculateLiquidValue() < game.getMinMoneyToTravel()) {
			game.getUi().finishGame("You don't have enough money to pay your crew and you don't have enough goods to sell."
					+ " You are stranded on this island, and are rubbish at trading pirate goods.");
			quit();
		}
		else {
			initialize();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {		
		frame.setBounds(100, 100, 1100, 595);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeOptionsPanel();
		initializeGameInfoPanel();
		initializeShipPanel();
	}
	
	/** Create the panel containing all the buttons to interact with the game
	 * 
	 */
	private void initializeOptionsPanel() {
		// Create panel to hold relevant components
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(blackline);
		optionsPanel.setBackground(new Color(0, 153, 255));
		optionsPanel.setBounds(35, 230, 450, 292);
		frame.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		// Use html in order to display multiple lines on the button
		JButton visitStoreButton = new JButton(String.format("<html>      Visit %s's Store<br> and view previously bought items</html>", game.getCurrentIsland().getIslandName()));
		visitStoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				visitStore();
			}
		});
		visitStoreButton.setBounds(12, 12, 425, 125);
		visitStoreButton.setBackground(new Color(153, 204, 255));
		optionsPanel.add(visitStoreButton);
		
		// Use html to display text as two lines instead of one!
		JButton viewVisitOtherIslandsButton = new JButton("<html>  View other islands<br>and travel to another island</html>");
		viewVisitOtherIslandsButton.setBounds(12, 155, 425, 125);
		viewVisitOtherIslandsButton.setBackground(new Color(153, 204, 255));
		optionsPanel.add(viewVisitOtherIslandsButton);
		viewVisitOtherIslandsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewIsland();
			}
		});
	}
	
	/** Method to initialize panel containing all of the information of a game
	 * 
	 */
	private void initializeGameInfoPanel() {
		JPanel gameFactsPanel = new JPanel();
		gameFactsPanel.setBorder(blackline);
		gameFactsPanel.setBackground(new Color(0, 153, 255));
		gameFactsPanel.setBounds(35, 40, 1000, 175);
		frame.getContentPane().add(gameFactsPanel);
		gameFactsPanel.setLayout(null);
		
		JLabel welcomeLabel = new JLabel(String.format("Welcome to %s %s!", game.getCurrentIsland().getIslandName(), game.getPlayer().getName()));
		welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(0, 20, 1000, 30);
		gameFactsPanel.add(welcomeLabel);
		
		JLabel gameFactsLabel = new JLabel("Quick game facts!");
		gameFactsLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		gameFactsLabel.setBounds(24, 45, 200, 18);
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
	
	/** Method to initialize panel containing information for a Player's Ship
	 * 
	 */
	private void initializeShipPanel() {
		// Create panel containing a description of a ship, plus any upgrades that have been boughtx
		JPanel shipPanel = new JPanel();
		shipPanel.setBounds(500, 230, 535, 292);
		shipPanel.setLayout(null);
		shipPanel.setBackground(new Color(0, 153, 255));
		frame.getContentPane().add(shipPanel);
		shipPanel.setBorder(blackline);
		
		JTextPane detailPane = new JTextPane();
		detailPane.setBounds(12, 180, 200, 100);
		detailPane.setText(game.getPlayer().getShip().getDescription());
		shipPanel.add(detailPane);
		
		JLabel shipPanelLabel = new JLabel(String.format("Info about your ship %s", game.getShip().getName()));
		shipPanelLabel.setBounds(12, 12, 300, 18);
		shipPanelLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		shipPanel.add(shipPanelLabel);
		
		JLabel shipUpgradesLabel = new JLabel("Ship upgrades");
		shipUpgradesLabel.setBounds(335, 60, 200, 15);
		shipPanel.add(shipUpgradesLabel);
		
		String[][] upgrades = game.getShip().upgradesToNestedArray();
		if (upgrades.length == 0) {
			// Ship hasnt bought any upgrades
			JLabel noUpgradesLabel = new JLabel("You havent bought any upgrades yet!");
			noUpgradesLabel.setBounds(250, 80, 300, 15);
			shipPanel.add(noUpgradesLabel);
		}
		else {
			JTable shipUpgradesTable = new JTable(game.getShip().upgradesToNestedArray(), new String[] {"Name", "Defense Boost"});
			shipUpgradesTable.setBounds(0, 0, 275, 200);
			JScrollPane sp = new JScrollPane(shipUpgradesTable);
			sp.setBounds(250, 80, 275, 200);
			shipPanel.add(sp);
		}
	}	
	
	/** Method for visiting a store. Creates a new VisitStoreScreen and
	 * closes this CoreOptionsScreen
	 */
	private void visitStore() {
		Screen visitStoreScreen = new VisitStoreScreen(getGame());
		visitStoreScreen.show();
		quit();
	}
	
	/** Method for viewing Islands that you can travel to. Creates a new ViewIslandsScreen
	 * and closes this CoreOptionsScreen
	 */
	private void viewIsland() {
		Screen viewIslands = new ViewIslandsScreen(getGame());
		viewIslands.show();
		quit();
	}
}
