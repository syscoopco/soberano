package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.domain.untracked.DeliveryFee;

public class DeliveryFeeMapper implements RowMapper<Object> {

	public DeliveryFee mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			DeliveryFee deliveryFee = new DeliveryFee();
			String country = rs.getString("country");
			if (!rs.wasNull()) {
				deliveryFee.setCountry(country);
				deliveryFee.setPostalCode(rs.getString("postalCode"));
				deliveryFee.setFee(rs.getBigDecimal("fee"));
			}
			return deliveryFee;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}