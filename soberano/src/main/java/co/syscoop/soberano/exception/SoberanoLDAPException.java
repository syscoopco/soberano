package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class SoberanoLDAPException extends SoberanoException {

	public SoberanoLDAPException(String LDAPErrorMessage) {
		super(SoberanoExceptionCodes.LDAP_EXCEPTION, LDAPErrorMessage);
	}
}