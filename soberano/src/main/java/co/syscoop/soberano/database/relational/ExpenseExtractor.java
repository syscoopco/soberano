package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.ExpenseRowData;

public final class ExpenseExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> expenseTableData = new ArrayList<Object>();
		ExpenseRowData expenseRowData = null;
        while (rs.next()) {
        	expenseRowData = new ExpenseRowData(rs.getInt("expenseId"));
        	expenseRowData.setConceptName(rs.getString("conceptName"));
        	expenseRowData.setDescription(rs.getString("description"));
        	expenseRowData.setPayeeName(rs.getString("payeeName"));
        	expenseRowData.setReference(rs.getString("reference"));
        	expenseRowData.setExpenseDate(rs.getDate("expenseDate"));
        	expenseRowData.setRecordingDate(rs.getDate("recordingDate"));
        	expenseRowData.setAmount(rs.getBigDecimal("expenseAmount"));
        	expenseRowData.setCurrency(rs.getString("expenseCurrency"));
        	expenseTableData.add(expenseRowData);
        }
        return expenseTableData;
	}
}