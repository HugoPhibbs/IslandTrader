package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class CoreOptionsScreen {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoreOptionsScreen window = new CoreOptionsScreen();
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
	public CoreOptionsScreen() {
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
			}
		});
		visitStoreButton.setBounds(25, 279, 400, 100);
		optionsPanel.add(visitStoreButton);
		
		JButton viewVisitOtherIslandsButton = new JButton("<html>      View other islands<br>and travel to another island</html>");
		viewVisitOtherIslandsButton.setBounds(25, 395, 400, 122);
		optionsPanel.add(viewVisitOtherIslandsButton);
		viewVisitOtherIslandsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}
	
	public void initializeTablePanel() {
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(512, 196, 546, 542);
		frame.getContentPane().add(tablePanel);
		tablePanel.setLayout(null);
		
		table = new JTable();
		table.setBounds(168, 269, 1, 1);
		tablePanel.add(table);
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
}
