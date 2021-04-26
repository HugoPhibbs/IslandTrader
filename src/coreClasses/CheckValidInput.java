package coreClasses;

/** Class to check for valid input across project
 * 
 * @author Hugo Phibbs
 * @version 16/4/2021
 * @since 15/4/2021
 */

public class CheckValidInput {
	
	/**
	 * Checks that chosen duration of the game is between 20 and 50 days. 
	 * @param days
	 */
	public static boolean durationIsValid(int days) {
		
		if (20 <= days && days <= 50) {
			return true;
		}
		return false;
	}
	
	public static boolean nameIsValid(String name) {
		/** 
		 * Do we need to check that the string doesn't have any leading white space, or should we remove this when inputting?
		 */
				
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
	
	public static boolean actionIntIsValid(int i, int lowerBound, int upperBound) {
		// Helper method that can be used throughout gameEnvironment
		// TODO transfer implementations of this to gameEnvironment etc
		return (i < lowerBound || i > upperBound);
	}
}
