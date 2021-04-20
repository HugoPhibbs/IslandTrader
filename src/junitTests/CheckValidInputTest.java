package junitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import gameSetupClasses.*;

/** JUnit testing class for CheckValidInputClass
 * 
 * @author Hugo Phibbs 
 * @version 15/4/2021
 * @since 15/4/2021
 */

class CheckValidInputTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@Test
	void nameIsValidTest() {
		assertEquals(true, CheckValidInputClass.nameIsValid("JACK"));
		assertEquals(true, CheckValidInputClass.nameIsValid("jackSparrow"));
		assertEquals(true, CheckValidInputClass.nameIsValid("Jack Sparrow"));
		assertEquals(false, CheckValidInputClass.nameIsValid("me"));					// too few characters
		assertEquals(false, CheckValidInputClass.nameIsValid("jacksparrowwwwww"));		// too many characters
		assertEquals(false, CheckValidInputClass.nameIsValid("the%"));					// special character
		assertEquals(false, CheckValidInputClass.nameIsValid("23jamie"));				// numbers present
		assertEquals(false, CheckValidInputClass.nameIsValid(""));
	}
	
	@Test
	void durationIsValidTest() {
		assertEquals(true, CheckValidInputClass.durationIsValid(20));
		assertEquals(true, CheckValidInputClass.durationIsValid(50));
		assertEquals(false, CheckValidInputClass.durationIsValid(19)); 		// duration too short
		assertEquals(false, CheckValidInputClass.durationIsValid(51)); 		// duration too long
		assertEquals(false, CheckValidInputClass.durationIsValid(-10));
	}
	
}
