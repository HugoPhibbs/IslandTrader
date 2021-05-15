package coreClasses;


import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

import exceptions.*;
import uiClasses.GameUi;


/* TODO 
 * how do we do message handling of errors, since they are all caught GameEnvironment, should we use
 * getMessage(), or create the messages themselves within GameEnvironment?
 */

// TODO need to have a method that handles a player not having any cash

/** Represents the game environment of an 'Island Trader' game. 
 * Contains necessary Game objects, along with general methods that dont really belong anywhere else
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 8/5/21
 * @since 2/4/21
 */
public class GameEnvironment {
	
	// TODO need to have a method that handles random events

	private Player player;
	private Island[] islandArray;
	private Ship[] shipArray;
	private GameUi ui;
	private int daysSelected;
	private int daysRemaining;
	private Island currentIsland;
	private Ship ship;
	// The minimum amount of money to travel off your particular island. 
	private int minMoneyToTravel;
	
	/** Constructor for GameEnvironment class
	 * 
	 * @param islandArray Island[] array containing islands to be used in game
	 * @param shipArray Ship[] array containing ships that can be chosen by player
	 * @param ui GameUi implementation to be used by game
	 */
	public GameEnvironment(Island[] islandArray, Ship[] shipArray, uiClasses.GameUi ui) {
		this.islandArray = islandArray;
		this.shipArray = shipArray;
		this.ui = ui;
	}
	
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
	
	/** Getter method for ui object
	 * 
	 * @return GameUi implementation being used
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
	public int getMinMoneyToTravel() {return minMoneyToTravel;}
	
	/** Method to reduce in-game days
	 * 
	 * @param daysPassed Integer for the number of in-game days passed
	 */
	public void reduceDaysRemaining(int daysPassed) {
		daysRemaining -= daysPassed;
	}
	
	public void setCurrentIsland(Island newCurrentIsland) {currentIsland = newCurrentIsland;}
	
	/**
	 * Method that is called when the user has entered all necessary information for setup, 
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
	
	/**
	 * Makes the necessary payments before sailing, then sails along a particular route to a 
	 * new Island. May encounter random events based on the probabilities of the particular route. 
	 * @param route The Route the player has chosen. 
	 */
	public void setSail(Route route, Island destination) {
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
    
	/**
	 * Based on the probabilities of each event for the specific route, uses a random number to decide
	 * if any random events will occur. Makes the necessary calls if they are to occur. 
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
			RescuedSailors.giveMoney(player);
		}
	}
	
	/**
	 * Works out the amount that has to be spent before this route can be sailed.
	 * Based on the cost to repair the ship and pay crew wages. 
	 * 
	 * @return cost The total amount that needs to be paid before sailing that route. 
	 */
	public int getCost(Route route) {
		int cost = ship.getRepairCost();
		// get cost of paying wages based on number of crew, distance or route (days sailing) and cost per crew per day.
		return cost;
	}
	
	/** Getter method for every game island besides the current island
	 * 
	 * @return Island[] array containing every game island besides the current island
	 */
	public Island[] getOtherIslands() {
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
	 * @return Ship[] array containing descriptions of every ship that a player can choose
	 */
	public ArrayList<String> getShipDescriptionArrayList() {
		// TODO implement
		ArrayList<String> shipDescriptionArrayList = new ArrayList<String>();
		for (Ship ship: shipArray) {
			shipDescriptionArrayList.add(ship.getDescription());
		}
		
		return shipDescriptionArrayList;
	}
	
	/**
	 * Calculates a score by dividing profit by days played.
	 * 
	 * @param the amount of money the player started with, needed for profit calculation.  
	 * @return The players score at time of call.
	 */
	public int getScore(int startMoney) {
		int profit = getPlayer().getMoneyBalance() - 1000;
		int daysPlayed = getDaysSelected() - getDaysRemaining();
		
		if (daysPlayed == 0) {
			return 0;
		} else {
			return profit / daysPlayed;
		}
	}
	
	/**
	 * Calculates the amount that needs to be paid before the cheapest route available can be sailed. 
	 * cost is dependent on amount of damage to the ship that needs to be repaired, as well as cost of wages to be paid. 
	 * 
	 * @return The amount of money required to take the cheapest sail option. 
	 */
	public void minMoneyRequired() {
		int repairCost = ship.getRepairCost();
		minMoneyToTravel += ship.getRouteWageCost(currentIsland.getShortestRoute(getOtherIslands())) + repairCost;
	}
	
	
	/**
	 * Based on your items you can sell at the current island's store, works out your players liquid value.
	 * @return The amount the player can sell all his sellable items for plus his bank balance. 
	 */
	public int getLiquidValue() {
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
}	

