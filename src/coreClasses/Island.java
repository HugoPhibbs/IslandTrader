package coreClasses;

import java.util.ArrayList; 

import exceptions.GoBackException;

public class Island {
	
	private String islandName;
	private Store islandStore;
	private Route[] routeArray;
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
		this.islandName = name;
		this.islandStore = store;
		this.description = description;
	}
	
	public void setRouteArray(Route[] routes) {this.routeArray = routes;}
	
	public Store getIslandStore() {return islandStore;}
	
	public String getIslandName() {return islandName;}

	/**
	 * @param destination - island you want to see routes to (from the player's current island)
	 * @return routesToDestination - ArrayList of routes from current island to destination island. 
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
	 * @param destination - island you want to see routes to (from the player's current island)
	 */
	public String viewRoutes(Island destination) {
		
		ArrayList<Route> routesToDestination = getPossibleRoutes(destination);
		String routeStr = "Routes to " + destination.getIslandName() + ':';

		for (Route availableRoute: routesToDestination) {
			routeStr += availableRoute.toString() + "\n";
		}
		return routeStr;
	}
	
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
	
	
	
	@Override
	public String toString() {
		return String.format("%s: %s", islandName, description);
	}
}
