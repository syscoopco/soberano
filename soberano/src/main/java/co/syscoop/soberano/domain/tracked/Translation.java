package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;

public class Translation extends BusinessActivityTrackedObject {
	
	private String language = "";
	private Integer position = 0;
	private String fromTerm = "";
	private String toTerm = "";
	
	public Translation() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_Translation_getAll\"" 
							+ "(:loginname)";
		getCountQuery = "SELECT soberano.\"fn_Translation_getCount\"(:loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	public Translation(Integer id) {
		this.setId(id);
	}
	
	public Translation(String language,
						Integer position,
						String fromTerm,
						String toTerm) {
		this.language = language;
		this.position = position;
		this.fromTerm = fromTerm;
		this.toTerm = toTerm;
	}

	@Override
	public void get() throws SQLException {
	}

	@Override
	public Integer print() throws SoberanoException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Translation_create\"(:language, "
				+ "											:position, "
				+ "											:fromterm, "
				+ "											:toterm, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("language", this.getLanguage());
		recordParameters.addValue("position", this.getPosition());
		recordParameters.addValue("fromterm", this.getFromTerm());
		recordParameters.addValue("toterm", this.getToTerm());
		
		return super.record();
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Translation_disable\"(:translationId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("translationId", this.getId());
		
		return super.disable();
	}
	
	@Override
	public PrintableData getReportFull() throws SQLException {
		
		return null;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getFromTerm() {
		return fromTerm;
	}

	public void setFromTerm(String fromTerm) {
		this.fromTerm = fromTerm;
	}

	public String getToTerm() {
		return toTerm;
	}

	public void setToTerm(String toTerm) {
		this.toTerm = toTerm;
	}	
}
