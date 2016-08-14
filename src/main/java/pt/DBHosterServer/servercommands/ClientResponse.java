package pt.DBHosterServer.servercommands;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import pt.DBHosterServer.Response;

public interface ClientResponse {
	void disconnect(PrintWriter out);
	void executeSql(PrintWriter out, Response serverResponse, String sql) throws JsonGenerationException, JsonMappingException, IOException;
	void prevSql(PrintWriter out, Response serverResponse) throws JsonGenerationException, JsonMappingException, IOException;
	void nextSql(PrintWriter out, Response serverResponse) throws JsonGenerationException, JsonMappingException, IOException;
	void setCountOfRows(String inputLine);
}