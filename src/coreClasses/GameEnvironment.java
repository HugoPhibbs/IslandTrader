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

/**
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 23/4/21
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
	
	public GameEnvironment(Island[] islandArray, Ship[] shipArray, uiClasses.GameUi ui) {
		this.islandArray = islandArray;
		this.shipArray = shipArray;
		this.ui = ui;
	}
	
	public Player getPlayer() {return player;}
	
	public Island[] getIslandArray() {return islandArray;}
	
	public GameUi getUi() {return ui;}
	
	public int getDaysRemaining() {return daysRemaining;}	
	
	public Island getCurrentIsland() {return currentIsland;}
	
	public Ship getShip() {return ship;}
	
	public Ship[] getShipArray() {return shipArray;}
	
	public int getDaysSelected() {return daysSelected;}
	
	public int getMinMoneyToTravel() {return minMoneyToTravel;}
	
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
	public void setSail(Route route) {
    	// Repair ship and pay wages before setting sail.
		ship.repairShip();
		ship.payWages(route, player);
		// Set sail
		randomEvents(route);
		// Arrive at new island
		int routeDuration = route.getDistance() / ship.getSpeed();
		reduceDaysRemaining(routeDuration);
		setCurrentIsland(route.getDestination());	
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
		int profit = getPlayer().getMoneyBalance() - 100;
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
		int cost = ship.getRepairCost();
		minMoneyToTravel += ship.getRouteWageCost(shortestRoute());
	}
	
	/**
	 * Searches through every route off the current island to any other island to find the
	 * one with lowest distance. 
	 * 
	 * @return the Route with lowest distance from currentIsland.
	 */
	private Route shortestRoute() {
		Route shortest = null;
		int minDist = 999999;		// effectively infinite in this situation, but an int.
		for (Island island: getOtherIslands()) {
			for (Route route: currentIsland.getPossibleRoutes(island)) {
				if (route.getDistance() < minDist) {
					minDist = route.getDistance();
					shortest = route;
				}
			}
		}
		return shortest;
	}
	
	/**
	 * Based on your items you can sell at the current island's store, works out your players liquid value.
	 * @return The amount the player can sell all his sellable items for plus his bank balance. 
	 */
	public int liquidValue() {
		return 10;
	}
	
	// ################## METHODS FOR VISTING A STORE ###################
	
	public String sellToPlayerHelper(String itemStoreToSellName) {
		// helper method for ui classes 
		Item itemToSell = currentIsland.getIslandStore().sellItemToPlayer(itemStoreToSellName, player);
		if (!itemToSell.getWithPlayer()) {
			// Since item didnt come in possession with player, find reason why not
			
			// check if item wasnt sold because of an error to do with it being an upgrade
			if (itemToSell.getName().endsWith("(upgrade)") && !Store.canSellUpgrade(player).equals("Can sell")){
				return Store.canSellUpgrade(player); // return reason why cant sell upgrade.
			}
			// otherwise find reason inherent in being an item
			return Store.canSellItem(player, itemToSell);
		}
		else {
			// If item was found, print transaction statement
			return String.format("You just bought %s for %s pirate bucks! \n", itemToSell.getName(), itemToSell.getPlayerBuyPrice());
	    }
	}
	
	public String sellToStoreHelper(String itemStoreToBuyName) {
		// helper method for ui
		Item itemToBuy = currentIsland.getIslandStore().buyItemFromPlayer(itemStoreToBuyName, player);
		if (itemToBuy == null) {
			// wasn't successful in getting item, print reason why from store
			return Store.buyItemChecker(player, itemToBuy);
		}
		else {
			// If item was found, print transaction statement
			return String.format("You just sold %s for %s pirate bucks! \n", itemToBuy.getName(), itemToBuy.getPlayerSellPrice());
		}	
	}	
}	

