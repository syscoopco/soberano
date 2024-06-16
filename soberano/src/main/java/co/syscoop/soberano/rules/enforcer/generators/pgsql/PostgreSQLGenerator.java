/**
 * 
 */
package co.syscoop.soberano.rules.enforcer.generators.pgsql;

import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import co.syscoop.soberano.exception.*;
import co.syscoop.soberano.rules.enforcer.generator.ColumnValueConstraint;
import co.syscoop.soberano.rules.enforcer.generator.ColumnValueRange;
import co.syscoop.soberano.rules.enforcer.generator.IRelationalModelGenerator;
import co.syscoop.soberano.rules.enforcer.generator.RelationalGenerator;

/**
 * @author Josue Portal
 *
 * PostgreSQLGenerator translates the action requests coming from a general relational generator to queries specifics to
 * the PostgreSQL DBMS.
 */
public class PostgreSQLGenerator extends RelationalGenerator implements IRelationalModelGenerator {

	PostgreSQLGenerator(BasicDataSource dataSource, 
						PlatformTransactionManager transactionManager, 
						TransactionDefinition transactionDefinition,
						String MetamodelPath) {
		super(dataSource, transactionManager, transactionDefinition, MetamodelPath);
	}
	
	@Override
	public String getDatabaseCreationQuery(String databaseName) {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		String query = "CREATE DATABASE \"" + databaseName + "\";";
		return query;
	}

	@Override
	public String getDatabaseDropQuery(String databaseName) {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		return "DROP DATABASE \"" + databaseName + "\"";
	}

	@Override
	public String getDatabaseExistenceTestQuery(String databaseName) {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		return "SELECT datname FROM \"pg_database\" WHERE datname ='" + databaseName + "'";
	}

	@Override
	public String getDatabaseURL(String DBMSUrl, String databaseName) {
		return DBMSUrl + databaseName;
	}

	@Override
	public String getRelationCreationQuery(String relationName, 
										ArrayList<String> columnNames,
										ArrayList<String> columnTypes,
										ArrayList<String> columnLengths, 
										ArrayList<String> columnScales,
										ArrayList<Boolean> columnAutomaticSerialFlags, 
										ArrayList<ColumnValueConstraint> columnValueConstraints,
										ArrayList<String> constraintQueries) throws UnsupportedORMDatatypeException {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		
		String query = "CREATE TABLE \"" + schemaQualifiedRelationName(relationName) + "\" () WITH (OIDS=FALSE);";
		
		query += getColumnsCreationQuery(schemaQualifiedRelationName(relationName),
										columnNames,
										columnTypes,
										columnLengths,
										columnScales,
										columnAutomaticSerialFlags,
										columnValueConstraints,
										constraintQueries);
		return query;
	}
	
	private String getColumnCreationQuery(String relationName, 
										String columnName, 
										String dataTypeClassName, 
										String columnLength,
										String columnScale,
										boolean automaticSerialOn) throws UnsupportedORMDatatypeException {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		
		String query = null;
		
		switch(dataTypeClassName) {
		case "co.syscoop.soberano.rules.enforcer.metamodel.LargeLengthTextDataType":
			columnLength = columnLength.equals("0")?"1":columnLength;
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" text;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.VariableLengthTextDataType":
			columnLength = columnLength.equals("0")?"1":columnLength;
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" character varying(" + columnLength + ");";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.DateTemporalDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" date;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.FixedLengthTextDataType":
			columnLength = columnLength.equals("0")?"1":columnLength;
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" character(" + columnLength + ");";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.UnsignedIntegerNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" integer;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.DecimalNumericDataType":
			//FIX THE PROBLEM WITH columnScale
			//query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" numeric(" + columnLength + ", " + columnScale + ");";
			
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" numeric;";
			
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.UnsignedSmallIntegerNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" smallint;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.SignedSmallIntegerNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" smallint;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.UnsignedTinyIntegerNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" smallint;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.TrueOrFalseLogicalDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" boolean;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.AutoCounterNumericDataType":
			String sequenceName = relationName + "_" + columnName + "_seq";
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" integer;";
			if (automaticSerialOn) {
				query += "ALTER TABLE \"" + relationName + "\" ALTER COLUMN \"" + columnName + "\" SET NOT NULL;";
				
				//generated sequences start at 1001, for letting some space to asserted entity type instances
				query = "CREATE SEQUENCE \"" + sequenceName + "\" INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1001 CACHE 1;" +
						query +
						"ALTER TABLE \"" + relationName + "\" ALTER COLUMN \"" + columnName + "\" SET DEFAULT nextval('\"" + sequenceName + "\"'::regclass);";
			}
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.ObjectIdOtherDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" uuid;";
			if (automaticSerialOn) {
				query += "ALTER TABLE \"" + relationName + "\" ALTER COLUMN \"" + columnName + "\" SET NOT NULL;";
				query = "CREATE OR REPLACE FUNCTION newuuid() RETURNS uuid AS " +
						"$BODY$ SELECT CAST(md5(current_database()|| user ||current_timestamp ||random()) as uuid) $BODY$ " +
					    "LANGUAGE sql VOLATILE COST 100;" +
						query +
						"ALTER TABLE \"" + relationName + "\" ALTER COLUMN \"" + columnName + "\" SET DEFAULT newuuid();";
			}
			break;	
		case "co.syscoop.soberano.rules.enforcer.metamodel.DoublePrecisionFloatingPointNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" double precision;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.SinglePrecisionFloatingPointNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" real;";
			break;			
		case "co.syscoop.soberano.rules.enforcer.metamodel.MoneyNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" money;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.DateAndTimeTemporalDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" timestamp with time zone;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.AutoTimestampTemporalDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" timestamp with time zone;";
			query += "ALTER TABLE \"" + relationName + "\" ALTER COLUMN \"" + columnName + "\" SET DEFAULT now();";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.PictureRawDataDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" bytea;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.VariableLengthRawDataDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" bytea;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.TimeTemporalDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" time with time zone;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.SignedIntegerNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" integer;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.UnsignedLargeIntegerNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" bigint;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.SignedLargeIntegerNumericDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" bigint;";
			break;
		case "co.syscoop.soberano.rules.enforcer.metamodel.LargeLengthRawDataDataType":
			query = "ALTER TABLE \"" + relationName + "\" ADD COLUMN \"" + columnName + "\" bytea;";
			break;
		default:
			throw new UnsupportedORMDatatypeException(dataTypeClassName);				
		}
		return query;
	}

