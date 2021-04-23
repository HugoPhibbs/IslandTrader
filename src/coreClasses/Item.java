package coreClasses;

public class Item{

    private Island storeIslandSoldAt; // island where the item was sold from player to store
    private String name;
    private int spaceTaken;
    // called playerBuyPrice and not buyPrice to avoid confusion with the perspective of the store 
    private int playerBuyPrice; // Price of Item within a store
    private int playerSellPrice; // Price of item that a player sells back to a store
    
    
    // could have a variable that tracks if it is in possession of a player
    // needs to include the amount paid for this item
    // and if it is sold, how much you sold it for and where it was sold.
    
    public Item(String name, int spaceTaken, int playerBuyPrice){
    	// TODO need to check if the inputs are correct,
    	this.name = name; // needs to be capitalised
    	this.spaceTaken = spaceTaken; // int
    	this.playerBuyPrice = playerBuyPrice; 
    	// calls setDescription()
    }

    /** Setter method for the Island that an Item as sold at
     *
     * @param islandSoldAt Island object that an Item was sold at
     */
    public void setStoreIslandSoldAt(Island storeIslandSoldAt){
        this.storeIslandSoldAt = storeIslandSoldAt;
    }
    
    public void setPlayerSellPrice(int playerSellPrice){
    	this.playerSellPrice = playerSellPrice;
    }
    

    public void setDescription(){
    	// takes a parameter from store, so when calling this, it shows the actual price in store
    	
    	// called by shop method when displaying items on sale. 
    	// could it be more simple just to include straight prices and not confuse ourselves with multipliers??
    	// if we have a thing with multipliers, we can easily add the same Item object to multiple stores
    	// and then the stores can decide the price easily
    	
        // implement
    }
    
    /** Getter method for the description of Item object
     * 
     * @return String description of Item object details
     */

    public String getDescription(){
    	// TODO implement!
    	return "";
    }

    /** Getter method for the name of Item object
     *
     * @return
     */
    public String getName(){
        return name;
    }

    /** Getter method for the space taken of Item object
     *
     * @return Integer for the space taken of Item Object
     */
    public int getSpaceTaken(){
        return spaceTaken;
    }

    /** Getter method for the base price of Item object
     *
     * @return Integer for the base price of Item Object
     */
    public int getPlayerBuyPrice(){
        return playerBuyPrice;
    }

    /** Getter method for the island that an Item object was sold at
     *
     * @return Island object that Item was sold at
     */
    public Island getStoreIslandSoldAt(){
        return storeIslandSoldAt;
    }
    
    public int getPlayerSellPrice() {
    	return playerSellPrice;
    }
}