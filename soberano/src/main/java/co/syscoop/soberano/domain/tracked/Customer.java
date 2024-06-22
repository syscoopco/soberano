package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.domain.untracked.ContactData;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;

public class Customer extends TrackedObject {
	
	private String firstName = "";
	private String lastName = "";
	private BigDecimal discount = new BigDecimal(0.0);
	private ContactData contactData = null;	
	
	public Customer() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Customer_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	public Customer(Integer id) {
		super(id);
	}
	
	public Customer(Integer id,
					Integer entityTypeInstanceObject,
					String emailAddress,
					String firstName,
					String lastName,
					String mobilePhoneNumber,
					String countryCode,
					String address,
					String postalCode,
					String town,
					Integer municipality,
					String city,
					Integer province,
					Double latitude,
					Double longitude,
					BigDecimal discount) {
		super(id, entityTypeInstanceObject, firstName + " " + lastName);
		this.setQualifiedName(firstName + " " + lastName + " : " + emailAddress);
		this.firstName = firstName;
		this.lastName = lastName;
		this.setContactData(new ContactData(emailAddress,
										 mobilePhoneNumber,
										 firstName + " " + lastName,
										 countryCode,
										 address,
										 postalCode,
										 town,
										 municipality,
										 city,
										 province,
										 latitude,
										 longitude));
		this.discount = discount;
	}

	public ContactData getContactData() {
		return contactData;
	}

	public void setContactData(ContactData contactData) {
		this.contactData = contactData;
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Customer_create\"(:firstName, "
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
				+ "											:discount, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("firstName", this.firstName);
		recordParameters.addValue("lastName", this.lastName);
		recordParameters.addValue("emailAddress", this.contactData.getEmailAddress());
		recordParameters.addValue("mobilePhoneNumber", this.contactData.getMobilePhoneNumber());
		recordParameters.addValue("address", this.contactData.getAddress());
		recordParameters.addValue("postalCode", this.contactData.getPostalCode());
		recordParameters.addValue("town", this.contactData.getTown());
		recordParameters.addValue("municipalityId", this.contactData.getMunicipalityId());
		recordParameters.addValue("city", this.contactData.getCity());
		recordParameters.addValue("latitude", this.contactData.getLatitude());
		recordParameters.addValue("longitude", this.contactData.getLongitude());
		recordParameters.addValue("discount", this.getDiscount());
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Customer_modify\"(:customerId, "
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
				+ "											:discount, "
				+ "											:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("customerId", this.getId());
		modifyParameters.addValue("firstName", this.firstName);
		modifyParameters.addValue("lastName", this.lastName);
		modifyParameters.addValue("emailAddress", this.contactData.getEmailAddress());
		modifyParameters.addValue("mobilePhoneNumber", this.contactData.getMobilePhoneNumber());
		modifyParameters.addValue("address", this.contactData.getAddress());
		modifyParameters.addValue("postalCode", this.contactData.getPostalCode());
		modifyParameters.addValue("town", this.contactData.getTown());
		modifyParameters.addValue("municipalityId", this.contactData.getMunicipalityId());
		modifyParameters.addValue("city", this.contactData.getCity());
		modifyParameters.addValue("latitude", this.contactData.getLatitude());
		modifyParameters.addValue("longitude", this.contactData.getLongitude());
		modifyParameters.addValue("discount", this.getDiscount());
		
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Customer_disable\"(:customerId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("customerId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Customer sourceCustomer = (Customer) sourceObject;
		setId(sourceCustomer.getId());
		setEntityTypeInstanceId(sourceCustomer.getEntityTypeInstanceId());
		setFirstName(sourceCustomer.getFirstName());
		setLastName(sourceCustomer.getLastName());
		setDiscount(sourceCustomer.getDiscount());
		contactData = new ContactData(sourceCustomer.getContactData());
	}

	@Override
	public Integer print() throws SoberanoException {
		return null;
	}
	
	public final class CustomerMapper implements RowMapper<Object> {

		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Customer customer = null;
				int id = rs.getInt("itemId");
				if (!rs.wasNull()) {
					customer = new Customer(id,
											rs.getInt("entityTypeInstanceId"),
											rs.getString("emailAddress"),
											rs.getString("firstName"),
											rs.getString("lastName"),
											rs.getString("mobilePhoneNumber"),
											rs.getString("countryCode"),
											rs.getString("address"),
											rs.getString("postalCode"),
											rs.getString("town"),
											rs.getInt("municipalityId"),
											rs.getString("city"),
											rs.getInt("provinceId"),
											rs.getDouble("latitude"),
											rs.getDouble("longitude"),
											rs.getBigDecimal("discount"));
				}
				return customer;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}

	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Customer_get\"(:itemId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("itemId", this.getId());
		super.get(new CustomerMapper());
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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

	@Override
	public PrintableData getReportFull() throws SQLException {
		return null;
	}

	@Override
	public PrintableData getReportMinimal() throws SQLException {
		return null;
	}
}
