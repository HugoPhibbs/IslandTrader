package uiClasses.gui;

import javax.swing.JPanel;
import javax.swing.JTable;

import coreClasses.*;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewIslandsScreen extends Screen {
	
	/** Island selected by the player.*/
	private Island selectedIsland;
	
	/** Button that a user can press when they have chosen an Island that they want to travel to */
	private JButton btnTravel;

	/** Label that shows the current Island that a user has selected */
	private JLabel lblSelectedIsland;
	
	/** TextPane to hold detailed information on a Island, including what it buys and sells */
	private JTextPane paneFullIslandInfo;
	
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
		frame.setBounds(100, 100, 1100, 530);
		
		createSelectIslandComponents();
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
		panelIslandSelection.setBounds(12, 43, 1075, 411);
		frame.getContentPane().add(panelIslandSelection);
		panelIslandSelection.setLayout(null);
		
		this.lblSelectedIsland = new JLabel("Select an island!");
		lblSelectedIsland.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedIsland.setBounds(593, 12, 184, 32);
		panelIslandSelection.add(lblSelectedIsland);
		
		JLabel lblInfo = new JLabel("Info:");
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblInfo.setBounds(593, 43, 70, 15);
		panelIslandSelection.add(lblInfo);
		
		this.paneFullIslandInfo = new JTextPane();
		paneFullIslandInfo.setText("");
		paneFullIslandInfo.setEditable(false);
		paneFullIslandInfo.setBounds(593, 71, 475, 120);
		panelIslandSelection.add(paneFullIslandInfo);
		
		// All the other islands that a user can reach from their current island */
		Island[] islandsToView = game.otherIslands();
		
		/* Buttons that a user can press, when pressed, gives them info on an island, and
		 * enables btnTravel if not already enabled. 
		 */
		JButton btnIsland1 = new JButton(islandsToView[0].getIslandName());
		btnIsland1.setBounds(12, 12, 274, 187);
		btnIsland1.addActionListener(e -> changeIslandInfo(islandsToView[0]));
		panelIslandSelection.add(btnIsland1);
		
		JButton btnIsland2 = new JButton(islandsToView[1].getIslandName());
		btnIsland2.setBounds(298, 12, 274, 187);
		btnIsland2.addActionListener(e -> changeIslandInfo(islandsToView[1]));
		panelIslandSelection.add(btnIsland2);
		
		JButton btnIsland3 = new JButton(islandsToView[2].getIslandName());
		btnIsland3.setBounds(12, 212, 274, 187);
		btnIsland3.addActionListener(e -> changeIslandInfo(islandsToView[2]));
		panelIslandSelection.add(btnIsland3);
		
		JButton btnIsland4 = new JButton(islandsToView[3].getIslandName());
		btnIsland4.setBounds(298, 212, 274, 187);
		btnIsland4.addActionListener(e -> changeIslandInfo(islandsToView[3]));
		panelIslandSelection.add(btnIsland4);
	}
	
	private void createTables() {
		JTable tableItemsStoreSells = new JTable();
		tableItemsStoreSells.setBounds(0, 0, 0, 0);
		
		JTable tableItemsStoreBuys = new JTable();
		
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
		frame.getContentPane().add(btnBack);
		
		this.btnTravel = new JButton("Travel to selected island");
		btnTravel.setEnabled(false); // set to disabled until a user chooses an island to travel to
		btnTravel.setBounds(730, 466, 234, 25);
		btnTravel.addActionListener(e -> viewRoutes());
		frame.getContentPane().add(btnTravel);
		
		JLabel lblInstructions = new JLabel("Select an Island to see info about that Island. ");
		lblInstructions.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInstructions.setBounds(12, 12, 412, 19);
		frame.getContentPane().add(lblInstructions);
	}

	/**	Changes the info displayed to be info on the selected island. Method is called each time an
	 * Island's button is clicked. 
	 * 
	 * @param island Island object that has been chosen from clicking one of the Island buttons
	 */
	private void changeIslandInfo(Island island) {
		btnTravel.setEnabled(true); // Has now been set to enabled since a island button has been pressed
		lblSelectedIsland.setText(island.getIslandName());
		paneFullIslandInfo.setText(island.fullInfo(game.getCurrentIsland().possibleRoutes(island)));
		selectedIsland = island;
	}
}
