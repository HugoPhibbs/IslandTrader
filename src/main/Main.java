package main;

import java.util.ArrayList;
import java.util.Arrays;

import coreClasses.GameEnvironment;
import coreClasses.Island;
import coreClasses.Item;
import coreClasses.Player;
import coreClasses.Store;
import uiClasses.*;


/**
 * The entry point to the program, creates instances of the classes required 
 * to start the game, then handles control to the class for the desired ui.
 * 
 * @author Jordan Vegar and Hugo Phibbs
 *
 */
public class Main {
	
	public static void main(String[] args) {
		// So we have basically moved the main method from gameEnvironment to main class
		
		// Create objects required to initiate GameEnvironement
		// Create stores and island
		Store s1 = new Store(); Store s2 = new Store(); Store s3 = new Store(); 
		Island i1 = new Island("other one", s1, "arb1"); 
		Island i2 = new Island("other two", s2, "arb2"); 
		Island i3 = new Island("other three", s3, "arb3"); 
		// Creating current instance of Island
		Store currStore = new Store(); 
		Island currentIsland = new Island("current", currStore, "arb description");
		// Create an island array, required for game environment constructor
		Island[] islands = new Island[] {currentIsland, i1, i2, i3};
		
		// Initiate the UI and Game Environment
		GameUi ui;
		// TODO: needs to be changed later to allow both UIs to work.
		// THis is however easier for testing the command line user interface
		ui = new CmdLineUi();
		GameEnvironment gameEnrviron = new GameEnvironment(islands, ui);
		ui.setup(gameEnrviron);
	}
}