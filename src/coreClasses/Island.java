package coreClasses;

import java.util.ArrayList;  

/** Represents an Island object
 * 
 * @author Jordan Vegar
 * @version 17/5/2021
 * @since 2/4/2021
 */
public class Island {
	// Class variables //
	/** The name of the island. */
    private String islandName;
    
	/** The object of Store that models the store on this island. */
	private Store islandStore;
	
	/** An array of all the routes from this island to another island.*/
	private Route[] routeArray;
	
	/** A brief description of the characteristics of this island, including an
	 * indication of what the store sells and buys. 
	 */
	private String description;
	
	/** Constructor method for an Island object
	 * 
	 * @param name String for the name of the Island
	 * @param store Store object on the Island
	 * @param description String brief description of the island, gives an indication
	 * 		  of what it's store will buy and sell. 
	 */
	public Island(String name, Store store, String description) {
		/*Note that the route Array is not initiated
	     * as to make the routes and routeArray, the island must already
	     * exist as it is a parameter of the route constructor.
	     */
		if (!CheckValidInput.nameIsValid(name)) {
			throw new IllegalArgumentException("Name for Island must have no more than 1 consecutive white space and be between 3 and 15 characters in length!");
		}
		this.islandName = name;
		this.islandStore = store;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", islandName, description);
	}
	
	/** Getter method for the store belonging to island object
	 * 
	 * @return Store belonging to Island Object
	 */
	public Store getIslandStore() {return islandStore;}
	
	/** Getter method for the name of an island object
	 * 
	 * @return String representation of an Island object
	 */
	public String getIslandName() {return islandName;}
	
	/** Getter method for the description of an island object. Also includes 
	 * information on what the specialty of the Island Store is
	 * 
	 * @return String representation for the description of an Island object
	 */
	public String getDescription() {
		return String.format("%s, Store specialises in %s", description, islandStore.getSpecialty());
	}

	/** Searches through the current island's routes to find those that go to the island the player
	 * wishes to travel to.
	 * 
	 * @param destination Island object that you want to see routes to (from the player's current island)
	 * @return ArrayList of the routes from the current island to the destination island. 
	 */
	public ArrayList<Route> possibleRoutes(Island destination) {
		
		
		ArrayList<Route> routesToDestination = new ArrayList<Route>();
		
		for (Route possibleRoute: routeArray) {
			// Check if island is in the array of two islands -array has no contains method :(
			if (possibleRoute.getIslands()[0] == destination || possibleRoute.getIslands()[1] == destination) {
				routesToDestination.add(possibleRoute);
			}
		}
		return routesToDestination;
	}
	
	/** Method for the getting a string representation of routes from an island
	 * 
	 * @param routes ArrayList<Route> containing all the routes from a particular island. (from the player's current island)
	 * @return String representation of all the routes from a given island
	 */
	public String viewRoutes(ArrayList<Route> routes) {
		
		String routeStr = "Routes to " + getIslandName() + ":\n";

		for (Route availableRoute: routes) {
			routeStr += " - " + availableRoute.toString() + "\n";
		}
		return routeStr;
	}
	
	/** Creates a detailed description of this island, including info about the routes to it 
	 * (from current island) and a list of items the store buys and sells.
	 *  
	 * @param routes ArrayList<Route> of routes from the player's current island to this island.
	 * @return fullInfo String giving a detailed description of this island.
	 */
	public String fullInfo(ArrayList<Route> routes) {
		String fullInfo = String.format("About %s: %s\n", getIslandName(), getDescription());
		
		fullInfo += viewRoutes(routes);
		
		fullInfo += islandStore.catalogueToString(islandStore.getSellCatalogue(), "sell");
		fullInfo += islandStore.catalogueToString(islandStore.getBuyCatalogue(), "buy");
	
		return fullInfo;
	}
	
	/** Route objects require Island objects to be created, so an islands routes are created
	 * after the island is created. For this reason the routeArray cannot be included in the
	 * constructor. This method initializes the variable routeArray.
	 * 
	 * @param routes Route[] array of routes that routeArray is to be assigned to. 
	 */
	public void setRouteArray(Route[] routes) {this.routeArray = routes;}
	
	/** Searches through every route from this Island to any other Island to find the one with 
	 * lowest distance. Allows calculating if the player has enough money to continue playing
	 * 
	 * @param otherIslands Island[] array of all islands in the game except the player's current island.
	 * @return Route with the lowest distance from currentIsland.
	 */
	public Route shortestPossibleRoute(Island[] otherIslands) {
		Route shortest = null;
		int minDist = 999999;		// effectively infinite in this situation, but an int.
		for (Island island: otherIslands) {
			for (Route route: possibleRoutes(island)) {
				if (route.getDistance() < minDist) {
					minDist = route.getDistance();
					shortest = route;
				}
			}
		}
		return shortest;
	}
}
