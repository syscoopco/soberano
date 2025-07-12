package co.syscoop.soberano.beans;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.database.relational.DaoBase;

public class ExportBean {

	private HashMap<String, Object> parameters;
	
	private DaoBase dao = null;
	
	public ExportBean(SoberanoDatasource soberanoDatasource) {
		dao = new DaoBase(soberanoDatasource.getDataSource());
	}
	
	protected List<Object> query(String queryStr, Map<String, Object> queryParameters, RowMapper<Object> mapper) throws SQLException {
		return dao.query(queryStr, queryParameters, mapper);
	}
	
	protected Object query(String queryStr, Map<String, Object> queryParameters, ResultSetExtractor<Object> extractor) throws SQLException {
		return dao.query(queryStr, queryParameters, extractor);
	}

	public HashMap<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, Object> parameters) {
		this.parameters = parameters;
	}
}
