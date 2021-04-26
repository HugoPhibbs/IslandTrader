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
	private Ship ship;
	private int moneyBalance;
	private int daysRemaining;
	private Island currentIsland;
	private ArrayList<Item> purchasedItems;
	
	public Player(String name, int startingCash, int days, Island startingIsland) {
		this.name = name;
		this.moneyBalance = startingCash;
		this.daysRemaining = days;
		this.currentIsland = startingIsland;
	}
	
	public String getName() {return name;}
	
	public int getMoneyBalance() {return moneyBalance;}
	
	public Ship getShip() {return ship;}
	
	public int getDaysRemaining() {return daysRemaining;}	
	
	public Island getCurrentIsland() {return currentIsland;}
	
	public ArrayList<Item> getPurchasedItems() {return purchasedItems;}
	
	public void addPurchasedItem(Item item) {
		purchasedItems.add(item);
	}
	
	public void reduceDaysRemaining(int daysPassed) {
		daysRemaining -= daysPassed;
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
	
	public void setCurrentIsland(Island newIsland) {
		this.currentIsland = newIsland;
	}
}
