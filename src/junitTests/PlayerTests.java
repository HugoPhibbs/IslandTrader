/**
 * 
 */
package junitTests;

import coreClasses.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jordan Vegar
 *
 */
class PlayerTests { 
	
	private Player testPlayer;
	private Ship testShip;
	
	
	@BeforeEach
	void createRequiredObjects() {
		testShip = new Ship("Black Pearl", 10,  5, 30);
		testPlayer = new Player("Jack Sparrow", 1000);
		testPlayer.setShip(testShip);
	}
	
	@Test
	void testMoneyBalanceToString() {
		assertEquals("You have a balance of: 1000 pirate bucks", testPlayer.moneyBalanceToString());
	}
	
}
