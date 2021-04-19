/**
 * 
 */
package junitTests;

import gameSetupClasses.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author Jordan Vegar
 *
 */
class PlayerTests { 

	@Test
	void nameIsValidTest() {
		assertEquals(true, Player.nameIsValid("JACK"));
		assertEquals(true, Player.nameIsValid("jackSparrow"));
		assertEquals(true, Player.nameIsValid("Jack Sparrow"));
		assertEquals(false, Player.nameIsValid("me"));					// too few characters
		assertEquals(false, Player.nameIsValid("jacksparrowwwwww"));		// too many characters
		assertEquals(false, Player.nameIsValid("the%"));					// special character
		assertEquals(false, Player.nameIsValid("23jamie"));				// number
	}
	
	@Test
	void durationIsValidTest() {
		assertEquals(true, Player.durationIsValid(20));
		assertEquals(true, Player.durationIsValid(50));
		assertEquals(false, Player.durationIsValid(19)); 		// duration too short
		assertEquals(false, Player.durationIsValid(51)); 		// duration too long
		assertEquals(false, Player.durationIsValid(-10));
	}
	
	// constructor test requires ship
	@Test
	void constructorTest() {
		
		
	}

}
