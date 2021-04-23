package junitTests;

import static org.junit.jupiter.api.Assertions.*; 


import org.junit.jupiter.api.*;

import coreClasses.*;

import java.util.*;

class ShipTest {
	
	private Ship testShip;
	private Player testPlayer;
	private Island testIsland;
	private Store testStore;

	@BeforeEach
	void setUpBeforeClass() {
		
		// Ship(String name, int speed, int crewSize, int maxUpgradeSpace, int maxCargoCapacity)
		testShip = new Ship("Black Pearl", 10,  4, 30, 20);
		
		testStore = new Store();
		testIsland = new Island("Shipwreck Cove", testStore, "Has alot of sand");
		testPlayer = new Player("Jack", testShip, 1000, 20, testIsland);
		
		testShip.setOwner(testPlayer);
	}
	
	@Test
	void takeDamageTest() {
		// Testing conditions
		// Boundary conditions: no damage, 100 damage
		assertEquals(false, testShip.takeDamage(0)); // Boundary Test
		assertEquals(true, testShip.takeDamage(100)); // Boundary test
	}
	
	@Test
	void payWagesTest() {

	}
	
	@Test 
	void repairShipTest() {
		
	}
	
	@Test
	void addItemTest() {
		Item newItem = new Item("Gold", 5, 10);
		// Tests:
		// adding an item that fills up cargo in one go
		// adding a first item that is more than cargo space 
		assertEquals(true, testShip.addItem(newItem));
	}
	
	@Test
	void addUpgradeTest() {	
		// TODO 
		// Max defense capability
		// No Space remaining in upgrade space - (equal anad more)
		ShipUpgrade testUpgrade1 = new ShipUpgrade("Cannon", 2, 90, 20);
		assertEquals(30, testShip.getRemainingUpgradeSpace());
		assertEquals(true, testShip.addUpgrade(testUpgrade1));
		assertEquals(20, testShip.getDefenseCapability());
		assertEquals(2, testShip.getOccupiedUpgradeSpace());
		assertEquals(28, testShip.getRemainingUpgradeSpace());
	}
	
	@Test
	void takeUpgradeTest() {
		
	}
	
	@Test
	void takeItemTest() {
		
	}
	
	@Test
	void getDailyWageCostTest() {
		
	}
	
	@Test
	void getRepairCostTest() {
		
	}
	
	@Test 
	void setDefenseCapabilityTest() {
		// Test conditions:
		// 0 input, 
		//assertEquals(true, testShip.setDefenseCapability(50));
		//assertThrows(IllegalArgumentException.class, () -> {testShip.setDefenseCapability(50);});
		//assertThrows(IllegalArgumentException.class, () -> {testShip.setDefenseCapability(0);});
	}
}
