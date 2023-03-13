package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.ldap.dao.LdapUserDao;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.domain.untracked.Authority;
import co.syscoop.soberano.domain.untracked.ContactData;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.Responsibility;
import co.syscoop.soberano.exception.SoberanoLDAPException;

public class Worker extends TrackedObject {

	private String loginName = ""; //email format
	private String firstName = "";
	private String lastName = "";
	private String password = "";
	private ContactData contactData = null;
	
	//responsibilities. same lengths arrays.
	//worker is assigned to responsibility in pos X for the authority in pos X.
	private ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
	private ArrayList<Integer> responsibilityIds = null;
	private ArrayList<Authority> authorities = new ArrayList<>();
	private Integer[] authorityIds = null;
	
	private void fillResponsiblityIds() {
		responsibilityIds = new ArrayList<Integer>();
		for (Responsibility resp : responsibilities) {
			responsibilityIds.add(resp.getId());
		}
	}
	
	private void fillAuthorityIds() {
		authorityIds = new Integer[authorities.size()];
		for (int i = 0; i < authorityIds.length; i++) {
			authorityIds[i] = authorities.get(i).getId();
		}
	}
	
	public Worker(Integer id) {
		super(id);
	}
	
	//useful to delete worker from ldap
	public Worker(String loginName) {
		this.loginName = loginName;
	}
	
	public Worker(Integer id,
			Integer entityTypeInstanceId,
			String loginName,
			String firstName,
			String lastName,
			String password,
			String mobilePhoneNumber,
			String countryCode,
			String address,
			String postalCode,
			String town,
			Integer municipalityId,
			String city,
			Integer provinceId,
			Double latitude,
			Double longitude) {
		super(id, entityTypeInstanceId, firstName + " " + lastName);
		this.loginName = loginName;
		this.setQualifiedName(firstName + " " + lastName + " : " + loginName);		
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setContactData(new ContactData(loginName,
										 mobilePhoneNumber,
										 firstName + " " + lastName,
										 countryCode,
										 address,
										 postalCode,
										 town,
										 municipalityId,
										 city,
										 provinceId,
										 latitude,
										 longitude));
	}
	
	public Worker(Integer id,
				Integer entityTypeInstanceId,
				String loginName,
				String firstName,
				String lastName,
				String password,
				String mobilePhoneNumber,
				String countryCode,
				String address,
				String postalCode,
				String town,
				Integer municipalityId,
				String city,
				Integer provinceId,
				Double latitude,
				Double longitude,
				ArrayList<Responsibility> responsibilities,
				ArrayList<Authority> authorities) {
		this(id,
			entityTypeInstanceId,
			loginName,
			firstName,
			lastName,
			password,
			mobilePhoneNumber,
			countryCode,
			address,
			postalCode,
			town,
			municipalityId,
			city,
			provinceId,
			latitude,
			longitude);
		this.responsibilities = responsibilities;
		fillResponsiblityIds();
		this.authorities = authorities;
		fillAuthorityIds();		
	}
	
