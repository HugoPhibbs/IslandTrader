package coreClasses;

import exceptions.*;
import java.util.ArrayList;

/**
 * 
 * @author Jordan Vegar
 * @version 1.1
 */
public class Player {
	
	// The name of the player, entered by the player.
	private String name;
	// The current amount of Pirate Bucks the player has. 
	private int moneyBalance;
	// A list of all items the player has purchased during the game, including those onsold. 
	private ArrayList<Item> purchasedItems;
	
	/**
	 * Constructor method
	 * 
	 * @param name The player's name
	 * @param startingCash The set amount of Pirate Bucks the player has at the start of the game. 
	 */
	public Player(String name, int startingCash) {
		this.name = name;
		this.moneyBalance = startingCash;
	}
	
	public String getName() {return name;}
	
	public int getMoneyBalance() {return moneyBalance;}
	
	public ArrayList<Item> getPurchasedItems() {return purchasedItems;}
	
	/**
	 * Adds an item the player has purchased to purchasedItems.
	 * 
	 * @param item An item the player has purchased.
	 */
	public void addPurchasedItem(Item item) {
		purchasedItems.add(item);
	}
	
	/**
	 * Checks the player has enough Pirate Bucks for a purchase, and if so, reduces
	 * there balance by that amount
	 * 
	 * @param amountSpent The amount the purchase costs.
	 */
	public void spendMoney(int amountSpent) {
		if (amountSpent <= moneyBalance) {
			moneyBalance -= amountSpent;
		}
		else {
			throw new InsufficientMoneyException("Not enough money!");
		}	
	}
	
	/**
	 * Increases the player's balance by the given amount
	 * 
	 * @param amountEarned The amount of Pirate Bucks gained by the player. 
	 */
	public void earnMoney(int amountEarned) {
		moneyBalance += amountEarned;
	}
}
