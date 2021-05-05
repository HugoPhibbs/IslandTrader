package coreClasses;

public class Item{

    private Island storeIslandSoldAt; // island where the item was sold from player to store
    private String name;
    private int spaceTaken;
    private boolean withPlayer; // keeps track of whether an item is currently with a player or not!
    // called playerBuyPrice and not buyPrice to avoid confusion with the perspective of the store 
    private int playerBuyPrice; // Price of Item within a store
    private int playerSellPrice = -1; // Price of item that a player sells back to a store, default value -1 if it hasnt been sold to a store yet
    
    
    // could have a variable that tracks if it is in possession of a player
    // needs to include the amount paid for this item
    // and if it is sold, how much you sold it for and where it was sold.
    
    public Item(String name, int spaceTaken, int playerBuyPrice){
    	
    	if (!CheckValidInput.nameIsValid(name)) {
    		throw new IllegalArgumentException("Name for Item must have no more than 1 consecutive white space and be between 3 and 15 characters in length!");
    	}
    	
    	this.name = name;
    	this.spaceTaken = spaceTaken;
    	this.playerBuyPrice = playerBuyPrice; 
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
    
    public void setWithPlayer(boolean withPlayer) {
    	this.withPlayer = withPlayer;
    }
    
    public boolean getWithPlayer() {
    	return withPlayer;
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