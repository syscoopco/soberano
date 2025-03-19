package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import co.syscoop.soberano.domain.untracked.PrintableData;

public interface IReport {
	
	public PrintableData getReportWithPrinterProfile() throws SQLException;
}
