package junitTests;

import static org.junit.jupiter.api.Assertions.*;  

import org.junit.jupiter.api.*;

import coreClasses.*;

/** JUnit tests for Ship
 * 
 * @author Hugo Phibbs
 *
 */
class ShipTest {
	
	private Ship testShip;
	private Player testPlayer;
	
	@BeforeEach
	void setUpBeforeClass() {
		
		testShip = new Ship("Black Pearl", 10,  5, 40);
		
		testPlayer = new Player("Jack Sparrow", 10000);
		
		testShip.setOwner(testPlayer);
	}
	
	@Test
	void takeDamageTest() {
		// Testing conditions
		// Boundary conditions: no damage, 100 damage
		// test with no upgrades equipped
		
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
		assertEquals(39, testShip.getHealthStatus()); //effective damage is 39
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
		Item newItem = new Item("Gold", 5, 10);
		testShip.addItem(newItem);
		assertEquals(true, testShip.getItems().size()==1); // check that item was addeds
	}
	
	@Test
	void addUpgradeTest() {	
		ShipUpgrade testUpgrade1 = new ShipUpgrade("Cannon", 2, 90, 20);
		testShip.addUpgrade(testUpgrade1);
		assertEquals(20, testShip.getDefenseCapability());
		testShip.addUpgrade(testUpgrade1);
		testShip.addUpgrade(testUpgrade1);
		assertEquals(40, testShip.getDefenseCapability()); // should now be maxed
	}
	
	@Test
	void upgradesToStringTest(){
		// Test when ship has no upgrades
		assertEquals("Ship isn't equipped with any upgrades yet", testShip.upgradesToString());
		
		// Test Normally
		ShipUpgrade testUpgrade1 = new ShipUpgrade("Canon(upgrade)", 2, 90, 20);
		ShipUpgrade testUpgrade2 = new ShipUpgrade("Armour(upgrade)", 2, 50, 10);
		ShipUpgrade testUpgrade3 = new ShipUpgrade("Crows-Nest(upgrade)", 2, 30, 3);
		testShip.addUpgrade(testUpgrade1);
		testShip.addUpgrade(testUpgrade2);
		testShip.addUpgrade(testUpgrade3);
		
		String expectedString = 
				"Black Pearl, has upgrades: \n"+
				"Canon, defense boost: 20, space taken: 2 \n"+
		        "Armour, defense boost: 10, space taken: 2 \n"+
				"Crows-Nest, defense boost: 3, space taken: 2 \n";
		assertEquals(expectedString,  testShip.upgradesToString());
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
	void routeWageCostTest() {
		// simple calculation, no real boundary conditions
		Route route1 = new Route("arb", 100, null, null);
		assertEquals(150, testShip.routeWageCost(route1));
	}
	
	@Test
	void calculateDaysSailingTest() {
		Route route1 = new Route("arb", 100, null, null);
		Route route2 = new Route("arb", 1, null, null);
		Route route3 = new Route("arb", 120, null, null);
		Ship testShip2  =  new Ship("Speedy Gonzales", 50, 0, 0);
		
		// Test with route distance/speed being a whole number
		assertEquals(2, testShip2.calculateDaysSailing(route1));
		// Test with route distance/speed being less than 1
		assertEquals(1, testShip2.calculateDaysSailing(route2));
		// Test with route distance/speed being above 1 but not a whole number
		assertEquals(3, testShip2.calculateDaysSailing(route3));
	}
	
	@Test
	void getRepairCostTest() {
		// simple calculation, no real boundary conditions
		testShip.takeDamage(50);
		assertEquals(35, testShip.repairCost());
	}
}
