package coreClasses;

import exceptions.InsufficientMoneyException;

/**
 * 
 * @author Jordan Vegar
 * @version 1.1
 */
public class Player {
	
	private String name;
	private Ship ship;
	private int moneyBalance;
	private int daysRemaining;
	private Island currentIsland;
	
	// DELETE LATER - constructor for testing while ship doesn't work
	public Player(String name, int startingCash, int days, Island startingIsland) {
		if (CheckValidInputClass.nameIsValid(name)) {this.name = name;} 
		else { throw new IllegalArgumentException("Invalid name. Please enter a name with 3-15 letters, not numbers or special characters.");}
		
		if (CheckValidInputClass.durationIsValid(days)) {this.daysRemaining = days;} 
		else { throw new IllegalArgumentException("Invalid game duration. Please enter a number of days between 20 and 50");}
		
		this.moneyBalance = startingCash;
		this.currentIsland = startingIsland;
	}
	
	public Player(String name, Ship ship, int startingCash, int days, Island startingIsland) {
		
		if (CheckValidInputClass.nameIsValid(name)) {this.name = name;} 
		else { throw new IllegalArgumentException("Invalid name. Please enter a name with 3-15 letters, not numbers or special characters.");}
		
		if (CheckValidInputClass.durationIsValid(days)) {this.daysRemaining = days;} 
		else { throw new IllegalArgumentException("Invalid game duration. Please enter a number of days between 20 and 50");}
		
		this.ship = ship;
		this.moneyBalance = startingCash;
		this.currentIsland = startingIsland;
	}
	
	public String getName() {return name;}
	
	public int getMoneyBalance() {return moneyBalance;}
	
	public Ship getShip() {return ship;}
	
	public int getDaysRemaining() {return daysRemaining;}	
	
	public Island getCurrentIsland() {return currentIsland;}
	
	
	public void reduceDaysRemaining(int daysPassed) {
		daysRemaining -= daysPassed;
	}
	
	public void spendMoney(int amountSpent) {
		if (amountSpent <= moneyBalance) {
			moneyBalance -= amountSpent;
		}
		else {
			throw new InsufficientMoneyException("Not enough money!");
		}	
	}
	
	public void earnMoney(int amountEarned) {
		moneyBalance += amountEarned;
	}
	
	public void setCurrentIsland(Island newIsland) {
		this.currentIsland = newIsland;
	}
}
