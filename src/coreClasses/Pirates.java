package coreClasses;

import exceptions.GameOverException;
import java.util.Random;
import java.util.ArrayList;

public class Pirates {
	// in ui, it separately calls, getDescription(), rollDice(), attackShip(int diceInt, Ship ship)
	// and in that order
    
    public static String getDescription(){
    	return "You have encountered pirates, roll the die to play your chances!";
    }

    public static String attackShip(int diceInt, Ship ship) {
    	
    	/* based on a game of chance
    	 * defense capability is multiplied by the number that is rolled by dice. 
    	 * random int is chosen because max defense capability is 50, tf max effectivity is
    	 * 300, so there is a chance of 18% of loosing with a perfect turn!, max dice
    	 * roll and max defense capability
    	 */
    	
    	Random random = new Random();
    	int randomAttack = random.nextInt(366); //arbitrary upper bound, can be adjusted if need be
    	
    	if (randomAttack > ship.getDefenseCapability()*diceInt) {
    		return takeGoods(ship);
    	}
    	return "attack_failed";
    }

    public static String takeGoods(Ship ship){
    	// public for testing
    	
    	Random random = new Random();
    	int randomGoodDemand = random.nextInt(120); //arbitrary upper bound, can be adjusted if need be
    	    			
        if (randomGoodDemand > ship.getOccupiedCargoCapacity()){
        	/* IMPORTANT due to this implementation, item and ship upgrades must be 
        	 * stored separately within a ship!
        	 */
        	String gameOverMessage = "You have less goods than what the pirates demand. \n"
        			+ "You and your crew have to walk the plank! \n"
        			+ "GAME OVER!";
        	return gameOverMessage;
        }
        else{
        	while (randomGoodDemand > 0) {
        		 Item biggestItem = getLargestShipItem(ship);
        		 ship.takeItem(biggestItem.getName());
        		 randomGoodDemand -= biggestItem.getSpaceTaken();
        	}
        	return "attack_successful";
        }
    }
    
    public static int rollDice() {
    	// ask for ui to rollDie, then print out what number was rolled, should
    	// be a button in gui or entry in cmd line ui to roll a dice
    	
    	// here to make experience more interactive in the ui, like actually rolling a dice
    	Random random = new Random();
    	return random.nextInt(6) + 1;
    }

    public static Item getLargestShipItem(Ship ship) {
    	// public for testing
    	ArrayList<Item> itemsArrayList = ship.getItems();
    	int biggestSize = -1;
    	Item biggestItem = null;
    	for (int i = 0; i < itemsArrayList.size(); i++) {
    		if (itemsArrayList.get(i).getSpaceTaken() > biggestSize) {
    			biggestItem = itemsArrayList.get(i);
    			biggestSize = biggestItem.getSpaceTaken();
    		}
    	}
    	return biggestItem;
    }
}
