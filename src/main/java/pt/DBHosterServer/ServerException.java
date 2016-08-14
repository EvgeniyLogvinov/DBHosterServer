package pt.DBHosterServer;

public class ServerException extends Exception {
	String message = null;
    
	public ServerException(String message) {
    	super(message);
    	this.message = message;
    }
    
    public String getMessage() {
    	return message;
    }
}
