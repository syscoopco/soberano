package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.database.relational.DeliveryFeeMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;

public class DeliveryProvider extends TrackedObject {

	private Double rate = 0.0;
	private Boolean isReseller = false;
	private ArrayList<String> feeCountries = new ArrayList<String>();
	private ArrayList<String> feePostalCodes = new ArrayList<String>();
	private ArrayList<BigDecimal> fees = new ArrayList<BigDecimal>();
		
	public DeliveryProvider(Integer id) {
		super(id);
	}
	
	public DeliveryProvider(Integer id, String name) {
		super(id, name);
	}
	
	public DeliveryProvider(Integer id, 
			Integer entityTypeInstanceId, 
			String name,
			Double rate,
			Boolean isReseller) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name);		
		this.setRate(rate);
		this.setIsReseller(isReseller);
	}
	
	public DeliveryProvider(Integer id, 
			Integer entityTypeInstanceId, 
			String name,
			Double rate,
			Boolean isReseller,
			ArrayList<String> feeCountries,
			ArrayList<String> feePostalCodes,
			ArrayList<BigDecimal> fees) {
		this(id, 
			entityTypeInstanceId, 
			name,
			rate,
			isReseller);	
		this.feeCountries = feeCountries;
		this.feePostalCodes = feePostalCodes;
		this.fees = fees;
	}
	
	public DeliveryProvider() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_DeliveryProvider_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_DeliveryProvider_create\"(:deliveryProviderName, "
				+ "											:rate, "				
				+ "											:isReseller, "
				+ "											:feeCountries, "
				+ "											:feePostalCodes, "
				+ "											:fees, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("deliveryProviderName", this.getName());
		recordParameters.addValue("rate", this.getRate());
		recordParameters.addValue("isReseller", this.getIsReseller());
		recordParameters.addValue("feeCountries", createArrayOfSQLType("varchar", this.getFeeCountries().toArray()));
		recordParameters.addValue("feePostalCodes", createArrayOfSQLType("varchar", this.getFeePostalCodes().toArray()));
		recordParameters.addValue("fees", createArrayOfSQLType("numeric", this.getFees().toArray()));
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_DeliveryProvider_modify\"(:deliveryProviderId,"
				+ "													:deliveryProviderName,"
				+ "													:rate, "				
				+ "													:isReseller, "
				+ "													:feeCountries, "
				+ "													:feePostalCodes, "
				+ "													:fees, "
				+ "													:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("deliveryProviderId", this.getId());
		modifyParameters.addValue("deliveryProviderName", this.getName());
		modifyParameters.addValue("rate", this.getRate());
		modifyParameters.addValue("isReseller", this.getIsReseller());
		modifyParameters.addValue("feeCountries", createArrayOfSQLType("varchar", this.getFeeCountries().toArray()));
		modifyParameters.addValue("feePostalCodes", createArrayOfSQLType("varchar", this.getFeePostalCodes().toArray()));
		modifyParameters.addValue("fees", createArrayOfSQLType("numeric", this.getFees().toArray()));
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_DeliveryProvider_disable\"(:deliveryProviderId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("deliveryProviderId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class DeliveryProviderMapper implements RowMapper<Object> {

		public DeliveryProvider mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				DeliveryProvider deliveryProvider = null;
				int id = rs.getInt("deliveryProviderId");
				if (!rs.wasNull()) {
					deliveryProvider = new DeliveryProvider(id,
														rs.getInt("entityTypeInstanceId"), 
														rs.getString("deliveryProviderName"),
														rs.getDouble("rate"),
														rs.getBoolean("isReseller"));
				}
				return deliveryProvider;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_DeliveryProvider_get\"(:deliveryProviderId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("deliveryProviderId", this.getId());
		super.get(new DeliveryProviderMapper());
	}
	
	public List<Object> getDeliveryFees() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_DeliveryProvider_getFees\"(:deliveryProviderId, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("deliveryProviderId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new DeliveryFeeMapper());
	}
	
	@Override
	protected void copyFrom(Object sourceObject) {
		
		DeliveryProvider sourceDeliveryProvider = (DeliveryProvider) sourceObject;
		setId(sourceDeliveryProvider.getId());
		setStringId(sourceDeliveryProvider.getStringId());
		setEntityTypeInstanceId(sourceDeliveryProvider.getEntityTypeInstanceId());
		setName(sourceDeliveryProvider.getName());	
		setQualifiedName(sourceDeliveryProvider.getQualifiedName());
		setRate(sourceDeliveryProvider.getRate());
		setIsReseller(sourceDeliveryProvider.getIsReseller());
		setFeeCountries(sourceDeliveryProvider.getFeeCountries());
		setFeePostalCodes(sourceDeliveryProvider.getFeePostalCodes());
		setFees(sourceDeliveryProvider.getFees());
	}
	
	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on the object
		return null;
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Boolean getIsReseller() {
		return isReseller;
	}

	public void setIsReseller(Boolean isReseller) {
		this.isReseller = isReseller;
	}

	public ArrayList<String> getFeeCountries() {
		return feeCountries;
	}

	public void setFeeCountries(ArrayList<String> feeCountries) {
		this.feeCountries = feeCountries;
	}

	public ArrayList<String> getFeePostalCodes() {
		return feePostalCodes;
	}

	public void setFeePostalCodes(ArrayList<String> feePostalCodes) {
		this.feePostalCodes = feePostalCodes;
	}

	public ArrayList<BigDecimal> getFees() {
		return fees;
	}

	public void setFees(ArrayList<BigDecimal> fees) {
		this.fees = fees;
	}
}
