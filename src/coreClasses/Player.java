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
	private ArrayList<Item> purchasedItems = new ArrayList<Item>();
	
	private Ship ship;
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
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	public Ship getShip() {
		return ship;
	}
	
	public String getName() {return name;}
	
	public int getMoneyBalance() {return moneyBalance;}
	
	public ArrayList<Item> getPurchasedItems() {return purchasedItems;}
	
	public String moneyBalanceToString() {
		return String.format("You have a balance of: %d pirate bucks", moneyBalance);
	}
	
    public String purchasedItemsToString() {
    	if (purchasedItems.size() == 0) {
    		return "You haven't bought any items yet, you can buy items at any Store! \n";
    	}
    	String result = "All items that have been bought and their details: \n";
    	for (Item item : purchasedItems) {
    		result += String.format("Item %s was bought for %d", item.getName(), item.getPlayerBuyPrice());
    		if (item.getPlayerSellPrice() != -1) {
    			result += String.format(" and was sold for %d at %s. \n", item.getPlayerSellPrice(), item.getStoreIslandSoldAt().getIslandName());
    		}
    		else {
    			result += " and has not yet been sold to a store. \n";
    		}
    	}
    	return result;
    }
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
	public boolean spendMoney(int amountSpent) {
		if (amountSpent <= moneyBalance) {
			moneyBalance -= amountSpent;
			return true;
		}
		return false; // not enough money
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
