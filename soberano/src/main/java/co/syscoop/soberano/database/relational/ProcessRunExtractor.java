package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.ProcessRunRowData;

public class ProcessRunExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> processRunTableData = new ArrayList<Object>();
		ProcessRunRowData processRunRowData = null;
        while (rs.next()) {
        	processRunRowData = new ProcessRunRowData(rs.getInt("operationId"));
        	processRunRowData.setEntityTypeInstanceId(rs.getInt("entityTypeInstanceId"));
        	processRunRowData.setProcess(rs.getString("process"));
        	processRunRowData.setCostCenter(rs.getString("costCenter"));
        	processRunRowData.setStage(rs.getString("stage"));
        	processRunRowData.setDescription(rs.getString("description"));
        	processRunRowData.setHistory(rs.getString("history"));
        	processRunRowData.setRecordingDate(rs.getTimestamp("recordingDate"));
        	processRunTableData.add(processRunRowData);
        }
        return processRunTableData;
	}
}