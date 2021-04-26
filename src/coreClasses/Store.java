package coreClasses;

import java.util.*;

import exceptions.*;

/** Represents a store
 * 
 * @author Hugo Phibbs
 * @version 23/4/2021
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
    
    /** Constructor for Store Class
     * 
     * @param name String name of Store object to be created
     * @param sellCatalogue HashMap<String, HashMap> of Items that the store sells
     * @param buyCatalogue HashMap<String, HashMap> of Items that a store buys
     */
    public Store(String name, HashMap<String, HashMap<String, Integer>> sellCatalogue, HashMap<String, HashMap<String, Integer>> buyCatalogue) {
    	
    	if (!CheckValidInput.nameIsValid(name)) {
    		throw new IllegalArgumentException("Name for store must have no more than 1 consecutive white space and be between 3 and 15 characters in length!");
    	}
    	
    	this.name = name;
    	this.sellCatalogue = sellCatalogue;
    	this.buyCatalogue = buyCatalogue;
    }
  
    // #################### GENERAL STORE METHODS #####################
    
    /** Creates and sells and item to a player 
     * 
     * @param itemName String for the name of Item to be created
     * @param player PLayer object to receive Item
     * @return Boolean, if transaction was successful
     */
    public boolean sellItem(String itemName, Player player) {
    	/* TODO 
    	 * game environment needs to handle the errors thrown. 
    	 */
    	
    	// Get price of item in sellCatalogue, and check that player has enough money
    	
    	if (sellCatalogue.get(itemName) == null) {
    		throw new IllegalStateException("BUG store does not sell this item!");
    	}
    	
    	int itemPrice = sellCatalogue.get(itemName).get("price");
    	if (player.getMoneyBalance() > itemPrice) {
    		throw new InsufficientMoneyException("You do not have enough money to buy this item!");
    	}
    	
    	// Create a NEW item object, based on the catalogue
    	Item itemToSell = new Item(itemName, sellCatalogue.get(itemName).get("spaceTaken"), itemPrice );
    		
        // Player has cash, so attempt to add
    	player.getShip().addItem(itemToSell); // may throw an exception, game environment should handle
    	player.spendMoney(itemToSell.getPlayerBuyPrice());
    	player.addPurchasedItem(itemToSell);
    	
    	return true;
    }
    
    /** Buys an item from a Player
     * 
     * @param itemName String for the name of Item to be bought 
     * @param player Player object that is selling an Item
     * @return Boolean if transaction was successful
     */
    public boolean buyItem(String itemName, Player player) {
    	if (buyCatalogue.get(itemName) == null) {
    		throw new IllegalStateException("BUG store does not buy this item!");
    	}
    	
    	int itemPrice = buyCatalogue.get(itemName).get("price");
    	
    	Item boughtItem = player.getShip().takeItem(itemName);
    	player.earnMoney(itemPrice);
    	
    	// Set island and price that a item was sold at
    	boughtItem.setPlayerSellPrice(itemPrice);
    	boughtItem.setStoreIslandSoldAt(storeIsland);
    	
    	return true;
    }
    
    // ##################### GETTER METHODS ########################
    
    /** Displays items that a store sells or buys
     * 
     * @param itemArrayList ArrayList with Item objects to be displayed
     * @return String representation of itemArrayList
     */
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
    
    /** Displays items that a store sells or buys
     * 
     * @param catalogue HashMap<String, HashMap> representation of the things that a store buys or sells
     * @return String representation of catalogue
     */
    public static String getDisplayString(HashMap<String, HashMap<String, Integer>> catalogue) {
    	
    	String result = "";
    	int i = 1;
    	
    	/*
    	 *  used bellow code from online https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
    	 */
    	for (Map.Entry<String, HashMap<String, Integer>> mapElement : catalogue.entrySet()) {
    		String itemName = (String) mapElement.getKey();
    		int itemPrice = catalogue.get(itemName).get("price");
    		result += String.format("%d. %s for %d Pirate Bucks \n", i, itemName, itemPrice);
    		i += 1;
    	}
    	return result.trim();
    }
    
    /** Gets the sellCatalogue for a store
     * 
     * @return HashMap<String, Hashmap> representation of the things that a store sells
     */
    public HashMap<String, HashMap<String, Integer>> getSellCatalogue(){
    	return this.sellCatalogue;
    }
    
    /** Gets the buyCatalogue for a store
     * 
     * @return HashMap<String, HashMap<String, Integer>> representation of the things that a store buys
     */
    public HashMap<String, HashMap<String, Integer>> getBuyCatalogue(){
    	return this.buyCatalogue;
    }
    
    /** Gets the name of the store
     * 
     * @return String for the name of the store
     */

    public String getName() {
    	return this.name;
    }
    
    /** Gets the description of the store
     * 
     * @return String for the description of the store
     */
    public String getDescription(){
        // returns the name of store, types of items it sells (eg ship upgrades, valuables, food etc)
        // called by visitStore method bellow
    	return "EMPTY";
    }
    
    // ##################### SETTER METHODS ########################
    
    /** Sets the Island that a store belongs to
     * 
     * @param island Island that a store belongs to
     */
    public void setStoreIsland(Island island) {
    	this.storeIsland = island;
    }
}