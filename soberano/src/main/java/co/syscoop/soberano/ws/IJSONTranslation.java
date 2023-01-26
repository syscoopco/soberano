package co.syscoop.soberano.ws;

public interface IJSONTranslation {

	public abstract String toJSON();
	public abstract Object fromJSONToObject(String representation);
	public abstract Object[] fromJSONToArray(String representation);
}
