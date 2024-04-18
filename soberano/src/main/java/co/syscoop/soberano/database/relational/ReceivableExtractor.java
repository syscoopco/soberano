package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.rowdata.ReceivableRowData;

public class ReceivableExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> receivableTableData = new ArrayList<Object>();
		ReceivableRowData receivableRowData = null;
        while (rs.next()) {
        	receivableRowData = new ReceivableRowData(rs.getInt("receivableId"));
        	receivableRowData.setEntityTypeInstanceId(rs.getInt("entityTypeInstanceId"));
        	receivableRowData.setDaysDelayed(rs.getInt("daysDelayed"));
        	receivableRowData.setCustomer(rs.getString("customer"));
        	receivableRowData.setDebtor(rs.getString("debtor"));
        	receivableRowData.setOrder(rs.getInt("order"));
        	receivableRowData.setAmountDue(rs.getBigDecimal("amountDue"));
        	receivableRowData.setHistory(rs.getString("history"));
        	receivableRowData.setRecordingDate(rs.getTimestamp("recordingDate"));
        	receivableTableData.add(receivableRowData);
        }
        return receivableTableData;
	}
}