package coreClasses;

import java.util.Random;

import uiClasses.GameUi;
import java.util.ArrayList;
import java.util.HashMap;

// TODO need to have a method that handles a player not having any cash

/** Represents the game environment of an 'Island Trader' game. 
 * Contains necessary Game objects, along with general methods that dont really belong anywhere else
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 8/5/21
 * @since 2/4/21
 */
public class GameEnvironment {
	
	// Final class variables
	private final Island[] islandArray;
	private final Ship[] shipArray;
	private final GameUi ui;
	// Non final class variables
	private Player player;
	private int daysSelected;
	private Pirates pirates; // Object for a pirates random event
	private RescuedSailors rescuedSailors; // Object for rescued sailors random event
	private int daysRemaining;
	private Island currentIsland;
	private Ship ship;
		private int minMoneyToTravel; // The minimum amount of money to travel off your particular island. 
	
	/** Constructor for GameEnvironment class
	 * 
	 * @param islandArray Island[] array containing islands to be used in game
	 * @param shipArray Ship[] array containing ships that can be chosen by player
	 * @param pirates Pirates object for a pirate attack random event
	 * @param rescuedSailors RescuedSailors object for a rescued sailors random event
	 * @param ui GameUi implementation to be used by game
	 */
	public GameEnvironment(Island[] islandArray, Ship[] shipArray, uiClasses.GameUi ui, Pirates pirates, RescuedSailors rescuedSailors) {
		this.islandArray = islandArray;
		this.shipArray = shipArray;
		this.ui = ui;
		this.rescuedSailors = rescuedSailors;
		this.pirates = pirates;
	}
	
	/** Method for finishing the set up for a game. 
	 * 
	 * Is called when the user has entered all necessary information for setup, 
	 * and all objects that required this information have been created. This method passes
	 * those objects to the current instance of GameEnvironment. 
	 * @param player
	 * @param ship
	 * @param duration
	 * @param startisland
	 */
	public void onSetupFinished(Player player, Ship ship, int duration, Island startisland) {
		this.player = player;
		this.ship = ship;
		this.daysSelected = duration;
		this.daysRemaining = duration;
		this.currentIsland = startisland;
		
		player.setShip(ship);
		
		ui.playGame();
	}
	
	/** Method to reduce in-game days
	 * 
	 * @param daysPassed Integer for the number of in-game days passed
	 */
	public void reduceDaysRemaining(int daysPassed) {
		daysRemaining -= daysPassed;
	}
	
	/** Method for setting sail to another island in a game.
	 * Makes the necessary payments before sailing, then sails along a particular route to a 
	 * new Island. May encounter random events based on the probabilities of the particular route. 
	 * 
	 * @param route The Route the player has chosen. 
	 */
	public void sailToNewIsland(Route route, Island destination) {
    	// Repair ship and pay wages before setting sail.
		ship.repairShip();
		ship.payWages(route, player);
		// Set sail
		randomEvents(route);
		// Arrive at new island
		int routeDuration = route.getDistance() / ship.getSpeed();
		reduceDaysRemaining(routeDuration);
		setCurrentIsland(destination);	
    }
    
	/** Performs random events while on route to another island
	 * Based on the probabilities of each event for the specific route, uses a random number to decide
	 * if any random events will occur. Makes the necessary calls if they are to occur. 
	 * 
	 * @param route The route the player is traveling along.
	 */
	private void randomEvents(Route route) {
		Random random = new Random();
		if (route.getPirateProb() >= random.nextInt(100)) {
			ui.pirateAttack();
		}
		if (route.getWeatherProb() >= random.nextInt(100)) {
			UnfortunateWeather.damageShip(ship);
		}
		if (route.getRescueProb() >= random.nextInt(100)) {
			// roll dice
			rescuedSailors.giveMoney(player);
		}
	}
	
	/** Calculates the amount that needs to be paid before the cheapest route available can be sailed. 
	 * cost is dependent on amount of damage to the ship that needs to be repaired, as well as cost of wages to be paid. 
	 * 
	 * @return The amount of money required to take the cheapest sail option. 
	 */
	public void minMoneyRequired() {
		minMoneyToTravel = ship.routeWageCost(currentIsland.shortestPossibleRoute(otherIslands())) + ship.repairCost();
	}
	
	//////////////////////////////////////////////////////////////
    /////////////////////// HELPER METHODS ///////////////////////
    //////////////////////////////////////////////////////////////

	/** Works out the amount that has to be spent before this route can be sailed.
	 * Based on the cost to repair the ship and pay crew wages. 
	 * 
	 * @return cost Integer for the total amount that needs to be paid before sailing that route. 
	 */
	public int getCost(Route route) {
		int cost = ship.repairCost();
		// get cost of paying wages based on number of crew, distance or route (days sailing) and cost per crew per day.
		cost += ship.routeWageCost(route);
		return cost;
	}
	
