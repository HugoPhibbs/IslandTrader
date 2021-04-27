package coreClasses;

import exceptions.*;
import java.util.ArrayList;

/**
 * 
 * @author Jordan Vegar
 * @version 1.1
 */
public class Player {
	
	private String name;
	private int moneyBalance;
	private ArrayList<Item> purchasedItems;
	
	public Player(String name, int startingCash) {
		this.name = name;
		this.moneyBalance = startingCash;
	}
	
	public String getName() {return name;}
	
	public int getMoneyBalance() {return moneyBalance;}
	
	public ArrayList<Item> getPurchasedItems() {return purchasedItems;}
	
	public void addPurchasedItem(Item item) {
		purchasedItems.add(item);
	}
	
	public void spendMoney(int amountSpent) {
		if (amountSpent <= moneyBalance) {
			moneyBalance -= amountSpent;
		}
		else {
			throw new InsufficientMoneyException("Not enough money!");
		}	
	}
	
	public void earnMoney(int amountEarned) {
		moneyBalance += amountEarned;
	}
}
