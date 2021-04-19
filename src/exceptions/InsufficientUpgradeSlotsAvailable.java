package exceptions;

public class InsufficientUpgradeSlotsAvailable extends IllegalArgumentException{

    public InsufficientUpgradeSlotsAvailable(){}

    public InsufficientUpgradeSlotsAvailable(String message){
        super(message);
    }
}