package pt.DBHosterServer.servercommands;

public class ClientResponseUtil {
	
	public static ClientResponse getClientResponse() {
		return (ClientResponse) new DBHosterClientResponse();
	}
}