	/** Getter method for every game island besides the current island
	 * 
	 * @return Island[] array containing every game island besides the current island
	 */
	public Island[] otherIslands() {
		Island[] otherIslands =  new Island[islandArray.length-1];
		int i =0;
		for (Island island: islandArray) {
			if (island != currentIsland) {
				otherIslands[i] = island;
				i++;
			}
		}
		return otherIslands;
	}
	
	/** Getter method for the descriptions of every ship that a player can choose
	 * 
	 * @return ArrayList<String> ArrayList containing descriptions of every ship that a player can choose
	 */
	public ArrayList<String> shipDescriptionArrayList() {
		// TODO implement
		ArrayList<String> shipDescriptionArrayList = new ArrayList<String>();
		for (Ship ship: shipArray) {
			shipDescriptionArrayList.add(ship.getDescription());
		}
		
		return shipDescriptionArrayList;
	}
	
	/** Calculates a score by dividing profit by days played.
	 * 
	 * @param Integer for the amount of money the player started with, needed for profit calculation.  
	 * @return Integer for the player's score at time of call.
	 */
	public int calculateScore(int startMoney) {
		int profit = getPlayer().getMoneyBalance() - 1000;
		int daysPlayed = getDaysSelected() - getDaysRemaining();
		
		if (daysPlayed == 0) {
			return 0;
		} else {
			return profit / daysPlayed;
		}
	}	
	
	/** Gets the current liquid value of a player in a game 
	 * Based on your items you can sell at the current island's store, works out your players liquid value.
	 * 
	 * @return Integer for the amount the player can sell all his sellable items for plus his bank balance. 
	 */
	public int calculateLiquidValue() {
		ArrayList<Item> items = ship.getItems();
		Store currStore = currentIsland.getIslandStore();
		HashMap<String, HashMap<String, Integer>> buyCatalogue = currStore.getBuyCatalogue();
		int liquidGoodsVal = 0;
		
		for (Item item: items) {
			if (buyCatalogue.containsKey(item.getName())) {
				liquidGoodsVal += buyCatalogue.get(item.getName()).get("price");
			}
		}
		return liquidGoodsVal + player.getMoneyBalance();
	}
	
	 /////////////////////////////////////////////////////////////////////////
    ///////////////////// GETTER AND SETTER METHODS //////////////////////////
    //////////////////////////////////////////////////////////////////////////
	
	/** Getter method for in game Player object
	 * 
	 * @return Player object belonging to GameEnvironment
	 */
	public Player getPlayer() {return player;}
	
	/** Getter method for Island Array 
	 * 
	 * @return Island[] array belonging to GameEnvironment
	 */
	public Island[] getIslandArray() {return islandArray;}
	
	/** Getter method for Pirates object belonging to GameEnvironment
	 * 
	 * @return Pirates object belonging to Game Environment
	 */
	public Pirates getPirates() {return pirates;}
	
	/** Getter method for RescuedSailors object belonging to GameEnvironment
	 * 
	 * @return RescuedSailors object belonging to GameEnvironment
	 */
	public RescuedSailors getRescuedSailors() {return rescuedSailors;}
	
	/** Getter method for ui object
	 * 
	 * @return GameUi object implementation being used
	 */
	public GameUi getUi() {return ui;}
	
	/** Getter method for game days remaining
	 * 
	 * @return Integer for the number of game days remaining
	 */
	public int getDaysRemaining() {return daysRemaining;}	
	
	/** Getter method for current game island
	 * 
	 * @return Island object for current game island
	 */
	public Island getCurrentIsland() {return currentIsland;}
	
	/** Getter method for in game Ship object
	 * 
	 * @return Ship object belonging to GameEnvironment
	 */
	public Ship getShip() {return ship;}
	
	/** Getter method for Ship Array
	 * 
	 * @return Ship[] Array containing all ships in game
	 */
	public Ship[] getShipArray() {return shipArray;}
	
	/** Getter method for days selected for a game
	 * 
	 * @return Integer for the number of days selected for a game
	 */
	public int getDaysSelected() {return daysSelected;}
	
	/** Getter method for the minimum amount of money to travel to another island
	 * 
	 * @return Integer for the minimum amount of money to travel to another island
	 */
	public int getMinMoneyToTravel() {return minMoneyToTravel;};
	
	/** Method for setting the current Island object in a game
	 *  
	 * @param newCurrentIsland Island object that the player at a game is currently located at
	 */
	public void setCurrentIsland(Island newCurrentIsland) {currentIsland = newCurrentIsland;}
	
	/** Method for setting a player object gor a game
	 * 
	 * @param player Player object to be set for a game
	 */
	public void setPlayer(Player player) {this.player = player;}
	
	/** Method for setting the ship object belonging to a player in a game
	 * 
	 * @param ship Ship object belonging to a player in a game
	 */
	public void setShip(Ship ship) {this.ship = ship;}
}	

