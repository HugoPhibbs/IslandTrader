/**
 * 
 */
package junitTests;

import coreClasses.*; 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

/**
 * @author Jordan Vegar
 *
 */
class IslandTests {
	
	
	// Note will probably transfer the code that sets up the stores islands and routes to a before each/all method. 
	@Test
	void viewRoutesTest() {
		// creating instances of island to create routes to for testing
		Store s1 = new Store(); Store s2 = new Store(); Store s3 = new Store(); 
		Island i1 = new Island("other 1", s1, "arb1"); 
		Island i2 = new Island("other 2", s2, "arb2"); 
		Island i3 = new Island("other 3", s3, "arb3"); 
		// Creating current instance of Island
		Store currStore = new Store(); 
		Island currentIsland = new Island("current", currStore, "arb description");
		// creating routes from the current island
		Route r11 = new Route("r11", 3, currentIsland, i1, "wavy route, but with storms come sailors to save");
		Route r12 = new Route("r12", 2, currentIsland, i1, "easy sailing, which pirates also enjoy");
		Route r21 = new Route("r21", 2, currentIsland, i2, "safe route, low risk, low reward.");
		Route r22 = new Route("r22", 2, currentIsland, i2, "wavy route, but with storms come sailors to save");
		Route r31 = new Route("r31", 4, currentIsland, i3, "long route, takes a detour to find calmer waters");
		Route r32 = new Route("r32", 1, currentIsland, i3, "Direct route with high winds, fast but lots of bad weather. ");
		// create route array and pass to current island
		Route[] routesFromCurrent = new Route[] {r11, r12, r21, r22, r31, r32};
		currentIsland.setRouteArray(routesFromCurrent);
		
		// How to test properly for void method that only prints, doesn't return?
		ByteArrayInputStream in1 = new ByteArrayInputStream("r11".getBytes());
		System.setIn(in1);
		assertEquals(r11, currentIsland.chooseRoute(i1));
		
	}

}
