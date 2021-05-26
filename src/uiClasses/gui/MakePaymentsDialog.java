package uiClasses.gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import coreClasses.GameEnvironment;
import coreClasses.Route;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MakePaymentsDialog extends JDialog {
	
	/** long Generated serial version UID. Class has unique values so not used. */
	private static final long serialVersionUID = 1169380786453432984L;
	
	/** ChooseRouteScreen the instance of ChooseRouteScreen that created this dialog box.*/
	private ChooseRouteScreen routeScreen;
	
	/** int The $ amount it will cost the player to pay their crew wages before sailing their chosen route.*/
	private int costWages;
	
	/** int The amount it will cost the player to repair their ship, which is required before traveling.*/
	private int costRepairs;
	
	/**
	 * Creates the JDialog box and calls the methods required to add components.
	 */
	public MakePaymentsDialog(GameEnvironment game, Route route, ChooseRouteScreen routeScreen) {
		setBounds(100, 100, 450, 229);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 222, 173));
		
		this.routeScreen= routeScreen;
		
		costWages = game.getShip().routeWageCost(route);
		costRepairs = game.getShip().repairCost();
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		createButtons();
		createInstructionLabel();
		createWageslabels(costWages);
		if (costRepairs != 0) {
			createRepairLabels(costRepairs);
		}
	}
	
	/** Creates the JPanel buttonPane and adds the JButtons for making the payment and canceling.*/
	private void createButtons() {
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(255, 222, 173));
		buttonPane.setBounds(0, 158, 440, 35);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);
		{
			JButton btnMakePayment = new JButton("Make Payment of $" + String.valueOf(costWages + costRepairs));
			btnMakePayment.setBackground(new Color(153, 204, 255));
			btnMakePayment .addActionListener(e -> makePayments());
			buttonPane.add(btnMakePayment);
			getRootPane().setDefaultButton(btnMakePayment);
		}
		{
			JButton btnCancel = new JButton("Cancel");
			btnCancel.setBackground(new Color(153, 204, 255));
			btnCancel.addActionListener(e -> dispose());
			buttonPane.add(btnCancel);
		}
	}
	
	
	/** Method called when btnMakePayments is clicked.
	 * Triggers the wage and repair payments to be made in SailingScreen, once it has been created in ChooseRouteScreen
	 * and closes the dialog box.
	 */
	private void makePayments() {
		routeScreen.confirmSelection();
		this.dispose();
	}
	
	/** Creates the JLabel which gives the player instructions.*/
	private void createInstructionLabel() {
		JLabel lblInstruction = new JLabel("Before you set sail, you need to:");
		lblInstruction.setFont(new Font("Dialog", Font.BOLD, 15));
		lblInstruction.setBounds(85, 10, 267, 18);
		getContentPane().add(lblInstruction);
	}
	
	/** Creates the JLabels that display the cost of paying your crew wages.*/
	private void createWageslabels(int cost) {
		JLabel lblPayWages = new JLabel("- Pay your crew wages");
		lblPayWages.setHorizontalAlignment(SwingConstants.CENTER);
		lblPayWages.setFont(new Font("Dialog", Font.BOLD, 15));
		lblPayWages.setBounds(118, 40, 187, 18);
		getContentPane().add(lblPayWages);
		
		JLabel lblWageAmount = new JLabel("$" + String.valueOf(cost));
		lblWageAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblWageAmount.setFont(new Font("Dialog", Font.BOLD, 15));
		lblWageAmount.setBounds(188, 70, 70, 15);
		getContentPane().add(lblWageAmount);
	}
	
	/** Creates the JLabels that display the cost of repairing your ship.*/
	private void createRepairLabels(int cost) {
		JLabel lblRepairShip = new JLabel("- Repair your Ship");
		lblRepairShip.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepairShip.setFont(new Font("Dialog", Font.BOLD, 15));
		lblRepairShip.setBounds(118, 97, 187, 18);
		getContentPane().add(lblRepairShip);
		
		JLabel lblRepairAmount = new JLabel("$" + String.valueOf(cost));
		lblRepairAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepairAmount.setFont(new Font("Dialog", Font.BOLD, 15));
		lblRepairAmount.setBounds(188, 127, 70, 15);
		getContentPane().add(lblRepairAmount);
	}
}
