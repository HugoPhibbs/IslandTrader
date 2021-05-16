package coreClasses;

/** General class to check for valid input across project
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 16/4/2021
 * @since 15/4/2021
 */
public class CheckValidInput {
	
	/**Checks that chosen duration of the game is between 20 and 50 days. 
	 * 
	 * @param days Integer for the number of days to be checked.
	 * @return boolean if the given game duration is valid
	 */
	public static boolean durationIsValid(int days) {
		if (20 <= days && days <= 50) {
			return true;
		}
		return false;
	}
	
	/** Checks if an inputed name is valid.
	 * Name must have no more than 2 consecutive white space, and be between 3 and 15 chars long
	 * 
	 * @param name String for the name to be checked
	 * @return boolean if the inputed name is valid
	 */
	public static boolean nameIsValid(String name) {
				
		boolean prevWhiteSpace = false;
		
		for (int i=0; i < name.length(); i++) {
			if (name.charAt(i) == ' ') {
				if (prevWhiteSpace) {
					return false;
				}
				prevWhiteSpace = true;
			}
			else if (!Character.isLetter(name.charAt(i))) {
				return false;
			}
			else {
				prevWhiteSpace = false;
			}
		}
		if (name.length() < 3 || name.length() > 15) {
			return false;
		}
		return true;
	}
}
