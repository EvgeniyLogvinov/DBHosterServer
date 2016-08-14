package pt.DBHosterServer.servercommands;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import pt.DBHosterServer.Response;

public class ExecuteSqlCommand implements ServerCommand {
	private ClientResponse clientResponse;
	private Response serverResponse = null;
	private String sql = null;
	private PrintWriter out = null;
		
	public ExecuteSqlCommand(ClientResponse clientResponse, PrintWriter out, Response serverResponse, String sql) {
		this.clientResponse = clientResponse;
		this.serverResponse = serverResponse;
		this.sql = sql;
		this.out = out;
	}

	public void executeCommand() throws JsonGenerationException, JsonMappingException, IOException {
		this.clientResponse.executeSql(out, serverResponse, sql);
	}

}
