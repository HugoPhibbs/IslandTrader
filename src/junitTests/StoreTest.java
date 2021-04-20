package junitTests;

import coreClasses.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StoreTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@Test
	void getDisplayStringTest() {
		//TODO what else to test?
		Item item1 = new Item("Gold", 1, 5);
		Item item2 = new Item("Silver", 1, 8);
		Item item3 = new Item("Carrot", 4, 3);
		ArrayList<Item> itemArrayList = new ArrayList<Item>(Arrays.asList(item1, item2, item3));
		//result += String.format("%d. %s for %d \n", i+1, currItem.getName(), currItem.getPrice());
		String result = "1. Gold for 5 Pirate Bucks \n2. Silver for 8 Pirate Bucks \n3. Carrot for 3 Pirate Bucks";
		assertEquals(result, Store.getDisplayString(itemArrayList));
	}

}
