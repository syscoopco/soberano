package co.syscoop.soberano.database.relational;

public class QueryResultWithReport {
	private Integer result = 0;
	private String report = "";
	
	public QueryResultWithReport() {}
	
	public QueryResultWithReport(Integer result, String report) {
		setResult(result);
		setReport(report);
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
}
