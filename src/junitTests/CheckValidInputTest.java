package junitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;  

import org.junit.jupiter.api.*;

import coreClasses.*;

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
		assertEquals(true, CheckValidInput.nameIsValid("JACK"));
		assertEquals(true, CheckValidInput.nameIsValid("jackSparrow"));
		assertEquals(true, CheckValidInput.nameIsValid("Jack Sparrow"));
		assertEquals(false, CheckValidInput.nameIsValid("me"));					// too few characters
		assertEquals(false, CheckValidInput.nameIsValid("jacksparrowwwwww"));		// too many characters
		assertEquals(false, CheckValidInput.nameIsValid("the%"));					// special character
		assertEquals(false, CheckValidInput.nameIsValid("23jamie"));				// numbers present
		assertEquals(false, CheckValidInput.nameIsValid(""));
	}
	
	@Test
	void durationIsValidTest() {
		assertEquals(true, CheckValidInput.durationIsValid(20));
		assertEquals(true, CheckValidInput.durationIsValid(50));
		assertEquals(false, CheckValidInput.durationIsValid(19)); 		// duration too short
		assertEquals(false, CheckValidInput.durationIsValid(51)); 		// duration too long
		assertEquals(false, CheckValidInput.durationIsValid(-10));
	}
}
