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
    private Island storeIsland;
    
    private HashMap<String, HashMap<String, Integer>> sellCatalogue;
    private HashMap<String, HashMap<String, Integer>> buyCatalogue;
    
    
    /* TODO
     * How to sell Upgrades and take advantage of inheritance?
     * just to be creative any mention of currency should be given as "pirate bucks"
     */
   
    
    // leave this in here for informal testing, can delete once we get to more formal testing. 
    public Store() {}
    
    public Store(String name, HashMap<String, HashMap<String, Integer>> sellCatalogue, HashMap<String, HashMap<String, Integer>> buyCatalogue) {
    	this.name = name;
    	this.sellCatalogue = sellCatalogue;
    	this.buyCatalogue = buyCatalogue;
    }
  
    // #################### GENERAL STORE METHODS #####################
    	
    public boolean sellItem(String itemName, Player player) {
    	// sells and item to a player
    	
    	// item is an item from the item ArrayList
    	
    	// TODO game environment needs to handle the errors thrown. 
    	
    	// TODO there is a conundrum here, you need to check that a player has cash first before you attempt to add it 
    	// to cargo hold (which may throw an error), or you could do the reverse. 
    	
    	
    	/*
    	 * if (!itemsToSell.contains(item)) {
    	 * throw new IllegalStateException("BUG this store does not sell this item");
    	}

    	 */
    	// TODO need to check that the item being sold is actaully sold by store
    	// Get price of item in sellCatalogue, and check that player has enough money
    	int itemPrice = sellCatalogue.get(itemName).get("price");
    	if (player.getMoneyBalance() > itemPrice) {
    		throw new InsufficientMoneyException("You do not have enough money to buy this item!");
    	}
    	
    	// Create a NEW item object, based on the catalogue
    	Item itemToSell = new Item(itemName, sellCatalogue.get(itemName).get("spaceTaken"), itemPrice );
    		
        // Player has cash, so attempt to add
    	player.getShip().addItem(itemToSell); // may throw an exception, game environment should handle
    	player.spendMoney(itemToSell.getPlayerBuyPrice());
    	
    	return true;
    }
    
    public boolean buyItem(String itemName, Player player) {
    	// buys an item from a player
    	
    	// item must be an object from itemsToBuy ArrayList
    	
    	/* 1. take item from player
    	 * 2. then add cash to their account, based on the price of the item in store ArrayList
    	 * 3. set sellPlayerSellPrice to bought item
    	 * 4. add bought item to an array
    	 */
    	
    	/*
    	 * // Necessary to throw below exception?
    	if (!itemsToBuy.contains(item)) {
    		throw new IllegalStateException("BUG this store does not buy this item");
    	}
    	 */
    	
    	int itemPrice = buyCatalogue.get(itemName).get("price");
    	
    	Item boughtItem = player.getShip().takeItem(itemName);
    	player.earnMoney(itemPrice);
    	
    	// Set island and price that a item was sold at
    	boughtItem.setPlayerSellPrice(itemPrice);
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
    
    public static String getDisplayString(HashMap<String, HashMap<String, Integer>> catalogue) {
    	
    	String result = "";
    	int i = 1;
    	
    	// used bellow code from online https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
    	for (Map.Entry<String, HashMap<String, Integer>> mapElement : catalogue.entrySet()) {
    		String itemName = (String) mapElement.getKey();
    		int itemPrice = catalogue.get(itemName).get("price");
    		result += String.format("%d. %s for %d Pirate Bucks \n", i, itemName, itemPrice);
    		i += 1;
    	}
    	return result.trim();
    }
    
    public HashMap<String, HashMap<String, Integer>> getSellCatalogue(){
    	return this.sellCatalogue;
    }
    
    public HashMap<String, HashMap<String, Integer>> getBuyCatalogue(){
    	return this.buyCatalogue;
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