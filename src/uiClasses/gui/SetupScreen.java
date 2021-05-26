package uiClasses.gui;

import javax.swing.JLabel;  
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import coreClasses.*;

import java.awt.Color;
import javax.swing.JTextPane;

public class SetupScreen extends Screen {
	// Class Variables //
	/** The instance of Ship the player has selected.*/
	private Ship selectedShip;
	
	/** The instance of Island the player has selected to start at.*/
	private Island selectedIsland;
	
	/** JLabel that displays an error message when the name entered by the player is not valid.*/
	private JLabel lblNameError;
	
	/** JSlider used by the player to select the game duration.*/
	private JSlider sliderDays;
	
	/** JLabel that displays the amount of days the player has selected to play for using the slider.*/
	private JLabel lblDisplayDays;
	
	/** JButton used to confirm choices and move on to the main game.*/ 
	private JButton btnConfirm;
	
	/** String The name the player has entered.*/
	private String playerName;
	
	
	/**
	 * Create the application.
	 */
	public SetupScreen(GameEnvironment game) {
		super("Setup Game", game);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		frame.setBounds(100, 100, 1100, 800);
		
		createMainLabels();
		createTextField();
		createConfirmButton();
		createDaysSlider();
		createPickShipComponents();
		createPickIslandComponents();
	}
	
	/**
	 * Called when confirm choices button is clicked.
	 * Creates the player object, passes the info gathered from the SetupScreen on to 
	 * Game Environment. GE then calls ui.play() to complete the setup process.
	 */
	private void setupComplete() {
		Player player = new Player(playerName, Gui.STARTING_MONEY); //TODO: Replace name once fix bug
		selectedShip.setOwner(player);
		game.onSetupFinished(player, selectedShip, sliderDays.getValue(), selectedIsland);
		this.quit();
	}
	
