package exceptions;

public class GameOverException extends IllegalStateException{
	
	public GameOverException(){
		super();
	}
	
	public GameOverException(String msg){
		super(msg);
	}
}