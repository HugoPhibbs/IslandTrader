package junitTests;

import static org.junit.jupiter.api.Assertions.*;  

import org.junit.jupiter.api.*;

import coreClasses.*;

class ShipTest {
	
	private Ship testShip;
	private Player testPlayer;
	private Island testIsland;
	private Store testStore;

	@BeforeEach
	void setUpBeforeClass() {
		
		// Ship(String name, int speed, int crewSize, int maxUpgradeSpace, int maxCargoCapacity)
		testShip = new Ship("Black Pearl", 10,  5, 30);
		
		testStore = new Store("Bobs shack", "Burgers", null, null);
		testIsland = new Island("Shipwreck Cove", testStore, "Has alot of sand");
		testPlayer = new Player("Jack Sparrow", 10000);
		
		testShip.setOwner(testPlayer);
	}
	
	@Test
	void takeDamageTest() {
		// Testing conditions
		// Boundary conditions: no damage, 100 damage
		// test with no upgrades equipped
		
		// REPORT with testing i realised that i needed to cast to a float when doing division otherwise it is just int division
		testShip.takeDamage(0);
		assertEquals(100, testShip.getHealthStatus()); // Boundary Test
		testShip.repairShip();
		testShip.takeDamage(100);
		assertEquals(0, testShip.getHealthStatus()); // Boundary test
		
		testShip.repairShip();
		ShipUpgrade newUpgrade1 = new ShipUpgrade("Canon(upgrade)", 10, 10, 40);
		testShip.addUpgrade(newUpgrade1);
		testShip.takeDamage(167);
		assertEquals(0, testShip.getHealthStatus()); //effective damage is 0.6*167, which is more than 100
		testShip.repairShip();
		ShipUpgrade newUpgrade2 = new ShipUpgrade("Canon(upgrade)", 10, 10, 10);
		testShip.addUpgrade(newUpgrade2);
		testShip.takeDamage(100);
		assertEquals(50.0, testShip.getHealthStatus()); //effective damage is 50
	}
	
	@Test
	void payWagesTest() {
		Route route1 = new Route("arb", 100, null, null);
		assertEquals(true, testShip.payWages(route1, testPlayer));
		testPlayer.spendMoney(testPlayer.getMoneyBalance());
		assertEquals(false, testShip.payWages(route1, testPlayer));
	}
	
	@Test 
	void repairShipTest() {
		testShip.takeDamage(50);
		assertEquals(true, testShip.repairShip());
		testShip.takeDamage(50);
		testPlayer.spendMoney(testPlayer.getMoneyBalance());
		assertEquals(false, testShip.repairShip());
	}
	
	@Test
	void addItemTest() {
		// simple process, easily to test. makes testing code as a whole really easy when things are put into methods!, you can test seperately
		Item newItem = new Item("Gold", 5, 10);
		testShip.addItem(newItem);
		assertEquals(true, testShip.getItems().size()==1); // check that item was added
	}
	
	@Test
	void addUpgradeTest() {	
		// TODO 
		// Max defense capability
		// No Space remaining in upgrade space - (equal anad more)
		ShipUpgrade testUpgrade1 = new ShipUpgrade("Cannon", 2, 90, 20);
		testShip.addUpgrade(testUpgrade1);
		assertEquals(20, testShip.getDefenseCapability());
		testShip.addUpgrade(testUpgrade1);
		testShip.addUpgrade(testUpgrade1);
		assertEquals(50, testShip.getDefenseCapability()); // should now be maxed
	}
	
	
	@Test
	void takeItemTest() {
		Item newItem = new Item("Gold", 5, 10);
		testShip.addItem(newItem);
		assertEquals(newItem, testShip.takeItem("Gold"));
		testShip.addItem(newItem);
		assertEquals(null, testShip.takeItem("gold")); // shouldnt take anything, name isnt correct
	}
	
	@Test
	void getRouteWageCostTest() {
		// simple calculation, no real boundary conditions
		Route route1 = new Route("arb", 100, null, null);
		assertEquals(250, testShip.routeWageCost(route1));
	}
	
	@Test
	void getRepairCostTest() {
		// simple calculation, no real boundary conditions
		testShip.takeDamage(50);
		assertEquals(500, testShip.repairCost());
	}
}
