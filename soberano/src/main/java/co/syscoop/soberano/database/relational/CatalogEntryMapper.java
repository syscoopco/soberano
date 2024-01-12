package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import co.syscoop.soberano.util.CatalogEntryRowData;

public class CatalogEntryMapper implements RowMapper<Object> {

	public CatalogEntryRowData mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			Integer itemId = rs.getInt("itemId");
			CatalogEntryRowData catalogEntryRow = new CatalogEntryRowData();			
			if (!rs.wasNull()) {
				catalogEntryRow.setItemId(itemId);
				catalogEntryRow.setItemName(rs.getString("itemName"));
				catalogEntryRow.setCategoryName(rs.getString("categoryName"));
				catalogEntryRow.setEntityTypeInstanceId(rs.getInt("entityTypeInstanceId"));
				catalogEntryRow.setItemEnabled(rs.getBoolean("itemEnabled"));
				catalogEntryRow.setItemPrice(rs.getBigDecimal("itemPrice"));
				catalogEntryRow.setItemReferencePrice(rs.getBigDecimal("itemReferencePrice"));
				catalogEntryRow.setSysCurrency(rs.getString("sysCurrency"));
				catalogEntryRow.setRefCurrency(rs.getString("refCurrency"));
				catalogEntryRow.setExchRate(rs.getBigDecimal("exchRate"));
			}
			return catalogEntryRow;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}