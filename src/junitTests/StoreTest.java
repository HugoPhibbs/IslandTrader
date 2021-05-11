package junitTests;

import coreClasses.*;
import main.Main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;

class StoreTest {
	private Store testStore;
	private Player testPlayer;
	private Ship testShip;
	private GameEnvironment gameEnvironment;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		HashMap<String, HashMap<String, Integer>> testBuyCatalogue = Main.createBuyCatalogues().get(0);
		HashMap<String, HashMap<String, Integer>> testSellCatalogue = Main.createSellCatalogues().get(0);
		testStore = new Store("Bobs shack", "burgers", testBuyCatalogue, testSellCatalogue);
		testPlayer = new Player("Batman", 100);
		testShip = new Ship("Batmobile", 10, 10, 10);
		testPlayer.setShip(testShip);
	}

	@Test 
	void sellItemToPlayerTest() {
		/* Test:
		 * item not being in stores inventory
		 * item being an upgrade, being and not being able to sell
		 * not being able to sell an item
		 * being a regular item
		 */
		// Test with store not having desired item in possession
		assertThrows(IllegalStateException.class, () -> {testStore.sellItemToPlayer("burger", testPlayer);}); 
		
		// Test with player having no money
		testPlayer.spendMoney(testPlayer.getMoneyBalance()); 
		Item goldItem = testStore.sellItemToPlayer(null, "Gold", testPlayer);
		assertEquals(false, goldItem.getWithPlayer());
		
		// Test with player having the exact amount of money
		testPlayer.earnMoney(goldItem.getPlayerBuyPrice());
		assertEquals(true, testStore.sellItemToPlayer(null, "Gold", testPlayer).getWithPlayer());
		
		// Test with testPlayer ship not having enough space on board ship
		testPlayer.earnMoney(100);
		testPlayer.setShip(new Ship("Batmobile", 10, 10, 2));
		testPlayer.earnMoney(goldItem.getPlayerBuyPrice());
		assertEquals(false, testStore.sellItemToPlayer(null, "Silver", testPlayer).getWithPlayer());
		
		// Test with testPlayer ship having exact enough space on board ship
		testPlayer.setShip(new Ship("Batmobile", 10, 10, 3));
		assertEquals(true, testStore.sellItemToPlayer(null, "Silver", testPlayer).getWithPlayer());
	}
	
	@Test
	void canSellItemTest() {
		
	}
	
	@Test
	void canSellUpgradeTest() {
		
	}
	
	@Test
	void buyItemFromPlayerTest() {
		
	}
	
	@Test
	void canBuyItemTest() {
		
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
