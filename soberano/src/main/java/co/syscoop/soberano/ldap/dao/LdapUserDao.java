package co.syscoop.soberano.ldap.dao;

import javax.naming.Name;
import javax.naming.directory.*;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.util.SpringUtility;

public class LdapUserDao {
	private LdapTemplate ldapTemplate;
	
	private LdapNameBuilder dnBuilder(Worker worker) {
		String loginName = worker.getLoginName();
		return LdapNameBuilder.newInstance().add("uid", loginName);
	}
	
	private Name buildDn(Worker worker) {
		return dnBuilder(worker).build();
	}
	
	private String extractLDAPErrorMessage(String errorMessage) {
		
		if (errorMessage.indexOf("NO_SUCH_OBJECT") >= 0) {
			return Labels.getLabel("message.error.LDAP.InexistentUser") + ": " + SpringUtility.loggedUser();
		}
		else if (errorMessage.indexOf("CONSTRAINT_VIOLATION") >= 0) {
			return Labels.getLabel("message.error.LDAP.PasswordConstraintViolation");
		}
		else {
			return errorMessage.substring(errorMessage.lastIndexOf(":") + 1, errorMessage.lastIndexOf("]"));
		}
	}
    
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
    
	public String insertUser(Worker worker) {
		try {
			Attributes userAttributes = new BasicAttributes();
		    BasicAttribute userBasicAttribute = new BasicAttribute("objectClass");
		    userBasicAttribute.add("person");
		    userBasicAttribute.add("inetOrgPerson");
		    userAttributes.put(userBasicAttribute);
		    userAttributes.put("cn", worker.getLoginName().toLowerCase());
		    userAttributes.put("sn", worker.getLoginName().toLowerCase());
		    userAttributes.put("uid", worker.getLoginName().toLowerCase());
		    userAttributes.put("userPassword", worker.getPassword());
		    Name newUserName = LdapNameBuilder.newInstance().add("uid=" + worker.getLoginName().toLowerCase()).build();
		    ldapTemplate.bind(newUserName, null, userAttributes);
		    return "";
	    }
	    catch(Exception ex) {
	    	return extractLDAPErrorMessage(ex.getLocalizedMessage());
	    }
	}
	
	public String changePassword(Worker worker, String newPassword) {
		try {
			ModificationItem[] mods = new ModificationItem[1];
			Attribute mod0 = new BasicAttribute("userPassword", newPassword);
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
			ldapTemplate.modifyAttributes(buildDn(worker), mods);
	    	return "";
	    }
	    catch(Exception ex) {
	    	ex.printStackTrace();
	    	return extractLDAPErrorMessage(ex.getLocalizedMessage());
	    }
	}
	
	public String deleteUser(Worker worker) {
		try {
			ldapTemplate.unbind(buildDn(worker));
	    	return "";
	    }
	    catch(Exception ex) {
	    	ex.printStackTrace();
	    	return extractLDAPErrorMessage(ex.getLocalizedMessage());
	    }
	}
}
