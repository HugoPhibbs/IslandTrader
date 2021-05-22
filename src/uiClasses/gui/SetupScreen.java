package uiClasses.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import coreClasses.*;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JList;

public class SetupScreen extends Screen {

	/** The instance of Ship the player has selected.*/
	public Ship selectedShip;
	/** The instance of Island the player has selected to start at.*/
	public Island selectedIsland;
	/** JLabel that displays an error message when the name entered by the
	 * player is not valid.
	 */
	private JLabel lblNameError;
	private JSlider sliderDays;
	private JLabel lblDisplayDays;
	private JButton btnConfirm;
	/**
	 * Create the application.
	 */
	public SetupScreen(GameEnvironment game) {
		super("Setup Game", game, null);
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
		Player player = new Player("Test Name", sliderDays.getValue()); //TODO: Replace name once fix bug
		game.onSetupFinished(player, selectedShip, sliderDays.getValue(), selectedIsland);
	}
		
	public void createMainLabels() {
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
		
		lblDisplayDays = new JLabel("30");
		lblDisplayDays.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDisplayDays.setFont(new Font("Dialog", Font.BOLD, 16));
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
		
		lblNameError = new JLabel("Must be 3-15 characters, and have not special characters");
		lblNameError.setForeground(Color.RED);
		lblNameError.setFont(new Font("Dialog", Font.BOLD, 11));
		lblNameError.setBounds(682, 77, 370, 14);
		frame.getContentPane().add(lblNameError);
	}
	
