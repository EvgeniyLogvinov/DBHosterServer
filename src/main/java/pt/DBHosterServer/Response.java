package pt.DBHosterServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import pt.DBHosterServer.json.JsonResponse;
import pt.DBHosterServer.json.element.JsonElement;
import pt.DBHosterServer.json.elements.JsonColumns;
import pt.DBHosterServer.json.elements.JsonElements;
import pt.DBHosterServer.json.elements.JsonErrors;
import pt.DBHosterServer.json.elements.JsonRows;

public class Response {
	Map<String, List<JsonElement>> elements = new HashMap<String, List<JsonElement>>();
	ObjectMapper mapper = new ObjectMapper();
	Connection connection = null;
	Statement stmt = null;
	int elementPosition = 0;
	int countOfRows = 0;
	
	public int getElementPosition() {
		return this.elementPosition;
	}

	public int getCountOfRows() {
		return this.countOfRows;
	}
	
	public void setCountOfRows(int countOfRows) {
		this.countOfRows = countOfRows;
	}
	
	public void setElementPosition(int elementPosition) {
		this.elementPosition = elementPosition;
	}
	
	public Statement getStatement() {
		return stmt;
	}

	public Response(Connection connection) throws SQLException {
		this.connection = connection;
		System.out.println("Creating statement...");
		stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(100);
	}
	
	public String getResponse(String sql) throws JsonGenerationException, JsonMappingException, IOException {
		String res = null;
		ResultSet rs = null;
		
		JsonElements columns = new JsonColumns();
		JsonRows rows = new JsonRows();
		
		rows.setCountOfRows(countOfRows);
		rows.setElementPosition(elementPosition);
		
		
		try {
			// execute sql
			System.out.println("Sql: " + sql);
			elements.clear();
			rs = stmt.executeQuery(sql);			
			List<JsonElement> columnList = columns.getElements(rs);
			List<JsonElement> rowList = rows.getElements(rs);
			elementPosition = rows.getElementPosition();
			elements.put("rows", rowList);
			elements.put("columns", columnList);	
			
			JsonResponse response = new JsonResponse(elements);	
			res = mapper.writeValueAsString(response);
		}

		catch (SQLSyntaxErrorException se) {
			res = getErrorResponse(se.getMessage());
		}

		catch (SQLException se) {
			res = getErrorResponse(se.getMessage());
		}

		finally {
			// finally block used to close resources
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					res = getErrorResponse(e.getMessage());
				}
			}
		}
		
		return res;
	}
	
	public void closeStatment() throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}
	
	// get error from json
	public String getErrorResponse(String errorText) 
			throws JsonGenerationException, JsonMappingException, IOException {
		JsonElements errors = new JsonErrors();
		List<JsonElement> errorList = null;
		errorList = errors.getErrors(errorText);
		elements.clear();
		elements.put("errors", errorList);
		
		System.out.println(errorText);		
		
		JsonResponse response = new JsonResponse(elements);
		
		return mapper.writeValueAsString(response);
	}

}