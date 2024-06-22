package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.domain.untracked.PrintableData;

public class PrintableDataMapper implements RowMapper<Object> {

	public PrintableData mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			PrintableData printableData = new PrintableData();
			String textToPrint = rs.getString("ttp");
			if (!rs.wasNull()) {
				printableData.setTextToPrint(textToPrint);
				printableData.setPrinterProfile(rs.getInt("printerProfile"));
			}
			return printableData;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}