package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;

public class ProductCategory extends TrackedObject {

	private Integer position = 0;
	private Boolean isEnabled = true;
	
	public ProductCategory(Integer id) {
		super(id);
	}
	
	public ProductCategory(Integer id, 
					Integer entityTypeInstanceId, 
					String name, 
					Integer position, 
					Boolean isEnabled) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name);
		this.setPosition(position);
		this.setIsEnabled(isEnabled);
	}
	
	public ProductCategory() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_ProductCategory_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	public ProductCategory(Integer id, String name) {
		super(id, name);
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_ProductCategory_create\"(:categoryName, "
				+ "											:categoryPosition, "
				+ "											:isEnabled, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("categoryName", this.getName());
		recordParameters.addValue("categoryPosition", this.position);
		recordParameters.addValue("isEnabled", this.isEnabled);
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_ProductCategory_modify\"(:categoryId, "
				+ "											:categoryName, "
				+ "											:categoryPosition, "
				+ "											:isEnabled, "
				+ "											:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("categoryId", this.getId());
		modifyParameters.addValue("categoryName", this.getName());
		modifyParameters.addValue("categoryPosition", this.position);
		modifyParameters.addValue("isEnabled", this.isEnabled);
		
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_ProductCategory_disable\"(:categoryId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("categoryId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class ProductCategoryMapper implements RowMapper<Object> {

		public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				ProductCategory category = null;
				int id = rs.getInt("categoryId");
				if (!rs.wasNull()) {
					category = new ProductCategory(id,
										rs.getInt("entityTypeInstanceId"),
										rs.getString("categoryName"),
										rs.getInt("categoryPosition"),
										rs.getBoolean("isEnabled"));
				}
				return category;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_ProductCategory_get\"(:categoryId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("categoryId", this.getId());
		super.get(new ProductCategoryMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		ProductCategory sourceCategory = (ProductCategory) sourceObject;
		setId(sourceCategory.getId());
		setEntityTypeInstanceId(sourceCategory.getEntityTypeInstanceId());
		setName(sourceCategory.getName());
		setPosition(sourceCategory.getPosition());
		setIsEnabled(sourceCategory.getIsEnabled());
	}
	
	@Override
	public Integer print() throws SoberanoException {
		return null;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
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
