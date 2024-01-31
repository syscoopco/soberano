package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.ShiftClosureRowData;

public class ShiftClosureExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> shiftClosureTableData = new ArrayList<Object>();
		ShiftClosureRowData shiftClosureRowData = null;
        while (rs.next()) {
        	shiftClosureRowData = new ShiftClosureRowData(rs.getInt("shiftClosureId"));
        	shiftClosureRowData.setShift(rs.getDate("shift"));
        	shiftClosureRowData.setClosureTime(rs.getTimestamp("closureTime"));
        	shiftClosureRowData.setRecordingDate(rs.getTimestamp("recordingDate"));
        	shiftClosureTableData.add(shiftClosureRowData);
        }
        return shiftClosureTableData;
	}
}