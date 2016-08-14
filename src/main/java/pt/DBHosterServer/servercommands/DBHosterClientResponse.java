package pt.DBHosterServer.servercommands;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import pt.DBHosterServer.Response;

public class DBHosterClientResponse implements ClientResponse {
	private int elementPosition = 0;
	private int countOfRows = 0;
	private String sql = null;

	// if client send "disconnect", we are shut down server
	public void disconnect(PrintWriter out) {
		System.out.println("... server shutting down ...");
		out.println("disconnect:");
	}

	// execute sql from client
	public void executeSql(PrintWriter out, Response serverResponse, String sql) 
			throws JsonGenerationException, JsonMappingException, IOException {
		this.elementPosition = 0;
		this.sql = sql;
		getResponse(out, serverResponse);
	}

	//get previous rows from Result set
	public void prevSql(PrintWriter out, Response serverResponse) 
			throws JsonGenerationException, JsonMappingException, IOException {
		this.elementPosition = serverResponse.getElementPosition() - countOfRows * 2;
		getResponse(out, serverResponse);
	}

	//get next rows from Result set
	public void nextSql(PrintWriter out, Response serverResponse) 
			throws JsonGenerationException, JsonMappingException, IOException {
		this.elementPosition = serverResponse.getElementPosition();
		getResponse(out, serverResponse);
	}

	//set count of rows from client
	public void setCountOfRows(String inputLine) {
		this.countOfRows = Integer.parseInt(inputLine.trim().substring(inputLine.indexOf(":") + 1));
	}

	// execute sql and create response to client
	private void getResponse(PrintWriter out, Response serverResponse)
			throws JsonGenerationException, JsonMappingException, IOException {
		serverResponse.setElementPosition(elementPosition);
		serverResponse.setCountOfRows(countOfRows);
		String response = serverResponse.getResponse(sql);
		elementPosition = serverResponse.getElementPosition();
		out.println(response);
		System.out.println("Response to client: " + response);
	}

}
