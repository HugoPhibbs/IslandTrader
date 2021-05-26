package coreClasses;

import java.util.HashMap; 

/** Class that models and provides information about a route between two islands.
 * 
 * @author Jordan Vegar
 * @version 17/5/21
 * @since 2/4/21
 */
public class Route {
	// Class attributes //
	/** String The name of this route. */
	private String routeName;
	
	/** int The distance of this route, expressed in nautical miles. */
	private int distance;
	
	/** Island[] An array of the two Islands this route connects. */
	private Island[] islands;
	
	/** String description of the Route. Must include indaction's about the how dangerous
	 * the route is (and whether the danger is in pirates or weather). Also must give an 
	 * indication as to the potential profitability of the route. 
	 */
	private String description;
	
	/** HashMap<String, Integer> A map that assigns the name of each possible RandomEvent to the probability of said
	 * random event happening on this particular route. 
	 */
	private HashMap<String, Integer> eventProbabilityMap;
	
	
	/** Creates a new route object. 
	 * 
	 * @param name String for the name of the route.
	 * @param distance int for the distance of the route
	 * @param islands Island[] array of the two island objects this route is between.
	 * @param description String for the description of the route. 
	 */
	public Route(String name, int distance, Island[] islands, String description) {
		this.routeName = name;
		this.distance = distance;
		this.islands = islands;
		this.description = description;
	}
	
	// Returns a string which gives a complete description of the route.
	@Override
	public String toString() {
		return String.format("%s is %d km long. %s", routeName, distance, description);
	}

	/** Takes probabilities (out of 100), and constructs the map that shows the probability of each RandomEvent. 
	 * 
	 * @param pirateProb int for the probability of pirates attacking on this route. 
	 * @param weatherProb int for the probability of encountering bad weather on this route.
	 * @param rescueProb int for the probability of coming across sailors that need rescuing on this route. 
	 */
	public void constructProbabilityMap(int pirateProb, int weatherProb, int rescueProb) {
		eventProbabilityMap = new HashMap<String, Integer>();
		eventProbabilityMap.put("Pirates", pirateProb);
		eventProbabilityMap.put("Weather", weatherProb);
		eventProbabilityMap.put("Rescue", rescueProb);
	} 
	
	/** Getter method for the name of this Route
	 * 
	 * @return String for the name of Route
	 */
	public String getRouteName() {return routeName;}
	
	/** Getter method for the distance of this Route
	 * 
	 * @return int for the distance of this Route 
	 */
	public int getDistance() {return distance;}
	
	/** Getter method for the Islands that this Route connects
	 * 
	 * @return Island[] array containing the two islands that this route connects
	 */
	public Island[] getIslands() {return islands;}
	
	/** Getter method for the description of a Route
	 * 
	 * @return String for the description of a Route
	 */
	public String getDescription() {return description;}
	
	/** Getter method for the probability of a Pirates random event
	 * Expressed out of 100
	 * 
	 * @return int for the probability of a Pirates random event
	 */
	public int getPirateProb() {return eventProbabilityMap.get("Pirates");}
	
	/** Getter method for the probability of a UnfortunateWeather random event
	 * Expressed out of 100
	 * 
	 * @return int for the probability of a UnfortunateWeather random event
	 */
	public int getWeatherProb() {return eventProbabilityMap.get("Weather");}
	
	/** Getter method for the probability of a RescuedSailors random event
	 * Expressed out of 100
	 * 
	 * @return int for the probability of a RescuedSailors random event
	 */
	public int getRescueProb() {return eventProbabilityMap.get("Rescue");}
}