	private String getColumnsCreationQuery(	String relationName,
											ArrayList<String> columnNames,
											ArrayList<String> columnTypes,
											ArrayList<String> columnLengths,
											ArrayList<String> columnScales,
											ArrayList<Boolean> columnAutomaticSerialFlags,
											ArrayList<ColumnValueConstraint> columnValueConstraints,
											ArrayList<String> constraintQueries) throws UnsupportedORMDatatypeException {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		
		String query = "";
		String constraintQuery = "";
		
		//for each column
		for (int i = 0; i < columnNames.size(); i++) {
			query += getColumnCreationQuery(relationName, 
										columnNames.get(i), 
										columnTypes.get(i), 
										columnLengths != null && columnLengths.size() > 0 && columnLengths.get(i) != null?columnLengths.get(i):"1", 
										columnScales != null && columnScales.size() > 0 && columnScales.get(i) != null?columnScales.get(i):"0", 
										columnAutomaticSerialFlags != null && columnAutomaticSerialFlags.size() > 0 && columnAutomaticSerialFlags.get(i) != null?columnAutomaticSerialFlags.get(i):false);
			
			//if it were passed value constraints
			if (columnValueConstraints != null && columnValueConstraints.get(i) != null) {
			
				//if the value constraint of the current column has a name
				if (!columnValueConstraints.get(i).getConstraintName().isEmpty()) {
					constraintQuery = "ALTER TABLE \"" +
									relationName +
									"\" DROP CONSTRAINT IF EXISTS \"" +
									columnValueConstraints.get(i).getConstraintName() + "\";";
					constraintQuery += "ALTER TABLE \"" + 
							relationName + 
							"\" ADD CONSTRAINT \"" + 
							columnValueConstraints.get(i).getConstraintName() + "\" CHECK (";
					
					//for each value range
					for (ColumnValueRange valueRange : columnValueConstraints.get(i).getColumnValueRanges()) {
					
						//if minimum value equals to maximum value
						if (valueRange.getMinValue().equals(valueRange.getMaxValue())) {
							constraintQuery += "\"" + columnNames.get(i) + "\" = '" + valueRange.getMinValue() + "' OR ";
						}
						//if maximum value is empty
						else if (valueRange.getMaxValue().isEmpty()) {
							constraintQuery += "\"" + columnNames.get(i) + "\" >= '" + valueRange.getMinValue() + "' OR ";
						}
						//if minimum value is empty
						else if (valueRange.getMinValue().isEmpty()) {
							constraintQuery += "\"" + columnNames.get(i) + "\" <= '" + valueRange.getMaxValue() + "' OR ";
						}
						//if both are filled and are different
						else {
							constraintQuery += "(\"" + columnNames.get(i) + 
									"\" >= '" + 
									valueRange.getMinValue() + 
									"' AND \""+ 
									columnNames.get(i) + 
									"\" <= '" + 
									valueRange.getMaxValue() + "') OR ";
						}
					}
					constraintQuery += ");";
					constraintQuery = constraintQuery.replace("OR );", ");");
					constraintQueries.add(constraintQuery);
				}
			}
		}
		return query;
	}

