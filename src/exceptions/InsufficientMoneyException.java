package exceptions;

public class InsufficientMoneyException extends IllegalArgumentException{
	
	// correct extension of IllegalArgumentException?
	// not ideal but had a skim and couldn't find any that make more sense - jv
	
	public InsufficientMoneyException() {}
	
	public InsufficientMoneyException(String message) {
		super(message);
	}
}
