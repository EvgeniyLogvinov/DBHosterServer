package pt.DBHosterServer.json.elements;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.DBHosterServer.json.element.JsonElement;
import pt.DBHosterServer.json.element.JsonRow;

public class JsonRows extends JsonElements {
	private int elementPosition = 0;
	private int countOfRows = 0;
	
	public int getElementPosition() {
		return elementPosition;		
	}
	
	public void setElementPosition(int elementPosition) {
		this.elementPosition = elementPosition;
	}
	
	public int getCountOfRows() {
		return countOfRows;		
	}
	
	public void setCountOfRows(int countOfRows) {
		this.countOfRows = countOfRows;
	}
	
	@Override
	public List<JsonElement> getElements(ResultSet rs) throws SQLException {
		List<JsonElement> rows = new ArrayList<JsonElement>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int j = 1;
		rs.absolute(elementPosition == -1 ? 0 : elementPosition);
		if (rs.isLast()) {
			rs.first();
		} 
		
		while (rs.next()) {
			Map<String, String> row = new HashMap<String, String>();

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				rsmd.getColumnName(i);
				row.put(rsmd.getColumnName(i).replaceAll("\"", "\\\\\""),
						rs.getString(rsmd.getColumnName(i)).replaceAll("\"", "\\\\\""));
			}
			rows.add(new JsonRow(row));
			if (j >= countOfRows) {
				break;
			}
			j++;			
		}
		elementPosition = rs.getRow();
		return rows;
	}

}