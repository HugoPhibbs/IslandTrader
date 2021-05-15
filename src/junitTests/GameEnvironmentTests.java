package junitTests;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import coreClasses.*;

/**
 * @author Jordan Vegar
 *
 */
class GameEnvironmentTests {
	
	private GameEnvironment testGame;
	
	@BeforeEach
	void getIsland() {
		Ship testShip = new Ship("Black Pearl", 10,  5, 30);
		Player testPlayer = new Player("Jack Sparrow", 1000);
		
	}
	
	@Test
	void testGetCost() {
		
	}
	
	@Test
	void testGetOtherIslands() {
		
	}
	
	// should we test getShipDescriptionArrayList()?
	
	@Test
	void testGetScore() {
		
	}
	
	@Test
	void testMinMoneyRequired() {
		
	}
	
	@Test
	void testGetLiquidValue() {
		
	}
	
	
	
	
}

