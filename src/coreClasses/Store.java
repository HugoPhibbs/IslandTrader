package coreClasses;

import java.lang.reflect.Array;   

import java.util.*;

/** Represents a Store object
 * 
 * @author Hugo Phibbs
 * @version 14/5/2021
 * @since 2/4/2021
 */

public class Store {
	// Class variables //
	/** Name of the store*/
    private String name; 
    
    /** Specialty of a store, indicates what it can buy and sell*/
    private String specialty; 
    
    /** Store object belonging to an island*/
    private Island storeIsland;
    
    /** Collection describing the Items that a store can sell to a player */
    private HashMap<String, HashMap<String, Integer>> sellCatalogue;
    
    /** Collection describing the Items that a store can buy from a player */
    private HashMap<String, HashMap<String, Integer>> buyCatalogue;
    
    
    /** Constructor for Store Class
     * 
     * @param name String name of Store object to be created
     * @param specialty String for the specialty of a Store
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
    
    //////////////////////////// GENERAL STORE METHODS ////////////////////////////////////////
    
    /** Gets the name of a chosen item to be sold or bought. Used by Cmd Line Ui to find the name of an 
     * Item that was displayed. 
     * 
     * @param displayArray Array that details items that can be sold or bought
     * @param chosenItemNum ItemNum action int chosen by cmd line ui of chosen item. This is one less than the
     * actual index in displayArray
     * @return String for the name of a chosen Item name
     */
    public static String chosenItemName(String[] displayArray, int chosenItemNum) {
    	// display ArrayList must have form {"itemName for ..."}, key thing being itemName is in first position
    	// To Note that the array is indexed one less than chosenItemNum, since chosenItemNum is a number > 1
    	// Representing an action in Cmd Line UI
    	return (String) Array.get(displayArray[chosenItemNum-1].split(" "), 0);
    }
    
    /////////////////////// METHODS FOR SELLING AN ITEM TO A PLAYER ///////////////////////////
    
    /** Method to check if a player wants to buy an item. If buying an item puts them in a possition where they need
     *  to sell items in order to travel to another island, then it returns a string. 
     *  otherwise returns null if they dont need to be asked. 
     *  Used by Ui before buying an item
     *  
     * @param gameEnvironment GameEnivornment object belonging to the current object
     * @param itemName String for the requested item that is wanted to be bought
     * @param player Player object that wants to buy an item
     * @return String Asking user to confirm that they want to sell an item if applicable, otherwise null
     */
	public String checkPlayerWantsToBuy(GameEnvironment gameEnvironment, String itemName) {
		if (sellCatalogue.get(itemName).get("price") > gameEnvironment.getMinMoneyToTravel()) {
			return "If you buy this item you will need to sell some of your items if you want to travel to another island. \n"
					+ "Are you sure you want to buy this item?";
		}
		return null;
	}
	
	/** Entry point for selling an item to a player. Calls Item sellItemToPlayer(GameEnvironment gameEnvironment, String itemName)
	 * for individual selling of items
	 * 
	 * @param gameEnvironment GameEnvironment object for the current game
	 * @param itemStoreToSellName String of the name of the requested item to sell to a player
	 * @param numItems Integer for the number of items requested to sell to a player
	 * @return String for the receipt of this transaction
	 */
	public String sellItemsToPlayer(GameEnvironment gameEnvironment, String itemStoreToSellName, int numItems) {
		/* The whole idea with selling and buying items is using the name of an Item that wants to be sold or not
		 * This is passed around instead of an Item object itself, which would've created alot of issues
		 */
		
        // Initialize variables
        String result = "";
        int numItemsBought = 0;
        int totalCost = 0;
        
        // For loop to sell items individually until counter has reached numItemsRequested or a player cannot buy any more
		for (int i = 0 ; i < numItems; i++) {
			// Sell an Item with the name 'itemStoreToSellName' to player
			Item itemToSell = sellItemToPlayer(gameEnvironment, itemStoreToSellName);
			
			/* Handle result of selling
			 * Item can either be with player or not
			 */
			if (!itemToSell.getWithPlayer()) {
				// Since item didn't come in possession with player, find reason why not
				
				result += "Not all of the requested items were sold to a player! \n";
				// Check if item wasn't sold because of an error to do with it being an upgrade
				if (itemToSell.getName().endsWith("(upgrade)") && !Store.canSellUpgradeToPlayer(gameEnvironment.getPlayer()).equals("Can sell")){
					result += Store.canSellUpgradeToPlayer(gameEnvironment.getPlayer()) + '\n'; // return reason why cant sell upgrade.
				}
				// Otherwise find reason inherent in being an Item object
				else { 
					result += canSellItemToPlayer(gameEnvironment, itemToSell) + '\n';
				}
				// Stop the selling of Items, because most recent sale didn't go through completely
				break;
			}
			else {
				// Otherwise an Item is now in possession with a player, so increase counters
				numItemsBought += 1;
				totalCost += itemToSell.getPlayerBuyPrice();
		    }
	    }
		
		// Add to the result a receipt for this transaction
		result += String.format("%d out of requested %d %s bought \nTotal cost of transaction: %d Pirate Bucks \n", numItemsBought, numItems, itemStoreToSellName, totalCost);
	
		// Add to receipt if Item is an upgrade, this includes info on a player's ship defense capability
		if (itemStoreToSellName.endsWith("(upgrade)")){
			result += upgradeSellReciept(gameEnvironment.getPlayer());
	    }
		// Return the result of transaction
		return result;
	}
	
