package coreClasses;

import java.util.Random; 

/** Represents a pirates random event
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 17/5/2021
 * @since 2/4/2021
 */
public class Pirates {
	/**The minimum value of goods you must have to satisfy the pirates.*/
	private int goodDemandValue;
	
	/** Constructor method for a Pirates random event
	 * 
	 * @param maxDamage Integer for the max damage that Pirates random event can impose onto a Ship object 
	 */
	public Pirates(int goodDemandValue) {
		this.goodDemandValue = goodDemandValue;
	}
   
    /** Method for attacking a ship object
     * Damage is based on chance and the dice number rolled by player
     * 
     * @param diceInt Integer for the dice value rolled by a player
     * @param ship Ship object to be attacked
     * @return String representation for the outcome of an attack
     */
    public String attackShip(int diceInt, Ship ship) {
    	/* So for a defense capability of 0< <=10, a dice roll of 6 will be certain to win
    	 * and then for a defense capability 10< <=20 a dice roll of 6 AND 5 will be certain to win
    	 * 
    	 * all the way down to when defense Capability of 40< <=50 and every dice roll should be certain to win
    	 */
    	
    	if (!((ship.getDefenseCapability() / 10) >= (6-diceInt))){
    		return takeGoods(ship);
    	}
    	return "attack_failed";
    }
    
    /** Method for taking goods from a ship object
     *  Called by attackShip(int, Ship) if player is
     *   lucky or not!
     *  
     * @param ship Ship object to have goods taken from
     * @return String for the result of taking ship goods. 
     */
    public String takeGoods(Ship ship) {
    	
    	int totalValueStolen = 0;
    	
    	// Take items from the ship until there is none left
    	while (ship.getItems().size() > 0) {
    		Item item = ship.getItems().get(0);
    		totalValueStolen += item.getPlayerBuyPrice();
    		ship.takeItem(item.getName());
    	}
    	
    	/* If the value taken is less than they demand, then user can continue with travel
    	 * otherwise the game is over
    	 */
    	if (totalValueStolen < goodDemandValue) {
    		return "game_over";
    	} else {
    		// Replenish the remaining upgrade space of a ship
    		ship.setRemainingItemSpace(ship.getMaxItemSpace());
    		return "attack_successful";
    	}
  
    }
    
    /** Method for rolling dice for a pirates random event
     * 
     * @return Integer for the result of rolling a die. A pseudo random number
     * between 1 and 6
     */
    public int rollDice() {
    	// ask for ui to rollDie, then print out what number was rolled, should
    	// be a button in gui or entry in cmd line ui to roll a dice
    	
    	// here to make experience more interactive in the ui, like actually rolling a dice
    	Random random = new Random();
    	return random.nextInt(6) + 1;
    }
    
	/** Getter method for the description of a pirates random event.
	 * Called before Pirates class methods by other classes
	 * 
	 * @return String representation of the unfortunate weather event
	 */
    public static String getDescription(){
    	return "You have encountered pirates, roll the die to play your chances!";
    }
}
