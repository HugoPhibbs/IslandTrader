package coreClasses;

import java.lang.reflect.Array;  

import java.util.*;

/** Represents a Store object
 * 
 * @author Hugo Phibbs
 * @version 8/5/2021
 * @since 2/4/2021
 */

public class Store {
    private String name; 
    private String specialty; // just to be more creative
    private Island storeIsland;
    private HashMap<String, HashMap<String, Integer>> sellCatalogue;
    private HashMap<String, HashMap<String, Integer>> buyCatalogue;
    
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
  
    // #################### METHODS FOR SELLING AN ITEM TO A PLAYER #####################
    
    /** Method to check if a player wants to buy an item. If buying an item puts them in a possition where they need
     *  to sell items in order to travel to another island, then it returns a string. 
     *  otherwise returns null if they dont need to be asked. 
     *  used by Ui before buying an item
     * @param gameEnvironment
     * @param itemName
     * @param player
     * @return
     */
	public String checkPlayerWantsToBuy(GameEnvironment gameEnvironment, String itemName) {
		if (sellCatalogue.get(itemName).get("price") > gameEnvironment.getMinMoneyToTravel()) {
			return "If you buy this item you will need to sell some of your items if you want to travel to another island. \n"
					+ "Are you sure you want to buy this item?";
		}
		return null;
	}
	
	public String sellItemsToPlayerHelper(GameEnvironment gameEnvironment, String itemStoreToSellName, int numItemsRequested) {
        String result = "";
        
        int numItemsBought = 0;
        int totalCost = 0;
		for (int i = 0 ; i < numItemsRequested; i++) {
			Item itemToSell = sellItemToPlayer(gameEnvironment, itemStoreToSellName);
			
			// handle result of selling
			if (!itemToSell.getWithPlayer()) {
				// Since item didnt come in possession with player, find reason why not
			
				
				// check if item wasnt sold because of an error to do with it being an upgrade
				if (itemToSell.getName().endsWith("(upgrade)") && !Store.canSellUpgradeToPlayer(gameEnvironment.getPlayer()).equals("Can sell")){
					result += Store.canSellUpgradeToPlayer(gameEnvironment.getPlayer()) + '\n'; // return reason why cant sell upgrade.
				}
				
				// otherwise find reason inherent in being an item
				result += canSellItemToPlayer(gameEnvironment, itemToSell) + '\n';
				break;
			}
			else {
				// If item was found, print transaction statement
				numItemsBought += 1;
				totalCost += itemToSell.getPlayerBuyPrice();
		    }
	    }
		
		result += String.format("%d out of requested %d %s bought \nTotal cost of transaction: %d Pirate Bucks \n", numItemsBought, numItemsRequested, itemStoreToSellName, totalCost);
	
		if (itemStoreToSellName.endsWith("(upgrade)")){
			result += getUpgradeSellReciept(gameEnvironment.getPlayer());
	    }
		return result;
	}
	
	
	private String getUpgradeSellReciept(Player player) {
		if (player.getShip().getDefenseCapability() == player.getShip().getMaxDefenseCapability()) {
			return String.format("Your defense capability is now maxed at %d! \n", player.getShip().getMaxDefenseCapability());
		}
		else {
			return String.format("Your defense capability is now %d! \n", player.getShip().getDefenseCapability());
		}
	}
	
