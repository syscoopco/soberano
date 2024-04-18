package co.syscoop.soberano.util.rowdata;

import java.util.Date;

public class ProcessRunRowData {
	private Integer processRunId = 0;
	private Integer entityTypeInstanceId = 0;
	private String process = "";
	private String costCenter = "";
	private String Stage = "";
	private String description = "";
	private String history = "";
	private Date recordingDate = null;
	
	public ProcessRunRowData(Integer processRunId) {
		this.setProcessRunId(processRunId);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(Date recordingDate) {
		this.recordingDate = recordingDate;
	}

	public Integer getProcessRunId() {
		return processRunId;
	}

	public void setProcessRunId(Integer processRunId) {
		this.processRunId = processRunId;
	}

	public Integer getEntityTypeInstanceId() {
		return entityTypeInstanceId;
	}

	public void setEntityTypeInstanceId(Integer entityTypeInstanceId) {
		this.entityTypeInstanceId = entityTypeInstanceId;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getStage() {
		return Stage;
	}

	public void setStage(String stage) {
		Stage = stage;
	}
}
