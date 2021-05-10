package coreClasses;

import java.util.ArrayList; 
import exceptions.GoBackException;

/** Represents an Island object
 * 
 * @author Jordan Vegar
 * @version 8/5/2021
 * @since 2/4/2021
 */
public class Island {
	
	// The name of the island.
    private String islandName;
	// The object of Store that models the store on this island. 
	private Store islandStore;
	// An array of all the routes from this island to another island.
	private Route[] routeArray;
	/**
	 * A brief description of the characteristics of this island, including an
	 * indication of what the store sells and buys. 
	 */
	private String description;
	
	/** Constructor method for Island object
	 * 
	 * @param name the name of the Island
	 * @param store the Store on the Island
	 * @param description test description of the island, gives an indication
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
	
	/** Getter method for the description of an island object
	 * 
	 * @return String representation for the description of an Island object
	 */
	public String getDescription() {return description;}

	/**
	 * Searches through the current island's routes to find those that go to the island the player
	 * wishes to travel to.
	 * 
	 * @param destination The Island you want to see routes to (from the player's current island)
	 * @return an ArrayList of routes from the current island to the destination island. 
	 */
	public ArrayList<Route> getPossibleRoutes(Island destination) {
		
		
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
	 * @param destination The Island you want to see routes to (from the player's current island)
	 * @return String representation of all the routes from a given island
	 */
	public String viewRoutes(ArrayList<Route> routes) {
		
		String routeStr = "Routes to " + getIslandName() + ":\n";

		for (Route availableRoute: routes) {
			routeStr += " - " + availableRoute.toString() + "\n";
		}
		return routeStr;
	}
	
	/**
	 * Creates a detailed description of this island, including info about the routes to it 
	 * (from current island) and a list of items the store buys and sells.
	 *  
	 * @param routes A list of routes from the player's current island to this island.
	 * @return fullInfo A string giving a detailed description of this island.
	 */
	public String getFullInfo(ArrayList<Route> routes) {
		String fullInfo = String.format("About %s: %s\n", getIslandName(), getDescription());
		
		fullInfo += viewRoutes(routes);
		
		fullInfo += islandStore.catalogueToString(islandStore.getSellCatalogue(), "sell");
		fullInfo += islandStore.catalogueToString(islandStore.getBuyCatalogue(), "buy");
	
		return fullInfo;
	}
	
	/**
	 * Route objects require Island objects to be created, so an islands routes are created
	 * after the island is created. For this reason the routeArray cannot be included in the
	 * constructor. This method initializes the variable routeArray.
	 * 
	 * @param routes The list of routes that routeArray is to be assigned to. 
	 */
	public void setRouteArray(Route[] routes) {this.routeArray = routes;}
}


while dict[value] != 
