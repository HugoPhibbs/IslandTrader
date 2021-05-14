package coreClasses;

import java.util.ArrayList;
 
/**
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 14/5/2021
 * @since 2/4/2021
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
	
	/** Setter method for the Ship belonging to a Player
	 * 
	 * @param ship Ship object to be set to belong to a Player
	 */
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	/** Getter method for the Ship object belonging to a Player
	 * 
	 * @return Ship object belonging to a Player
	 */
	public Ship getShip() {
		return ship;
	}
	
	/** Getter method for the name of a Player
	 * 
	 * @return String for the name of a Player
	 */
	public String getName() {return name;}
	
	/** Getter method for the current money balance of a Player
	 * 
	 * @return Integer of the money balance of a player
	 */
	public int getMoneyBalance() {return moneyBalance;}
	
	/** Getter method for the purchased items of a Player
	 * Both ones that have been bought and sold back to a store
	 * 
	 * @return ArrayList<Item> the purchased items of a player
	 */
	public ArrayList<Item> getPurchasedItems() {return purchasedItems;}
	
	/** Method to convert a Players money balance into a String representation
	 * 
	 * @return String representation of a Player's money balance
	 */
	public String moneyBalanceToString() {
		return String.format("You have a balance of: %d pirate bucks", moneyBalance);
	}
	
	/** Method to convert a Player's purchased Items into a String representation
	 * 
	 * @return String representation of a Players purchased Items
	 */
    public String purchasedItemsToString() {
    	if (purchasedItems.size() == 0) {
    		return "You haven't bought any items yet, you can buy items at any Store! \n";
    	}
    	String result = "All items that have been bought and their details: \n";
    	for (Item item : purchasedItems) {
    		result += String.format("Item %s was bought for %d Pirate Bucks", item.getName(), item.getPlayerBuyPrice());
    		if (item.getPlayerSellPrice() != -1) {
    			result += String.format(" and was sold for %d at %s. \n", item.getPlayerSellPrice(), item.getStoreIslandSoldAt().getIslandName());
    		}
    		else if (!item.getName().endsWith("(upgrade)")){
    			result += " and has not yet been sold to a store. \n";
    		}
    		else { //because upgrades cannot be sold back
    			result += "\n";
    		}
    	}
    	return result;
    }
    
    /** Converts purchased items of a Player into array format.
     * returned String[][] array is in tabular format, so it can easily be used by GUI
     * 
     * @return String[][] nested Array representation of the purchased items of a Player. 
     */
    public String[][] purchasedItemsToArray(){
    	
    	// used by GUI
    	
    	// componenents in array have (<name>, <buyPrice>, <sellPrice>)
    	ArrayList<ArrayList<String>> purchasedItemsArrayList = new ArrayList<ArrayList<String>>();
    	
    	if (purchasedItems.size() == 0) {
    		// return null if purchased items is empty, gui handles 
    		return null;
    	}
    	else {
    		for (int i=0; i < purchasedItems.size(); i++) {
    			Item currItem = purchasedItems.get(i);
    			
    			purchasedItemsArrayList.get(i).add(currItem.getName());
    			purchasedItemsArrayList.get(i).add(Integer.toString(currItem.getPlayerBuyPrice()));
    			
    			if (currItem.getPlayerSellPrice() != -1) {
    				purchasedItemsArrayList.get(i).add(Integer.toString(currItem.getPlayerSellPrice()));
    			}
    			else {
    				purchasedItemsArrayList.get(i).add("N/A");
    			}
    		}
    	}
    	return (String [][]) purchasedItemsArrayList.toArray();
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
	 * @return Boolean value if money was spent or not, ie player had enough cash to pay
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
