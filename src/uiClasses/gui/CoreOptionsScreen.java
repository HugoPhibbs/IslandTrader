package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

import coreClasses.GameEnvironment;

public class CoreOptionsScreen extends Screen{

	private JTable table;
	private JPanel tablePanel;

	
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
	public CoreOptionsScreen(GameEnvironment gameEnvironment) {
		// Make the parent of the setup screen to be null
		super("Core options screen", gameEnvironment, null);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		initializeOptionsPanel();
		initializeTablePanel();
		initializeGameInfoPanel();
	}
	
	public void initializeOptionsPanel() {
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBounds(31, 196, 469, 542);
		frame.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		JButton viewShipPropertiesButton = new JButton("View Ship Properties");
		viewShipPropertiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewShipDetails();
			}
		});
		viewShipPropertiesButton.setBounds(25, 24, 400, 100);
		optionsPanel.add(viewShipPropertiesButton);
		
		JButton viewBoughtItemsButton = new JButton("View Bought Items");
		viewBoughtItemsButton.setBounds(25, 151, 400, 100);
		optionsPanel.add(viewBoughtItemsButton);
		
		JButton visitStoreButton = new JButton("Visit <islandName> Store");
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
			}
		});
	}
	
	public void initializeTablePanel() {
		this.tablePanel = new JPanel();
		tablePanel.setBounds(512, 196, 546, 542);
		frame.getContentPane().add(tablePanel);
		tablePanel.setLayout(null);
	
	}
	
	public void createTable(String [][] rows, String[] columns) {
		tablePanel.removeAll();
		JTable newTable = new JTable(rows, columns);
		newTable.setBounds(168, 269, 1, 1);
		tablePanel.add(newTable);
		newTable.setVisible(true);
	}
	
	public void initializeGameInfoPanel() {
		JPanel gameFactsPanel = new JPanel();
		gameFactsPanel.setBounds(31, 25, 1026, 145);
		frame.getContentPane().add(gameFactsPanel);
		gameFactsPanel.setLayout(null);
		
		JLabel playerNameLabel = new JLabel("Player: <playerName>");
		playerNameLabel.setBounds(24, 12, 209, 26);
		gameFactsPanel.add(playerNameLabel);
		
		JLabel playerCashLabel = new JLabel("Cash: <playerCash>");
		playerCashLabel.setBounds(24, 61, 247, 15);
		gameFactsPanel.add(playerCashLabel);
		
		JLabel daysRemainingLabel = new JLabel("Days Remaining: <daysRemaining>");
		daysRemainingLabel.setBounds(24, 88, 315, 15);
		gameFactsPanel.add(daysRemainingLabel);
		
		JLabel currentIslandLabel = new JLabel("Current island: <currentIsland>");
		currentIslandLabel.setBounds(351, 105, 194, 15);
		gameFactsPanel.add(currentIslandLabel);
		
		JLabel currentScoreLabel = new JLabel("Current Score");
		currentScoreLabel.setBounds(624, 105, 285, 15);
		gameFactsPanel.add(currentScoreLabel);
	}
	
	public void visitStore() {
		 // TODO implement
		hide();
		Screen visitStoreScreen = new VisitStoreScreen(getGame(), this);
		visitStoreScreen.show();
		
	}
	
	public void viewShipDetails() {
		// TODO implement
		// Show ship details in the info box opposite
		String[][] rows = { 
				{"1", "3", "5"},
				{"4", "6", "7"}
		};
		String[] columns ={"Test", "Test", "Test"};
		createTable(rows, columns);
	}
}
