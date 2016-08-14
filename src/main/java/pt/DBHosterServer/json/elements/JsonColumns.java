package pt.DBHosterServer.json.elements;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pt.DBHosterServer.json.element.JsonColumn;
import pt.DBHosterServer.json.element.JsonElement;

public class JsonColumns extends JsonElements {

	@Override
	public List<JsonElement> getElements(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		ArrayList<JsonElement> columns = new ArrayList<JsonElement>();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if (rsmd.getColumnType(i) < 100) {
				JsonColumn jColumn = new JsonColumn(
						rsmd.getColumnName(i).replaceAll("\"", "\\\\\""),
						String.valueOf(rsmd.getColumnType(i)));
				columns.add(jColumn);
			} else {
				return getErrors("Your table have Blob/Clob or others byte type");
			}
		}
	
		return columns;
	}
	
}