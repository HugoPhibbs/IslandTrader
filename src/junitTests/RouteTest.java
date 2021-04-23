package junitTests;

import coreClasses.*; 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RouteTest {

	@Test
	void constructProbabilityMapTest() {
		Island a = new Island(); Island b = new Island(); 
		Route r = new Route(3, a, b, "safe");
		r.constructProbabilityMap(11, 12, 17);
		assertEquals(11, r.getPirateProb());
		assertEquals(12, r.getWeatherProb());
		assertEquals(17, r.getRescueProb());
	}

}
