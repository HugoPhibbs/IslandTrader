package coreClasses;

import java.util.*; 

import exceptions.*;

// --TODO--
// A method to see the total space taken up by items in the itemArrayList
// do we need to explictly say that a method throws an exception?

// TODO -- how to implement adding upgrades
// input check on upgrades when they are made in upgrade, DONT check for valid in ship class. 
// then when adding an item with addUpgrade, call a PRIVATE method addDefenseBoost(int defenseBoost) of some sort
// that does all necessary math and calc to add
// this way we can always ensure that defense boost is a valid input. 
// also need to make sure that upgrades cant be added if the defenseCapabilty is already 50

/* TODO IDEA
 * Could be easier to represent the storage space on the ship based on the sum of spacetaken for the objects in the arraylist
 * have some sort of method that goes through the arraylist and adds the qualities of .spacetaken for each of the upgrade/item objects in the arraList
 * this would make more sense and be easier to debug. 
 * 
 * HOWEVER this could overcomplicate things, as this would require a for loop everytime we wanted to calculate the cummulative space taken
 * of Item and ShipUpgrade objects. 
 */

/* TODO
 * process for adding upgrade should check have some sort of general method accociated with it
 * can check if it is an upgrade, then adds it correspondingly. 
 */

/** Represents a ship
 * @author Hugo Phibbs
 * @version 23/4/2021
 * @since 2/4/2021
 */

public class Ship {
	// Class variables that cannot be altered after initialization
    private final String name;
    private final int maxUpgradeSpace;
    private final int maxCargoCapacity;
    private final int speed;             // unit distance per day
    private final int crewSize;
    private Player owner;
    // Class variables that can be altered after initialization
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
        // have to check each of the constructor parameters to see if they are a valid input
        // name: has to be a non empty String
        // maxCargoCapacity: cannot be negative!
    	
    	// TODO need to check that all the names being added to crewArray are valid
    	
    	// Check name and crewArray are valid
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
        return (healthStatus <= 0);
    }
    
    /** Repairs a Ship object
     * 
     * @return A boolean value if ship was repaired
     */
    public boolean repairShip() {
    	// Called if the ship has less than 100 health before setting off for another island
    	int repairCost = getRepairCost();
    	owner.spendMoney(repairCost);
        this.healthStatus = 100;
        return true;
    }

    /** Pays the wages for the Ship's crew
     *
     * @param route Route object that the Ship is sailing on between two islands
     * @param player Player object for player that is currently playing
     */
    public boolean payWages(Route route, Player player) {
    	// Called every time a player wants to sell sail to another island
        int daysSailing = route.getDistance() / speed; // days sailing dependent on ship speed. 
        int totalWageCost = getDailyWageCost() * daysSailing;
    	player.spendMoney(totalWageCost);
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
        else if (defenseCapability >= 50){
            throw new MaxDefenseCapabilityException("Ship has reached max defense capability!");
        }
        else if (remainingUpgradeSpace >= upgrade.getSpaceTaken()) {
        	// catches (remainingUpgradeSpaceAvailable >= upgrade.spaceTaken) from if block above
        	throw new InsufficientUpgradeSlotsAvailable("Not enough space to add this ship upgrade!");
        }
		return false; // to satisfy compiler, if method returns false, something has seriously gone wrong!
    }
    
    /** Helper method for addUpgrade(ShipUpgrade upgrade)
     * 
     * @param upgrade Upgrade object to be added to ship
     */
    private void addDefenseBoost(ShipUpgrade upgrade) {
    	defenseCapability += upgrade.getDefenseBoost();
    	if (defenseCapability > 50) {
    		defenseCapability = 50;
    	}
    }
    
    /** Takes upgrade from ship and returns it
     * 
     * @param upgradeName String name for the name of upgrade to be removed
     * @return Upgrade object with name matching upgradeName
     */
    
    public ShipUpgrade takeUpgrade(String upgradeName) {
    	// Takes an input of a String upgradeName and removes the first occurance
    	// of an upgrade that has the same name from the upgradeArrayList
    	// returns upgrade if it exists in array, otherwise throws an exception.
    	// called by Store class when ever a player wants to sell an upgrade
    	for (ShipUpgrade currUpgrade : upgrades) {
    		if (currUpgrade.getName().equals(upgradeName)) {
    			removeUpgrade(currUpgrade);
    			return currUpgrade;
    		}
    	} // Did not find Upgrade, raise an exception 
    	throw new IllegalArgumentException("Upgrade is not in ship's possession");
    }
    
    /** Helper method for takeUpgrade(String upgradeName){
     * 
     * @param upgrade Upgrade object to be removed from Ship's possession
     * @return Boolean value if Upgrade was removed successfully.
     */
    private boolean removeUpgrade(ShipUpgrade upgrade) {
    	// Helper method for takeUpgrade(String upgradeName)
    	if (defenseCapability < upgrade.getDefenseBoost()) {
    		// To catch any possible bug in program
    		throw new IllegalStateException("ERROR defenseCapability cannot be made less than 0");
    	}
    	defenseCapability -= upgrade.getDefenseBoost();
    	upgrades.remove(upgrade);
    	return true;
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
       else {
           throw new InsufficientCargoSpaceException("You do not have enough space to add this item");
       }
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
    	
    	// TODO can't we roll takeItem and takeUpgrade into the same method?
    	for (Item currItem : items) {
    		if (currItem.getName() == itemName) {
    			items.remove(currItem);
    			return currItem;
    		}
    	} // Did not find Item, raise an Exception
    	throw new IllegalArgumentException("Item is not in player's possession");
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
    public int getDailyWageCost() {
    	int SINGLEDAILYWAGE = 100; // Arbitrary value for the daily wage of a single crew member
    	return SINGLEDAILYWAGE * crewSize;
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
    

    public int getOccupiedCargoCapacity() {
    	return maxCargoCapacity - remainingCargoCapacity;
    }
    
    public int getRemainingUpgradeSpace() {return remainingUpgradeSpace;}
    
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
   
    
    public int getDefenseCapability() {return defenseCapability;}
    
    
    // // ########################### SETTER METHODS ###########################################
    /** Sets the Ship's owner
     * 
     * @param owner Player object for owner of the ship
     */
    public void setOwner(Player owner) {this.owner = owner;}  
    
    
   // TODO implement bellow?
    // public void setSpeed()
    
    // TODO ship size!!
}
