package coreClasses;

import java.util.*;

import exceptions.*;

/** Represents a store
 * 
 * @author Hugo Phibbs
 * @version 20/4/2021
 * @since 2/4/2021
 */

public class Store {
    private String name; // something creative
    private ArrayList<Item> itemsToSell= new ArrayList<Item>(); // items to sell to player
    private ArrayList<Item> itemsToBuy = new ArrayList<Item>();  // items that store will buy from player
    private Island storeIsland;
    
    
    /* TODO
     * How to sell Upgrades and take advantage of inheritance?
     * just to be creative any mention of currency should be given as "pirate bucks"
     */
   
    
    // leave this in here for informal testing, can delete once we get to more formal testing. 
    public Store() {}
    
    public Store(String name, ArrayList<Item> itemsToSell, ArrayList<Item> itemsToBuy) {
    	this.name = name;
    	this.itemsToSell = itemsToSell;
    	this.itemsToBuy = itemsToBuy;
    }
  
    // #################### GENERAL STORE METHODS #####################
    	
    public boolean sellItem(Item item, Player player) {
    	// sells and item to a player
    	
    	// item is an item from the item ArrayList
    	
    	// TODO game environment needs to handle the errors thrown. 
    	
    	// TODO there is a conundrum here, you need to check that a player has cash first before you attempt to add it 
    	// to cargo hold (which may throw an error), or you could do the reverse. 
    	
    	
    	if (!itemsToSell.contains(item)) {
    		throw new IllegalStateException("BUG this store does not sell this item");
    	}
    	if (player.getMoneyBalance() > item.getPlayerBuyPrice()) {
    		throw new InsufficientMoneyException("You do not have enough money to buy this item");
    	}
        // Player has cash, so attempt to add
    	player.getShip().addItem(item); // may throw an exception, game environment should handle
    	player.spendMoney(item.getPlayerBuyPrice());
    	return true;
    }
    
    public boolean buyItem(Item item, Player player) {
    	// buys an item from a player
    	
    	// item must be an object from itemsToBuy ArrayList
    	
    	/* 1. take item from player
    	 * 2. then add cash to their account, based on the price of the item in store ArrayList
    	 * 3. set sellPlayerSellPrice to bought item
    	 * 4. add bought item to an array
    	 */
    	
    	// Necessary to throw below exception?
    	if (!itemsToBuy.contains(item)) {
    		throw new IllegalStateException("BUG this store does not buy this item");
    	}
    	
    	Item boughtItem = player.getShip().takeItem(item.getName());
    	player.earnMoney(item.getPlayerBuyPrice());
    	boughtItem.setPlayerSellPrice(item.getPlayerBuyPrice());
    	
    	// Set island that a item was sold at
    	boughtItem.setStoreIslandSoldAt(storeIsland);
    	
    	return true;
    }
    
    // ##################### GETTER METHODS ########################
    public static String getDisplayString(ArrayList<Item> itemArrayList) {
    	/* Returns a string representation of everything in itemArrayList to be sold 
    	 * or bought by a store
    	 */
    	String result = "";
    	
    	for (int i = 0; i < itemArrayList.size(); i++) {
    		Item currItem = itemArrayList.get(i);
    		result += String.format("%d. %s for %d Pirate Bucks \n", i+1, currItem.getName(), currItem.getPlayerBuyPrice());
    	}
    	
    	// return result string without the trailing white space
    	return result.trim();
    }
    
    public ArrayList<Item> getItemsToBuy() {
    	return this.itemsToBuy;
    }
    
    public ArrayList<Item> getItemsToSell(){
    	return this.itemsToSell;
    }
    
    public String getName() {
    	return this.name;
    }
  
    public String getDescription(){
        // returns the name of store, types of items it sells (eg ship upgrades, valuables, food etc)
        // called by visitStore method bellow
    	return "EMPTY";
    }
    
    // ##################### SETTER METHODS ########################
    
    public void setStoreIsland(Island island) {
    	this.storeIsland = island;
    }
}