    /** Creates and sells and item to a player 
     * 
     * @param itemName String for the name of Item to be created
     * @param player PLayer object to receive Item
     * @return Boolean, if transaction was successful
     */
    public Item sellItemToPlayer(GameEnvironment gameEnvironment, String itemName) {
    	// returns the item that was sold either way, this is to make handling alternative flow
    	// ALOT easier!
    	if (sellCatalogue.get(itemName) == null) {
    		throw new IllegalStateException("BUG store does not sell this item!");
    	}
    	
    	// check whether itemName is an upgrade, then call a helper method to handle. 
    	// TODO need to discard object if it doesnt work? or will java do it automatically??
    	
    	
    	Item itemToSell = new Item(itemName, sellCatalogue.get(itemName).get("spaceTaken"), sellCatalogue.get(itemName).get("price"));
    	
    	if (!canSellItemToPlayer(gameEnvironment, itemToSell).equals("Can sell")){
    		return itemToSell; // handled by ui
    	}
    	
    	if (itemName.endsWith("(upgrade)")) {
    		// cast down to a shipUpgrade, using inheritance.
    		ShipUpgrade upgradeToSell = new ShipUpgrade(itemToSell.getName(), itemToSell.getSpaceTaken(), 
    				itemToSell.getPlayerBuyPrice(), 
    				sellCatalogue.get(itemToSell.getName()).get("defenseBoost")); 
    		
    		// set defenseCapability of new upgradeToSellObject
    		//upgradeToSell.setDefenseBoost(sellCatalogue.get(upgradeToSell.getName()).get("defenseBoost"));
    		if (!canSellUpgradeToPlayer(gameEnvironment.getPlayer()).equals("Can sell")) {
    			return (Item) upgradeToSell;
    		}
    		gameEnvironment.getPlayer().getShip().addUpgrade(upgradeToSell);
    	}
    	else { // regular item
    		gameEnvironment.getPlayer().getShip().addItem(itemToSell);
    	}
    	
    	// set with player, used by ui to check if transaction was completed
    	itemToSell.setWithPlayer(true);
    	gameEnvironment.getPlayer().spendMoney(itemToSell.getPlayerBuyPrice());
    	gameEnvironment.getPlayer().addPurchasedItem(itemToSell);
    
    	return itemToSell; // note that if we handled a shipUpgrade, it would return it. 
    }
    
    
    // only can sell ship upgrades, and these count as items, against the quota of total items that a ship can carry
    // put a wrapper class to selling Items and Upgrades (super class vs child class)
    
    // how to access upgrades, should these be in their own catalogue, doesnt really make any sense to do it like that!
    
    /** Checks if selling an item from a store is permissible
     * 
     * @param player Player object that item is trying to be sold to 
     * @param itemToSell Item being checked if it can be sold 
     * @return String representation if item can be sold or not, along with a reason if not. 
     */
    public String canSellItemToPlayer(GameEnvironment gameEnvironment, Item itemToSell) {
    	if (gameEnvironment.getPlayer().getMoneyBalance() < itemToSell.getPlayerBuyPrice()) {
    		return "Can't sell Item, Player does not have enough money to buy this item!";
    	}
    	
    	// need to check seperarately for an upgrade in terms of space
    	else if (gameEnvironment.getPlayer().getShip().getRemainingItemSpace() < itemToSell.getSpaceTaken() && !itemToSell.getName().endsWith("(upgrade)")) {
    		return "Can't sell Item(s), Player does not have enough space to store item(s)!";
    	}
    	else if (gameEnvironment.getLiquidValue() - itemToSell.getPlayerBuyPrice() + sellCatalogue.get(itemToSell.getName()).get("price") < gameEnvironment.getMinMoneyToTravel())
    		return "Can't sell Item(s), Player wouldn't be able to travel anywhere if Item was bought!";
    	return "Can sell";
    }
    
    /** Checks if a store can sell an upgrade
     * 
     * @param player Player object that upgrade is trying to be sold to 
     * @param upgradeToSell ShipUpgrade object that is trying to be sold
     * @return Boolean if ShipUpgrade object can be sold. 
     */
    public static String canSellUpgradeToPlayer(Player player) {
    	// other checking is done by canSellItem Method above
    	
    	// dont need to check for the size of an upgrade, as there is no mention of this in a ship
    	// this is limited by the max defenseCapability, which can in of sort act like a maxUpgradeSpace, which makes so much more sense!!!!!!!!!!!!!
    	if (player.getShip().getDefenseCapability() >= 50) {
    		return "Can't sell Upgrade(s), Ship already has max defense Capability";
    	}
    	return "Can sell";
    }
    
    // ############## METHODS FOR BUYING AN ITEM FROM A PLAYER #################
    
