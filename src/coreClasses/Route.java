package coreClasses;

import java.util.HashMap;

/**
 * 
 * @author Jordan Vegar
 * @version 1.0
 * 
 *
 */
public class Route {

	private String routeName;
	private int distance;
	private Island origin;
	private Island destination;
	
	/**
	 * String description of the Route. Must include indaction's about the how dangerous
	 * the route is (and whether the danger is in pirates or weather). Also must give an 
	 * indication as to the potential profitability of the route. 
	 */
	private String description;
	private HashMap<String, Integer> eventProbabilityMap;
	
	public Route(String name, int distance, Island origin, Island destination, String description) {
		this.routeName = name;
		this.distance = distance;
		this.origin = origin;
		this.destination = destination;
		this.description = description;
	}
	
	public void constructProbabilityMap(int pirateProb, int weatherProb, int rescueProb) {
		eventProbabilityMap = new HashMap<String, Integer>();
		eventProbabilityMap.put("Pirates", pirateProb);
		eventProbabilityMap.put("Weather", weatherProb);
		eventProbabilityMap.put("Rescue", rescueProb);
	} 
	
	public String getRouteName() {return routeName;}
	
	public int getDistance() {return distance;}
	
	public Island getOrigin() {return origin;}
	
	public Island getDestination() {return destination;}
	
	public String getDescription() {return description;}
	
	public int getPirateProb() {return eventProbabilityMap.get("Pirates");}
	
	public int getWeatherProb() {return eventProbabilityMap.get("Weather");}
	
	public int getRescueProb() {return eventProbabilityMap.get("Rescue");}
	
	@Override
	public String toString() {
		return String.format("%s: Takes %d days. %s", routeName, distance, description);
	}
	
	
	
}
