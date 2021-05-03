package coreClasses;


import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

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
	
	// TODO fyi where do we create a new ship? -hp
	// also i think sailShip should be implemented by game environment-see comment in ship class

	private Player player;
	private Island[] islandArray;
	private Ship[] shipArray;
	private GameUi ui;
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
	
	public void reduceDaysRemaining(int daysPassed) {
		daysRemaining -= daysPassed;
	}
	
	public void setCurrentIsland(Island newCurrentIsland) {currentIsland = newCurrentIsland;}
	
	public void onSetupFinished(Player player, Ship ship, int duration, Island startisland) {
		this.player = player;
		this.ship = ship;
		this.daysRemaining = duration;
		this.currentIsland = startisland;
		
		ui.playGame();
	}
	
	/**
	 * Works out the total cost to repair the ship and pay wages for a particular route. 
	 * 
	 * @return cost The total amount that needs to be paid before sailing that route. 
	 */
	public int getCost(Route route) {
		return 10;
	}
	
	
	
	public void getIsland() {
		// Gets a new island or goes back to take turn
		try {
			Island newIsland = chooseOtherIsland();
			getRoute(newIsland);
		} catch (GoBackException e) {
			return;
		}	
	}
	
	/**
	 * Method to handle calls to Island.chooseRoute. If a route is chosen, the player sets sail along that route. 
	 * Alternatively the player chooses to go back, and getIsland is called. 
	 * @param newIsland Island that has been selected to travel to.
	 */
	public void getRoute(Island newIsland) {
		// Gets route to new island or goes back to reselect island
		try {
			Route selectedRoute = currentIsland.chooseRoute(newIsland);
		} catch (GoBackException e) {
			return;
		}
		// call Ship.setSail() once implemented
		System.out.println("worked");
	}

	
	/**
	 * Prints a string representation of each island that is not the current island.
	 */
	public void viewOtherIslands() {
		ArrayList<Island> otherIslands = getOtherIslandsList();
		
		for (Island otherIsland: otherIslands) {
				System.out.println(otherIsland);
		}
		
		String input = TakeInput.inputString("To view more information about an island, enter the name of the island.");
		
		for (Island island: otherIslands) {
			if (island.getIslandName().toLowerCase() == input) {
				String islandInfo = "The island " + island.getIslandName() + " can be reached from your current island by the following routes:\n";
				islandInfo += currentIsland.viewRoutes(island);  // TODO: separate view routes from choose route.
				// TODO: add info string about what the island's store buys and sells. 
			}
		}
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
	
	public Island chooseOtherIsland() {
		
		ArrayList<Island> otherIslands = getOtherIslandsList();
		
		// delete this once viewOtherIslands is working
		for (Island otherIsland: otherIslands) {
				System.out.println(otherIsland);
		}
		
		String inputStr = TakeInput.inputString("To travel to an Island, enter the island's name. To go back please type \"back\"");
		
		if (inputStr.equals("back")) {
			throw new GoBackException();
		}
		else {
			for (Island otherIsland: otherIslands) {
				if (inputStr.equals(otherIsland.getIslandName().toLowerCase())) {
					return otherIsland;
				}
			}
		}
		
		System.out.println("Invalid Input");
		return chooseOtherIsland();
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
