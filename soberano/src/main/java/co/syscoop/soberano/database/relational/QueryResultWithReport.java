package co.syscoop.soberano.database.relational;

public class QueryResultWithReport {
	private Integer result = 0;
	private String report = "";
	private Integer printerProfileId = 0;
	
	public QueryResultWithReport() {}
	
	public QueryResultWithReport(Integer result, String report, Integer printerProfileId) {
		setResult(result);
		setReport(report);
		setPrinterProfileId(printerProfileId);
	}
	
	public Integer getResult() {
		return result;
	}
	
	public void setResult(Integer result) {
		this.result = result;
	}
	
	public String getReport() {
		return report;
	}
	
	public void setReport(String report) {
		this.report = report;
	}

	public Integer getPrinterProfileId() {
		return printerProfileId;
	}

	public void setPrinterProfileId(Integer printerProfileId) {
		this.printerProfileId = printerProfileId;
	}
}
