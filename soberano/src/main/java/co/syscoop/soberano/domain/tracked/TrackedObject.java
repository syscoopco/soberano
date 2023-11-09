package co.syscoop.soberano.domain.tracked;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.database.relational.DaoBase;
import co.syscoop.soberano.database.relational.QueryResultMapper;
import co.syscoop.soberano.beans.SoberanoDatasource;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.helper.DomainObjectMapper;
import co.syscoop.soberano.domain.untracked.helper.DomainObjectMapperWithStringId;
import co.syscoop.soberano.util.SpringUtility;

/**
 * @author Josue Portal
 *
 * A tracked object is a business domain object on which access and change of state are restricted, according
 * to organizational structure (authorities, responsibilities, assignments) and business dynamics (life cycles, stages,
 * decisions). In the conceptual model of a Soberano instance, they can be identified by the fact type 
 * ... is identified by EntityTypeInstance_id, which connects that model with Soberano metamodel.
 */
public abstract class TrackedObject extends DomainObject implements ITrackedObject {
	
	protected Integer entityTypeInstanceId = 0;
	
	//it is expected queries and parameters to be set by subclasses
	protected String recordQuery = "";
	protected MapSqlParameterSource recordParameters = null;
	protected String modifyQuery = "";
	protected MapSqlParameterSource modifyParameters = null;
	protected String disableQuery = "";
	protected MapSqlParameterSource disableParameters = null;
	protected String getQuery = "";
	protected Map<String,	Object> getParameters = new HashMap<String, Object>();
	protected String getReportQuery = "";
	protected Map<String,	Object> getReportParameters = new HashMap<String, Object>();
	
	//it is expected queries and parameters to be set by subclasses
	protected String getCountQuery = "";
	
	private Integer printer = 0;
	
	public TrackedObject() {};
	
	public TrackedObject(Integer id) {
		super(id);
	}
	
	public TrackedObject(String name) {
		super(name);
	}
	
	public TrackedObject(Integer id, String name) {
		super(id, name);
	}
	
	public TrackedObject(String id, String name) {
		super(id, name);
	}

	public TrackedObject(Integer id, Integer entityTypeInstanceId) {
		super(id);
		this.setEntityTypeInstanceId(entityTypeInstanceId); 
	}
	
	public TrackedObject(Integer id, Integer entityTypeInstanceId, String name) {
		super(id, name);
		this.setEntityTypeInstanceId(entityTypeInstanceId);
	}
	
	protected abstract void copyFrom(Object object);

	public Integer getEntityTypeInstanceId() {
		return entityTypeInstanceId;
	}

	public void setEntityTypeInstanceId(Integer entityTypeInstanceId) {
		this.entityTypeInstanceId = entityTypeInstanceId;
	}
	
	class TrackedObjectDao extends DaoBase {

		public TrackedObjectDao(BasicDataSource dataSource) {
			super(dataSource);
		}
		
		private MapSqlParameterSource addLoginname(String query, MapSqlParameterSource parameters) {
			if (query.indexOf("loginname") > -1) {
				parameters.addValue("loginname", SpringUtility.loggedUser().toLowerCase());
			}
			return parameters;
		}
		
		public Integer record(String recordQuery, MapSqlParameterSource recordParameters) throws SQLException {			
			return (query(recordQuery, this.addLoginname(recordQuery, recordParameters) , new QueryResultMapper()).get(0));
		}
		
		public Integer modify(String modifyQuery, MapSqlParameterSource modifyParameters) throws SQLException {			
			return (query(modifyQuery, this.addLoginname(modifyQuery, modifyParameters) , new QueryResultMapper()).get(0));
		}
		
		public Integer disable(String disableQuery, MapSqlParameterSource disableParameters) throws SQLException {			
			return (query(disableQuery, this.addLoginname(disableQuery, disableParameters) , new QueryResultMapper()).get(0));
		}
		
