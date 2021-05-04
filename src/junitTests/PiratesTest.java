package junitTests;

import coreClasses.Ship;
import coreClasses.Item;
import coreClasses.Pirates;
import coreClasses.ShipUpgrade;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PiratesTest {
	
	@Test
	void attackShipTest() {
		Ship ship1 = new Ship("Black Pearl", 100, 10, 100, 100);
		ShipUpgrade upgrade1 = new ShipUpgrade("Canon", 10, 10, 10);
		ShipUpgrade upgrade2 = new ShipUpgrade("Crows Nest", 10, 10, 5);
		ShipUpgrade upgrade3 = new ShipUpgrade("Armour", 10, 10, 7);
		ship1.addUpgrade(upgrade1);
		ship1.addUpgrade(upgrade2);
		ship1.addUpgrade(upgrade3);
		
		// TODO how to test code that relies on chance?
	}

	@Test 
	void rollDiceTest() {
		
	}
	
	@Test
	void takeGoodsTest() {
		
	}
	
	@Test
	void getLargestShipItemTest() {
		/* boundary conditions:
		 * equal size items
		 */
		Ship ship1 = new Ship("Black Pearl", 100, 10, 100, 100);
		Item item1 = new Item("Gold", 10, 10);
		Item item2 = new Item("Silver", 7, 10);
		Item item3 = new Item("Large Chest", 20, 10);
		Item item4 = new Item("Bananas", 5, 10);
		Item item5 = new Item("Bottle of Rum", 3, 10);
		ship1.addItem(item1);
		ship1.addItem(item2);
		ship1.addItem(item3);
		ship1.addItem(item4);
		ship1.addItem(item5);
		assertEquals(item3, Pirates.getLargestShipItem(ship1));
		ship1.takeItem(item3.getName());
		ship1.takeItem(item1.getName());
		assertEquals(item2, Pirates.getLargestShipItem(ship1));
		
		Ship ship2 = new Ship("Bat Mobile", 100, 10, 100, 100);
		assertEquals(null, Pirates.getLargestShipItem(ship2)); // no items belonging to Ship
	}
}
