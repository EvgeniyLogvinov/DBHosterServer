package pt.DBHosterServer.json.elements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pt.DBHosterServer.json.element.JsonElement;
import pt.DBHosterServer.json.element.JsonError;

public abstract class JsonElements {
	public abstract List<JsonElement> getElements(ResultSet rs) throws SQLException;
	
	public List<JsonElement> getErrors(String error) {
		JsonError jsonError = new JsonError(error);
		List<JsonElement> errors = new ArrayList<JsonElement>();
		errors.add(jsonError);
		
		return errors;
	}

}
