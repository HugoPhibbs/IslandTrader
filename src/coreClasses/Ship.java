package coreClasses;

import java.util.*;  

import exceptions.*;

/** Represents a ship
 * @author Hugo Phibbs
 * @version 6/5/2021
 * @since 2/4/2021
 */

public class Ship {
	// Final class variables
    private final String name;
    private final int maxItemSpace;
    private final int speed;             // unit distance per day
    private final int crewSize; 
	private final int COST_PER_CREW_PER_DAY = 5;
	private final int maxDefenseCapability;
    private Player owner;
    // Non-final class variables
    private int remainingItemSpace;
    private int defenseCapability = 0;
    private int healthStatus = 100;
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<ShipUpgrade> upgrades = new ArrayList<ShipUpgrade>();

    /** Constructor for Ship
     *
     * @param name A String for the name of the ship
     * @param shipSize Integer for the size of the ship, influences crew size and the max cargo space of a ship
     * @param speed Integer for the speed of the ship as it travels between islands (assume constant)
     */
    public Ship(String name, int speed, int shipSize, int maxDefenseCapability){    	
    	if (!CheckValidInput.nameIsValid(name)) {
    	    String msg1 = "Name for ship must have no more than 1 consecutive white space and be between 3 and 15 characters in length!";
    		throw new IllegalArgumentException(msg1);
    	}
    	if (shipSize * 10 > 100) {
    		String msg2 = "Ship size cannot be more than 10!";
    		throw new IllegalArgumentException(msg2);
    	}
    	this.name = name;
    	this.speed = speed;
    	this.crewSize = shipSize; // neat numbers
    	this.maxItemSpace = shipSize * 10; // ie for every unit of crewSize max item space adjusts with this. arbitary constant
    	this.maxDefenseCapability = maxDefenseCapability;
    	
    	remainingItemSpace = maxItemSpace;
    }
    
    // ########################### GENERAL SHIP METHODS ###########################################
    
    /** Enacts damage onto Ship Object
     *
     * @param damage Integer for damage to be inflicted onto ship
     */
    public void takeDamage(int damage) {
    	// TODO, ceiling of below calculations
        healthStatus -= (int) damage * (1-(float)defenseCapability/100); 
        // in practicality health status will always be above 0, because max damage of unfortunate weather is 99
    }
    
    /** Repairs a Ship object
     * 
     * @return A boolean value if ship was repaired
     */
    public boolean repairShip() {
    	// Called if the ship has less than 100 health before setting off for another island
    	int repairCost = getRepairCost();
    	if (owner.spendMoney(repairCost)) {
    		this.healthStatus = 100;
    		return true; // not enough money
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
        int totalWageCost = getRouteWageCost(route);
    	return player.spendMoney(totalWageCost);
    }
   
    // ########################### MANAGING SHIP UPGRADES #######################################
    
    /** Adds a new upgrade to this Ship
     *
     * @param upgrade Upgrade object to be added to ship
     * @return Boolean value if the item was added to ship successfully
     * @throws InsufficientUpgradeSlotsRemaining
     * Exception thrown if the remaining upgrade slots is not enough to store upgrade
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
    	// checking if upgrade can be added is done by store class. 
    	defenseCapability += upgrade.getDefenseBoost();
    	if (defenseCapability > maxDefenseCapability) { // so it doesnt go above max 
    		defenseCapability = maxDefenseCapability;
    	}
    }
    
    public String upgradesToString() {
    	if (upgrades.size() == 0) {
    		return "Ship isnt equipped with any upgrades yet";
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

    // ########################### MANAGING SHIP ITEMS ###########################################
    
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
     * @return Item object with name matching itemName
     */
    
    public Item takeItem(String itemName) {
    	// Takes an input of a string itemName, and removes&returns the first 
    	// occurance of an Item object with the name itemName
    	// otherwise throws an exception if the item is not present
    	// called by Store class when ever a a player wants to sell an upgrade
    	
    	for (Item currItem : 	items) {
    		// Bellow line took a while to realize that == points to the SAME object.
    		if (currItem.getName().equals(itemName)) {
    			items.remove(currItem);
    			return currItem;
    		}
    	}
    	return null; // did not find and/or remove inputted item
    }
    
    // ########################### GETTER METHODS ###########################################
    
    public String getDescription() {
    	return String.format("Ship %s, has properties: \n"
    			+ "Max Item-Space: %d \n"
    			+ "Remaining Item-Space: %d \n"
    			+ "Speed: %d \n"
    			+ "Crew-size: %d \n"
    			+ "Defense Capability: %d \n"
    			+ "Max Defense Capability: %d \n"
    			, name, maxItemSpace, remainingItemSpace, speed, crewSize, defenseCapability, maxDefenseCapability);
    	
    }
    
    /** Getter method for the daily wage of a ship's crew
     * 
     * @return Integer for the total wage cost of the ship's crew
     */
    public int getRouteWageCost(Route route) {
    	int daysSailing = route.getDistance() / speed; // days sailing dependent on ship speed. 
    	return COST_PER_CREW_PER_DAY * crewSize * daysSailing;
    }
   
    /** Getter method for the cost to repair ship because of damage
     * 
     * @return Integer for the cost of a ship repair
     */
    public int getRepairCost() {
    	int damageInflicted = 100-healthStatus;
    	int REPAIRCOSTCONSTANT = 10; // Arbitrary value for the cost per unit of damge
    	return damageInflicted * REPAIRCOSTCONSTANT;
    }
    
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
    
    // // ########################### SETTER METHODS ###########################################
    /** Sets the Ship's owner
     * 
     * @param owner Player object for owner of the ship
     */
    public void setOwner(Player owner) {this.owner = owner;}  
}
