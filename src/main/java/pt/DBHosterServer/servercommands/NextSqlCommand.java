package pt.DBHosterServer.servercommands;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import pt.DBHosterServer.Response;

public class NextSqlCommand implements ServerCommand {
	private ClientResponse clientResponse;
	private Response serverResponse = null;
	private PrintWriter out = null;
	
	public NextSqlCommand(ClientResponse clientResponse, PrintWriter out, Response serverResponse) {
		this.clientResponse = clientResponse;
		this.serverResponse = serverResponse;
		this.out = out;
	
	}
	
	public void executeCommand() throws JsonGenerationException, JsonMappingException, IOException {
		this.clientResponse.nextSql(out, serverResponse);
	}

}