	public void createTextField() {
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
	
	private void createConfirmButton() {
		btnConfirm = new JButton("Confirm Choices");
		btnConfirm.addActionListener(e -> setupComplete());
		btnConfirm.setBounds(910, 739, 178, 25);
		btnConfirm.setEnabled(false);
		frame.getContentPane().add(btnConfirm);
	}

	private void createDaysSlider() {
		sliderDays = new JSlider();
		sliderDays.setPaintLabels(true);
		sliderDays.setMinorTickSpacing(1);
		sliderDays.setMinimum(20);
		sliderDays.setMaximum(50);
		sliderDays.setBounds(693, 103, 299, 19);
		sliderDays.addChangeListener(e -> lblDisplayDays.setText(String.valueOf(sliderDays.getValue())));
		frame.getContentPane().add(sliderDays);
	}
	
	public void createPickShipComponents() {
		
		JPanel panelPickShip = new JPanel();
		panelPickShip.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPickShip.setBounds(12, 184, 1065, 250);
		frame.getContentPane().add(panelPickShip);
		panelPickShip.setLayout(null);
		
		JLabel lblSelectedShip = new JLabel("Select a ship!");
		lblSelectedShip.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedShip.setBounds(869, 12, 196, 40);
		panelPickShip.add(lblSelectedShip);
		
		JLabel lblSpeed = new JLabel("Speed:");
		lblSpeed.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSpeed.setBounds(869, 64, 56, 17);
		panelPickShip.add(lblSpeed);
		
		JLabel lblCrewSize = new JLabel("Crew Size:");
		lblCrewSize.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCrewSize.setBounds(869, 107, 86, 17);
		panelPickShip.add(lblCrewSize);
		
		JLabel lblCargoCapacity = new JLabel("Cargo capacity:");
		lblCargoCapacity.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCargoCapacity.setBounds(869, 152, 123, 17);
		panelPickShip.add(lblCargoCapacity);
		
		JLabel lblMaxDefense = new JLabel("Max Defense:");
		lblMaxDefense.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMaxDefense.setBounds(869, 198, 86, 17);
		panelPickShip.add(lblMaxDefense);

		JLabel lblSpeedVal = new JLabel("-");
		lblSpeedVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSpeedVal.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSpeedVal.setBounds(1006, 64, 34, 17);
		panelPickShip.add(lblSpeedVal);
		
		JLabel lblCrewVal = new JLabel("-");
		lblCrewVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCrewVal.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCrewVal.setBounds(1006, 108, 34, 17);
		panelPickShip.add(lblCrewVal);
		
		JLabel lblCargoVal = new JLabel("-");
		lblCargoVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCargoVal.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCargoVal.setBounds(1006, 153, 34, 17);
		panelPickShip.add(lblCargoVal);
		
		JLabel lblDefenseVal = new JLabel("-");
		lblDefenseVal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDefenseVal.setFont(new Font("Dialog", Font.BOLD, 14));
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
	
	private void changeShipStats(Ship ship, JLabel name, JLabel speed, JLabel crew, JLabel cargo, JLabel defense) {
		name.setText(ship.getName());
		speed.setText(String.valueOf(ship.getSpeed()));
		crew.setText(String.valueOf(ship.getCrewSize()));
		cargo.setText(String.valueOf(ship.getRemainingItemSpace()));
		defense.setText(String.valueOf(ship.getMaxDefenseCapability()));
		selectedShip = ship;
	}
	
	public void createPickIslandComponents() {
		
		JPanel panelPickIsland = new JPanel();
		panelPickIsland.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPickIsland.setBounds(12, 477, 1065, 250);
		frame.getContentPane().add(panelPickIsland);
		panelPickIsland.setLayout(null);
		
		JLabel lblSelectedIsland = new JLabel("Select an island!");
		lblSelectedIsland.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblSelectedIsland.setBounds(869, 12, 196, 40);
		panelPickIsland.add(lblSelectedIsland);
		
		JTextPane txtpnThisIslandInfo = new JTextPane();
		txtpnThisIslandInfo.setText("This island sells tomatos and buys gold. It has routes ot all other isalnds.");
		txtpnThisIslandInfo.setBounds(869, 76, 184, 135);
		panelPickIsland.add(txtpnThisIslandInfo);
		
		JLabel lblInfo = new JLabel("Info:");
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblInfo.setBounds(869, 52, 70, 15);
		panelPickIsland.add(lblInfo);

		JButton btnIsland1 = new JButton("New button");
		btnIsland1.addActionListener(e -> changeIslandInfo(game.getIslandArray()[0], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland1.setBounds(12, 12, 156, 226);
		panelPickIsland.add(btnIsland1);
		
		JButton btnIsland2 = new JButton("New button");
		btnIsland2.addActionListener(e -> changeIslandInfo(game.getIslandArray()[1], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland2.setBounds(180, 12, 156, 226);
		panelPickIsland.add(btnIsland2);
		
		JButton btnIsland3 = new JButton("New button");
		btnIsland3.addActionListener(e -> changeIslandInfo(game.getIslandArray()[2], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland3.setBounds(348, 12, 156, 226);
		panelPickIsland.add(btnIsland3);
		
		JButton btnIsland4 = new JButton("New button");
		btnIsland4.addActionListener(e -> changeIslandInfo(game.getIslandArray()[3], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland4.setBounds(516, 12, 156, 226);
		panelPickIsland.add(btnIsland4);
		
		JButton btnIsland5 = new JButton("New button");
		btnIsland5.addActionListener(e -> changeIslandInfo(game.getIslandArray()[4], lblSelectedIsland, txtpnThisIslandInfo));
		btnIsland5.setBounds(684, 12, 156, 226);
		panelPickIsland.add(btnIsland5);
	}
	
	private void changeIslandInfo(Island island, JLabel name, JTextPane islandInfo) {
		name.setText(island.getIslandName());
		islandInfo.setText(island.toString());
		selectedIsland = island;
	}

	private void checkNameEntered(String name) {
		
		if (CheckValidInput.nameIsValid(name)) {
			lblNameError.setVisible(false);
		} else {
			lblNameError.setVisible(false);
		}
	}
	
	/**
	 * Checks whether all necessary information to continue has been entered. If it has, confirm
	 * button is enabled. 
	 */
	private void readyToConfirm() {
		if (!lblNameError.isVisible() && selectedShip != null && selectedIsland != null) {
			btnConfirm.setEnabled(true);
		} else {
			btnConfirm.setEnabled(false);
		}
	}
}

