package coreClasses;

import java.util.Random;

/** Represents an unfortunate weather random event
 * 
 * @author Hugo Phibbs
 * @version 8/5/2021
 * @since 2/4/2021
 */
public class UnfortunateWeather{

	/** Getter method for the description of an unfortunate weather random event
	 * 
	 * @return String representation of the unfortunate weather event
	 */
	public static String getDescription() {
		return "You have entered a dangerous storm, you are at the mercy of the weather!";
	}
	
	/** Method for damaging a ship object
	 * imparts damage up to 99 as to avoid reducing ship health to 0
	 * 
	 * @param ship Ship object to be damaged
	 */
	public static int damageShip(Ship ship) {
		// Create a random int between 0 and 99, this is the damage imparted onto a Ship
		Random random = new Random();
		int damage = random.nextInt(100); // max damage of 99, can't end game
		ship.takeDamage(damage);
		return damage;
	}
}

