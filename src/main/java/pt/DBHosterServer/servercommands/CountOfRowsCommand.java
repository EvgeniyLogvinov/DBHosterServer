package pt.DBHosterServer.servercommands;

public class CountOfRowsCommand implements ServerCommand {
	private String inputLine = null;
	private ClientResponse clientResponse;

	public CountOfRowsCommand(ClientResponse clientResponse, String inputLine) {
		this.inputLine= inputLine;
		this.clientResponse = clientResponse;
	}

	public void executeCommand() {
		this.clientResponse.setCountOfRows(inputLine);	
	}

}
