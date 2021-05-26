package junitTests;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import coreClasses.*;

/** JUnit tests for Route 
 * 
 * @author Jordan Vegar
 *
 */
class routeTests {
	
	private Route testRoute;

	@BeforeEach
	void createRequiredObjects() {
		Island i1 = new Island("test A", null, null);
		Island i2 = new Island("test B", null, null);
		testRoute = new Route("Stormy Route", 150, new Island[] {i1, i2}, "Careful, this route is prone to bad weather");
	}
	
	@Test
	void testToSring() {
		assertEquals("Stormy Route is 150 km long. Careful, this route is prone to bad weather", testRoute.toString());
	}
}
