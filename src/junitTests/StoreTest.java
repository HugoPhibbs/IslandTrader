package junitTests;

import coreClasses.*;
import main.Main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;

/** JUnit tests for Store
 * 
 * @author Hugo Phibbs
 *
 */
class StoreTest {
	private Store testStore;
	private Island testIsland;
	private Player testPlayer;
	private Ship testShip;
	private GameEnvironment testGameEnvironment;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		HashMap<String, HashMap<String, Integer>> testBuyCatalogue = Main.createBuyCatalogues().get(0);
		HashMap<String, HashMap<String, Integer>> testSellCatalogue = Main.createSellCatalogues().get(0);
		testPlayer = new Player("Batman", 100);
		testShip = new Ship("Batmobile", 10, 10, 10);
		testPlayer.setShip(testShip);
		
		// Create a test island and store, based off of Cyprus
		this.testStore = new Store("War Goods", "Ship upgrades", testBuyCatalogue, testSellCatalogue); 
		this.testIsland = new Island("Cyprus", testStore, "arb description");
		testStore.setStoreIsland(testIsland);
		
		testGameEnvironment = new GameEnvironment(null, null, null, null, null);
		testGameEnvironment.setPlayer(testPlayer);
		testGameEnvironment.setShip(testShip);
		testGameEnvironment.setCurrentIsland(testIsland);
	}

	@Test 
	void sellItemsToPlayerTest() {
		/* Test:
		 * item not being in stores inventory
		 * item being an upgrade, being and not being able to sell
		 * not being able to sell an item
		 * being a regular item
		 */
		
		// Test with store not having desired item in possessionexpectedResultString
		assertThrows(IllegalArgumentException.class, () -> {testStore.sellItemsToPlayer(testGameEnvironment, "burger", 1);}); 
		
		String resultString;
		String expectedResultString = "";

		// Test with player having no money
		testPlayer.spendMoney(testPlayer.getMoneyBalance()); 
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Gold", 1);
		expectedResultString = "Can't sell Item(s), Player does not have enough money to buy this item!\n0 out of requested 1 Gold bought \nTotal cost of transaction: 0 Pirate Bucks \n";
		assertEquals(expectedResultString, resultString);
		
		// Test with player having the exact amount of money
		testPlayer.earnMoney(3); // price of gold at Cyrpus, will need to be changed if Cyprus' catalogue is changed
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Gold", 1);
		expectedResultString = "1 out of requested 1 Gold bought \nTotal cost of transaction: 3 Pirate Bucks \n";
		assertEquals(expectedResultString, resultString);
		
		// Test with testPlayer ship not having enough space on board ship
		testPlayer.earnMoney(100); 
		testPlayer.setShip(new Ship("Batmobile", 10, -1, 2));
		testPlayer.getShip().setRemainingItemSpace(1); //Use setter method to directly modify the remaining Item space
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Gold", 1);
		expectedResultString = "Can't sell Item(s), Player does not have enough space to store item(s)!\n0 out of requested 1 Gold bought \nTotal cost of transaction: 0 Pirate Bucks \n";
		assertEquals(expectedResultString, resultString);;
		
		// Test with testPlayer ship having exact enough space on board ship
		testPlayer.setShip(new Ship("Batmobile", 10, 10, 3));
		assertEquals(expectedResultString, resultString);
		
		// Test with testPlayer not having enough liquid cash to be able to sail anywhee
		// TODO implement!
		
		/* Test with selling upgrades to a player
		 * dont need to test all the same things as we did with item, only code specific for upgrades
		 */
		
		testPlayer.setShip(new Ship("mountain tiger", 0, 0, 50));
		testStore.buyItemsFromPlayer("Canon(upgrade)", testPlayer, 1);
	}
	
	@Test
	void canSellItemToPlayerTest() {
		// Most of it has been tested above
		// Test with testPlayer not having enough liquid cash to be able to sail anywhee
		// TODO implement!
		
	}
	
	@Test
	void canSellUpgradeTest() {
		String resultString = "";
		String expectedResultString = "";
		
		// Test with player ship having a max defense capability
		testPlayer.setShip(new Ship("mountain tiger", 0, 0, 50));
		ShipUpgrade testUpgrade1 = new ShipUpgrade("cannon", 1, 1, 40);
		ShipUpgrade testUpgrade2 = new ShipUpgrade("cannon", 1, 1, 10);
		testPlayer.getShip().addUpgrade(testUpgrade1);
		testPlayer.getShip().addUpgrade(testUpgrade2);
		resultString = Store.canSellUpgradeToPlayer(testPlayer);
		expectedResultString = "Can't sell Upgrade(s), Ship already has max defense Capability";
		assertEquals(expectedResultString, resultString);
		
		// Test with player ship having less than max defense capability
		testPlayer.setShip(new Ship("mountain tiger", 0, 0, 50));
		ShipUpgrade testUpgrade3 = new ShipUpgrade("cannon", 1, 1, 40);
		testPlayer.getShip().addUpgrade(testUpgrade3);
		resultString = Store.canSellUpgradeToPlayer(testPlayer);
		expectedResultString = "Can sell";
		assertEquals(expectedResultString, resultString);
	}
	
	@Test
	void buyItemsFromPlayerTest() {
		String resultString = "";
		String expectedResultString = "";
		
		// Test with player having item in possession
		testStore.sellItemsToPlayer(testGameEnvironment, "Gold", 1);
		resultString = testStore.buyItemsFromPlayer("Gold", testPlayer, 1);
		assertEquals("1 out of a requested 1 Gold was sold to the store \nTotal monetary gain: 10", resultString);
		
		// Test with player not having item in possession
		resultString = testStore.buyItemsFromPlayer("Silver", testPlayer, 1);
		expectedResultString = "Player does not have this item in possession!\n0 out of a requested 1 Silver was sold to the store \nTotal monetary gain: 0";
		assertEquals(expectedResultString, resultString);
	}
	
	@Test
	void canBuyItemFromPlayerTest() {
		// tested in buyItemFromPlayerTest()
	}
	
	@Test 
	void catalogueToArrayListTest() {
		
	}
	
	@Test
	void catalogueToString() {
		
	}
	
	@Test
	void getChosenNameTest() {
		
	}
}
