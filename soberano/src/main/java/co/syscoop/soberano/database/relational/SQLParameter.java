package co.syscoop.soberano.database.relational;

public class SQLParameter {
	
	private SQLParameterType parameterType;
	
	private Object  parameterValue;
	
	SQLParameter(SQLParameterType parameterType, Object parameterValue) {
		this.parameterType = parameterType;
		this.parameterValue= parameterValue;
	}

	public SQLParameterType getParameterType() {
		return parameterType;
	}

	public void setParameterType(SQLParameterType parameterType) {
		this.parameterType = parameterType;
	}

	public Object getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(Object parameterValue) {
		this.parameterValue = parameterValue;
	}
}
