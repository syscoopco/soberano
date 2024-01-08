package co.syscoop.soberano.util;

public class SQLQueryFilterParam {

	private String paramName = "";
	private Object paramValue = null;
	
	public SQLQueryFilterParam (String paramName, Object paramValue) {
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	
	public String getParamName() {
		return paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}
}
