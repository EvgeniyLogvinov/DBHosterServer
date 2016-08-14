package pt.DBHosterServer.servercommands;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public class ClientResponseInvoker {
	public ServerCommand command;
	
	public ClientResponseInvoker(ServerCommand command) {
		this.command = command;
	}
	
	public void execute() throws JsonGenerationException, JsonMappingException, IOException {
		this.command.executeCommand();
	}
}
