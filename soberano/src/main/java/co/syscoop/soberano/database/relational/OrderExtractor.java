package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.rowdata.OrderRowData;

public class OrderExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> orderTableData = new ArrayList<Object>();
		OrderRowData orderRowData = null;
        while (rs.next()) {
        	orderRowData = new OrderRowData(rs.getInt("operationId"));
        	orderRowData.setEntityTypeInstanceId(rs.getInt("entityTypeInstanceId"));
        	orderRowData.setLabel(rs.getString("orderLabel"));
        	orderRowData.setCustomer(rs.getString("customer"));
        	orderRowData.setCounter(rs.getString("counter"));
        	orderRowData.setStage(rs.getString("stage"));
        	orderRowData.setDescription(rs.getString("description"));
        	orderRowData.setHistory(rs.getString("history"));
        	orderRowData.setRecordingDate(rs.getTimestamp("recordingDate"));
        	orderTableData.add(orderRowData);
        }
        return orderTableData;
	}
}