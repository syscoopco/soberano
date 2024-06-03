package co.syscoop.soberano.domain.untracked;

public class Parameter {
	
	private String paramName = "";
	private String paramValue = "";
	
	public Parameter(String paramName,
					String paramValue) {
		this.setParamName(paramName);
		this.setParamValue(paramValue);
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
