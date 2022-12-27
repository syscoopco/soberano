package co.syscoop.soberano.exception;

public enum SoberanoExceptionCodes
{
	//general
    UNDETERMINED_ERROR(0),
    
    //database
    SQL_EXCEPTION(1001),
    
    //conceptual model    
    UNSUPPORTED_ORM_DATATYPE(2001),    
    UNSUPPORTED_ORM_VALUECONSTRAINT_RESTRICTION(2002),
    MULTIPLE_INHERITANCE_UNSUPPORTED(2003),
    UNARY_FACTTYPE_CANNOT_BE_OBJECTIFIED(2004),
    OBJECTIFIED_UNARY_FACTTYPES_UNSUPPORTED(2005),
    COMPOSED_VALUETYPES_UNSUPPORTED(2005),
    DEPENDENCY_CYCLE(2006),
    
    //ldap
    LDAP_EXCEPTION(3001),
	
	//business
	NON_EXISTENT_OBJECT_OR_NOT_ENOUGH_RIGHTS(4001);

    int exceptionCode;

    SoberanoExceptionCodes( int exceptionCode ) { this.exceptionCode = exceptionCode; }

    public int getExceptionCode() { return exceptionCode; }
}