package pt.DBHosterServer.servercommands;

import java.io.PrintWriter;

public class DisconnectCommand implements ServerCommand {
	private PrintWriter out = null;
	private ClientResponse clientResponse = null;
	
	public DisconnectCommand(ClientResponse clientResponse, PrintWriter out) {
		this.clientResponse = clientResponse;
		this.out = out;
	}

	public void executeCommand() {
		this.clientResponse.disconnect(out);
	}

}
