package coreClasses;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author Jordan Vegar
 *
 */
public final class TakeInput {
	
	/**
	 * 
	 * @param message String that is printed to prompt the user for input. 
	 * @return inputStr the user's input as a string, converted to lower case for simple comparison.
	 */
	public static String inputString(String message) {
		Scanner myScanner = new Scanner(System.in);
		System.out.println(message);
		String inputStr = myScanner.nextLine().toLowerCase();
		myScanner.close();
		return inputStr;
	}
	
	// TODO: final return statement never executes, is only there to satisfy the compiler. Rewrite to make better style.
	public static int inputInt(String message) {
		System.out.println(message);
		boolean successful = false;
		while (!successful) {
			try {
				int input = inputIntHelper();
				return input;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
		}
		return 0;
	}
	
	public static int inputIntHelper() {
		Scanner scan = new Scanner(System.in);
		if (!scan.hasNext()) {
			scan.next();
		}
		int input = scan.nextInt();
		scan.close();
		return input;
	}
}
