package coreClasses;

import java.util.Random;
import java.util.ArrayList;

/** Represents a pirates random event
 * 
 * @author Hugo Phibbs
 * @version 8/5/2021
 * @since 2/4/2021
 */
public class Pirates {
	// in ui, it separately calls, getDescription(), rollDice(), attackShip(int diceInt, Ship ship)
	// and in that order
    
	/** Getter method for the description of a pirates random event.
	 * Called before Pirates class methods by other classes
	 * @return String representation of the unfortunate weather event
	 */
    public static String getDescription(){
    	return "You have encountered pirates, roll the die to play your chances!";
    }

    /** Method for attacking a ship object
     * Damage is based on chance and the dice number rolled by player
     * 
     * @param diceInt Integer for the dice value rolled by a player
     * @param ship Ship object to be attacked
     * @return String representation for the outcome of an attack
     */
    public static String attackShip(int diceInt, Ship ship) {
    	
    	/* based on a game of chance
    	 * defense capability is multiplied by the number that is rolled by dice. 
    	 * random int is chosen because max defense capability is 50, tf max effectivity is
    	 * 300, so there is a chance of 18% of loosing with a perfect turn!, max dice
    	 * roll and max defense capability
    	 */
    	
    	Random random = new Random();
    	int randomAttack = random.nextInt(366); //arbitrary upper bound, can be adjusted if need be
    	
    	if (randomAttack > ship.getDefenseCapability()*diceInt) {
    		return takeGoods(ship);
    	}
    	return "attack_failed";
    }
    
    /** Method for taking goods from a ship object
     *  Called by attackShip(int, Ship) if player is lucky or not!
     *  
     * @param ship Ship object to have goods taken from
     * @return String for the result of taking ship goods. 
     */
    public static String takeGoods(Ship ship){
    	// public for testing
    	
    	Random random = new Random();
    	int randomGoodDemand = random.nextInt(120); //arbitrary upper bound, can be adjusted if need be
    	    			
    	while (randomGoodDemand > 0) {
    		 Item biggestItem = getLargestShipItem(ship);
    		 if (biggestItem == null || biggestItem.getSpaceTaken() < randomGoodDemand) {
    			 String gameOverMessage = "You have less goods than what the pirates demand. \n"
    	        			+ "You and your crew have to walk the plank! \n"
    	        			+ "GAME OVER!";
    	        	return gameOverMessage;
    		 }
    		 ship.takeItem(biggestItem.getName());
    		 randomGoodDemand -= biggestItem.getSpaceTaken();
    	}
    	return "attack_successful";
    }
    
    /** Method for rolling dice for a pirates random event
     * 
     * @return Integer for the result of rolling a die. A pseudo random number
     * between 1 and 6
     */
    public static int rollDice() {
    	// ask for ui to rollDie, then print out what number was rolled, should
    	// be a button in gui or entry in cmd line ui to roll a dice
    	
    	// here to make experience more interactive in the ui, like actually rolling a dice
    	Random random = new Random();
    	return random.nextInt(6) + 1;
    }

    /** Getter method for the largest Item object belonging to a ship.
     *  Searches Items array of ship. Used by takeGoods for taking Items from a ship.
     * 
     * @param ship Ship object that contains items to be searched
     * @return Item object that is the largest belonging to a ship 
     */
    public static Item getLargestShipItem(Ship ship) {
    	// public for testing
    	ArrayList<Item> itemsArrayList = ship.getItems();
    	int biggestSize = -1;
    	Item biggestItem = null;
    	for (int i = 0; i < itemsArrayList.size(); i++) {
    		// pirates cannot take upgrades, no capability for this in ship!
    		if (itemsArrayList.get(i).getSpaceTaken() > biggestSize) {
    			biggestItem = itemsArrayList.get(i);
    			biggestSize = biggestItem.getSpaceTaken();
    		}
    	}
    	return biggestItem;
    }
}
