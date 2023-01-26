package co.syscoop.soberano.ws;

public class TestObject implements IJSONTranslation{

	private int intField = 0;
	private String strField = "0";
	
	public TestObject(int intField, String strField) {
		this.intField = intField;
		this.strField = strField;
	}
	
	@Override
	public String toJSON() {
		StringBuilder json = new StringBuilder();
		json.append("{\"intField\":\"")
		.append(intField).append("\", \"strField\":\"")
		.append(strField).append("\"}");
		return json.toString();
	}

	@Override
	public Object fromJSONToObject(String representation) {
		return null;
	}

	@Override
	public Object[] fromJSONToArray(String representation) {
		return null;
	}
}
