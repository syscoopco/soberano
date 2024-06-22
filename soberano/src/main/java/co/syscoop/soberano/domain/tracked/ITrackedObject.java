package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;

public interface ITrackedObject {

	abstract public Integer record() throws SQLException, Exception;
	abstract public void get() throws SQLException;
	abstract public void get(ResultSetExtractor<Object> extractor) throws SQLException;
	abstract public void get(RowMapper<Object> mapper) throws SQLException;
	abstract public Integer modify() throws SQLException, Exception;	
	abstract public Integer makeDecision(String decision) throws SQLException; //it's passed the name of the decision
	abstract public Integer disable() throws SQLException, Exception;
	abstract public Integer print() throws SoberanoException;
	abstract public String getReport() throws SQLException;
	abstract public PrintableData getReportFull() throws SQLException;
	abstract public PrintableData getReportMinimal() throws SQLException;
	abstract public List<Object> query(String queryStr, Map<String, Object> queryParameters, RowMapper<Object> mapper) throws SQLException;
	
	//limit and offset are useful for windowed lists, as in paged lists / grids / scrollable trees / etc.
	//the semantics is the typical, for sql standard.
	//passing negatives values discard them.
	abstract public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException;
	abstract public List<DomainObject> getAll(Boolean stringId) throws SQLException;
	abstract public Integer getCount() throws SQLException;
}
