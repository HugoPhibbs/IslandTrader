package uiClasses.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import coreClasses.GameEnvironment;
import coreClasses.Route;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MakePaymentsDialog extends JDialog {
		
	private GameEnvironment game;
	private Route route;
	private ChooseRouteScreen routeScreen;
	/**
	 * Create the dialog.
	 */
	public MakePaymentsDialog(GameEnvironment game, Route route, ChooseRouteScreen routeScreen) {
		setBounds(100, 100, 450, 229);
		getContentPane().setLayout(null);
		
		int costWages = game.getShip().routeWageCost(route);
		int costRepairs = game.getShip().repairCost();
	
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 158, 440, 35);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton btnMakePayment = new JButton("Make Payment of $" + String.valueOf(costWages + costRepairs));
				btnMakePayment.addActionListener(e -> routeScreen.confirmSelection());
				buttonPane.add(btnMakePayment);
				getRootPane().setDefaultButton(btnMakePayment);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(e -> dispose());
				buttonPane.add(btnCancel);
			}
		}
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		createInstructionLabel();
		createWageslabels(costWages);
		if (costRepairs != 0) {
			createRepairLabels(costRepairs);
		}
	}
	
	private void createInstructionLabel() {
		JLabel lblInstruction = new JLabel("Before you set sail, you need to:");
		lblInstruction.setFont(new Font("Dialog", Font.BOLD, 15));
		lblInstruction.setBounds(85, 10, 267, 18);
		getContentPane().add(lblInstruction);
	}
	
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
