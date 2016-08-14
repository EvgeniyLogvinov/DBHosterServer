package pt.DBHosterServer.json.element;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class JsonError extends JsonElement {

	public JsonError() {
	}

	@JsonProperty("error") 
	public String error;
	
	@JsonCreator
	public JsonError(@JsonProperty("error") String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return "JsonError [error=" + error + "]";
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}

}
