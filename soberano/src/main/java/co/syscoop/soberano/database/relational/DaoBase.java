package co.syscoop.soberano.database.relational;

import java.sql.*;
import java.util.*;

import org.apache.commons.dbcp2.BasicDataSource;

import org.postgresql.util.PSQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.*;

public class DaoBase {

	private BasicDataSource dataSource = null;
	
	public DaoBase() {}
	
	public class NonResultMapper implements RowMapper<Integer> {

		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
	        return 0;
	    }
	}
	
	public class CountMapper implements RowMapper<Integer> {

		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Integer count = rs.getInt("count");
		        return count;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	public class ReportMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				String report = new String(rs.getString("report"));
		        return report;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	public class LoginNameMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				
				String loginName =  rs.getString("LoginName");
				if (rs.wasNull()) loginName = "";
				return loginName;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	private PreparedStatementCallback<Object> preparedStatementCallback = new PreparedStatementCallback<Object>() {
																					@Override
																					public Object doInPreparedStatement(PreparedStatement arg0)
																								throws SQLException, DataAccessException {
																						return arg0.executeUpdate();}
																					};
	
	public DaoBase(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public <T> T queryForObject(String queryStr,
								Map<String,	Object> namedParameters,
								Class<T> requiredType) throws PSQLException {
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			return namedParameterJdbcTemplate.queryForObject(queryStr, namedParameters, requiredType);
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public <T> List<T> query(String queryStr,
							SqlParameterSource paramSource, 
							RowMapper<T> mapper) throws PSQLException {
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			return namedParameterJdbcTemplate.query(queryStr, paramSource, mapper);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public <T> List<T> query(String queryStr,
							Map<String,	Object> namedParameters,
							RowMapper<T> mapper) throws PSQLException {
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			return namedParameterJdbcTemplate.query(queryStr, namedParameters, mapper);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public <T> T query(String queryStr,
			Map<String,	Object> namedParameters,
			ResultSetExtractor<T> extractor) throws PSQLException {
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			return namedParameterJdbcTemplate.query(queryStr, namedParameters, extractor);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public Object execute(String queryStr,
						Map<String, Object> namedParameters) throws PSQLException {
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			return namedParameterJdbcTemplate.execute(queryStr, namedParameters, preparedStatementCallback);
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public KeyHolder insert(String insertionQueryStr,
							Map<String, Object> parameterMap,
							SqlParameter[] parametersDeclaration, 
							String[] generatedColumnNames) throws PSQLException {
		
		try {
			SqlUpdate sqlUpdate = new SqlUpdate(dataSource, insertionQueryStr);
			for (SqlParameter sqlParam : parametersDeclaration) {
				sqlUpdate.declareParameter(sqlParam);
			}
			sqlUpdate.setGeneratedKeysColumnNames(generatedColumnNames);
			sqlUpdate.setReturnGeneratedKeys(true);
			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			sqlUpdate.updateByNamedParam(parameterMap, keyHolder);
			return keyHolder;
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public void update(String updatingQueryStr,
			Map<String, Object> parameterMap,
			SqlParameter[] parametersDeclaration) throws PSQLException {
		try {
			SqlUpdate sqlUpdate = new SqlUpdate(dataSource, updatingQueryStr);
			for (SqlParameter sqlParam : parametersDeclaration) {
				sqlUpdate.declareParameter(sqlParam);
			}
			sqlUpdate.setReturnGeneratedKeys(false);
			sqlUpdate.updateByNamedParam(parameterMap);
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public Array createArray(String sqlStypeName, Object[] javaArray) throws SQLException {
		
		Connection cnn = dataSource.getConnection();
		Array sqlArray = cnn.createArrayOf(sqlStypeName, javaArray);
		cnn.close();
		return sqlArray;
	}
}
