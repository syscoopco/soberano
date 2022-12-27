/**
 * 
 */
package co.syscoop.soberano.rules.enforcer.generator;

import java.util.ArrayList;
import co.syscoop.soberano.exception.UnsupportedORMDatatypeException;

/**
 * @author Josue Portal
 *
 * Every relational model generator must implement the interface IRelationalModelGenerator.
 */
public interface IRelationalModelGenerator extends ILogicalModelGenerator {

	//Returns the parameterized query for updating rows in a relation, meeting a criteria giving by a conjunction of
	//equalities between columns of the same relation and scalar values. columnToUpdateNames is for passing the names of
	//columns to be updated. In newValueParameterNames are passed the names of the parameters for giving new values to
	//the updated columns. criteriaColumnNames are the names of the columns of the relation that participate in the criteria.
	//criteriaParameterNames contains the names of the parameters for evaluating the columns (equality tests) in the criteria.
	abstract public String getUpdatingQuery(String relationName, String[] columnToUpdateNames, String[] newValueParameterNames, String[] criteriaColumnNames, String[] criteriaParameterNames);
	
	//Returns the parameterized query for inserting rows in a relation. The indexes of the parameter names array must correspond
	//to the indexes of the parameter values array.
	abstract public String getInsertionQuery(String relationName, String[] columnNames, String[] parameterNames);
	
	//Returns the query that must be used by a specialized relational logical model generator to create a schema.
	abstract String getSchemaCreationQuery(String schemaName);
	
	//Returns the query that must be used by a specialized relational model generator to create a relation with columns
	//of given names, types, lengths, and serial generation behavior.
	abstract String getRelationCreationQuery(String relationName, ArrayList<String> columnNames, ArrayList<String> columnTypes, ArrayList<String> columnLengths, ArrayList<String> columnScales, ArrayList<Boolean> columnAutomaticSerialFlags, ArrayList<ColumnValueConstraint> columnValueConstraints, ArrayList<String> constraintQueries) throws UnsupportedORMDatatypeException;

	//Returns the query that must be used by a specialized relational logical model generator to create the target relational
	//database.
	abstract String getDatabaseCreationQuery(String databaseName);
	
	//Returns the query that must be used by a specialized relational logical model generator to drop the target relational
	//database.
	abstract String getDatabaseDropQuery(String databaseName);
	
	//Returns the query that must be used by a specialized relational logical model generator to test the existence of the target
	//relational database.
	abstract String getDatabaseExistenceTestQuery(String databaseName);
	
	//Get the database URL from the URL of the DBMS used initially by the data source and the name of the database.
	abstract String getDatabaseURL(String DBMSUrl, String databaseName);
	
	//Get the query string for creating a primary key constraint in a relation with the name relationName. The primary key will be composed by the
	//columns with the names passed in primaryKeyColumnsNames.
	abstract String getPrimaryKeyCreationQueryString(String relationName, ArrayList<String> primaryKeyColumnsNames);
	
	//Get the query string for creating a foreign key constraint in a relation with the name locaTableName. The foreign key will target a relation of
	//name foreignTableName. The names of the local and foreign columns are passed in localColumnNames and foreignColumnNames respectively.
	abstract String getForeignKeyCreationQueryString(String foreignKeyName, String locaTableName, String foreignTableName, ArrayList<String> localColumnNames, ArrayList<String> foreignColumnNames);
	
	//Get the query string for creating in a relation a unique constraint with a set of columns whose names are passed in uniqueConstraintColumnsNames.
	abstract String getUniqueConstraintCreationQueryString(String relationName, String constraintName, ArrayList<String> uniqueConstraintColumnsNames);
}
