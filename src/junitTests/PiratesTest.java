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
		 *      0  1  2  3  4
		 *    1 F  F  F  F  F
		 *    2 F  F  F  F  S
		 * DR 3 F  F  F  S  S
		 *    4 F  F  S  S  S
		 *    5 F  S  S  S  S
		 *    6 S  S  S  S  S
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
	void takeGoodsTest() {
		Ship testShip = new Ship("Queen Mary", 0, 10, 0);
		Pirates testPirates = new Pirates(100);
		// Test with ship having no Items
		assertEquals("game_over", testPirates.takeGoods(testShip));
		
		// Test with ship having items more than the wanted amount by the pirates
		testShip.addItem(new Item("Football", 5, 50));
		testShip.addItem(new Item("Cherries", 5, 60));
		assertEquals("attack_successful", testPirates.takeGoods(testShip));
		assertEquals(0, testShip.getItems().size());
		
		// Test with ship having the exact value that the pirates want
		testShip.addItem(new Item("Drink-Bottle", 5, 50));
		testShip.addItem(new Item("Laptop", 4, 50));
		assertEquals("attack_successful", testPirates.takeGoods(testShip));
		assertEquals(0, testShip.getItems().size());
		
		// Test with ship having less than the value that the pirates want
		testShip.addItem(new Item("Toothbrush", 5, 40));
		testShip.addItem(new Item("Rubix-Cube", 4, 50));
		assertEquals("game_over", testPirates.takeGoods(testShip));
		assertEquals(0, testShip.getItems().size());
	}
}