    public String buyItemsFromPlayerHelper(String itemStoreToBuyName, Player player, int numItems) {
    	String result = "";
    	
    	int numItemsSold = 0;
    	int totalGain= 0;
    	
    	for (int i=0; i < numItems; i++) {
    		Item itemToBuy = buyItemFromPlayer(itemStoreToBuyName, player);
    		if (itemToBuy == null) {
    			// wasn't successful in getting item, print reason why from store
    			result += Store.canBuyItemFromPlayer(player, itemToBuy) +'\n';
    			break;
    		}
    		else {
    			// If item was found, print transaction statement
    			numItemsSold += 1;
    			totalGain += itemToBuy.getPlayerSellPrice();
    		}	
    	}
    	
    	result += String.format("%d out of a requested %d %s was sold to the store \nTotal monetary gain: %d ", numItemsSold, numItems, itemStoreToBuyName, totalGain);
    	return result;
    }
	
    /** Buys an item from a Player to a store
     * 
     * @param itemName String for the name of Item to be bought 
     * @param player Player object that is selling an Item
     * @return Boolean if transaction was successful
     */
    private Item buyItemFromPlayer(String itemName, Player player) {
    	if (buyCatalogue.get(itemName) == null) {
    		throw new IllegalStateException("BUG store does not buy this item!");
    	}
    	
    	Item itemToBuy = player.getShip().takeItem(itemName);
    	
    	if (!canBuyItemFromPlayer(player, itemToBuy).equals("Can buy")) {
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
    public static String canBuyItemFromPlayer(Player player, Item itemToBuy) {
    	if (itemToBuy == null) {
    		return "Player does not have this item in possession!";
    	}
    	return "Can buy";
    }
    
    /** Converts a sell or buy catalogue into an an Array List that can be easily displayed
     * 
     * @param catalogue Catalogue to be parsed into a displayArrayList
     * @return ArrayList for what you can buy or sell from a store
     */
    public String[] catalogueToArray(String operation){
    	ArrayList<String> displayArrayList = new ArrayList<String>();
    	/*
    	 *  used bellow code from online https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
    	 */
    	
    	HashMap<String, HashMap<String, Integer>> catalogue = getCatalogue(operation);
    	
    	for (Map.Entry<String, HashMap<String, Integer>> mapElement : catalogue.entrySet()) {
    		String itemName = (String) mapElement.getKey();
    		int itemPrice = catalogue.get(itemName).get("price");
    		int itemSpaceTaken = catalogue.get(itemName).get("spaceTaken");
    		String result = String.format("%s for %d Pirate Bucks", itemName, itemPrice);
    		if (itemName.endsWith("(upgrade)")) {
    			result += String.format(", with a defense boost of %d", catalogue.get(itemName).get("defenseBoost"));
    		}
    		else {
    			result += String.format(", taking up %d space", itemSpaceTaken);
    		}
    		displayArrayList.add(result);
        }
    	return (String[]) displayArrayList.toArray();
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
        		catalogueString += itemString + "; ";
    	    }
    		return catalogueString + "\n";
    	}
    	catch (IllegalStateException ise) {
    		return String.format("This Store doesnt have a %s catalogue yet, please add one!", buyOrSell);
    	}
    }
    // ##################### GETTER METHODS ########################
    
    /** Gets the visit options for a store, used by ui
     * 
     * @return ArrayList for the options for visiting a store
     */
    public static String [] getVisitOptions() {
    	String[] optionsArray = 
    		    {"View and buy items that the store sells." 
				,"View and sell Items that the store buys."
				,"View previously bought items."
				,"View the amount of money that you have."};
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
    
    public HashMap<String, HashMap<String, Integer>> getCatalogue(String operation){
    	// gets the catalogue, used by ui and locally alike
    	if (operation == "buy") {
    		HashMap<String, HashMap<String, Integer>> catalogue = buyCatalogue;
    	}
    	else {
    		HashMap<String, HashMap<String, Integer>> catalogue = sellCatalogue;
    	}
    	
    	return catalogue;
    }
    
    /** Gets the description of the store
     * 
     * @return String for the description of the store
     */
    public String getDescription(){
    	// specific things that a store sells will be given on request!
    	return String.format("Store %s, is located on %s and specialises in %s!", name, storeIsland.getIslandName(), specialty);
    }
    
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
    
    // ##################### SETTER METHODS ########################
    
    /** Sets the Island that a store belongs to
     * 
     * @param island Island that a store belongs to
     */
    public void setStoreIsland(Island storeIsland) {this.storeIsland = storeIsland;}
}