    /** Creates and sells an item to a player 
     * 
     * @param itemName String for the name of Item to be created
     * @param player Player object to receive Item
     * @return boolean, if transaction was successful
     */
    private Item sellItemToPlayer(GameEnvironment gameEnvironment, String itemName) {
    	// Check if a Store sells an Item with the name itemName
    	if (sellCatalogue.get(itemName) == null) {
    		throw new IllegalArgumentException("BUG store does not sell this item!");
    	}
    	
    	// Create an Item with information from a Store's sellCatalogue
    	Item itemToSell = new Item(itemName, sellCatalogue.get(itemName).get("spaceTaken"), sellCatalogue.get(itemName).get("price"));
    	
    	// Check if an item can be sold to a player
    	if (!canSellItemToPlayer(gameEnvironment, itemToSell).equals("Can sell")){
    		/* Return the Item, hasn't been given to a player, so sellItemsToPlayer(GameEnvironment, String, int)
    		 * Knows that it hasnt been sold
    		 */
    		return itemToSell; 
    	}
    	
    	// Check if the Item created has a name belonging to an ShipUpgrade.
    	if (itemName.endsWith("(upgrade)")) {
    		// Create a ShipUprade object with the properties of the itemToSell object and information taken from sellCatalogue
    		ShipUpgrade upgradeToSell = new ShipUpgrade(itemName, itemToSell.getSpaceTaken(), 
    				itemToSell.getPlayerBuyPrice(), 
    				sellCatalogue.get(itemToSell.getName()).get("defenseBoost")); 
    		
    		// Check if this new ShipUpgrade can be sold. 
    		if (!canSellUpgradeToPlayer(gameEnvironment.getPlayer()).equals("Can sell")) {
    			return (Item) upgradeToSell;
    		}
    		
    		// Otherwise add this Upgrade to a Player's Ship
    		gameEnvironment.getPlayer().getShip().addUpgrade(upgradeToSell);
    	}
        // Otherwise Item is an instance of the Item Class. 
    	else {
    		gameEnvironment.getPlayer().getShip().addItem(itemToSell);
    	}
    	
    	/* Set itemToSell with Player and spend Players money
    	 * Note that even if an Item was given to a Player's Ship as a ShipUpgrade, it is stored as an Item
    	 * As this satisfies all the functionality needed.
    	 */
    	itemToSell.setWithPlayer(true);
    	gameEnvironment.getPlayer().spendMoney(itemToSell.getPlayerBuyPrice());
    	gameEnvironment.getPlayer().addPurchasedItem(itemToSell);
    
    	// Return Item that was sold
    	return itemToSell; 
    }
    
	
	/** Method to for getting the receipt from selling an upgrade to a Player
	 * 
	 * @param player Player object that an Item was sold to
	 * @return String describing the new Defense Capability of a Player's Ship
	 */
	private String upgradeSellReciept(Player player) {
		// Check if a player has maxed their Ship's defense capability, otherwise let them know what the current value of it is
		if (player.getShip().getDefenseCapability() == player.getShip().getMaxDefenseCapability()) {
			return String.format("Your defense capability is maxed at %d! \n", player.getShip().getMaxDefenseCapability());
		}
		else {
			return String.format("Your defense capability is now %d! \n", player.getShip().getDefenseCapability());
		}
	}
	
