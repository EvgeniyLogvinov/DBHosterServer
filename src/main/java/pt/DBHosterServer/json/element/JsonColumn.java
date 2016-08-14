package pt.DBHosterServer.json.element;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class JsonColumn extends JsonElement {
	public String name;
	public String type;
	
	@JsonCreator
	public JsonColumn(@JsonProperty("name") String name, @JsonProperty("type") String type) {
		this.name = name;
		this.type = type;
	}
	
	public JsonColumn() {
	}
	
	@Override
	public String toString() {
		return "JsonColumn [name=" + name + ", type=" + type + "]";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}