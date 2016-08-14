package pt.DBHosterServer.json.elements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import pt.DBHosterServer.json.element.JsonElement;

public class JsonErrors extends JsonElements {

	@Override
	public List<JsonElement> getElements(ResultSet rs) throws SQLException {
		return null;
	}
	
}
