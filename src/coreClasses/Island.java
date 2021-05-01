package coreClasses;

import java.util.ArrayList; 

import exceptions.GoBackException;

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
	
	/**
	 * Constructor class. Note that the route Array is not initiated
	 * as to make the routes and routeArray, the island must already
	 * exist as it is a parameter of the route constructor.
	 * @param name the name of the Island
	 * @param store the Store on the Island
	 * @param description test description of the island, gives an indication
	 * 		  of what it's store will buy and sell. 
	 */
	public Island(String name, Store store, String description) {
		
		if (!CheckValidInput.nameIsValid(name)) {
			throw new IllegalArgumentException("Name for Island must have no more than 1 consecutive white space and be between 3 and 15 characters in length!");
		}
		this.islandName = name;
		this.islandStore = store;
		this.description = description;
	}
	
	/**
	 * Route objects require Island objects to be created, so an islands routes are created
	 * after the island is created. For this reason the routeArray cannot be included in the
	 * constructor. This method initializes the variable routeArray.
	 * 
	 * @param routes The list of routes that routeArray is to be assigned to. 
	 */
	public void setRouteArray(Route[] routes) {this.routeArray = routes;}
	
	public Store getIslandStore() {return islandStore;}
	
	public String getIslandName() {return islandName;}

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
			if (possibleRoute.getDestination() == destination) {
				routesToDestination.add(possibleRoute);
			}
		}
		return routesToDestination;
	}

	/**
	 * Prints out a description of each route from the current island to the destination island.
	 * 
	 * @param destination The Island you want to see routes to (from the player's current island)
	 */
	public String viewRoutes(Island destination) {
		
		ArrayList<Route> routesToDestination = getPossibleRoutes(destination);
		String routeStr = "Routes to " + destination.getIslandName() + ':';

		for (Route availableRoute: routesToDestination) {
			routeStr += availableRoute.toString() + "\n";
		}
		return routeStr;
	}
	
	/**
	 * TODO move to CmdLine UI / GameEnvironment
	 */
	public Route chooseRoute(Island destination) {
		ArrayList<Route> routesToDestination = getPossibleRoutes(destination);
		
		String inputStr = TakeInput.inputString("Enter a route's name to take that route. To go back please type \"back\"");
		
		if (inputStr.equals("back")) {
			throw new GoBackException();
		}
		else {
			for (Route possibleRoute: routesToDestination) {
				if (inputStr.equals(possibleRoute.getRouteName())) {
					return possibleRoute;
				}
			}
		}
		System.out.println("Invalid Input");
		return chooseRoute(destination);
	}
	
	public String getFullInfo() {
		String fullInfo = toString() + "\n";
		// add method to view route info
		// add string of items that the store sells
		// add string of itmes that the store buys
		fullInfo += "This is the full info string" 	// line only for testing, delete once mehtd fully implemented.
		return fullInfo;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", islandName, description);
	}
}