	public Worker() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Worker_getAll\"" + "(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public ContactData getContactData() {
		return contactData;
	}

	public void setContactData(ContactData contactData) {
		this.contactData = contactData;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ArrayList<Responsibility> getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(ArrayList<Responsibility> responsibilities) {
		this.responsibilities = responsibilities;
		fillResponsiblityIds();
	}

	public ArrayList<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(ArrayList<Authority> authorities) {
		this.authorities = authorities;
		fillAuthorityIds();
	}
	
	private String addUserToLDAP() {
		
		LdapUserDao ldapUserDao = (LdapUserDao) SpringUtility.applicationContext().getBean("ldapUser");
		return ldapUserDao.insertUser(this);
	}
	
	private void changePassword(String pwd) throws SoberanoLDAPException {
		
		//if password isn't empty, change password
		if (!pwd.isEmpty()) {
			LdapUserDao userDao  = (LdapUserDao) SpringUtility.applicationContext().getBean("ldapUser");
			String errorMessage = userDao.changePassword(this, pwd);
			if (!errorMessage.isEmpty()) throw new SoberanoLDAPException(errorMessage);
		}
	}
	
	public String deleteUserFromLDAP() {
		
		LdapUserDao ldapUserDao = (LdapUserDao) SpringUtility.applicationContext().getBean("ldapUser");
		return ldapUserDao.deleteUser(this);
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Worker_create\"(:firstName, "
				+ "											:lastName, "
				+ "											:emailAddress, "
				+ "											:mobilePhoneNumber, "
				+ "											:address, "
				+ "											:postalCode, "
				+ "											:town, "
				+ "											:municipalityId, "
				+ "											:city, "
				+ "											:latitude, "
				+ "											:longitude, "
				+ "											:responsibilities, "
				+ "											:authorities, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("firstName", this.firstName);
		recordParameters.addValue("lastName", this.lastName);
		recordParameters.addValue("emailAddress", this.getLoginName());
		recordParameters.addValue("mobilePhoneNumber", this.contactData.getMobilePhoneNumber());
		recordParameters.addValue("address", this.contactData.getAddress());
		recordParameters.addValue("postalCode", this.contactData.getPostalCode());
		recordParameters.addValue("town", this.contactData.getTown());
		recordParameters.addValue("municipalityId", this.contactData.getMunicipalityId());
		recordParameters.addValue("city", this.contactData.getCity());
		recordParameters.addValue("latitude", this.contactData.getLatitude());
		recordParameters.addValue("longitude", this.contactData.getLatitude());
		recordParameters.addValue("responsibilities", createArrayOfSQLType("integer", responsibilityIds.toArray()));
		recordParameters.addValue("authorities", createArrayOfSQLType("integer", authorityIds));
		
		Integer qryResult = super.record();
		if (qryResult > 0) {
			
			//add user to directory
			String errorMessage = addUserToLDAP();
			if (!errorMessage.isEmpty()) throw new Exception(errorMessage);
		}
		else {
			qryResult = -1; //not enough permissions
		}
		return qryResult;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Worker_modify\"(:workerId, "
				+ "											:firstName, "
				+ "											:lastName, "
				+ "											:emailAddress, "
				+ "											:mobilePhoneNumber, "
				+ "											:address, "
				+ "											:postalCode, "
				+ "											:town, "
				+ "											:municipalityId, "
				+ "											:city, "
				+ "											:latitude, "
				+ "											:longitude, "
				+ "											:responsibilities, "
				+ "											:authorities, "
				+ "											:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("workerId", this.getId());
		modifyParameters.addValue("firstName", this.firstName);
		modifyParameters.addValue("lastName", this.lastName);
		modifyParameters.addValue("emailAddress", this.getLoginName());
		modifyParameters.addValue("mobilePhoneNumber", this.contactData.getMobilePhoneNumber());
		modifyParameters.addValue("address", this.contactData.getAddress());
		modifyParameters.addValue("postalCode", this.contactData.getPostalCode());
		modifyParameters.addValue("town", this.contactData.getTown());
		modifyParameters.addValue("municipalityId", this.contactData.getMunicipalityId());
		modifyParameters.addValue("city", this.contactData.getCity());
		modifyParameters.addValue("latitude", this.contactData.getLatitude());
		modifyParameters.addValue("longitude", this.contactData.getLongitude());
		modifyParameters.addValue("responsibilities", createArrayOfSQLType("integer", responsibilityIds.toArray()));
		modifyParameters.addValue("authorities", createArrayOfSQLType("integer", authorityIds));
		
		Integer qryResult = super.modify();
		if (qryResult < 0) {
			qryResult = -1; //not enough permissions
		}
		else {
			changePassword(password);
		}
		return qryResult;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Worker_disable\"(:workerId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("workerId", this.getId());
		
		Integer qryResult = super.disable();
		if (qryResult < 0) {
			qryResult = -1; //not enough permissions
		}
		else {
			changePassword(password);
		}
		return qryResult;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
	
	public final class WorkerExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Worker extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Worker worker = null;
			Integer workerCurrentlyBeingExtractedId = -1;
	        while (rs.next()) {
	        	if (workerCurrentlyBeingExtractedId != rs.getInt("workerId")) {
	        		workerCurrentlyBeingExtractedId = rs.getInt("workerId");
	        		worker = new Worker(rs.getInt("workerId"),
										rs.getInt("entityTypeInstanceId"),
										rs.getString("loginName"),
										rs.getString("firstName"),
										rs.getString("lastName"),
										rs.getString("password"),
										rs.getString("mobilePhoneNumber"),
										rs.getString("countryCode"),
										rs.getString("address"),
										rs.getString("postalCode"),
										rs.getString("town"),
										rs.getInt("municipalityId"),
										rs.getString("city"),
										rs.getInt("provinceId"),
										rs.getDouble("latitude"),
										rs.getDouble("longitude"));
	        	}
	        	worker.getAuthorities().add(new Authority(rs.getInt("authorityId"), rs.getString("authorityName")));
	        	worker.getResponsibilities().add(new Responsibility(rs.getInt("responsibilityId"), rs.getString("responsibilityName")));
	        }
	        return worker;
		}
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Worker_get\"(:workerId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("workerId", this.getId());
		super.get(new WorkerExtractor());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Worker sourceWorker = (Worker) sourceObject;
		setId(sourceWorker.getId());
		setEntityTypeInstanceId(sourceWorker.getEntityTypeInstanceId());
		setLoginName(sourceWorker.getLoginName());
		setFirstName(sourceWorker.getFirstName());
		setLastName(sourceWorker.getLastName());
		setPassword(sourceWorker.getPassword());
		contactData = new ContactData(sourceWorker.getContactData());
		setResponsibilities(sourceWorker.getResponsibilities());
		setAuthorities(sourceWorker.getAuthorities());
	}

	public ArrayList<Integer> getResponsibilityIds() {
		return responsibilityIds;
	}

	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on worker
		return null;
	}
}
