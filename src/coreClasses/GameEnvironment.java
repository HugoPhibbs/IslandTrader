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

/**
 * 
 * @author Jordan Vegar and Hugo Phibbs
 * @version 23/4/21
 * @since 2/4/21
 */


/** TODO 
 * the main thing to remember is that we want to keep this all modular. 
 * so all the UI should be kept to game environment
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
	
	public void reduceDaysRemaining(int daysPassed) {
		daysRemaining -= daysPassed;
	}
	
	public void setCurrentIsland(Island newCurrentIsland) {currentIsland = newCurrentIsland;}
	
	public void onSetupFinished(Player player, Ship ship, int duration, Island startisland) {
		this.player = player;
		this.ship = ship;
		this.daysSelected = duration;
		this.daysRemaining = duration;
		this.currentIsland = startisland;
		
		ui.playGame();
	}
	
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
		if (route.getPirateProb() < random.nextInt(100)) {
			// roll dice
			Pirates.attackShip(1, ship);
		}
		if (route.getWeatherProb() < random.nextInt(100)) {
			UnfortunateWeather.damageShip(ship);
		}
		if (route.getRescueProb() < random.nextInt(100)) {
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
	
	public void buyFromStore(String itemToBuyName) {
		currentIsland.getIslandStore().buyItem(itemToBuyName, player);
	}
	
	public void sellToStore(String itemToSellName) {
		currentIsland.getIslandStore().buyItem(itemToSellName, player);
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
		int score = profit / daysPlayed;
		return score;
	}
	
	/**
	 * Calculates the amount that needs to be paid before the cheapest route available can be sailed. 
	 * cost is dependent on amount of damage to the ship that needs to be repaired, as well as cost of wages to be paid. 
	 * 
	 * @return The amount of money required to take the cheapest sail option. 
	 */
	public int minMoneyRequired() {
		return 10;
	}
	
	/**
	 * Finds the shortest route off the current Island to any other island
	 * 
	 * @return
	 */
	private Route shortestRoute() {
		
	}
	
	
	
		
	/**
	 * Note that this is an informal test environment, when i write the proper Junit tests ill use actual varaible names and what not.
	 * Still keep it organised tho so its readable. 5th island coming to a game near you soon.
	 */
	
	// TODO migrate this entire method into the main class
	public static void main(String[] args) {
		Store s1 = new Store(); Store s2 = new Store(); Store s3 = new Store(); 
		Island i1 = new Island("other 1", s1, "arb1"); 
		Island i2 = new Island("other 2", s2, "arb2"); 
		Island i3 = new Island("other 3", s3, "arb3"); 
		
		/* TODO
		 * when creating a new store the store needs to reference the island that it belongs to
		 * a two way relationship like player and ship, method setIslandStore in store allows this
		 */
		// Creating current instance of Island
		Store currStore = new Store(); 
		Island currentIsland = new Island("current", currStore, "arb description");
		// creating routes from the current island
		Route r11 = new Route("r11", 3, currentIsland, i1, "wavy route, but with storms come sailors to save");
		Route r12 = new Route("r12", 2, currentIsland, i1, "easy sailing, which pirates also enjoy");
		Route r21 = new Route("r21", 2, currentIsland, i2, "safe route, low risk, low reward.");
		Route r22 = new Route("r22", 2, currentIsland, i2, "wavy route, but with storms come sailors to save");
		Route r31 = new Route("r31", 4, currentIsland, i3, "long route, takes a detour to find calmer waters");
		Route r32 = new Route("r32", 1, currentIsland, i3, "Direct route with high winds, fast but lots of bad weather. ");
		// create route array and pass to current island
		Route[] routesFromCurrent = new Route[] {r11, r12, r21, r22, r31, r32};
		currentIsland.setRouteArray(routesFromCurrent);
		// Create a player
		Player player1 = new Player("ben", 100, 30, currentIsland);
		// Create a game environment
		Island[] islands = new Island[] {currentIsland, i1, i2, i3};
		GameEnvironment game1 = new GameEnvironment(player1, islands);
		// test viewOtherIslands
		game1.chooseOtherIsland();
		
		// note from me: fyi there needs to be 5 islands
		// game1.takeTurn();
	}	
	// TODO need to have a method that handles a player not having any cash
}
