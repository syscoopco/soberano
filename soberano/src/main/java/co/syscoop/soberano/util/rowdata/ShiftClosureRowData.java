package co.syscoop.soberano.util.rowdata;

import java.util.Date;

public class ShiftClosureRowData {
	private Integer shiftClosureId = 0;
	private Date shift = null;
	private Date closureTime = null;
	private Date recordingDate = null;
	private Integer stageId = 0;
	
	public ShiftClosureRowData(Integer shiftClosureId) {
		this.shiftClosureId = shiftClosureId;
	}
	
	public Date getShift() {
		return shift;
	}

	public void setShift(Date shift) {
		this.shift = shift;
	}

	public Date getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(Date recordingDate) {
		this.recordingDate = recordingDate;
	}

	public Integer getShiftClosureId() {
		return shiftClosureId;
	}

	public void setShiftId(Integer shiftClosureId) {
		this.shiftClosureId = shiftClosureId;
	}

	public Date getClosureTime() {
		return closureTime;
	}

	public void setClosureTime(Date closureTime) {
		this.closureTime = closureTime;
	}

	public Integer getStageId() {
		return stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}
}
