package main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import coreClasses.GameEnvironment;
import coreClasses.Island;
import coreClasses.Item;
import coreClasses.Player;
import coreClasses.Store;

/**
 * The entry point to the program, creates instances of the classes required 
 * to start the game, then handles control to the class for the desired ui.
 * 
 * @author Jordan Vegar and Hugo Phibbs
 *
 */
public class main {
	
	public static void main(String[] args) {
		// Create objects required to initiate GameEnvironement
		// Create stores and island
		Store s1 = new Store(); Store s2 = new Store(); Store s3 = new Store(); 
		Island i1 = new Island("other 1", s1, "arb1"); 
		Island i2 = new Island("other 2", s2, "arb2"); 
		Island i3 = new Island("other 3", s3, "arb3"); 
		// Creating current instance of Island
		Store currStore = new Store(); 
		Island currentIsland = new Island("current", currStore, "arb description");
		// Create a player
		Player player1 = new Player("ben", 100, 30, currentIsland);
		// Create a game environment
		Island[] islands = new Island[] {currentIsland, i1, i2, i3};
		GameEnvironment game1 = new GameEnvironment(player1, islands);
		
		
	}
	
	
}
