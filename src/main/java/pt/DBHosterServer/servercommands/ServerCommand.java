package pt.DBHosterServer.servercommands;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface ServerCommand {
	
	void executeCommand() throws JsonGenerationException, JsonMappingException, IOException;
}
