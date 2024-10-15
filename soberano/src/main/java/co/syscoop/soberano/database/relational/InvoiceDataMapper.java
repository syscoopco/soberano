package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.util.rowdata.InvoiceDataRowData;

public class InvoiceDataMapper implements RowMapper<Object> {

	public InvoiceDataRowData mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			Integer orderId = rs.getInt("oid");
			InvoiceDataRowData invoiceDataRowData = new InvoiceDataRowData();			
			if (!rs.wasNull()) {
				invoiceDataRowData.setOrderId(orderId);
				invoiceDataRowData.setInventoryItemCode(rs.getString("icode"));
				invoiceDataRowData.setInventoryItemName(rs.getString("iname"));
				invoiceDataRowData.setUnit(rs.getString("unit"));
				invoiceDataRowData.setItemPrice(rs.getBigDecimal("price"));
				invoiceDataRowData.setQuantity(rs.getBigDecimal("iqty"));
				invoiceDataRowData.setItemAmount(rs.getBigDecimal("iamount"));
				invoiceDataRowData.setOrderDiscount(rs.getBigDecimal("orderdiscount"));
				invoiceDataRowData.setCustomerName(rs.getString("customername"));
				invoiceDataRowData.setContactData(rs.getString("contactdata"));
				invoiceDataRowData.setInvoiceDate(rs.getString("invoicedate"));
				invoiceDataRowData.setStageId(rs.getInt("stage"));
			}
			return invoiceDataRowData;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}