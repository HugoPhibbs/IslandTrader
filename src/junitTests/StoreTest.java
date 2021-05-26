package junitTests;

import coreClasses.*; 

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
		HashMap<String, Integer> silverPropertiesBuyTest = new HashMap<String, Integer>();
		silverPropertiesBuyTest.put("spaceTaken", 3);
		silverPropertiesBuyTest.put("price", 4);
		HashMap<String, Integer> goldPropertiesBuyTest = new HashMap<String, Integer>();
		goldPropertiesBuyTest.put("spaceTaken", 5);
		goldPropertiesBuyTest.put("price", 10);
		HashMap<String, Integer> bananaPropertiesBuyTest = new HashMap<String, Integer>();
		bananaPropertiesBuyTest.put("spaceTaken", 1);
		bananaPropertiesBuyTest.put("price", 1);
		HashMap<String, Integer> bandagesPropertiesBuyTest = new HashMap<String, Integer>();
		bandagesPropertiesBuyTest.put("spaceTaken", 3);
		bandagesPropertiesBuyTest.put("price", 4);
		HashMap<String, Integer> limePropertiesBuyTest = new HashMap<String, Integer>();
		limePropertiesBuyTest.put("spaceTaken", 1);
		limePropertiesBuyTest.put("price", 1);
		
		HashMap<String, Integer> silverPropertiesSellTest = new HashMap<String, Integer>();
		silverPropertiesSellTest.put("spaceTaken", 3);
		silverPropertiesSellTest.put("price", 4);
		HashMap<String, Integer> goldPropertiesSellTest = new HashMap<String, Integer>();
		goldPropertiesSellTest.put("spaceTaken", 5);
		goldPropertiesSellTest.put("price", 10);
		HashMap<String, Integer> bananaPropertiesSellTest = new HashMap<String, Integer>();
		bananaPropertiesSellTest.put("spaceTaken", 1);
		bananaPropertiesSellTest.put("price", 1);
		HashMap<String, Integer> bandagesPropertiesSellTest = new HashMap<String, Integer>();
		bandagesPropertiesSellTest.put("spaceTaken", 3);
		bandagesPropertiesSellTest.put("price", 4);
		HashMap<String, Integer> limePropertiesSellTest = new HashMap<String, Integer>();
		limePropertiesSellTest.put("spaceTaken", 1);
		limePropertiesSellTest.put("price", 1);
		HashMap<String, Integer> canonPropertiesSellTest = new HashMap<String, Integer>();
		canonPropertiesSellTest.put("spaceTaken", 0);
		canonPropertiesSellTest.put("price", 50);
		canonPropertiesSellTest.put("defenseBoost", 10);

		HashMap<String, HashMap<String, Integer>> testBuyCatalogue = new HashMap<String, HashMap<String, Integer>>();
		testBuyCatalogue.put("Gold", goldPropertiesBuyTest);
		testBuyCatalogue.put("Silver", silverPropertiesBuyTest);
		testBuyCatalogue.put("Banana", bananaPropertiesBuyTest);
		testBuyCatalogue.put("Bandages", bandagesPropertiesBuyTest);
		testBuyCatalogue.put("Lime", limePropertiesBuyTest);

		HashMap<String, HashMap<String, Integer>> testSellCatalogue = new HashMap<String, HashMap<String, Integer>>();
		testSellCatalogue.put("Gold", goldPropertiesBuyTest);
		testSellCatalogue.put("Silver", silverPropertiesBuyTest);
		testSellCatalogue.put("Banana", bananaPropertiesBuyTest);
		testSellCatalogue.put("Bandages", bandagesPropertiesSellTest);
		testSellCatalogue.put("Lime", limePropertiesSellTest);
		testSellCatalogue.put("Canon(upgrade)", canonPropertiesSellTest);
		
		testPlayer = new Player("Batman", 0);
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
		expectedResultString = "Not all of the requested Items were sold to a Player! \nCan't sell Item(s), Player does not have enough money to buy this item!\n0 out of requested 1 Gold bought \nTotal cost of transaction: 0 Pirate Bucks \n";
		assertEquals(expectedResultString, resultString);
		
		// Test with player having the exact amount of money
		testPlayer.earnMoney(25); // price of gold at Cyrpus, will need to be changed if Cyprus' catalogue is changed
		testGameEnvironment.setMinMoneyToTravel(0);
		
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Gold", 1);
		expectedResultString = "1 out of requested 1 Gold bought \nTotal cost of transaction: 10 Pirate Bucks \n";
		assertEquals(expectedResultString, resultString);
		
		// Test with testPlayer ship not having enough space on-board ship to store items
		testPlayer.earnMoney(100); 
		testPlayer.setShip(new Ship("Batmobile", 10, 1, 2));
		testPlayer.getShip().setRemainingItemSpace(1); //Use setter method to directly modify the remaining Item space
		testGameEnvironment.setShip(testPlayer.getShip());
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Gold", 1);
		expectedResultString = "Not all of the requested Items were sold to a Player! \nCan't sell Item(s), Player does not have enough space to store item(s)!\n0 out of requested 1 Gold bought \nTotal cost of transaction: 0 Pirate Bucks \n";
		assertEquals(expectedResultString, resultString);;
		
		// Test with upgrades
		// An upgrade that maxes out defense capability
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Canon(upgrade)", 1);
		expectedResultString = "1 out of requested 1 Canon(upgrade) bought \nTotal cost of transaction: 50 Pirate Bucks \nYour defense capability is maxed at 2! \n";
		assertEquals(expectedResultString, resultString);
		
		// An upgrade that can't be sold because the defense capability of a ship is already maxed
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Canon(upgrade)", 1);
		expectedResultString = "Not all of the requested Items were sold to a Player! \nCan't sell Upgrade(s), Ship already has max defense Capability\n0 out of requested 1 Canon(upgrade) bought \nTotal cost of transaction: 0 Pirate Bucks \nYour defense capability is maxed at 2! \n";
		assertEquals(expectedResultString, resultString);
		
		// Test normally with upgrades
		testPlayer.setShip(new Ship("Green Hornet", 10, 10, 30));
		testPlayer.earnMoney(100);
		testGameEnvironment.setShip(testPlayer.getShip());
		resultString = testStore.sellItemsToPlayer(testGameEnvironment, "Canon(upgrade)", 2);
		expectedResultString = "2 out of requested 2 Canon(upgrade) bought \nTotal cost of transaction: 100 Pirate Bucks \nYour defense capability is now 20! \n";
		assertEquals(expectedResultString, resultString);
	}
	
	@Test
	void canSellUpgradeTest() {
		String resultString = "";
		String expectedResultString = "";
		
		// Test with player ship having a max defense capability
		testPlayer.setShip(new Ship("mountain tiger", 0, 0, 40));
		ShipUpgrade testUpgrade1 = new ShipUpgrade("cannon", 1, 1, 40);
		ShipUpgrade testUpgrade2 = new ShipUpgrade("cannon", 1, 1, 10);
		testPlayer.getShip().addUpgrade(testUpgrade1);
		testPlayer.getShip().addUpgrade(testUpgrade2);
		testGameEnvironment.setShip(testPlayer.getShip());
		resultString = Store.canSellUpgradeToPlayer(testGameEnvironment, testUpgrade1);
		expectedResultString = "Can't sell Upgrade(s), Ship already has max defense Capability";
		assertEquals(expectedResultString, resultString);
		
		// Test with player ship having less than max defense capability
		testPlayer.setShip(new Ship("mountain tiger", 0, 0, 40));
		testPlayer.earnMoney(100); // To avoid triggering liquidation clause
		ShipUpgrade testUpgrade3 = new ShipUpgrade("cannon", 1, 1, 29);
		testPlayer.getShip().addUpgrade(testUpgrade3);
		testGameEnvironment.setShip(testPlayer.getShip());
		resultString = Store.canSellUpgradeToPlayer(testGameEnvironment, testUpgrade2);
		expectedResultString = "Can sell";
		assertEquals(expectedResultString, resultString); 
	
		// Set up a mock game
		GameEnvironment testGameEnvironment2 = new GameEnvironment(null, null, null, null, null);
		Player testPlayer2 = new Player("David", 110);
		testPlayer2.setShip(new Ship("mountain tiger", 0, 0, 40));
		testGameEnvironment2.setPlayer(testPlayer2);
		testGameEnvironment2.setShip(testPlayer2.getShip());
		testGameEnvironment2.setMinMoneyToTravel(100);
		HashMap<String, HashMap<String, Integer>> testSellCatalogue = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> testSailsProperties = new HashMap<String, Integer>();
		testSailsProperties.put("price", 20);
		testSailsProperties.put("spaceTaken", 0);
		testSailsProperties.put("defenseBoost", 10);
		testSellCatalogue.put("Sails(upgrade)", testSailsProperties);
		Store testStore2 = new Store("Krusty crab", "pies", testSellCatalogue, null);
		Island testIsland2 = new Island("Bikini Bottom", testStore2, "Stuff");
		testGameEnvironment2.setCurrentIsland(testIsland2);
		
		// Test with (liquidWorth - upgradePrice < minMoneyToTravel)
		resultString = testStore2.sellItemsToPlayer(testGameEnvironment2, "Sails(upgrade)", 1);
		expectedResultString = "Not all of the requested Items were sold to a Player! \n"+
				"Can't sell Upgrades(s), Player wouldn't be able to travel anywhere if Upgrade was bought!\n"+
				"0 out of requested 1 Sails(upgrade) bought \n"+
				"Total cost of transaction: 0 Pirate Bucks \n"+
				"Your defense capability is now 0! \n";
		assertEquals(expectedResultString, resultString);
		
		// Test with (liquidWorth - upgradePrice = minMoneyToTravel)
		testGameEnvironment2.setMinMoneyToTravel(90);
		resultString = testStore2.sellItemsToPlayer(testGameEnvironment2, "Sails(upgrade)", 1);
		expectedResultString = "1 out of requested 1 Sails(upgrade) bought \n"+
				"Total cost of transaction: 20 Pirate Bucks \n"+
				"Your defense capability is now 10! \n";
		assertEquals(expectedResultString, resultString);
	}
	
	@Test
	void buyItemsFromPlayerTest() {
		String resultString = "";
		String expectedResultString = "";
		
		// Test with player having item in possession
		testPlayer.earnMoney(100);
		testStore.sellItemsToPlayer(testGameEnvironment, "Gold", 1);
		resultString = testStore.buyItemsFromPlayer("Gold", testPlayer, 1);
		assertEquals("1 out of a requested 1 Gold was sold to the store \nTotal monetary gain: 10", resultString);
		
		// Test with player not having item in possession
		resultString = testStore.buyItemsFromPlayer("Silver", testPlayer, 1);
		expectedResultString = "Not all of the requested Items were bought from a Player! \nPlayer does not have this item in possession!\n0 out of a requested 1 Silver was sold to the store \nTotal monetary gain: 0";
		assertEquals(expectedResultString, resultString);
		
		// Test with a supposed Item that isnt been sold by a Store
		assertThrows(IllegalArgumentException.class, () -> {testStore.buyItemsFromPlayer("burger", testPlayer, 1);}); 
	}
	
	@Test
	void checkPlayerWantsToBuyItemTest() {
		
	}
	
	@Test
	void chosenNameTest() {
		String[] displayArray = new String[] {"Canon(upgrade) has price 2 and space taken 3", "Treasure-Map has price 5 and space taken 8"};
		assertEquals("Canon(upgrade)", Store.chosenItemName(displayArray, 1));
		assertEquals("Treasure-Map", Store.chosenItemName(displayArray, 2));
	}
}
