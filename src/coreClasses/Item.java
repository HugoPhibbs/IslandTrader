package coreClasses;

/** Represents an Item object
 * 
 * @author Hugo Phibbs
 * @version 8/5/2021
 * @since 2/4/2021
 */
public class Item{

    private Island storeIslandSoldAt; // island where the item was sold from player to store
    private String name;
    private int spaceTaken;
    private boolean withPlayer; // keeps track of whether an item is currently with a player or not!
    private int playerBuyPrice; // Price of Item within a store for a player to buy
    private int playerSellPrice = -1; // Price of item that a player sells back to a store, default value -1 if it hasnt been sold to a store yet
    
    /** Constructor method for an Item object
     * 
     * @param name String for the name of item object to be created
     * @param spaceTaken Integer for the space taken of item object to be created
     * @param playerBuyPrice Integer for the price that a player can buy this item for
     */
    public Item(String name, int spaceTaken, int playerBuyPrice){
    	
    	this.name = name;
    	this.spaceTaken = spaceTaken;
    	this.playerBuyPrice = playerBuyPrice; 
    }
    
    // ################### GETTER METHODS ##########################
    
    /** Getter method for whether an item is with a player or not
     * 
     * @return Boolean value for whether an Item is with a player or not
     */
    public boolean getWithPlayer() {return withPlayer;}

    /** Getter method for the name of Item object
     *
     * @return String for the name of an Item object
     */
    public String getName(){return name;}

    /** Getter method for the space taken of Item object
     *
     * @return Integer for the space taken of Item Object
     */
    public int getSpaceTaken(){return spaceTaken;}

    /** Getter method for the base price of Item object
     *
     * @return Integer for the base price of Item Object
     */
    public int getPlayerBuyPrice() {return playerBuyPrice;}

    /** Getter method for the island that an Item object was sold at
     *
     * @return Island object that Item was sold at
     */
    public Island getStoreIslandSoldAt() {return storeIslandSoldAt;}
    
    /** Getter method for the sell price of an item back to a store
     * 
     * @return Integer value for the sell price of an item back to a store
     */
    public int getPlayerSellPrice() {return playerSellPrice;}
    
    // ###################### SETTER METHODS #######################
    
    /** Setter method for the Island that an Item as sold at
    *
    * @param islandSoldAt Island object that an Item was sold at
    */
   public void setStoreIslandSoldAt(Island storeIslandSoldAt) {this.storeIslandSoldAt = storeIslandSoldAt;}
   
   /** Setter method for the player sell price of an item back to a store
    * 
    * @param playerSellPrice Integer for the sell price to be set
    */
   public void setPlayerSellPrice(int playerSellPrice) {this.playerSellPrice = playerSellPrice;}
   
   /** Setter method for if an item is with a player or not
    * 
    * @param withPlayer Boolean value for whether an item is with a player or not
    */
   public void setWithPlayer(boolean withPlayer) {this.withPlayer = withPlayer;}
}