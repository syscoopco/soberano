package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.database.relational.QueryBigDecimalResultMapper;
import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ProcessRunningException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.SpringUtility;

public class Product extends InventoryItem { 
	
	private BigDecimal price = new BigDecimal(0.0);
	private BigDecimal referencePrice = new BigDecimal(0.0);
	private Boolean isEnabled = false;
	private Integer costCenter = 0;
	private Integer process = 0;
	private BigDecimal oneRunQuantity = new BigDecimal(0);
	private Integer position = 0;
	private Boolean isAnAddition = false;
	
	//product categories
	private ArrayList<ProductCategory> productCategories = new ArrayList<ProductCategory>();
	private Integer[] productCategoryIds = null;
	
	private void fillProductCategoryIds() {
		productCategoryIds = new Integer[productCategories.size()];
		for (int i = 0; i < productCategoryIds.length; i++) {
			productCategoryIds[i] = productCategories.get(i).getId();
		}
	}
	
	public Product(Integer id) {
		super(id);
	}
	
	public Product(Integer id, 
					Integer entityTypeInstanceId, 					
					String inventoryItemCode, 
					String name,
					BigDecimal price, 
					BigDecimal referencePrice,
					BigDecimal minimumInventoryLevel,
					Integer unit,
					Integer costCenter,
					Boolean isEnabled,
					Integer process,
					Integer position,
					Boolean isAnAddition) {
		super(id, entityTypeInstanceId, name);
		this.setStringId(inventoryItemCode);
		this.setQualifiedName(name + " : " + inventoryItemCode);
		this.setPrice(price);
		this.setReferencePrice(referencePrice);
		setMinimumInventoryLevel(minimumInventoryLevel);
		this.setUnit(unit);
		this.setCostCenter(costCenter);
		this.setIsEnabled(isEnabled);
		this.setProcess(process);
		this.setPosition(position);
		this.setIsAnAddition(isAnAddition);
	}
	
	public Product(Integer id, 
			Integer entityTypeInstanceId, 
			String inventoryItemCode,
			String name,			 
			BigDecimal price, 
			BigDecimal referencePrice,
			BigDecimal minimumInventoryLevel,
			Integer unit,
			Integer costCenter,
			Boolean isEnabled,
			Integer process,
			Integer position,
			Boolean isAnAddition,
			ArrayList<ProductCategory> productCategories) {
		this(id, 
			entityTypeInstanceId, 
			inventoryItemCode, 
			name,			
			price, 
			referencePrice,
			minimumInventoryLevel,
			unit,
			costCenter,
			isEnabled,
			process,
			position,
			isAnAddition);
		this.productCategories = productCategories;
		fillProductCategoryIds();
	}
	
	public Product() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Product_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Product_create\"(:productName, "
				+ "											:inventoryCode, "
				+ "											:minimumInventoryLevel, "
				+ "											:unit, "
				+ "											:costCenter, "
				+ "											:price, "
				+ "											:referencePrice, "
				+ "											:isEnabled, "
				+ "											:position, "
				+ "											:isAnAddition, "
				+ "											:productCategories, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("productName", this.getName());
		recordParameters.addValue("inventoryCode", this.getStringId());
		recordParameters.addValue("minimumInventoryLevel", this.getMinimumInventoryLevel());
		recordParameters.addValue("unit", this.getUnit());
		recordParameters.addValue("costCenter", this.getCostCenter());
		recordParameters.addValue("price", this.getPrice());
		recordParameters.addValue("referencePrice", this.getReferencePrice());
		recordParameters.addValue("isEnabled", this.isEnabled);
		recordParameters.addValue("position", this.position);
		recordParameters.addValue("isAnAddition", this.isAnAddition);
		recordParameters.addValue("productCategories", createArrayOfSQLType("integer", productCategoryIds));
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Product_modify\"(:productId, "
				+ "											:productName, "
				+ "											:inventoryCode, "
				+ "											:minimumInventoryLevel, "
				+ "											:unit, "
				+ "											:costCenter, "
				+ "											:price, "
				+ "											:referencePrice, "
				+ "											:isEnabled, "
				+ "											:position, "
				+ "											:isAnAddition, "
				+ "											:productCategories, "
				+ "											:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("productId", this.getId());
		modifyParameters.addValue("productName", this.getName());
		modifyParameters.addValue("inventoryCode", this.getStringId());
		modifyParameters.addValue("minimumInventoryLevel", this.getMinimumInventoryLevel());
		modifyParameters.addValue("unit", this.getUnit());
		modifyParameters.addValue("costCenter", this.getCostCenter());
		modifyParameters.addValue("price", this.getPrice());
		modifyParameters.addValue("referencePrice", this.getReferencePrice());
		modifyParameters.addValue("isEnabled", this.isEnabled);
		modifyParameters.addValue("position", this.position);
		modifyParameters.addValue("isAnAddition", this.isAnAddition);
		modifyParameters.addValue("productCategories", createArrayOfSQLType("integer", productCategoryIds));
		
