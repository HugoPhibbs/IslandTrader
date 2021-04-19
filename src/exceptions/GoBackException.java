package exceptions;

public class GoBackException extends IllegalArgumentException {
	
	public GoBackException() {}
	
	public GoBackException(String message) {
		super(message);
	}
}
