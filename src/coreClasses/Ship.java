package coreClasses;

import java.util.*;   

/** Represents a ship
 * @author Hugo Phibbs
 * @version 19/5/2021
 * @since 2/4/2021
 */

public class Ship {
	// Class variables //
	/** Name for the ship */
    private final String name;
    
    /** Max amount of Item object that a ship can hold */
    private final int maxItemSpace;
    
    /** Max defense capability that can be reached with adding upgrades to a ship */
    private final int maxDefenseCapability;
    
    /** Speed of a ship for traveling between islands, unit distance per day */
    private final int speed; 
    
    /** Size of a ships crew */
    private final int crewSize; 
    
    /** The daily wage cost of a single crew member */
	private final int COST_PER_CREW_PER_DAY = 5;
	
	/** Player object that owns this Ship object in a game */
    private Player owner;
    
    /** The remaining amount of Items that a ship can carry */
    private int remainingItemSpace;
    
    /** Current defense capability of a ship from adding upgrades */
    private int defenseCapability = 0;
    
    /** Current health status of a ship, can take damage from unfortunate weather event */
    private int healthStatus = 100;
    
    /** ArrayList containing all the Items that a ship is currently carrying */
    private ArrayList<Item> items = new ArrayList<Item>();
    
    /** ArrayList containing all the Upgrades that a ship currently has equipped */
    private ArrayList<ShipUpgrade> upgrades = new ArrayList<ShipUpgrade>();

    
    /** Constructor for a Ship object
     *
     * @throws IllegalArgumentException if constructor parameters are invalid
     * @param name String for the name of the ship
     * @param speed Integer for the speed of the ship as it travels between islands (assume constant)
     * @param shipSize Integer for the size of the ship, influences crew size and the max cargo space of a ship
     * @param maxDefenseCapability Integer for the max defense capability of a ship
     */
    public Ship(String name, int speed, int shipSize, int maxDefenseCapability) throws IllegalArgumentException{    	
    	if (!CheckValidInput.nameIsValid(name)) {
    	    String msg1 = "Name for ship must have no more than 1 consecutive white space and be between 3 and 15 characters in length!";
    		throw new IllegalArgumentException(msg1);
    	}
    	if (shipSize * 10 > 100) {
    		String msg2 = "Ship size cannot be more than 10!";
    		throw new IllegalArgumentException(msg2);
    	}
    	
    	if (maxDefenseCapability > 50) {
    		String msg3 = "Max defense capability cannot be more than 50";
    		throw new IllegalArgumentException(msg3);
    	}
    	
    	this.name = name;
    	this.speed = speed;
    	this.crewSize = shipSize; // neat numbers
    	this.maxItemSpace = (int) shipSize * 10; // ie for every unit of crewSize max item space adjusts with this. arbitrary constant
    	this.maxDefenseCapability = maxDefenseCapability;
    	
    	remainingItemSpace = maxItemSpace;
    }

    ////////////////////////////////// GENERAL SHIP METHODS ////////////////////////////////////////
    
    /** Enacts damage onto Ship Object
     *
     * @param damage Integer for damage to be inflicted onto ship
     */
    public void takeDamage(int damage) {
        healthStatus -= (int) damage * (1-(float)defenseCapability/100); 
        // in practicality health status will always be above 0, because max damage of unfortunate weather is 99
    }
    
    /** Repairs a Ship object. Repairs ship if a Player has enough money to pay the cost. 
     * 
     * @return A boolean value if ship was repaired
     */
    public boolean repairShip() {
    	// Called if the ship has less than 100 health before setting off for another island
    	int repairCost = repairCost();
    	if (owner.spendMoney(repairCost)) {
    		this.healthStatus = 100;
    		return true;
    	}
        return false;
    }

    /** Pays the wages for the Ship's crew
     *
     * @param route Route object that the Ship is sailing on between two islands
     * @param player Player object for player that is currently playing
     * @return Boolean if wages were paid or not
     */
    public boolean payWages(Route route, Player player) {
    	// Called every time a player wants to sell sail to another island
        int totalWageCost = routeWageCost(route);
    	return player.spendMoney(totalWageCost);
    }
    
    ///////////////////////////////// MANAGING SHIP UPGRADES ///////////////////////////////////////
    
    /** Adds a new upgrade to this Ship. Checking if a ship can add this upgrade is handled by Store
     *
     * @param upgrade Upgrade object to be added to ship
     */
    public void addUpgrade(ShipUpgrade upgrade) {
        addDefenseBoost(upgrade);
        upgrades.add(upgrade);
    }
    
    /** Helper method for addUpgrade(ShipUpgrade upgrade)
     * 
     * @param upgrade Upgrade object to be added to ship
     */
    private void addDefenseBoost(ShipUpgrade upgrade) {
    	// Checking if upgrade can be added is done by Store class. 
    	defenseCapability += upgrade.getDefenseBoost();
    	if (defenseCapability > maxDefenseCapability) { // so it doesnt go above max 
    		defenseCapability = maxDefenseCapability;
    	}
    }
    
