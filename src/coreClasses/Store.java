package coreClasses;

import java.lang.reflect.Array; 

import java.util.*;

import exceptions.*;

/** Represents a store
 * 
 * @author Hugo Phibbs
 * @version 6/5/2021
 * @since 2/4/2021
 */

public class Store {
    private String name; 
    private String specialty; // just to be more creative
    private Island storeIsland;
    private HashMap<String, HashMap<String, Integer>> sellCatalogue;
    private HashMap<String, HashMap<String, Integer>> buyCatalogue;
    
    public Store() {}
    /** Constructor for Store Class
     * 
     * @param name String name of Store object to be created
     * @param sellCatalogue HashMap<String, HashMap> of Items that the store sells
     * @param buyCatalogue HashMap<String, HashMap> of Items that a store buys
     */
    public Store(String name, String specialty, HashMap<String, HashMap<String, Integer>> sellCatalogue, HashMap<String, HashMap<String, Integer>> buyCatalogue) {
    	
    	if (!CheckValidInput.nameIsValid(name)) {
    		throw new IllegalArgumentException("Name for store must have no more than 1 consecutive white space and be between 3 and 15 characters in length!");
    	}
    	this.name = name;
    	this.specialty = specialty;
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
    public Item sellItem(String itemName, Player player) {
    	
    	// Get price of item in sellCatalogue, and check that player has enough money
    	
    	
    	// returns the item that was sold either way, this is to make handling alternative flow
    	// ALOT easier!
    	if (sellCatalogue.get(itemName) == null) {
    		throw new IllegalStateException("BUG store does not sell this item!");
    	}
    	
    	Item itemToSell = new Item(itemName, sellCatalogue.get(itemName).get("spaceTaken"), sellCatalogue.get(itemName).get("price") );
    	// TODO need to discard object if it doesnt work? or will java do it automatically??
    	if (sellItemChecker(player, itemToSell) != null){
    		return itemToSell;
    	}
    	itemToSell.setWithPlayer(true);
    	player.spendMoney(itemToSell.getPlayerBuyPrice());
    	player.getShip().addItem(itemToSell);
    	player.addPurchasedItem(itemToSell);
    	
    	return itemToSell;
    }
    
    /** Checks if selling an item from a STORE TO A PLAYER is permissible
     *  if it is, returns null, otherwise returns String for reason why not
     * 
     * @param player
     * @return
     */
    public static String sellItemChecker(Player player, Item itemToSell) {
    	if (player.getMoneyBalance() < itemToSell.getPlayerBuyPrice()) {
    		return "Player does not have enough money to buy this item!";
    	}
    	else if (player.getShip().getRemainingCargoCapacity() < itemToSell.getSpaceTaken()) {
    		return "Player does not have enough space to store this item!";
    	}
    	return null;
    }
    
    /** Buys an item from a Player to a store
     * 
     * @param itemName String for the name of Item to be bought 
     * @param player Player object that is selling an Item
     * @return Boolean if transaction was successful
     */
    public Item buyItem(String itemName, Player player) {
    	if (buyCatalogue.get(itemName) == null) {
    		throw new IllegalStateException("BUG store does not buy this item!");
    	}
    	
    	Item itemToBuy = player.getShip().takeItem(itemName);
    	
    	if (buyItemChecker(player, itemToBuy) != null) {
    		// not successful, return item, and dont remove it from players possession
    		return itemToBuy;
    	}
        // Set island that item was sold at and give player money
    	int itemPrice = buyCatalogue.get(itemName).get("price");
    	
    	// Set item to not be with player, opposite process to what is happening above.
    	itemToBuy.setWithPlayer(false);
    	player.earnMoney(itemPrice);
    	itemToBuy.setPlayerSellPrice(itemPrice);
    	itemToBuy.setStoreIslandSoldAt(storeIsland);

    	return itemToBuy;
    }
    
    /** Checks if buying an item from a PLAYER TO A STORE is permissible
     *  if it is, returns null, otherwise returns String for reason why not
     * 
     * @param player
     * @return
     */
    public static String buyItemChecker(Player player, Item itemToBuy) {
    	if (itemToBuy == null) {
    		return "Player does not have this item in possession!";
    	}
    	return null;
    }
    
    /** Converts a sell or buy catalogue into an an Array List that can be easily displayed
     * 
     * @param catalogue Catalogue to be parsed into a displayArrayList
     * @return ArrayList for what you can buy or sell from a store
     */
    public static ArrayList<String> catalogueToArrayList(HashMap<String, HashMap<String, Integer>> catalogue){
    	ArrayList<String> displayArrayList = new ArrayList<String>();
    	/*
    	 *  used bellow code from online https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
    	 */
    	for (Map.Entry<String, HashMap<String, Integer>> mapElement : catalogue.entrySet()) {
    		String itemName = (String) mapElement.getKey();
    		int itemPrice = catalogue.get(itemName).get("price");
    		int itemSpaceTaken = catalogue.get(itemName).get("spaceTaken");
    		displayArrayList.add(String.format("%s for %d Pirate Bucks, taking up %d space", itemName, itemPrice, itemSpaceTaken));
        }
    	return displayArrayList;
    }
    
    /**
     * Creates and returns a string representation of the given catalogue, useful for giving a quick overview
     * of what a store buys or sells. Is called by island.fullInfo.
     * 
     * @param catalogue The catalogue (either buy or sell) to be made into a string. 
     * @param buyOrSell Whether the catalogue is for items to be bought or sold. Should be "buy" or "sell".
     * @return A string representation of the catalogue.
     */
    public String catalogueToString(HashMap<String, HashMap<String, Integer>> catalogue, String buyOrSell) {
    	String catalogueString = "The store on this island " + buyOrSell + "s:\n";
    	try {
    		for (String itemString: catalogueToArrayList(catalogue)) {
        		catalogueString += itemString;
    	    }
    		return catalogueString;
    	}
    	catch (IllegalStateException ise) {
    		return String.format("This Store doesnt have a %s catalogue yet, please add one!", buyOrSell);
    	}
    }
    // ##################### GETTER METHODS ########################
    
    /** Gets the name of the store
     * 
     * @return String for the name of the store
     */
    public String getName() {return name;}
    
    /** Getter method for the specialty of a store
     * 
     * @return String for the specialty of a store
     */
    public String getSpecialty() {return specialty;}
    
    /** Getter method for the Island that a store belongs to
     * 
     * @return Island that store object belongs to
     */
    public Island getStoreIsland() {return storeIsland;}
   
    /** Gets the sellCatalogue for a store
     * 
     * @return HashMap<String, Hashmap> representation of the things that a store sells
     */
    public HashMap<String, HashMap<String, Integer>> getSellCatalogue() {return sellCatalogue;}
    
    /** Gets the buyCatalogue for a store
     * 
     * @return HashMap<String, HashMap<String, Integer>> representation of the things that a store buys
     */
    public HashMap<String, HashMap<String, Integer>> getBuyCatalogue() {return buyCatalogue;}
    
    /** Gets the description of the store
     * 
     * @return String for the description of the store
     */
    public String getDescription(){
    	// specific things that a store sells will be given on request!
    	return String.format("Store %s, is located on %s and specialised in %s!", name, storeIsland.getIslandName(), specialty);
    }
    
    /** Gets the visit options for a store, used by ui
     * 
     * @return ArrayList for the options for visiting a store
     */
    public static String [] getVisitOptions() {
    	String[] optionsArray = 
    		    {"View and buy items that the store sells." 
				,"View and sell Items that the store buys."
				,"View previously bought items."
				,"View the amount of money that you have."
				,"Exit store."};
    	return optionsArray;
    }
    
    /** Gets the name of a chosen item to be sold or bought
     * 
     * @param displayArrayList ArrayList that details items that can be sold or bought
     * @param chosenItemNum ItemNum action int chosen by cmd line ui of chosen item
     * @return
     */
    public static String getChosenItemName(ArrayList<String> displayArrayList, int chosenItemNum) {
    	// gets the name of a chosen, as in implemented code from game environment, just nice to have here
    	// display ArrayList must have form {"itemName for ..."}, key thing being itemName is in first position
    	
    	return (String) Array.get(displayArrayList.get(chosenItemNum-1).split(" "), 0);
    }
    
    // ##################### SETTER METHODS ########################
    
    /** Sets the Island that a store belongs to
     * 
     * @param island Island that a store belongs to
     */
    public void setStoreIsland(Island storeIsland) {
    	this.storeIsland = storeIsland;
    }
    
}