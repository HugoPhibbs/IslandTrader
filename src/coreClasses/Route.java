package coreClasses;

import java.util.HashMap;
import java.util.List;

/**
 * Class that models and provides information about a route between two islands.
 * 
 * @author Jordan Vegar
 * 
 */
public class Route {
	
	// The name of this route. 
	private String routeName;
	
	// The distance of this route, expressed in nautical miles. 
	private int distance;
	
	// An array of the two islands this route connects.
	private Island[] islands;
	
	/**
	 * String description of the Route. Must include indaction's about the how dangerous
	 * the route is (and whether the danger is in pirates or weather). Also must give an 
	 * indication as to the potential profitability of the route. 
	 */
	private String description;
	
	/**
	 * A map that assigns the name of each possible RandomEvent to the probability of said
	 * random event happening on this particular route. 
	 */
	private HashMap<String, Integer> eventProbabilityMap;
	
	/**
	 * Creates a new route object. 
	 * 
	 * @param name The name of the route.
	 * @param distance The distance of the route
	 * @param islands An array of the two island objects this route is between.
	 * @param description A description of the route. 
	 */
	public Route(String name, int distance, Island[] islands, String description) {
		this.routeName = name;
		this.distance = distance;
		this.islands = islands;
		this.description = description;
	}
	
	/**
	 * Takes probabilities (out of 100), and constructs the map that shows the probability of each RandomEvent. 
	 * 
	 * @param pirateProb The probability of pirates attacking on this route. 
	 * @param weatherProb The probability of encountering bad weather on this route.
	 * @param rescueProb The probability of coming across sailors that need rescuing on this route. 
	 */
	public void constructProbabilityMap(int pirateProb, int weatherProb, int rescueProb) {
		eventProbabilityMap = new HashMap<String, Integer>();
		eventProbabilityMap.put("Pirates", pirateProb);
		eventProbabilityMap.put("Weather", weatherProb);
		eventProbabilityMap.put("Rescue", rescueProb);
	} 
	
	public String getRouteName() {return routeName;}
	
	public int getDistance() {return distance;}
	
	public Island[] getIslands() {return islands;}
	
	public String getDescription() {return description;}
	
	public int getPirateProb() {return eventProbabilityMap.get("Pirates");}
	
	public int getWeatherProb() {return eventProbabilityMap.get("Weather");}
	
	public int getRescueProb() {return eventProbabilityMap.get("Rescue");}
	
	// Returns a string which gives a complete description of the route.
	@Override
	public String toString() {
		return String.format("%s is %d km long. %s", routeName, distance, description);
	}

}