    /** Checks if selling an item from a store is permissible. Returns "Can sell" if permissable otherwise returns 
     * a String returning the reason why not. 
     * 
     * @param player Player object that item is trying to be sold to 
     * @param itemToSell Item being checked if it can be sold 
     * @return String representation if item can be sold or not, along with a reason if not. 
     */
    public String canSellItemToPlayer(GameEnvironment gameEnvironment, Item itemToSell) {
    	if (gameEnvironment.getPlayer().getMoneyBalance() < itemToSell.getPlayerBuyPrice()) {
    		return "Can't sell Item(s), Player does not have enough money to buy this item!";
    	}
    	// Note that ShipUpgrades all have spaceTaken of 0, so it doesnt count against a Ship's Item capacity
    	else if (gameEnvironment.getPlayer().getShip().getRemainingItemSpace() < itemToSell.getSpaceTaken() && !itemToSell.getName().endsWith("(upgrade)")) {
    		return "Can't sell Item(s), Player does not have enough space to store item(s)!";
    	}
    	else if (gameEnvironment.calculateLiquidValue() - itemToSell.getPlayerBuyPrice() + sellCatalogue.get(itemToSell.getName()).get("price") < gameEnvironment.getMinMoneyToTravel())
    		return "Can't sell Item(s), Player wouldn't be able to travel anywhere if Item was bought!";
    	return "Can sell";
    }
    
    /** Checks if a store can sell an upgrade to a player. Returns "Can sell" if it can, otherwise
     * returns a String for the reason explaining why not. 
     * 
     * @param player Player object that upgrade is trying to be sold to 
     * @param upgradeToSell ShipUpgrade object that is trying to be sold
     * @return Boolean if ShipUpgrade object can be sold. 
     */
    public static String canSellUpgradeToPlayer(Player player) {
    	if (player.getShip().getDefenseCapability() >= player.getShip().getMaxDefenseCapability()) {
    		return "Can't sell Upgrade(s), Ship already has max defense Capability";
    	}
    	return "Can sell";
    }
    
    ////////////////// METHODS FOR BUYING AN ITEM FROM A PLAYER /////////////////
    
    /** Method for buying items from a player
     *  Calls buyItemFromPlayer(String itemName, Player player) for individual items
     *  Buys multiple instances of the Item given by itemStoreToBuyName
     *  
     * @param itemStoreToBuyName String for the requested item to be bought from a player
     * @param player Player object who is selling items to a store
     * @param numItems Integer for the number of items requested to sell
     * @return String for the receipt of the transaction
     */
    public String buyItemsFromPlayer(String itemStoreToBuyName, Player player, int numItems) {
    	
    	// Initialize variables
    	String result = "";
    	int numItemsSold = 0;
    	int totalGain= 0;
    	
    	// For loop to buy items individually until counter has reached numItemsRequested or a player cannot sell any more
    	for (int i=0; i < numItems; i++) {
    		// Get the Item that was sold to a Store
    		Item itemToBuy = buyItemFromPlayer(itemStoreToBuyName, player);
    		
    		// If the itemToBuy was null, then no Items with the name itemStoreToBuyName were found in a Player's Ship possession
    		if (itemToBuy == null) {
    			result += "Not all of the requested Items were bought from a Player!";
    			// Wasn't successful in getting item, print reason why from store
    			result += Store.canBuyItemFromPlayer(player, itemToBuy) +'\n';
    			
    			break;
    		}
    		else {
    			// Item was found
    			numItemsSold += 1;
    			totalGain += itemToBuy.getPlayerSellPrice();
    		}	
    	}
    	
    	// Create a result String for transaction and return it
    	result += String.format("%d out of a requested %d %s was sold to the store \nTotal monetary gain: %d", numItemsSold, numItems, itemStoreToBuyName, totalGain);
    	return result;
    }
	
