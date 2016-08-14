package pt.DBHosterServer.json.element;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonCreator;

public class JsonRow extends JsonElement {
	private Map<String, String> fields = new HashMap<String, String>();
	
	public JsonRow() {
	}
	 
	@JsonCreator
	public JsonRow(Map<String, String> fields) {
		this.fields = fields;
	}
	
	@JsonAnyGetter
    public Map<String, String> any() {
        return fields;
    }
	
	@JsonAnySetter
    public void set(String name, String value) {
		fields.put(name, value);
    }
	
	@Override
    public String toString() {
        return "JsonRow [fields=" + fields + "]";
    }

}
