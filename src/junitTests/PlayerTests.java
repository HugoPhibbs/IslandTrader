/**
 * 
 */
package junitTests;

import coreClasses.*; 
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** JUnit tests for Player
 * 
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
	
	@Test
	void testPurchasedItemsToString() {
		assertEquals("You haven't bought any items yet, you can buy items at any Store! \n",
				testPlayer.purchasedItemsToString());
		// Create an item and add to the players items.
		testPlayer.addPurchasedItem(new Item("Tomato", 2, 3));
		String expectedString = "All items that have been bought and their details: \nItem Tomato was bought "
				+ "for 3 Pirate Bucks and has not yet been sold to a store. \n";
		assertEquals(expectedString, testPlayer.purchasedItemsToString());
	}
	
	@Test
	void testPurchasedItemsToArray() {
		testPlayer.addPurchasedItem(new Item("Tomato", 2, 1));
		testPlayer.addPurchasedItem(new Item("Gold", 5, 25));
		String[][] testArray = new String[][] {{"Tomato", "1", "N/A"}, {"Gold", "25", "N/A"}};
		String[][] actual = testPlayer.purchasedItemsToArray();

		assertEquals("Tomato", actual[0][0]);
		assertEquals("1", actual[0][1]);
		assertEquals("N/A", actual[0][2]);
		
		
		assertEquals("Gold", actual[1][0]);
		assertEquals("25", actual[1][1]);
		assertEquals("N/A", actual[1][2]);	
	}
	
	
	@Test
	void testSpendMoney() {
		assertEquals(true, testPlayer.spendMoney(500));
		assertEquals(500, testPlayer.getMoneyBalance());
	
		assertEquals(false, testPlayer.spendMoney(501));
		assertEquals(false, testPlayer.spendMoney(-10));
		assertEquals(500, testPlayer.getMoneyBalance());
	}
}
