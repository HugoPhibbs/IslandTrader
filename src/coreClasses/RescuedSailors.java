package coreClasses;

public class RescuedSailors {
	
	public static String getDescription() {
		return "You have encountered stranded sailors. \n You have recieved 50 Pirate Bucks as a reward for rescuing them!";
	}

	public static void giveMoney(Player player) {
		player.earnMoney(50); // arbitrary value, not based on chance in specs, so just make it 50
	}
}