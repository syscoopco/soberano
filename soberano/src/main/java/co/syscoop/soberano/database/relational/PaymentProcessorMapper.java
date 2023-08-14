package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.domain.untracked.PaymentProcessor;

public class PaymentProcessorMapper implements RowMapper<Object> {

	public PaymentProcessor mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			PaymentProcessor paymentProcessor = null;
			int id = rs.getInt("itemId");
			if (!rs.wasNull()) {
				paymentProcessor = new PaymentProcessor(id, rs.getString("itemName"));
			}
			return paymentProcessor;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}