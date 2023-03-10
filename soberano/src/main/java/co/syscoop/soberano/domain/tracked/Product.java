package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.domain.untracked.DomainObject;

public class Product extends InventoryItem { 
	
	private BigDecimal price = new BigDecimal(0.0);
	private BigDecimal referencePrice = new BigDecimal(0.0);
	private Boolean isEnabled = false;
	
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
					String name,
					String inventoryItemCode, 
					BigDecimal price, 
					BigDecimal referencePrice,
					BigDecimal minimumInventoryLevel,
					Integer unit,
					Boolean isEnabled) {
		super(id, entityTypeInstanceId, name);
		this.setStringId(inventoryItemCode);
		this.setQualifiedName(name + " : " + inventoryItemCode);
		this.setPrice(price);
		this.setReferencePrice(referencePrice);
		setMinimumInventoryLevel(minimumInventoryLevel);
		this.setUnit(unit);
		this.setIsEnabled(isEnabled);
	}
	
	public Product(Integer id, 
			Integer entityTypeInstanceId, 
			String name,
			String inventoryItemCode, 
			BigDecimal price, 
			BigDecimal referencePrice,
			BigDecimal minimumInventoryLevel,
			Integer unit,
			Boolean isEnabled,
			ArrayList<ProductCategory> productCategories) {
		this(id, 
			entityTypeInstanceId, 
			name,
			inventoryItemCode, 
			price, 
			referencePrice,
			minimumInventoryLevel,
			unit,
			isEnabled);
		this.productCategories = productCategories;
		fillProductCategoryIds();
	}
	
	public Product() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Product_getAll\"" + "(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Product_create\"(:productName, "
				+ "											:inventoryCode, "
				+ "											:minimumInventoryLevel, "
				+ "											:unit, "
				+ "											:price, "
				+ "											:referencePrice, "
				+ "											:isEnabled, "
				+ "											:productCategories, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("productName", this.getName());
		recordParameters.addValue("inventoryCode", this.getStringId());
		recordParameters.addValue("minimumInventoryLevel", this.getMinimumInventoryLevel());
		recordParameters.addValue("unit", this.getUnit());
		recordParameters.addValue("price", this.getPrice());
		recordParameters.addValue("referencePrice", this.getReferencePrice());
		recordParameters.addValue("isEnabled", this.isEnabled);
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
				+ "											:price, "
				+ "											:referencePrice, "
				+ "											:isEnabled, "
				+ "											:productCategories, "
				+ "											:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("productId", this.getId());
		modifyParameters.addValue("productName", this.getName());
		modifyParameters.addValue("inventoryCode", this.getStringId());
		modifyParameters.addValue("minimumInventoryLevel", this.getMinimumInventoryLevel());
		modifyParameters.addValue("unit", this.getUnit());
		modifyParameters.addValue("price", this.getPrice());
		modifyParameters.addValue("referencePrice", this.getReferencePrice());
		modifyParameters.addValue("isEnabled", this.isEnabled);
		modifyParameters.addValue("productCategories", createArrayOfSQLType("integer", productCategoryIds));
		
		Integer qryResult = super.modify();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Product_disable\"(:productId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("productId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
	
	public final class ProductExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Product product = null;
			Integer productCurrentlyBeingExtractedId = -1;
	        while (rs.next()) {
	        	if (productCurrentlyBeingExtractedId != rs.getInt("productId")) {
	        		productCurrentlyBeingExtractedId = rs.getInt("productId");
	        		product = new Product(rs.getInt("productId"),
											rs.getInt("entityTypeInstanceId"),
											rs.getString("productName"),
											rs.getString("inventoryItemCode"),
											rs.getBigDecimal("productPrice"),
											rs.getBigDecimal("productReferencePrice"),
											rs.getBigDecimal("minimumInventoryLevel"),
											rs.getInt("productUnit"),
											rs.getBoolean("isEnabled"));
	        	}
	        	product.getProductCategories().add(new ProductCategory(rs.getInt("categoryId"), rs.getString("categoryName")));
	        }
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
	public Integer print() throws SQLException {
		// TODO Auto-generated method stub
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
		setPrice(sourceProduct.getPrice());
		setReferencePrice(sourceProduct.getReferencePrice());
		setIsEnabled(sourceProduct.getIsEnabled());
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

	public void setProductCategories(ArrayList<ProductCategory> productCategories) {
		this.productCategories = productCategories;
		fillProductCategoryIds();
	}
}
