package coreClasses;

import java.util.ArrayList;
 
/** Represents an Player object
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 17/5/2021
 * @since 2/4/2021
 */
public class Player {
	// Class variables //
	/** The name of the player, entered by the player. */
	private String name;
	
	/** The current amount of Pirate Bucks the player has.  */
	private int moneyBalance;
	
	/** A list of all items the player has purchased during the game, including those unsold. */
	private ArrayList<Item> purchasedItems = new ArrayList<Item>();
	
	/** Ship object belonging to a player in game */
	private Ship ship;
	
	
	/** Constructor method for a player object
	 * 
	 * @param name String for the player's name
	 * @param startingCash Integer for the set amount of Pirate Bucks the player has at the start of the game. 
	 */
	public Player(String name, int startingCash) {
		this.name = name;
		this.moneyBalance = startingCash;
	}
	
	/** Method to convert a Player's purchased Items into a String representation
	 * Used for Command Line Only
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
    	if (purchasedItems.size() == 0) {
    		// return null if purchased items is empty, gui handles 
    		return null;
    	}
    	else {
    		ArrayList<String[]> purchasedItemsArrayList = new ArrayList<String[]>();
    		for (int i=0; i < purchasedItems.size(); i++) {
    			Item currItem = purchasedItems.get(i);
    			
    			String [] infoArray = new String[] {
    					currItem.getName(), 
    					Integer.toString(currItem.getPlayerBuyPrice()), 
    					"N/A", 
    					"N/A"
    			};
    			if (currItem.getPlayerSellPrice() != -1) {
    				// If an item has been sold back to a store, include information on this
    				infoArray[2] = Integer.toString(currItem.getPlayerSellPrice());
    				infoArray[3] = currItem.getStoreIslandSoldAt().getIslandName();
    			}
    			
    			purchasedItemsArrayList.add(infoArray);
    		}
    		return purchasedItemsArrayList.toArray(new String[purchasedItemsArrayList.size()][4]);
    	}
    }
    
	/** Adds an item the player has purchased to purchasedItems.
	 * 
	 * @param item Item that the player has purchased.
	 */
	public void addPurchasedItem(Item item) {
		purchasedItems.add(item);
	}
	
	/** Checks the player has enough Pirate Bucks for a purchase, and if so, reduces
	 * there balance by that amount
	 * 
	 * @param amountSpent Integer for the amount the purchase costs.
	 * @return Boolean value if money was spent or not, ie player had enough cash to pay
	 */
	public boolean spendMoney(int amountSpent) {
		if (amountSpent <= moneyBalance && amountSpent >= 0) {
			moneyBalance -= amountSpent;
			return true;
		}
		return false; // not enough money
	}
	
	/** Method to convert a Players money balance into a String representation
	 * 
	 * @return String representation of a Player's money balance
	 */
	public String moneyBalanceToString() {
		return String.format("You have a balance of: %d pirate bucks", moneyBalance);
	}

	/** Increases the player's balance by the given amount
	 * 
	 * @param amountEarned Integer for the amount of Pirate Bucks gained by the player. 
	 */
	public void earnMoney(int amountEarned) {moneyBalance += amountEarned;}
	
	/** Setter method for the Ship belonging to a Player
	 * 
	 * @param ship Ship object to be set to belong to a Player
	 */
	public void setShip(Ship ship) {this.ship = ship;}
	
	/** Getter method for the Ship object belonging to a Player
	 * 
	 * @return Ship object belonging to a Player
	 */
	public Ship getShip() {return ship;}
	
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
}
