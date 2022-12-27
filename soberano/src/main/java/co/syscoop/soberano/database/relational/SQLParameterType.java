package co.syscoop.soberano.database.relational;

public enum SQLParameterType {
	Boolean(0),
	Integer(1),
	Double(2),
	Money(3),
	String(4),
	VarLengthString(5),
	Date(6),
	UUID(7),
	SQLArrayType(8);
	
	int parameterTypeNr;
	
	SQLParameterType(int parameterTypeNr) {
		this.parameterTypeNr = parameterTypeNr;
	}

    public int getParameterTypeNr() {
    	return this.parameterTypeNr;
    };
}
