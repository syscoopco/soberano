package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

public class BusinessActivityTrackedObject extends TrackedObject {
	
	private Date occurrenceTime = null;
	private String reference = null;
	private String notes = "";
	
	public BusinessActivityTrackedObject() {};
	
	public BusinessActivityTrackedObject(Integer id) {
		super(id);
	}
		
	public Date getOccurrenceTime() {
		return occurrenceTime;
	}
	
	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public void get() throws SQLException {
	}

	@Override
	public Integer print() throws SQLException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return super.getAll(orderByColumn, descOrder, limit, offset, extractor);
	}

	@Override
	public Integer getCount() throws SQLException {
		return super.getCount();
	}

	@Override
	public String getReport() throws SQLException {
		return super.getReport();
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
