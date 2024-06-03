package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import co.syscoop.soberano.domain.tracked.Currency;

public final class CurrencyMapper implements RowMapper<Object> {

	public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			Currency currency = null;
			int id = rs.getInt("itemId");
			if (!rs.wasNull()) {
				currency = new Currency(id,
										rs.getInt("entityTypeInstanceId"),											
										rs.getString("itemCode"),
										rs.getString("itemName"),
										rs.getBoolean("isSystemCurrency"),
										rs.getBoolean("isPriceReferenceCurrency"),
										rs.getBoolean("isCash"),
										rs.getBigDecimal("exchangeRate"),
										rs.getInt("itemPosition"),
										rs.getInt("paymentProcessor"),
										rs.getString("paymentProcessorName"));
			}
			return currency;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}