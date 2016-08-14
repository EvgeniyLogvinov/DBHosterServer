package pt.DBHosterServer.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonCreator;

import pt.DBHosterServer.json.element.JsonElement;

public class JsonResponse {

private Map<String, List<JsonElement>> elements = new HashMap<String, List<JsonElement>>();
	
	public JsonResponse() {
	}
	 
	@JsonCreator
	public JsonResponse(Map<String, List<JsonElement>> elements) {
		this.elements = elements;
	}
	
	@JsonAnyGetter
    public Map<String, List<JsonElement>> any() {
        return elements;
    }
	
	@JsonAnySetter
    public void set(String name, List<JsonElement> value) {
		elements.put(name, value);
    }
	
	@Override
    public String toString() {
        return "JsonResponse [fields=" + elements + "]";
    }

}