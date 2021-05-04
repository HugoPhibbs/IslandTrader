package coreClasses;

import exceptions.GameOverException;
import java.util.Random;

public class UnfortunateWeather{

	public static String getDescription() {
		return "You have entered a dangerous storm, you are at the mercy of the weather!";
	}
	
	public static void damageShip(Ship ship) {
		Random random = new Random();
		int damage = random.nextInt(100); // max damage of 99, cant end game
		ship.takeDamage(damage);
	}
}

