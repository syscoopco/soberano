package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class Configuration extends BusinessActivityTrackedObject {
	
	private BigDecimal surcharge = new BigDecimal(0);
	private Integer shiftOpeningHour = 0;
	private Integer shiftOpeningMinutes = 0;
	private Boolean firstOrderRequiresCashOperation = false;
	private Boolean spiOperationRequiresConfirmation = false;
	
	public Configuration() {}
	
	public Configuration(BigDecimal surcharge, Integer shiftOpeningHour, Integer shiftOpeningMinutes, 
						Boolean firstOrderRequiresCashOperation, Boolean spiOperationRequiresConfirmation) {
		setSurcharge(surcharge);
		setShiftOpeningHour(shiftOpeningHour);
		setShiftOpeningMinutes(shiftOpeningMinutes);
		setFirstOrderRequiresCashOperation(firstOrderRequiresCashOperation);
		setSpiOperationRequiresConfirmation(spiOperationRequiresConfirmation);
	}
	
	public final class ConfigurationMapper implements RowMapper<Object> {
		
		public Configuration mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Configuration configuration = null;
				BigDecimal surcharge = rs.getBigDecimal("surcharge");
				if (!rs.wasNull()) {
					configuration = new Configuration(surcharge,
													rs.getInt("shiftOpeningHour"),
													rs.getInt("shiftOpeningMinutes"),
													rs.getBoolean("firstOrderRequiresCashOperation"),
													rs.getBoolean("spiOperationRequiresConfirmation"));
				}
				return configuration;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Configuration_get\"(:loginname)";
		getParameters = new HashMap<String, Object>();
		super.get(new ConfigurationMapper());
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Configuration_modify\"(:surcharge, "
								+ " 								:shiftopeninghour, "
								+ " 								:shiftopeningminutes, "
								+ "									:firstorderrequirescashoperation, "
								+ "									:spiOperationRequiresConfirmation, "
								+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("surcharge", this.getSurcharge());
		modifyParameters.addValue("shiftopeninghour", this.getShiftOpeningHour());
		modifyParameters.addValue("shiftopeningminutes", this.getShiftOpeningMinutes());
		modifyParameters.addValue("firstorderrequirescashoperation", this.getFirstOrderRequiresCashOperation());
		modifyParameters.addValue("spiOperationRequiresConfirmation", this.getSpiOperationRequiresConfirmation());
		
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	protected void copyFrom(Object sourceObject) {
		
		Configuration sourceConf = (Configuration) sourceObject;
		setSurcharge(sourceConf.getSurcharge());
		setEntityTypeInstanceId(sourceConf.getEntityTypeInstanceId());
		setShiftOpeningHour(sourceConf.getShiftOpeningHour());
		setShiftOpeningMinutes(sourceConf.getShiftOpeningMinutes());
		setFirstOrderRequiresCashOperation(sourceConf.getFirstOrderRequiresCashOperation());
		setSpiOperationRequiresConfirmation(sourceConf.getSpiOperationRequiresConfirmation());
	}

	public BigDecimal getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(BigDecimal surcharge) {
		this.surcharge = surcharge;
	}

	public Integer getShiftOpeningHour() {
		return shiftOpeningHour;
	}

	public void setShiftOpeningHour(Integer shiftHour) {
		this.shiftOpeningHour = shiftHour;
	}

	public Integer getShiftOpeningMinutes() {
		return shiftOpeningMinutes;
	}

	public void setShiftOpeningMinutes(Integer shiftMinutes) {
		this.shiftOpeningMinutes = shiftMinutes;
	}

	public Boolean getFirstOrderRequiresCashOperation() {
		return firstOrderRequiresCashOperation;
	}

	public void setFirstOrderRequiresCashOperation(Boolean firstOrderRequiresCashOperation) {
		this.firstOrderRequiresCashOperation = firstOrderRequiresCashOperation;
	}

	public Boolean getSpiOperationRequiresConfirmation() {
		return spiOperationRequiresConfirmation;
	}

	public void setSpiOperationRequiresConfirmation(Boolean spiOperationRequiresConfirmation) {
		this.spiOperationRequiresConfirmation = spiOperationRequiresConfirmation;
	}
}
