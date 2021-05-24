package junitTests;

import coreClasses.Ship; 
import coreClasses.Item;
import coreClasses.Pirates;
import coreClasses.ShipUpgrade;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** JUnit Tests for Pirates
 * 
 * @author Hugo Phibbs
 *
 */
class PiratesTest {
	
	@Test
	void attackShipTest() {
		/* So expected result matrix:
		 *            DC//10  
		 *      0  1  2  3  4  5
		 *    1 F  F  F  F  F  5
		 *    2 F  F  F  F  S  S
		 * DR 3 F  F  F  S  S  S
		 *    4 F  F  S  S  S  S
		 *    5 F  S  S  S  S  S
		 *    6 S  S  S  S  S  S
		 * 
		 */
		Ship testShip1 = new Ship("BatMobile", 0, 10, 50);
		Pirates testPirates = new Pirates(0); // Keep it to zero to ensure that we have a black and white picture of what the if statement is doing
		
		/* Test with a ship having zero defense capability but with a dice roll of 6 */
		assertEquals("attack_failed", testPirates.attackShip(6, testShip1));
		assertEquals("attack_successful", testPirates.attackShip(5, testShip1));
		
		ShipUpgrade upgrade1 = new ShipUpgrade("Canon", 10, 10, 10);		
		ShipUpgrade upgrade2 = new ShipUpgrade("Crows Nest", 10, 10, 5);
		ShipUpgrade upgrade3 = new ShipUpgrade("Armour", 10, 10, 7);
		testShip1.addUpgrade(upgrade1);
		testShip1.addUpgrade(upgrade2);
		testShip1.addUpgrade(upgrade3);
		/* Test with ship having a defense capability of 22, so a dice roll of 4, 5, 6 should be enough for ship to survive */
		assertEquals("attack_failed", testPirates.attackShip(4, testShip1));
		assertEquals("attack_failed", testPirates.attackShip(6, testShip1));
		assertEquals("attack_successful", testPirates.attackShip(3, testShip1));
		assertEquals("attack_successful", testPirates.attackShip(1, testShip1));
		
		ShipUpgrade upgrade4 = new ShipUpgrade("Canon", 10, 10, 30);
		testShip1.addUpgrade(upgrade4); // add this upgrade to max the defense capability of a ship
		/* Test with ship having max in-game defense capability of 50, every dice int should now be enough to fend off pirates */
		assertEquals("attack_failed", testPirates.attackShip(6, testShip1));
		assertEquals("attack_failed", testPirates.attackShip(4, testShip1));
		assertEquals("attack_failed", testPirates.attackShip(1, testShip1));
	}
	
	@Test
	void getLargestShipItemTest() {
		/* boundary conditions:
		 * equal size items
		 */
		Ship ship1 = new Ship("Black Pearl", 10, 10, 20);
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
		
		Ship ship2 = new Ship("Bat Mobile", 100, 10, 50);
		assertEquals(null, Pirates.getLargestShipItem(ship2)); // no items belonging to Ship
	}
}
