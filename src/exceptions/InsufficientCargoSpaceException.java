package exceptions;

public class InsufficientCargoSpaceException extends IllegalArgumentException{
    // throws an exception for insufficient cargo
    // message should contain how much space cargo is left, and how much cargo was needed to add an item
    // that threw this exception in the first place.

    public InsufficientCargoSpaceException(){}

    public InsufficientCargoSpaceException(String message){
        super(message);
    }
}