	/** Method to create the main labels for the setup screen */
	private void createMainLabels() {
		JLabel lblWelcome = new JLabel("Welcome to Island Trader!");
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 22));
		lblWelcome.setBounds(321, 0, 332, 27);
		frame.getContentPane().add(lblWelcome);
		
		JLabel lblAskName = new JLabel("Please enter your name: ");
		lblAskName.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAskName.setBounds(26, 50, 225, 19);
		frame.getContentPane().add(lblAskName);
		
		JLabel lblAskDays = new JLabel("How many days would you like to play for?");
		lblAskDays.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAskDays.setBounds(26, 101, 377, 19);
		frame.getContentPane().add(lblAskDays);
		
		this.lblDisplayDays = new JLabel("30");
		lblDisplayDays.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDisplayDays.setFont(new Font("Dialog", Font.BOLD, 18));
		lblDisplayDays.setBounds(1008, 105, 44, 17);
		frame.getContentPane().add(lblDisplayDays);
		
		JLabel lblPickship = new JLabel("Pick a Ship");
		lblPickship.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPickship.setBounds(26, 153, 96, 19);
		frame.getContentPane().add(lblPickship);
		
		JLabel lblPickIsland = new JLabel("Pick a Starting Island");
		lblPickIsland.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPickIsland.setBounds(26, 446, 188, 19);
		frame.getContentPane().add(lblPickIsland);
		
		this.lblNameError = new JLabel("Must be 3-15 characters, and have not special characters");
		lblNameError.setForeground(Color.RED);
		lblNameError.setFont(new Font("Dialog", Font.BOLD, 11));
		lblNameError.setBounds(682, 77, 370, 14);
		lblNameError.setVisible(false);
		frame.getContentPane().add(lblNameError);
		
		// Label for the days that you can choose with the slider
		JLabel lblNewLabel = new JLabel("20                    30                    "
				+ "40                   50");
		lblNewLabel.setBounds(693, 120, 325, 15);
		frame.getContentPane().add(lblNewLabel);
	}
	
	/** Creates the JTextField the user enters their name into.*/
	private void createTextField() {
		JTextField textFieldName = new JTextField();
		textFieldName.setFont(new Font("Dialog", Font.PLAIN, 16));
		textFieldName.setBounds(693, 50, 359, 23);
		textFieldName.getDocument().addDocumentListener(new DocumentListener(){
		    @Override
		    public void insertUpdate(DocumentEvent e) { checkNameEntered(textFieldName.getText()); }
		    @Override
		    public void removeUpdate(DocumentEvent e) { checkNameEntered(textFieldName.getText()); }
		    @Override
		    public void changedUpdate(DocumentEvent e) { checkNameEntered(textFieldName.getText()); }
		});
		frame.getContentPane().add(textFieldName);
	}
	
	/**
	 * Creates the JButton to confirm, which is only enabled when user input is valid.
	 * Button takes you through to the game menu.
	 */
	private void createConfirmButton() {
		btnConfirm = new JButton("Confirm Choices");
		btnConfirm.addActionListener(e -> setupComplete());
		btnConfirm.setBounds(910, 739, 178, 25);
		btnConfirm.setEnabled(false);
		frame.getContentPane().add(btnConfirm);
	}
	
	/** Creates the JSlider used to select the game duration.*/
	private void createDaysSlider() {
		sliderDays = new JSlider();
		sliderDays.setPaintLabels(true);
		sliderDays.setMinorTickSpacing(1);
		sliderDays.setMinimum(20);
		sliderDays.setMaximum(50);
		sliderDays.setBounds(693, 103, 299, 19);
		sliderDays.setValue(30);
		sliderDays.addChangeListener(e -> lblDisplayDays.setText(String.valueOf(sliderDays.getValue())));
		frame.getContentPane().add(sliderDays);
	}
	
	/** Creates the JPanel and all the components within it that are used for selecting a ship.*/
	private void createPickShipComponents() {
		
		JPanel panelPickShip = new JPanel();
		panelPickShip.setBorder(blackline);
		panelPickShip.setBounds(12, 184, 1065, 250);
		frame.getContentPane().add(panelPickShip);
		panelPickShip.setLayout(null);
		
		JLabel lblSelectedShip = new JLabel("Select a ship!");
		lblSelectedShip.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedShip.setBounds(869, 12, 196, 40);
		panelPickShip.add(lblSelectedShip);
		
		JLabel lblSpeed = new JLabel("Speed:");
		lblSpeed.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSpeed.setBounds(869, 64, 56, 17);
		panelPickShip.add(lblSpeed);
		
		JLabel lblCrewSize = new JLabel("Crew Size:");
		lblCrewSize.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCrewSize.setBounds(869, 107, 86, 17);
		panelPickShip.add(lblCrewSize);
		
		JLabel lblCargoCapacity = new JLabel("Cargo capacity:");
		lblCargoCapacity.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCargoCapacity.setBounds(869, 152, 123, 17);
		panelPickShip.add(lblCargoCapacity);
		
		JLabel lblMaxDefense = new JLabel("Max Defense:");
		lblMaxDefense.setFont(new Font("Dialog", Font.BOLD, 12));
		lblMaxDefense.setBounds(869, 198, 150, 17);
		panelPickShip.add(lblMaxDefense);

		JLabel lblSpeedVal = new JLabel("-");
		lblSpeedVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSpeedVal.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSpeedVal.setBounds(1006, 64, 34, 17);
		panelPickShip.add(lblSpeedVal);
		
		JLabel lblCrewVal = new JLabel("-");
		lblCrewVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCrewVal.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCrewVal.setBounds(1006, 108, 34, 17);
		panelPickShip.add(lblCrewVal);
		
		JLabel lblCargoVal = new JLabel("-");
		lblCargoVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCargoVal.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCargoVal.setBounds(1006, 153, 34, 17);
		panelPickShip.add(lblCargoVal);
		
		JLabel lblDefenseVal = new JLabel("-");
		lblDefenseVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDefenseVal.setFont(new Font("Dialog", Font.BOLD, 12));
		lblDefenseVal.setBounds(1006, 199, 34, 17);
		panelPickShip.add(lblDefenseVal);
		
		JButton btnShip1 = new JButton("Black Pearl");
		btnShip1.addActionListener(e -> changeShipStats(game.getShipArray()[0], lblSelectedShip, lblSpeedVal, lblCrewVal, lblCargoVal, lblDefenseVal));
		btnShip1.setBounds(12, 12, 198, 226);
		panelPickShip.add(btnShip1);
		
		JButton btnShip2 = new JButton("Thunder Bird");
		btnShip2.addActionListener(e -> changeShipStats(game.getShipArray()[1], lblSelectedShip, lblSpeedVal, lblCrewVal, lblCargoVal, lblDefenseVal));
		btnShip2.setBounds(222, 12, 198, 226);
		panelPickShip.add(btnShip2);
		
		JButton btnShip3 = new JButton("Batmobile");
		btnShip3.addActionListener(e -> changeShipStats(game.getShipArray()[2], lblSelectedShip, lblSpeedVal, lblCrewVal, lblCargoVal, lblDefenseVal));
		btnShip3.setBounds(432, 12, 198, 226);
		panelPickShip.add(btnShip3);
		
		JButton btnShip4 = new JButton("Apollo");
		btnShip4.addActionListener(e -> changeShipStats(game.getShipArray()[3], lblSelectedShip, lblSpeedVal, lblCrewVal, lblCargoVal, lblDefenseVal));
		btnShip4.setBounds(642, 12, 198, 226);
		panelPickShip.add(btnShip4);
		
	}
	
	/** Creates the JPanel and all the components within it that are used for selecting a starting Island.*/
	private void createPickIslandComponents() {
		
		JPanel panelPickIsland = new JPanel();
		panelPickIsland.setBorder(blackline);
		panelPickIsland.setBounds(12, 477, 1065, 250);
		frame.getContentPane().add(panelPickIsland);
		panelPickIsland.setLayout(null);
		
		JLabel lblSelectedIsland = new JLabel("Select an island!");
		lblSelectedIsland.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedIsland.setBounds(869, 12, 196, 40);
		panelPickIsland.add(lblSelectedIsland);
		
		JTextPane txtpnThisIslandInfo = new JTextPane();
		txtpnThisIslandInfo.setText("");
		txtpnThisIslandInfo.setBounds(869, 76, 184, 135);
		panelPickIsland.add(txtpnThisIslandInfo);
		
		JLabel lblInfo = new JLabel("Info:");
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblInfo.setBounds(869, 52, 70, 15);
		panelPickIsland.add(lblInfo);

		JButton btnIsland1 = new JButton("Cyprus");
		btnIsland1.addActionListener(e -> changeIslandInfo(game.getIslandArray()[0], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland1.setBounds(12, 12, 156, 226);
		panelPickIsland.add(btnIsland1);
		
		JButton btnIsland2 = new JButton("Sicily");
		btnIsland2.addActionListener(e -> changeIslandInfo(game.getIslandArray()[1], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland2.setBounds(180, 12, 156, 226);
		panelPickIsland.add(btnIsland2);
		
		JButton btnIsland3 = new JButton("Corsica");
		btnIsland3.addActionListener(e -> changeIslandInfo(game.getIslandArray()[2], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland3.setBounds(348, 12, 156, 226);
		panelPickIsland.add(btnIsland3);
		
		JButton btnIsland4 = new JButton("Malta");
		btnIsland4.addActionListener(e -> changeIslandInfo(game.getIslandArray()[3], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland4.setBounds(516, 12, 156, 226);
		panelPickIsland.add(btnIsland4);
		
		JButton btnIsland5 = new JButton("Ibiza");
		btnIsland5.addActionListener(e -> changeIslandInfo(game.getIslandArray()[4], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland5.setBounds(684, 12, 156, 226);
		panelPickIsland.add(btnIsland5);
	}
	
	/**
	 * Method is called when the user clicks on a ship's button.
	 * Method displays the information about the selected ship.
	 * @param ship Ship
	 * @param name JLabel
	 * @param speed JLabel
	 * @param crew JLabel
	 * @param cargo JLabel
	 * @param defense JLabel
	 */
	private void changeShipStats(Ship ship, JLabel name, JLabel speed, JLabel crew, JLabel cargo, JLabel defense) {
		name.setText(ship.getName());
		speed.setText(String.valueOf(ship.getSpeed()));
		crew.setText(String.valueOf(ship.getCrewSize()));
		cargo.setText(String.valueOf(ship.getRemainingItemSpace()));
		defense.setText(String.valueOf(ship.getMaxDefenseCapability()));
		selectedShip = ship;
		readyToConfirm();
	}
	
	/**
	 * Method is called when the user clicks on an Island's button.
	 * Method displays the information about the selected island.
	 * @param island
	 * @param name
	 * @param islandInfo
	 */
	private void changeIslandInfo(Island island, JLabel name, JTextPane islandInfo) {
		name.setText(island.getIslandName());
		islandInfo.setText(island.toString());
		selectedIsland = island;
		readyToConfirm();
	}
	
	/**
	 * Verifies whether the name the user has entered is valid. If it is, error message is made invisible.
	 * If the name isn't valid, error message is made visible. Method calls readyToConfirm so the confirm button
	 * can be appropriately enabled or disabled. 
	 * @param name String The name the player has entered.
	 */
	private void checkNameEntered(String name) {
		
		if (CheckValidInput.nameIsValid(name)) {
			lblNameError.setVisible(false);
		} else {
			lblNameError.setVisible(true);
		}
		playerName = name;
		readyToConfirm();
	}
	
	/**
	 * Checks whether all necessary information to continue has been entered. If it has, confirm
	 * button is enabled. 
	 */
	private void readyToConfirm() {
		if (playerName != null &&CheckValidInput.nameIsValid(playerName) && selectedShip != null && selectedIsland != null) {
			btnConfirm.setEnabled(true);
		} else {
			btnConfirm.setEnabled(false);
		}
	}
}

