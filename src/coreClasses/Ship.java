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
    private final int maxUpgradeSpace;
    private final int maxCargoCapacity;
    private final int speed;             // unit distance per day
    private final int crewSize;
    private Player owner;
    // Non-final class variables
    private int remainingUpgradeSpace;
    private int remainingCargoCapacity;
    private int defenseCapability = 0;
    private int healthStatus = 100;
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<ShipUpgrade> upgrades = new ArrayList<ShipUpgrade>();

    /** Constructor for Ship
     *
     * @param name A String for the name of the ship
     * @param crewArray Array for the crew of the ship
     * @param maxCargoCapacity Integer for the max amount of cargo capacity of the ship
     * @param speed Integer for the speed of the ship as it travels between islands (assume constant)
     */
    public Ship(String name, int speed, int crewSize, int maxUpgradeSpace, int maxCargoCapacity){    	
    	if (!CheckValidInput.nameIsValid(name)) {
    	    String msg1 = "Name for ship must have no more than 1 consecutive white space and be between 3 and 15 characters in length!";
    		throw new IllegalArgumentException(msg1);
    	}
    	if (maxCargoCapacity > 100) {
    		String msg2 = "maxCargoCapacity cannot be more than 100!";
    		throw new IllegalArgumentException(msg2);
    	}
    	this.name = name;
    	this.speed = speed;
    	this.crewSize = crewSize;
    	this.maxUpgradeSpace = maxUpgradeSpace;
    	this.maxCargoCapacity = maxCargoCapacity;
    	
    	remainingCargoCapacity =  maxCargoCapacity;
    	remainingUpgradeSpace = maxUpgradeSpace;
    }
    
    // ########################### GENERAL SHIP METHODS ###########################################
    
    /** Enacts damage onto Ship Object
     *
     * @param damage Integer for damage to be inflicted onto ship
     * @return A boolean value if the ship survived the attack!
     */
    public boolean takeDamage(int damage) {
    	// TODO, ceiling of below calculations
        healthStatus -= (int) damage * (1-defenseCapability/100); 
        // in practicality will always be above 0, because max damage of unfortunate weather is 99
        return (healthStatus <= 0); 
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
     */
    public boolean payWages(Route route, Player player) {
    	// Called every time a player wants to sell sail to another island
        int totalWageCost = getRouteWageCost(route);
    	if (!player.spendMoney(totalWageCost)) {
    		return false; // not enough money
    	}
    	return true;
    }
   
    // ########################### MANAGING SHIP UPGRADES #######################################
    
    /* TODO move upgrade to item system. 
     * check equals and sameness.
     */

    /** Adds a new upgrade to this Ship
     *
     * @param upgrade Upgrade object to be added to ship
     * @return Boolean value if the item was added to ship successfully
     * @throws InsufficientUpgradeSlotsRemaining
     * Exception thrown if the remaining upgrade slots is not enough to store upgrade
     */
    public boolean addUpgrade(ShipUpgrade upgrade) {
        if (remainingUpgradeSpace >= upgrade.getSpaceTaken() && defenseCapability < 50) {
            remainingUpgradeSpace -= upgrade.getSpaceTaken();
            addDefenseBoost(upgrade);
            upgrades.add(upgrade);
            return true;
        } 
		return false; // not enough remaingUpgrade space, or max defense Capability
    }
    
    /** Helper method for addUpgrade(ShipUpgrade upgrade)
     * 
     * @param upgrade Upgrade object to be added to ship
     */
    private boolean addDefenseBoost(ShipUpgrade upgrade) {
    	// TODO, need to check in cmd line if adding an upgrade maxed out defense.
    	defenseCapability += upgrade.getDefenseBoost();
    	if (defenseCapability > 50) {
    		defenseCapability = 50;
    		return true;
    	}
    	return false; // ship already has max defense capabiility;
    }
   
    // ########################### MANAGING SHIP ITEMS ###########################################
    
    /** Adds an Item Object to this Ship's cargo hold
    *
    * @param item Item object to be added to cargo hold
    * @throws InsufficientCargoException Exception thrown if the remaining cargo space is not enough to store item
    */
   public boolean addItem(Item item) throws InsufficientCargoSpaceException{
   	   // Adds an Item to the ship's cargo hold
   	   // If the ship has enough cargo space, Item is added
       // otherwise an Exception is thrown
   	
       if (remainingCargoCapacity >= item.getSpaceTaken()) {
           items.add(item);
           remainingCargoCapacity -= item.getSpaceTaken();
           return true;
       } 
       return false; // not enough space to add an item
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
    	
    	for (Item currItem : items) {
    		if (currItem.getName() == itemName) {
    			items.remove(currItem);
    			return currItem;
    		}
    	}
    	return null; // did not find and/or remove inputted item
    }
    
    // ########################### GETTER METHODS ###########################################
    
    public String getDescription() {
    	return String.format("Ship %s, has stats: \n"
    			+ "Max Cargo-Capacity: %d \n"
    			+ "Max Upgrade-Space: %d \n"
    			+ "Speed: %d \n"
    			+ "Crew-size: %d"
    			, name, maxCargoCapacity, maxUpgradeSpace, speed, crewSize);
    }
    
    /** Getter method for the daily wage of a ship's crew
     * 
     * @return Integer for the total wage cost of the ship's crew
     */
    public int getRouteWageCost(Route route) {
    	int daysSailing = route.getDistance() / speed; // days sailing dependent on ship speed. 
    	int SINGLEDAILYWAGE = 10; // Arbitrary value for the daily wage of a single crew member
    	return SINGLEDAILYWAGE * crewSize * daysSailing;
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
    public int getRemainingCargoCapacity() {return remainingCargoCapacity;}
    
    /** Getter for the occupied cargo capacity of a Ship Object
     * 
     * @return Integer for the max Cargo capacity of Ship Object
     */
    public int getOccupiedCargoCapacity() {
    	return maxCargoCapacity - remainingCargoCapacity;
    }
    
    /** Getter for the remaining upgrade space of a Object
     * 
     * @return Integer for the remaining upgrade space of Ship Object
     */
    public int getRemainingUpgradeSpace() {return remainingUpgradeSpace;}
    
    /** Getter for the occupied upgrade space of a Object
     * 
     * @return Integer for the occupied upgrade space of Ship Object
     */
    public int getOccupiedUpgradeSpace() {
    	return maxUpgradeSpace - remainingUpgradeSpace;
    }
    
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
