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
		// Use the buy and sell catalogues from the main class for Cyprus.
		HashMap<String, HashMap<String, Integer>> testBuyCatalogue = Main.createBuyCatalogues().get(0);
		HashMap<String, HashMap<String, Integer>> testSellCatalogue = Main.createSellCatalogues().get(0);
		
		testPlayer = new Player("Batman", 100);
		testShip = new Ship("Batmobile", 10, 10, 10);
		testPlayer.setShip(testShip);
		
		// Create a test island and store, based off of Cyprus
		this.testStore = new Store("War Goods", "Ship upgrades", testSellCatalogue, testBuyCatalogue); 
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
		
		// Test with testPlayer ship not having enough space on-board ship to store items
		testPlayer.earnMoney(100); 
		testPlayer.setShip(new Ship("Batmobile", 10, 1, 2));
		testPlayer.getShip().setRemainingItemSpace(1); //Use setter method to directly modify the remaining Item space
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Gold", 1);
		expectedResultString = "Not all of the requested items were sold to a player! \nCan't sell Item(s), Player does not have enough space to store item(s)!\n0 out of requested 1 Gold bought \nTotal cost of transaction: 0 Pirate Bucks \n";
		assertEquals(expectedResultString, resultString);;
		
		// Test with upgrades
		// An upgrade that maxes out defense capability
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Canon(upgrade)", 1);
		expectedResultString = "1 out of requested 1 Canon(upgrade) bought \nTotal cost of transaction: 50 Pirate Bucks \nYour defense capability is maxed at 2! \n";
		assertEquals(expectedResultString, resultString);
		
		// An upgrade that can't be sold because the defense capability of a ship is already maxed
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Canon(upgrade)", 1);
		expectedResultString = "Not all of the requested items were sold to a player! \nCan't sell Upgrade(s), Ship already has max defense Capability\n0 out of requested 1 Canon(upgrade) bought \nTotal cost of transaction: 0 Pirate Bucks \nYour defense capability is maxed at 2! \n";
		assertEquals(expectedResultString, resultString);
		
		// Test normally with upgrades
		testPlayer.setShip(new Ship("Green Hornet", 10, 10, 30));
		testPlayer.earnMoney(100);
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Canon(upgrade)", 2);
		expectedResultString = "2 out of requested 2 Canon(upgrade) bought \nTotal cost of transaction: 100 Pirate Bucks \nYour defense capability is now 20! \n";
		assertEquals(expectedResultString, resultString);
		
		// Test gameEnvironment.calculateLiquidValue() - itemToSell.getPlayerBuyPrice() + sellCatalogue.get(itemToSell.getName()).get("price") < gameEnvironment.getMinMoneyToTravel()
		
		// 
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
		
		// Test with a supposed Item that isnt been sold by a Store
		assertThrows(IllegalArgumentException.class, () -> {testStore.buyItemsFromPlayer("burger", testPlayer, 1);}); 
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
	void chosenNameTest() {
		String[] displayArray = new String[] {"Canon(upgrade) has price 2 and space taken 3", "Treasure-Map has price 5 and space taken 8"};
		assertEquals("Canon(upgrade)", Store.chosenItemName(displayArray, 1));
		assertEquals("Treasure-Map", Store.chosenItemName(displayArray, 2));
	}
}
