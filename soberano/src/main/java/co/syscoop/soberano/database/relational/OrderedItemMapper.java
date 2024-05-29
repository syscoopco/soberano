package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import co.syscoop.soberano.domain.untracked.OrderedItem;

public class OrderedItemMapper implements RowMapper<Object> {

	public OrderedItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			Integer orderId = rs.getInt("orderId");
			OrderedItem orderedItem = new OrderedItem();			
			if (!rs.wasNull()) {
				orderedItem.setOrderId(orderId);
				orderedItem.setProcessRunId(rs.getInt("processRunId"));
				orderedItem.setProductName(rs.getString("productName"));
				orderedItem.setOrderedQuantity(rs.getBigDecimal("orderedQuantity"));
				orderedItem.setInstructions(rs.getString("instructions"));
				orderedItem.setServedQuantity(rs.getBigDecimal("servedQuantity"));
				orderedItem.setUnit(rs.getString("unit"));
			}
			return orderedItem;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}