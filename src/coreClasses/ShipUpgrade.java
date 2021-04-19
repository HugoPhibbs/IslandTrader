package coreClasses;

/** Represents a ship upgrade
 * @author Hugo Phibbs
 * @version 14/2/2021
 * @since 2/4/2021
 */

public class ShipUpgrade extends Item{
    // just a note that we have made all quantities into integers to make everything simple--for report
	// NOTE: items of the same name MUST Have the same qualities as every other upgrade of the same name, apart from price
	// TODO how to implement the above line?
	
	// Class Variables
    private int defenseBoost; 
    
    /** Constructor method for ShipUpgrade object
     * 
     * @param name String for the name of ShipUpgrade
     * @param spaceTaken Integer for the space taken of ShipUpgrade
     * @param basePrice Integer for the base price of ShipUpgrade
     * @param defenseBoost Integer for the defense boost of a shipUpgrade
     */
    public ShipUpgrade(String name, int spaceTaken, int price, int defenseBoost){
    	// Call Item constructor
    	super(name, spaceTaken, price);
    	
    	// Check and add defenseBoost
    	if (defenseBoostIsValid(defenseBoost)) {this.defenseBoost = defenseBoost;}
    	else {throw new IllegalArgumentException("Defense boost must be above 0 and less than 50!");}  
    }
    
    /** Checks if a defenseBoost is valid, helper for constructor
     * 
     * @param defenseBoost Integer for defenseBoost to be checked
     * @return boolean if inputed defenseBoost is valid
     */
    private boolean defenseBoostIsValid(int defenseBoost) {
    	return (defenseBoost < 50 && defenseBoost > 0);
    }
    
    /** Getter method for the defense boost of ShipUpgrade object
     * 
     * @return Integer defenseBoost how much ShipUpgrade object benefits the defense of a ship
     */
    public int getDefenseBoost() { return defenseBoost; }    
}