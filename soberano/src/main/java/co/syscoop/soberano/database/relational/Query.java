package co.syscoop.soberano.database.relational;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.dbcp2.BasicDataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.sun.rowset.*;

public class Query {

	private String loggedUser = "";
	private BasicDataSource dataSource = null;
	private boolean runsUnderLoggedUser = true;
	private ArrayList<SQLParameter> paramValues = null;
	private String queryString = "";
	private boolean ignoreResultingRows = false;
	
	private void initQueryAttributes() {	
		paramValues = new ArrayList<SQLParameter>();
	}
	
	public Query() throws SQLException {		
		initQueryAttributes();
	}
	
	public Query(String queryString,
				boolean runsUnderLoggedUser,
				String loggedUser,
				BasicDataSource dataSource,
				boolean ignoreResultingRows) {
	
		initQueryAttributes();
		this.queryString = queryString;
		this.runsUnderLoggedUser = runsUnderLoggedUser;
		this.loggedUser = loggedUser;
		this.dataSource = dataSource;
		this.ignoreResultingRows = ignoreResultingRows;
	}

	public boolean runsUnderLoggedUser() {
		return runsUnderLoggedUser;
	}

	public void runsUnderLoggedUser(boolean runsUnderLoggedUser) {
		this.runsUnderLoggedUser = runsUnderLoggedUser;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public ArrayList<SQLParameter> getParamValues() {
		return paramValues;
	}

	public void addParamValue(SQLParameter paramValue) {
		this.paramValues.add(paramValue);
	}
	
	@SuppressWarnings({ "rawtypes", "restriction"})
	public CachedRowSet executeQuery(boolean transactional) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement sqlStatement = null;
		ResultSet resultingRows = null;
		CachedRowSet rows = null;
		try {
				dbConnection = DataSourceUtils.getConnection(dataSource);
				sqlStatement = dbConnection.prepareCall(queryString);
				
				int paramIndex = 1;
			
				if (runsUnderLoggedUser)
				{
					sqlStatement.setString(paramIndex, loggedUser);
					paramIndex = paramIndex + 1;
				}
				
				for( int i = 0; i < paramValues.size(); i++ ) {
					SQLParameterType parameterType= paramValues.get(i).getParameterType();
					switch(parameterType) {
						case Boolean:
							sqlStatement.setBoolean(paramIndex, (Boolean) paramValues.get(i).getParameterValue());
							paramIndex++;
							break;
						case Integer:
							sqlStatement.setInt(paramIndex, (Integer) paramValues.get(i).getParameterValue());
							paramIndex++;
							break;
						case Double:
						case Money:
							sqlStatement.setBigDecimal(paramIndex, new BigDecimal((Double)paramValues.get(i).getParameterValue()));
							paramIndex++;
							break;
						case String:
						case VarLengthString:
							sqlStatement.setString(paramIndex, (String) paramValues.get(i).getParameterValue());
							paramIndex++;
							break;
						case UUID:
							sqlStatement.setObject(paramIndex, (String) paramValues.get(i).getParameterValue(), Types.OTHER);
							paramIndex++;
							break;
						case Date:
							sqlStatement.setDate(paramIndex, new Date(((java.util.Date)paramValues.get(i).getParameterValue()).getTime()));
							paramIndex++;
							break;
						case SQLArrayType:
							if (paramValues.get(i).getParameterValue() != null && 
									((paramValues.get(i).getParameterValue() instanceof ArrayList && !((ArrayList) paramValues.get(i).getParameterValue()).isEmpty()) ||
									(paramValues.get(i).getParameterValue() instanceof Object[] && ((Object[]) paramValues.get(i).getParameterValue()).length > 0))) {
									sqlStatement.setString(paramIndex, DatabaseUtil.arrayToString(paramValues.get(i).getParameterValue()));
							} else {
								sqlStatement.setNull(paramIndex, Types.VARCHAR);
							}
							paramIndex++;
							break;
					}
				}
				
				if (ignoreResultingRows)
				{
					sqlStatement.execute();
					return null;
				}
				else
				{
					resultingRows = sqlStatement.executeQuery();
					rows = new CachedRowSetImpl();
					rows.populate(resultingRows);
					return rows;
				}
		}
		catch(SQLException e) {
			throw e;
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			DatabaseUtil.close(sqlStatement);
			if (!transactional) DatabaseUtil.close(dbConnection);
		}
	}
}