		Integer qryResult = super.modify();
		if (qryResult == -2) {
			throw new ProcessRunningException();
		}
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Product_disable\"(:productId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("productId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}
	
	public BigDecimal setItemPrice(BigDecimal itemPrice) throws SQLException, Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Product_setPrice\"(:itemId, "
				+ "											:itemPrice, "
				+ "											:loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("itemId", this.getId());
		parametersMap.put("itemPrice", itemPrice);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (BigDecimal) super.query(qryStr, parametersMap, new QueryBigDecimalResultMapper()).get(0);
	}
	
	public BigDecimal setItemReferencePrice(BigDecimal itemReferencePrice) throws SQLException, Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Product_setReferencePrice\"(:itemId, "
				+ "											:itemReferencePrice, "
				+ "											:loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("itemId", this.getId());
		parametersMap.put("itemReferencePrice", itemReferencePrice);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (BigDecimal) super.query(qryStr, parametersMap, new QueryBigDecimalResultMapper()).get(0);
	}
	
	public Integer showInCatalog() throws SQLException, Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Product_showInCatalog\"(:itemId, "
				+ "											:loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("itemId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer hideInCatalog() throws SQLException, Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Product_hideInCatalog\"(:itemId, "
				+ "											:loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("itemId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {
		
		if (stringId) {
			getAllQuery = "SELECT * FROM soberano.\"fn_Product_getAllWithStringId\"(:loginname)";
		}
		return super.getAll(stringId);
	}
	
	public final class ProductMapperWithStringId implements RowMapper<Object> {

		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Product domainObject = new Product();
				Integer id = rs.getInt("domainObjectId");
				if (!rs.wasNull()) {
					domainObject.setId(id);
					domainObject.setStringId(rs.getString("domainObjectStringId"));
					domainObject.setName(rs.getString("domainObjectName"));
					domainObject.setUnit(rs.getInt("unit"));
					domainObject.setOneRunQuantity(rs.getBigDecimal("oneRunQuantity"));
				}
				return domainObject;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	public List<Object> getAllWithUnit() throws SQLException {
		
		getAllQueryNamedParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());		
		return query("SELECT * FROM soberano.\"fn_Product_getAllWithStringId\"(:loginname)", 
					getAllQueryNamedParameters, 
					new ProductMapperWithStringId());
	}
	
	public List<Object> getAllWithUnitForOrder() throws SQLException {
		
		getAllQueryNamedParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());		
		return query("SELECT * FROM soberano.\"fn_Product_getAllWithStringIdForOrder\"(:loginname)", 
					getAllQueryNamedParameters, 
					new ProductMapperWithStringId());
	}
	
	public List<Object> getAdditionsWithUnitsForOrder() throws SQLException {
		
		getAllQueryNamedParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());		
		return query("SELECT * FROM soberano.\"fn_Product_getAdditionsWithStringIdForOrder\"(:loginname)",
					getAllQueryNamedParameters, 
					new ProductMapperWithStringId());
	}
	
	public final class ProductExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Product product = null;
			Integer productCurrentlyBeingExtractedId = -1;
	        while (rs.next()) {
	        	if (productCurrentlyBeingExtractedId != rs.getInt("itemId")) {
	        		productCurrentlyBeingExtractedId = rs.getInt("itemId");
	        		product = new Product(rs.getInt("itemId"),
											rs.getInt("entityTypeInstanceId"),
											rs.getString("itemCode"),
											rs.getString("itemName"),
											rs.getBigDecimal("itemPrice"),
											rs.getBigDecimal("itemReferencePrice"),
											rs.getBigDecimal("inventoryLevel"),
											rs.getInt("itemUnit"),
											rs.getInt("costCenter"),
											rs.getBoolean("isEnabled"),
											rs.getInt("itemProcess"),
											rs.getInt("productPosition"),
											rs.getBoolean("productIsAddition"));
	        	}
	        	product.getProductCategories().add(new ProductCategory(rs.getInt("categoryId"), rs.getString("categoryName")));
	        }
	        product.fillProductCategoryIds();
	        return product;
		}
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Product_get\"(:productId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("productId", this.getId());
		super.get(new ProductExtractor());
	}
	
	@Override
	public Integer print() throws SoberanoException {
		return null;
	}
	
	@Override
	protected void copyFrom(Object object) {
		Product sourceProduct = (Product) object;
		setId(sourceProduct.getId());
		setEntityTypeInstanceId(sourceProduct.getEntityTypeInstanceId());
		setName(sourceProduct.getName());
		setStringId(sourceProduct.getStringId());
		setMinimumInventoryLevel(sourceProduct.getMinimumInventoryLevel());
		setUnit(sourceProduct.getUnit());
		setCostCenter(sourceProduct.getCostCenter());
		setPrice(sourceProduct.getPrice());
		setReferencePrice(sourceProduct.getReferencePrice());
		setIsEnabled(sourceProduct.getIsEnabled());
		setProcess(sourceProduct.getProcess());
		setPosition(sourceProduct.getPosition());
		setIsAnAddition(sourceProduct.getIsAnAddition());
		setProductCategories(sourceProduct.getProductCategories());
		fillProductCategoryIds();
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(BigDecimal referencePrice) {
		this.referencePrice = referencePrice;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public ArrayList<ProductCategory> getProductCategories() {
		return productCategories;
	}
	
	public Integer[] getProductCategoryIds() {
		return productCategoryIds;
	}

	public void setProductCategories(ArrayList<ProductCategory> productCategories) {
		this.productCategories = productCategories;
		fillProductCategoryIds();
	}

	public Integer getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(Integer costCenter) {
		this.costCenter = costCenter;
	}

	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
	}

	public BigDecimal getOneRunQuantity() {
		return oneRunQuantity;
	}

	public void setOneRunQuantity(BigDecimal oneRunQuantity) {
		this.oneRunQuantity = oneRunQuantity;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getIsAnAddition() {
		return isAnAddition;
	}

	public void setIsAnAddition(Boolean isAnAddition) {
		this.isAnAddition = isAnAddition;
	}
}
