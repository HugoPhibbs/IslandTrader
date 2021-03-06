package coreClasses;

/** Represents a RescuedSailors random event
 * 
 * @author Hugo Phibbs
 * @version 17/5/2021
 * @since 2/4/2021
 */
public class RescuedSailors {
	/** int Amount of money given to a player for saving sailors */
	private int REWARD_MONEY;
	
	
	/** Constructor method for a RescuedSailors event
	 * 
	 * @param REWARD_MONEY int for the amount of money given to a player as a reward upon rescue
	 */
	public RescuedSailors(int REWARD_MONEY) {
		this.REWARD_MONEY = REWARD_MONEY;
	}
	
	/** Method to give reward money to a player. Gives an arbitrary amount
	 * 
	 * @param player Player object to receive money 
	 */
	public void giveMoney(Player player) {
		player.earnMoney(REWARD_MONEY); 
	}
	
	/** Getter method for the description of a RescuedSailors event
	 * 
	 * @return String representation for the description of a Rescued Sailors Event
	 */
	public static String getDescription() {
		return "You have encountered stranded sailors. \n You have recieved 50 Pirate Bucks as a reward for rescuing them!";
	}
}