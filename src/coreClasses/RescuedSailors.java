package coreClasses;

/** Represents a RescuedSailors random event
 * 
 * @author Hugo Phibbs
 * @version 14/5/2021
 * @since 2/4/2021
 */
public class RescuedSailors {
	
	private int REWARD_MONEY;
	
	/** Constructor method for a RescuedSailors event
	 * 
	 * @param REWARD_MONEY Integer for the amount of money given to a player as a reward upon rescue
	 */
	public RescuedSailors(int REWARD_MONEY) {
		this.REWARD_MONEY = REWARD_MONEY;
	}
	
	/** Getter method for the description of a RescuedSailors event
	 * 
	 * @return String representation for the description of a Rescued Sailors Event
	 */
	public static String getDescription() {
		return "You have encountered stranded sailors. \n You have recieved 50 Pirate Bucks as a reward for rescuing them!";
	}
	
	/** Method to give reward money to a player. Gives an arbitary amount
	 * 
	 * @param player
	 */
	public void giveMoney(Player player) {
		player.earnMoney(REWARD_MONEY); // arbitrary value, not based on chance in specs, so just make it 50
	}
}