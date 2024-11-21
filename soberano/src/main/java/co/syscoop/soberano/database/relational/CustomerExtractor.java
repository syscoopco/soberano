package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import co.syscoop.soberano.util.rowdata.CustomerRowData;

public final class CustomerExtractor implements ResultSetExtractor<List<Object>> {
	
	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Object> customerTableData = new ArrayList<Object>();
		CustomerRowData customerRowData = null;
        while (rs.next()) {
        	customerRowData = new CustomerRowData();
        	customerRowData.setDomainObjectName(rs.getString("domainObjectName"));
        	customerRowData.setDomainObjectId(rs.getInt("domainObjectId"));
        	customerTableData.add(customerRowData);
        }
        return customerTableData;
	}
}