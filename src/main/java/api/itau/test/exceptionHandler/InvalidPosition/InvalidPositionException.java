package api.itau.test.exceptionHandler.InvalidPosition;

public class InvalidPositionException extends RuntimeException{
    public InvalidPositionException(String message){
        super(message);
    }
}