    /** Buys an item from a Player to a store. Returns the itemToBuy whether the transaction is
     * successful or not. Other methods check if transaction was successful
     * 
     * @param itemName String for the name of Item to be bought 
     * @param player Player object that is selling an Item
     * @return Item object involved in the transaction
     */
    private Item buyItemFromPlayer(String itemName, Player player) {
    	if (buyCatalogue.get(itemName) == null) {
    		throw new IllegalArgumentException("BUG store does not buy this item!");
    	}
    	
    	Item itemToBuy = player.getShip().takeItem(itemName);
    	
    	// Check if an item can be bought from a player, return it if can't
    	if (!canBuyItemFromPlayer(player, itemToBuy).equals("Can buy")) {
    		return itemToBuy;
    	}
    	// Otherwise...
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
     * if it is returns the string "Can buy" otherwise returns a string explaining
     * why it cannot be done. 
     * 
     * @param player Player object trying to buy an item
     * @param itemToBuy Item object trying to be bought by a player
     * @return String for explaining if buying is permissible or not
     */
    public static String canBuyItemFromPlayer(Player player, Item itemToBuy) {
    	if (itemToBuy == null) {
    		return "Player does not have this item in possession!";
    	}
    	return "Can buy";
    }
    
    //////////////////////// DEALING WITH CATALOGUES /////////////////////////////
    
    /** Converts a sell or buy catalogue into an an Array that can be easily displayed by cmd line UI
     * 
     * @param catalogue HashMap<String, HashMap<String, Integer>> catalogue to be parsed into an Array
     * @return Array for what you can buy or sell from a store
     */
    public String[] catalogueToArray(HashMap<String, HashMap<String, Integer>> catalogue){
    	ArrayList<String> displayArrayList = new ArrayList<String>();
    	/*
    	 *  used bellow code from online https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
    	 */
    	for (Map.Entry<String, HashMap<String, Integer>> mapElement : catalogue.entrySet()) {
    		// Convert a nested HashMap into an Array representation
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
    	return displayArrayList.toArray(new String[displayArrayList.size()]);
    }
    
    public String[][] catalogueToNestedArray(HashMap<String, HashMap<String, Integer>> catalogue){
    	// converts a catalogue into a nested array to be used by GUI for tables!
    
    	ArrayList<String[]> tempOuterArrayList = new ArrayList<String[]>();
    	
    	for (Map.Entry<String, HashMap<String, Integer>> mapElement : catalogue.entrySet()) {
    		// Convert a nested HashMap into an Array representation
    		String itemName = (String) mapElement.getKey();
    		String itemPrice = Integer.toString(catalogue.get(itemName).get("price"));
    		String itemSpaceTaken = Integer.toString(catalogue.get(itemName).get("spaceTaken"));
    		String[] infoArray = new String[] {
					itemName, 
					itemPrice,
					itemSpaceTaken,
					"N/A"};
    		
    		if (itemName.endsWith("(upgrade)")) {
    			infoArray[3] = Integer.toString(catalogue.get(itemName).get("defenseBoost"));
    			}
    		tempOuterArrayList.add(infoArray);
    		}
    	// Convert the arrayList with nested String arrays into a String[][] array
    	return tempOuterArrayList.toArray(new String[tempOuterArrayList.size()][4]);
    }
    
    
    
    /** Creates and returns a string representation of the given catalogue, useful for giving a quick overview
     * of what a store buys or sells. Is called by island.fullInfo.
     * 
     * @param catalogue The catalogue (either buy or sell) to be made into a string. 
     * @param buyOrSell Whether the catalogue is for items to be bought or sold. Should be "buy" or "sell".
     * @return A string representation of the catalogue.
     */
    public String catalogueToString(HashMap<String, HashMap<String, Integer>> catalogue, String buyOrSell) {
    	String catalogueString = "The store on this island " + buyOrSell + "s:\n";
    	try {
    		for (String itemString: catalogueToArray(catalogue)) {
        		catalogueString += itemString + "; ";
    	    }
    		return catalogueString + "\n";
    	}
    	catch (IllegalStateException ise) {
    		return String.format("This Store doesnt have a %s catalogue yet, please add one!", buyOrSell);
    	}
    }
    
    /** General getter method for getting either the buyCatalogue or sellCatalogue of a 
     * store. Useful for use in UI to increase code usability and maintainability. 
     *  
     * @param buyOrSell String for the operation requested, should be "buy" or "sell" otherwise 
     * method returns null. 
     * @return HashMap<String, HashMap<String, Integer>> catalogue associated with 'buyOrSell'
     */
    public HashMap<String, HashMap<String, Integer>> getCatalogue(String buyOrSell){
    	if (buyOrSell == "buy") {
    		return buyCatalogue;
    	}
    	else if (buyOrSell == "sell"){
    		return sellCatalogue;
    	}
    	return null;
    }
    
    ////////////////////////// GETTER AND SETTER METHODS ///////////////////////////////
    
    /** Gets the visit options for a store, used by cmd ui
     * 
     * @return String[] for the options of visiting a store
     */
    public static String [] getVisitOptions() {
    	String[] optionsArray = 
    		    {"View and buy items that the store sells." 
				,"View and sell Items that the store buys."
				,"View previously bought items."
				,"View the amount of money that you have."};
    	return optionsArray;
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
     * @return HashMap<String, HashMap> representation of the things that a store sells
     */
    public HashMap<String, HashMap<String, Integer>> getSellCatalogue() {return sellCatalogue;}
    
    /** Gets the buyCatalogue for a store
     * 
     * @return HashMap<String, HashMap<String, Integer>> representation of the things that a store buys
     */
    public HashMap<String, HashMap<String, Integer>> getBuyCatalogue() {return buyCatalogue;}   
    
    /** Sets the Island that a store belongs to
     * 
     * @param island Island that a store belongs to
     */
    public void setStoreIsland(Island storeIsland) {this.storeIsland = storeIsland;}
}
