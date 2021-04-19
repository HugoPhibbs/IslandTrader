package coreClasses;

import java.util.*;

/** Represents a store
 * 
 * @author Hugo Phibbs
 * @version 16/4/2021
 * @since 2/4/2021
 */

public class Store {
    private String name; // something creative
    private ArrayList<Item> itemsToSell= new ArrayList<Item>(); // items to sell to player
    private ArrayList<Item> itemsToBuy = new ArrayList<Item>();  // items that store will buy from player
    private ArrayList<Item> itemsSold = new ArrayList<Item>(); // items sold to player
    private ArrayList<Item> itemsBought = new ArrayList<Item>();
    private Island storeIsland;
    
    
    /* TODO
     * How to sell Upgrades and take advantage of inheritance?
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
    	/* Explanation
    	 * I couldve either checked that a player has the cash or check that the ship has the space
    	 * wouldn't have mattered
    	 */
    	
    	// TODO there is a conundrum here, you need to check that a player has cash first before you attempt to add it 
    	// to cargo hold (which may throw an error), or you could do the reverse. 
    	
    	
    	if (!itemsToSell.contains(item)) {
    		throw new IllegalStateException("BUG this store does not sell this item");
    	}
    	if (player.getMoneyBalance() > item.getPrice()) {
    		throw new IllegalStateException("You do not have enough money to buy this item");
    	}
        // Player has cash, so attempt to add
    	player.getShip().addItem(item); // may throw an exception, game environment should handle
    	player.spendMoney(item.getPrice());
    	
    	// add Item to itemSold
    	itemsSold.add(item);
    	// add items bought at
    	item.setIslandBoughtAt(this.storeIsland);
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
    	
    	if (!itemsToBuy.contains(item)) {
    		throw new IllegalStateException("BUG this store does not buy this item");
    	}
    	
    	Item boughtItem = player.getShip().takeItem(item.getName());
    	player.earnMoney(item.getPrice());
    	boughtItem.setPlayerSellPrice(item.getPrice());
    	
    	itemsBought.add(boughtItem);
    	return true;
    }
    
    // ##################### GETTER METHODS ########################
    public ArrayList<Item> getItemsToBuy() {
    	return this.itemsToBuy;
    }
    
    public ArrayList<Item> getItemsToSell(){
    	return this.itemsToSell;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public ArrayList<Item> getItemsSold(){
    	return this.itemsSold;
    }
    
    public ArrayList<Item> getItemsBought(){
    	return this.itemsBought;
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