		public Map<String, Object> addLoginname(String query, Map<String, Object> parameters) {
			if (query.indexOf("loginname") > -1) {
				parameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
			}
			return parameters;
		}
		
		public List<DomainObject> getAll(String getAllQuery, Map<String, Object> getAllQueryNamedParameters, Boolean stringId) throws SQLException {			
			return query(getAllQuery, this.addLoginname(getAllQuery, getAllQueryNamedParameters), stringId ? new DomainObjectMapperWithStringId() : new DomainObjectMapper());
		}
		
		public Object get(Map<String, Object> getParameters, ResultSetExtractor<Object> extractor) throws SQLException {			
			return query(getQuery, this.addLoginname(getQuery, getParameters), extractor);
		}
		
		public Object get(Map<String, Object> getParameters, RowMapper<Object> mapper) throws SQLException {			
			return query(getQuery, this.addLoginname(getQuery, getParameters), mapper);
		}
		
		public String getReport() throws SQLException {			
			
			List<String> reports = query(getReportQuery, this.addLoginname(getReportQuery, getReportParameters), new ReportMapper());
			return reports.get(0);
		};
		
		public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
			
			String qryStr = getAllQuery +
							" ORDER BY \"" + orderByColumn + "\" ";
			if (descOrder)
				qryStr += "DESC ";
			else 
				qryStr += "ASC ";

			qryStr += "LIMIT " + Integer.toString(limit);
			qryStr += " OFFSET " + Integer.toString(offset);			
			
			return query(qryStr, this.addLoginname(qryStr, getAllQueryNamedParameters), extractor);
		}
		
		public Integer getCount() throws SQLException {			
			
			List<Integer> counts = query(getCountQuery, this.addLoginname(getCountQuery, getAllQueryNamedParameters), new CountMapper());
			return counts.get(0);
		};
	}
	
	protected TrackedObjectDao trackedObjectDao = new TrackedObjectDao(((SoberanoDatasource) SpringUtility.applicationContext().getBean("soberanoDatasource")).getDataSource());

	protected Array createArrayOfSQLType(String sqlStypeName, Object[] javaArray) throws SQLException {		
		return trackedObjectDao.createArray(sqlStypeName, javaArray);
	}
	
	public List<Object> query(String queryStr, Map<String, Object> queryParameters, RowMapper<Object> mapper) throws SQLException {
		return trackedObjectDao.query(queryStr, queryParameters, mapper);
	}
	
	@Override
	public Integer record() throws SQLException, Exception {		
		Integer newTrackerObjectId = trackedObjectDao.record(recordQuery, recordParameters);
		this.setId(newTrackerObjectId);
		return newTrackerObjectId;
	}

	@Override
	public void get(ResultSetExtractor<Object> extractor) throws SQLException {
		copyFrom(trackedObjectDao.get(getParameters, extractor));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void get(RowMapper<Object> mapper) throws SQLException {
		copyFrom(((ArrayList<Object>) trackedObjectDao.get(getParameters, mapper)).get(0));
	}

	@Override
	public Integer makeDecision(String decision) throws SQLException {
		return 0;
	}

	@Override
	public Integer modify() throws SQLException, Exception {
		Integer result = trackedObjectDao.modify(modifyQuery, modifyParameters);
		return result;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
		Integer result = trackedObjectDao.disable(disableQuery, disableParameters);
		return result;
	}

	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {
		return trackedObjectDao.getAll(getAllQuery, getAllQueryNamedParameters, stringId);
	}
	
	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return trackedObjectDao.getAll(orderByColumn, descOrder, limit, offset, extractor);
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return trackedObjectDao.getCount();
	}
	
	@Override
	public String getReport() throws SQLException {
		return trackedObjectDao.getReport();
	}

	public Integer getPrinter() {
		return printer;
	}

	public void setPrinter(Integer printer) {
		this.printer = printer;
	}
}