	@Override
	public String getPrimaryKeyCreationQueryString(String relationName, ArrayList<String> primaryKeyColumnsNames) {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		
		if (primaryKeyColumnsNames.size() > 0) {
			String queryString = "ALTER TABLE \"" + schemaQualifiedRelationName(relationName) + "\" ADD CONSTRAINT \"PK_" + relationName + "\" PRIMARY KEY(\"";
			
			//for each column
			for (String columnName : primaryKeyColumnsNames) {
				queryString += columnName + "\",\"";
			}
			queryString += ");";
			return queryString.replace(",\");", ");");
		}
		else return ";";
	}

	@Override
	public String getForeignKeyCreationQueryString(String foreignKeyName, String locaTableName, String foreignTableName, ArrayList<String> localColumnNames, ArrayList<String> foreignColumnNames) {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		
		if (localColumnNames.size() > 0 && (localColumnNames.size() == foreignColumnNames.size())) {
			
			//construct foreign key query string
			String fkQueryString = "ALTER TABLE \"" + schemaQualifiedRelationName(locaTableName) + "\" ADD CONSTRAINT \"" + foreignKeyName + "\" " + "FOREIGN KEY (\"";
			
			//for each local column
			for (int i = 0 ; i < localColumnNames.size() ; i++) {
				fkQueryString += localColumnNames.get(i) + "\",\"";
			}
			fkQueryString += ")";
			fkQueryString = fkQueryString.replace(",\")", ")") + " REFERENCES \"" + schemaQualifiedRelationName(foreignTableName) + "\" (\"";
			
			//for each foreign column
			for (int i = 0 ; i < foreignColumnNames.size() ; i++) {
				fkQueryString += foreignColumnNames.get(i) + "\",\"";
			}
			fkQueryString += ")";
			fkQueryString = fkQueryString.replace(",\")", ")") + " MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;";

			//construct foreign key index
			String randomSuffix = Double.toString(Math.round((Math.random() * 10000))).replace(".0", ""); //for avoiding duplication of index names
			String fkIndexQueryString = "CREATE INDEX \"FKI_" + foreignKeyName + "_" + randomSuffix + "\" ON \"" + schemaQualifiedRelationName(locaTableName) + "\" USING btree (\"";
			//for each local column
			for (int i = 0 ; i < localColumnNames.size() ; i++) {
				fkIndexQueryString += localColumnNames.get(i) + "\",\"";
			}
			fkIndexQueryString += ")";
			fkIndexQueryString = fkIndexQueryString.replace(",\")", ");");
			
			return fkQueryString + fkIndexQueryString;
		}
		else return ";";
	}

	@Override
	public String getUniqueConstraintCreationQueryString(String relationName, String constraintName, ArrayList<String> uniqueConstraintColumnsNames) {

		// TODO evaluate the possibility of SQL injection and prevent it
		
		if (uniqueConstraintColumnsNames.size() > 0) {
			
			String queryString = "ALTER TABLE \"" + schemaQualifiedRelationName(relationName) + "\" ADD CONSTRAINT \"" + constraintName + "\" UNIQUE(\"";
			
			//for each column
			for (String columnName : uniqueConstraintColumnsNames) {
				queryString += columnName + "\",\"";
			}
			queryString += ");";
			return queryString.replace(",\");", ");");
		}
		else return ";";
	}

	@Override
	public String getSchemaCreationQuery(String schemaName) {
		
		// TODO evaluate the possibility of SQL injection and prevent it
		String query = "CREATE SCHEMA \"" + schemaName + "\";";
		return query;
	}

	private String schemaQualifiedRelationName(String relationName) {
		return relationName.replace(".", "\".\"");
	}

	@Override
	public String getInsertionQuery(String relationName, String[] columnNames, String[] parameterNames) {
		String query = "INSERT INTO \"" + schemaQualifiedRelationName(relationName) + "\" (";
		for (String columnName : columnNames) {
			query += "\"" + columnName + "\", ";
		}
		query += ")";
		query = query.replace(", )", ") VALUES(");
		for (String parameterName : parameterNames) {
			query += parameterName + ", ";
		}
		query += ")";
		query = query.replace(", )", ");");		
		return query;
	}

	@Override
	public String getUpdatingQuery(String relationName,
									String[] columnToUpdateNames,
									String[] newValueParameterNames,
									String[] criteriaColumnNames, 
									String[] criteriaParameterNames) {

		String query = "UPDATE \"" + schemaQualifiedRelationName(relationName) + "\" SET ";
		for(int i = 0 ; i < columnToUpdateNames.length ; i++) {
			query += "\"" + columnToUpdateNames[i] + "\" = " + newValueParameterNames[i] + ", ";
		}
		query += " WHERE ";
		query = query.replace(",  WHERE ", " WHERE ");
		for(int i = 0 ; i < criteriaColumnNames.length ; i++) {
			query += "\"" + criteriaColumnNames[i] + "\" = " + criteriaParameterNames[i] + ", ";
		}
		query += "#";
		query = query.replace(", #", ";");	
		return query;
	}
}