    /** Method for converting upgrades that a Ship has into String form
     * 
     * @return String representation of the upgrades that a Ship has
     */
    public String upgradesToString() {
    	if (upgrades.size() == 0) {
    		return "Ship isn't equipped with any upgrades yet";
    	}
    	String result = String.format("%s, has upgrades: \n", name);
    	
    	for (ShipUpgrade shipUpgrade : upgrades) {
    		String upgradeName = shipUpgrade.getName();
    		int index = upgradeName.lastIndexOf("(upgrade)");
    		upgradeName = upgradeName.substring(0, index);
    		
    		result += String.format("%s, defense boost: %d, space taken: %d \n", upgradeName, shipUpgrade.getDefenseBoost(), shipUpgrade.getSpaceTaken());
    	}
    	
    	return result;
    }
    
    ///////////////////////////////// MANAGING ROUTES //////////////////////////////////////
    
    /** Method that returns the total wage cost for traveling along a route
     * 
     * @param route Route object that a Ship wishes to travel on
     * @return Integer for the total wage cost to travel along this route
     */
    public int routeWageCost(Route route) {
    	int daysSailing = route.getDistance() / speed; // days sailing dependent on ship speed. 
    	return getDailyWageCost() * daysSailing;
    }
    
    /** Method that returns the cost to repair ship because of damage
     * 
     * @return Integer for the cost of a ship repair
     */
    public int repairCost() {
    	int damageInflicted = 100-healthStatus;
    	int REPAIR_COST_CONSTANT = 1; // can be adjusted for balance
    	return damageInflicted * crewSize * REPAIR_COST_CONSTANT;
    }
   
    //////////////////////////////// MANAGING SHIP ITEMS ///////////////////////////////////////////
    
    /** Adds an Item Object to this Ship's cargo hold
    *
    * @param item Item object to be added to cargo hold
    */
   public void addItem(Item item){
	   // checking that you can add an item is done by store
       remainingItemSpace -= item.getSpaceTaken();
       items.add(item);
   }
    
    /** Takes Item from ship and returns it
     * 
     * @param itemName String name for the name of Item to be removed
     * @return Item object with name matching itemName. Returns null if a match wasnt found
     */
    
    public Item takeItem(String itemName) {
    	// Takes an input of a string itemName, and removes&returns the first 
    	// occurance of an Item object with the name itemName
    	
    	for (Item currItem : 	items) {
    		if (currItem.getName().equals(itemName)) {
    			items.remove(currItem);
    			return currItem;
    		}
    	}
    	return null; // did not find an Item with name 'itemName'
    }
    
    /////////////////////////////// GETTER AND SETTER METHODS //////////////////////////////////////
    
    /** Getter method for the description of a Ship
     * 
     * @return String representation of a ship
     */
    public String getDescription() {
    	return String.format("Ship %s, has properties: \n"
    			+ "Max Item-Space: %d \n"
    			+ "Remaining Item-Space: %d \n"
    			+ "Speed: %d \n"
    			+ "Crew-size: %d \n"
    			+ "Wage Cost per day: %d \n"
    			+ "Defense Capability: %d \n"
    			+ "Max Defense Capability: %d \n"
    			, name, maxItemSpace, remainingItemSpace, speed, crewSize, getDailyWageCost(), defenseCapability, maxDefenseCapability);
    	
    }
    
    /** Getter method for the daily wage cost of all the crew on board a Ship
     * 
     * @return Integer for the daily wage cost of all the crew on board a Ship
     */
    public int getDailyWageCost() {return COST_PER_CREW_PER_DAY * crewSize;}
    
    /** Getter for the max cargo capacity of Ship Object
     * 
     * @return Integer for the max Cargo capacity of Ship Object
     */
    public int getRemainingItemSpace() {return remainingItemSpace;}

    /** Getter method for the size of crew on board ship
     * 
     * @return Integer for the number of crew on board 
     */
    public int getCrewSize() {return crewSize;}
    
    /** Getter method for the name of this Ship Object
     *
     * @return String for the name of the ship
     */
    public String getName(){return name;}

    /** Getter method for the items for this Ship object
     *
     * @return ArrayList of the ship's items
     */
    public ArrayList<Item> getItems() {return items;}
    
    /** Getter method for the upgrades for this Ship Object
     * 
     * @return ArrayList of the ship's upgrades
     */
    public ArrayList<ShipUpgrade> getUpgrades() {return upgrades;}

    /** Gets the health status for this Ship Object
     *
     * @return Integer value for the health of the Ship
     */
    public int getHealthStatus() {return healthStatus;}
   
    /** Gets the defense capability for this Ship Object
    *
    * @return Integer value for the defense capability of the Ship
    */
    public int getDefenseCapability() {return defenseCapability;}
    
    /** Getter method for the speed of this ship object
     * 
     * @return Integer value for the speed of a ship object
     */
    public int getSpeed() {return speed;}
    
    /** Getter method for the max defense capability of a ship object
     * 
     * @return Integer value for the max defense capability of a ship object
     */
    public int getMaxDefenseCapability() {return maxDefenseCapability;}
    
    /** Sets the Ship's owner
     * 
     * @param owner Player object for owner of the ship
     */
    public void setOwner(Player owner) {this.owner = owner;}  
    
    /** Method to set the remaining item space of a ship
     * 
     * @param remainingItemSpace Integer for the remaining Item space for a ship
     */
    public void setRemainingItemSpace(int remainingItemSpace) {
    	this.remainingItemSpace = remainingItemSpace;
    }
}
