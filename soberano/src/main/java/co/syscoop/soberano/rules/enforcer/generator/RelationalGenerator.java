/**
 * 
 */
package co.syscoop.soberano.rules.enforcer.generator;

import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import javax.sql.rowset.CachedRowSet;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.*;
import co.syscoop.soberano.database.relational.*;
import co.syscoop.soberano.exception.*;
import co.syscoop.soberano.rules.enforcer.metamodel.*;

/**
 * @author Josue Portal
 *
 * RelationalGenerator is a logical generator specialized in generating and deploying relational logical models. 
 */
public abstract class RelationalGenerator extends LogicalModelGenerator implements IRelationalModelGenerator{
	
	//URL of the DBMS configured in the Spring context file.
	private String DBMSUrl = "";
	
	//DataSource bean configured in the Spring context file. This is the destination data source that will store the implementation
	//of the generated logical model.
	private BasicDataSource dataSource = null;
		
	//The relational model generation is a transactional process that is non DBMS technology - dependent.
	private PlatformTransactionManager transactionManager = null;
	private TransactionDefinition transactionDefinition = null;
	
	//The name of the target relational database that is the same as the name of the Object-Role Modeling model.
	private String databaseName = null;
	
	//Stores the implementation of a role. In a relational database, a role is implemented in the form of a set of columns belonging to a relation.
	private class RoleImplementation {
		
		//The name of the relation within the role is implemented.
		public String relationName = null;
		
		//The names of the columns that implement the role.
		public ArrayList<ORMRBAColumn> columns = new ArrayList<ORMRBAColumn>();
		
		RoleImplementation(String relationName) {
			this.relationName = relationName;
		}
		
		//Peer implementation of the role. This is the case of a role that belongs to an objectification but at the same time is the corole in a external preferred
		//scheme of an entity types. In this case, peerImplementation is the implementation of the same role in the objectification.
		public RoleImplementation peerImplementation = null;
	}
	
	//This map is used to locate a role implementation by the id of the role.
	private HashMap<String, RoleImplementation> roleImplementations = new HashMap<String, RoleImplementation>();
	
	//Stores the constraints queries to be applied after restoring the fact model.
	private ArrayList<String> constraintQueries = new ArrayList<String>();
	
	//This constructor must be called from the descendant classes: specialized logical model generators (to PostgreSQL, SQL Server, ...).
	protected RelationalGenerator(BasicDataSource dataSource,
								PlatformTransactionManager transactionManager,
								TransactionDefinition transactionDefinition,
								String metamodelFileName) {
		this.dataSource = dataSource;
		this.DBMSUrl = dataSource.getUrl();
		this.transactionManager = transactionManager;
		this.transactionDefinition = transactionDefinition;
		super.setMetamodelFilePath(metamodelFileName);
	}
	
	//ORMRBAColumn represents a column when applying ORMRBA generation method approach.
	private class ORMRBAColumn {
		String columnName = "";
		String columnType = "";
		String columnLenght = ""; //or precision
		String columnScale = "";
		Boolean automaticSerial = false;
		ORMRBAColumn(String columnName, String columnType, String columnLenght, String columnScale, Boolean automaticSerial, ColumnValueConstraint columnValueConstraint) {
			this.columnName = columnName;
			this.columnType = columnType;
			this.columnLenght = columnLenght;
			this.columnScale = columnScale;
			this.automaticSerial = automaticSerial;
			this.columnValueConstraint = columnValueConstraint;
		}
		ColumnValueConstraint columnValueConstraint = null;
	}
	
	//UniqueConstraint represents a unique constraint defined by a set of columns.
	private class UniqueConstraint {
		public ArrayList<ORMRBAColumn> columns = new ArrayList<RelationalGenerator.ORMRBAColumn>();
		public String constraintName = "";
		UniqueConstraint(String constraintName) {
			this.constraintName = constraintName;
		}
	}
	
	//ForeignKey represent a relational foreign key.
	private class ForeignKey {
		public String foreignKeyName = "";
		public String foreignRelationName = "";
		public ArrayList<ORMRBAColumn> localColumns = new ArrayList<RelationalGenerator.ORMRBAColumn>();
		public ArrayList<ORMRBAColumn> foreignColumns = new ArrayList<RelationalGenerator.ORMRBAColumn>();
		ForeignKey(String foreigKeyName, String foreignRelationName) {
			this.foreignKeyName = foreigKeyName;
			this.foreignRelationName = foreignRelationName;
		}
	}
	
	//Relation represents a relation already existent in the target relational database, before applying the generation approach ORMRBA.
	private class Relation {
		
		//Relation name.
		public String name = "";
		
		Relation(String name) {
			this.name = name;
		}
		
		public ArrayList<ORMRBAColumn> columns = new ArrayList<ORMRBAColumn>();
		public ArrayList<ORMRBAColumn> primaryKeyColumns = new ArrayList<ORMRBAColumn>();
		public ArrayList<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
		public HashMap<String, ForeignKey> foreignKeyLocator = new HashMap<String, ForeignKey>();
		public ArrayList<UniqueConstraint> uniqueConstraints = new ArrayList<UniqueConstraint>();
		public HashMap<String, UniqueConstraint> uniqueConstraintLocator = new HashMap<String, UniqueConstraint>();
	}
	
	//For iterating the relations.
	private ArrayList<Relation> relations = new ArrayList<Relation>();
	
	//For rapidly locating a relation by its name.
	private HashMap<String, Relation> relationLocator = new HashMap<String, Relation>();
	
	//The id automatically taken by the domain of the metamodel.
	int DomainId = 0; 

	//Creates a relational database with the name of the ORM model.
	private void createDatabase() throws SoberanoSQLException, SoberanoException {

		try {
			Query query = new Query(getDatabaseCreationQuery(databaseName), false, null, dataSource, true);
			query.executeQuery(false);
		}
		catch(Exception cause) {
			throw new SoberanoException(cause);
		}
	}
	
	private String schemaQualifiedRelationName(String schemaName, String relationName) {
		return schemaName.isEmpty()?relationName:schemaName + "." + relationName;
	}

	//During the application of the generation approach ORMRBA, registerARelation registers the information related to a relation that must already exist
	//in the target relational database. This is done by updating the array and map relations and relationLocator respectively.
	private void registerARelation(String relationName) {
		
		//if the relation has not been registered yet
		if (!relationLocator.containsKey(relationName)) {
			Relation relation = new Relation(relationName);
			relationLocator.put(relationName, relation);
			relations.add(relation);
		}
	}
	
	private Boolean objectTypeIsASubtype(ObjectTypeElement objectType) throws MultipleInheritanceUnsupportedException {
		
		//count the sub type meta roles played by the entity type
		int count = 0;
		for (Role r : objectType.getPlayedRoless().get(0).getRoles()) {
			if (r.getClass().getName().contains("SubtypeMetaRole")) {
				count++;
			}
		}
		
		if (count > 0) {
			if (count > 1) {
				throw new MultipleInheritanceUnsupportedException(objectType.getName());
			}
			else return true;
		} else {
			return false;
		}
	}
	
	//Add a column to a foreign key.
	private void addColumnToForeignKey(String foreignKeyName, Relation relation, String foreignRelationName, ORMRBAColumn localColumn, ORMRBAColumn foreignColumn) {
		
		if (!relation.foreignKeyLocator.containsKey(foreignKeyName)) {
			ForeignKey foreignKey = new ForeignKey(foreignKeyName, foreignRelationName);
			relation.foreignKeys.add(foreignKey);
			relation.foreignKeyLocator.put(foreignKeyName, foreignKey);
		}
		relation.foreignKeyLocator.get(foreignKeyName).localColumns.add(localColumn);
		relation.foreignKeyLocator.get(foreignKeyName).foreignColumns.add(foreignColumn);
	}
		
	//getORMRBAColumnNameForFunctionalRole is for getting column names for functional roles played by value types.
	private String getORMRBAColumnNameForFunctionalRole(ORMModel parsedORMModel, Role role) {
		
		String columnName = "";
		
		//the fact type of the role
		Fact fact = parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId());
		
		//the name of the value type
		String valueTypeName = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId()).getName();
		
		//always take the first reading
		String reading = (fact.getReadingOrderss().get(0).getReadingOrders().get(0).getReadingss().get(0).getReadings().get(0).getData()).replaceAll(" ", "_");
		
		//if the value type plays the first role in the sequence of the reading
		if (role.getId().equals(fact.getReadingOrderss().get(0).getReadingOrders().get(0).getRoleSequences().get(0).getRoles().get(0).getRef())) {
			columnName = reading.replace("{0}", valueTypeName).replace("{1}", "This");
		}
		else {
			columnName = reading.replace("{0}", "This").replace("{1}", valueTypeName);
		}
		return columnName;	
	}
	
	//getORMRBAColumnNameForObjectification is for getting column names for roles played by value types and that do not participate in reference schemes
	//nor are functional roles.
	private String getORMRBAColumnNameForFactType(ORMModel parsedORMModel, Role role) {
		
		String columnName = "";
		
		//if the role has a name
		if (!role.getName().isEmpty()) {
			
			//the column name is the name of the role
			columnName = role.getName();
		}
		else {
			//takes the name of the value type
			columnName = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId()).getName();
			
		}
		return columnName;	
	}
	
	//This version of getORMRBAColumnName is for getting column names for roles belonging to preferred identifiers and played by value types.
	private String getORMRBAColumnName(ORMModel parsedORMModel, Role role) {
	
		String columnName = "";
		
		//if the role has a name
		if (!role.getName().isEmpty()) {
			
			//the column name is the name of the role
			columnName = role.getName();
		}
		else {
			//if the role belongs to an objectified fact
			if (parsedORMModel.getFactss().get(0).getObjectifiedFactTypeLocator().containsKey(parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId()).getId())) {
				
				//the column name is the name of the value type
				columnName = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId()).getName();
			}
			else {
				//the column name is the name of the fact type
				columnName = parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId()).get_Name();
			}
		}
		return columnName;
	}
	
	private ColumnValueConstraint getValueConstraint(Role role, ValueType valueType) {
		
		//get the value restrictions of the role
		List<ValueRestriction> valueRestrictions = role.getValueRestrictions();
		
		//if the role doesn't have value restrictions
		if (valueRestrictions.size() == 0) {
			
			//takes the value restrictions of the value type
			valueRestrictions = valueType.getValueRestrictions();
		}
		
		//create a new anonymous value constraint to be applied to the column and add it to the array
		ColumnValueConstraint columnValueConstraint = new ColumnValueConstraint("");
		
		//for each value restrictions
		for (ValueRestriction valueRestriction : valueRestrictions) {
					
			//for each value constraint
			for (ValueConstraint valueConstraint : valueRestriction.getValueConstraints()) {
						
				//give a name to the constraint
				columnValueConstraint.setConstraintName(valueConstraint.getName());
						
				//for each value range
				for (ValueRange valueRange : valueConstraint.getValueRangess().get(0).getValueRanges()) {
							
					//add the value range to the value constraint
					columnValueConstraint.getColumnValueRanges().add(new ColumnValueRange(valueRange.getMinValue(), valueRange.getMaxValue()));
				}
			}
		}
		return columnValueConstraint;
	}
	
	private void addColumnToUniqueConstraint(String constraintName, Relation relation, ORMRBAColumn column) {
		
		if (!relation.uniqueConstraintLocator.containsKey(constraintName)) {
			UniqueConstraint uniqueConstraint = new UniqueConstraint(constraintName);
			relation.uniqueConstraints.add(uniqueConstraint);
			relation.uniqueConstraintLocator.put(constraintName, uniqueConstraint);
		}
		relation.uniqueConstraintLocator.get(constraintName).columns.add(column);
	}
		
	private RoleImplementation implementRoleInObjectifiedType(ORMModel parsedORMModel, Role role, ValueType player, String preferredUniquenessConstraintId, ObjectifiedType objectifiedType) {
		
		//get the name of the objectified type
		String objectifiedTypeName = parsedORMModel.getObjectss().get(0).getObjectifiedTypeLocator().get(
									parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId()).getId()).getName();
		
		//if the relation derived from the objectified type does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), objectifiedTypeName))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), objectifiedTypeName));
		}
		
		//the relation of the objectified type
		Relation relation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), objectifiedTypeName));
		
		//prepare the column
		ORMRBAColumn column = new ORMRBAColumn(getORMRBAColumnNameForFactType(parsedORMModel, role),
				parsedORMModel.getDataTypess().get(0).getDataTypeLocator().get(player.getConceptualDataTypes().get(0).getRef()).getClass().getName(),
				player.getConceptualDataTypes().get(0).getLength(),
				player.getConceptualDataTypes().get(0).getScale(),
				
				//enable automatic generation of column values (uuid, autocounters) only if it is the preferred identifier of
				//the objectified fact type or it is not an identifier at all
				(objectifiedType.getPreferredIdentifiers().size() > 0 && 
						objectifiedType.getPreferredIdentifiers().get(0).getRef().equals(preferredUniquenessConstraintId)) ||
						preferredUniquenessConstraintId == null?true:false,
				getValueConstraint(role, player));
		
		//add the column to the relation
		relation.columns.add(column);
		
		//if the player belongs to a preferred identifier
		if (preferredUniquenessConstraintId != null) {
			
			//if this is the preferred identifier of the objectified type (remember it could be a preferred identifier, but not of the 
			//objectified fact type. perhaps it is an external identifier of an entity type playing a role in this fact type)
			if (objectifiedType.getPreferredIdentifiers().size() > 0 && objectifiedType.getPreferredIdentifiers().get(0).getRef().equals(preferredUniquenessConstraintId)) {
				relation.primaryKeyColumns.add(column);
			}
		}
		
		//return the implementation of the role
		RoleImplementation roleImplementation = new RoleImplementation(relation.name);
		roleImplementation.columns.add(column);
		return roleImplementation;
	}
	
	private RoleImplementation implementFunctionalRole(ORMModel parsedORMModel, Role role, ValueType player, ObjectTypeElement coplayer) {
		
		//if the relation derived from the coplayer does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()));
		}
				
		//the relation of the coplayer
		Relation relation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()));
				
		RoleImplementation roleImplementation = new RoleImplementation(relation.name);
				
		//prepare the column
		ORMRBAColumn column = new ORMRBAColumn(getORMRBAColumnNameForFunctionalRole(parsedORMModel, role),
				parsedORMModel.getDataTypess().get(0).getDataTypeLocator().get(player.getConceptualDataTypes().get(0).getRef()).getClass().getName(),
				player.getConceptualDataTypes().get(0).getLength(),
				player.getConceptualDataTypes().get(0).getScale(),
				true,
				getValueConstraint(role, player));
				
		//add the column to the relation
		relation.columns.add(column);
		
		//add column to role implementation
		roleImplementation.columns.add(column);
		return roleImplementation;
	}
	
	private RoleImplementation implementRoleInObjectTypeRelation(ORMModel parsedORMModel, Role role, ValueType player, ObjectTypeElement coplayer, Boolean columnIsPartOFThePrimaryKey, ObjectTypeElement superType) throws MultipleInheritanceUnsupportedException {
		
		//this call prevents multiple inheritance since objectTypeIsASubtype throws an exception if so
		objectTypeIsASubtype(coplayer);
			
		//if the relation derived from the coplayer type does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()));
		}
			
		//the relation of the coplayer
		Relation relation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()));
			
		//prepare the column
		ORMRBAColumn column = new ORMRBAColumn(getORMRBAColumnName(parsedORMModel, role),
						parsedORMModel.getDataTypess().get(0).getDataTypeLocator().get(player.getConceptualDataTypes().get(0).getRef()).getClass().getName(),
						player.getConceptualDataTypes().get(0).getLength(),
						player.getConceptualDataTypes().get(0).getScale(),
						superType == null?true:false,
						getValueConstraint(role, player));
			
		//add the column to the relation
		relation.columns.add(column);
		
		//add the column to role implementation
		RoleImplementation roleImplementation = new RoleImplementation(relation.name);
		roleImplementation.columns.add(column);
		
		//if required, add the column to the primary key of the relation
		if (columnIsPartOFThePrimaryKey) {
			
			//add column to primary key
			relation.primaryKeyColumns.add(column);
			
			//add foreign key to supertype relation
			if (superType != null) {
				addColumnToForeignKey("FK_ST_" + schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), relation, schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), column, column);
			}
			
			//scan sub types
			for (Role r : coplayer.getPlayedRoless().get(0).getSupertypeMetaRoles()) {
				
				//get subtype
				EntityType subtype = (EntityType) parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(
									parsedORMModel.getFactss().get(0).getSubTypeMetaroleLocator().get(r.getRef()).getId());
				RoleImplementation roleImpl = implementRoleInObjectTypeRelation(parsedORMModel,
																				role, 
																				player, 
																				subtype, 
																				subtype.getPreferredIdentifiers().size() == 0?true:false,
																				coplayer);
				roleImplementations.put(r.getId(), roleImpl);
			}
		}
		//add the column to a unique constraint
		else {
			if (superType != null) {
				
				//add the column to a unique constraint
				addColumnToUniqueConstraint("UC_ST_" + relation.name + "_" + schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), relation, column);
				addColumnToForeignKey("FK_ST_" + schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), relation, schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), column, column);
			}
		}
		return roleImplementation;
	}
	
	private RoleImplementation implementRoleInObjectification(ORMModel parsedORMModel, RoleImplementation roleImplementation, Role role, ValueType player, ObjectTypeElement coplayer, String preferredUniquenessConstraintId) throws MultipleInheritanceUnsupportedException {
		
		Boolean roleBelongsToAPrefferedIdentifier = parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId());
		Boolean roleBelongsToAnObjectTypePrefferedIdentifier = roleBelongsToAPrefferedIdentifier && 
				coplayer != null && 
				!coplayer.getClass().getName().contains("ValueType") &&
				(!parsedORMModel.getConstraintss().get(0).getInternalConstraintLocator().containsKey(preferredUniquenessConstraintId) ||
				parsedORMModel.getConstraintss().get(0).getConstraintLocator().get(preferredUniquenessConstraintId).getRoleSequences().get(0).getRoles().size() == 1); 
		RoleImplementation roleImpl = null;
		ObjectifiedType objectifiedType = parsedORMModel.getObjectss().get(0).getObjectifiedTypeLocator().get(parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId()).getId());
		
		if (roleBelongsToAnObjectTypePrefferedIdentifier && 
				((EntityType) coplayer).getPreferredIdentifiers().size() > 0 
				&&  ((EntityType) coplayer).getPreferredIdentifiers().get(0).getRef().equals(preferredUniquenessConstraintId)) {
			roleImpl = implementRoleInObjectTypeRelation(parsedORMModel, role, player, coplayer, true, null);
		}
		
		//the role belongs to the preferred identifier of the objectification
		if (roleBelongsToAPrefferedIdentifier && 
			(objectifiedType.getPreferredIdentifiers().size() > 0 && parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().get(role.getId()).equals(objectifiedType.getPreferredIdentifiers().get(0).getRef()))) {
				
			//propagate the role along the subtypes of the objectification
			for (Role r : objectifiedType.getPlayedRoless().get(0).getSupertypeMetaRoles()) {
				
				//get subtype
				EntityType subtype = (EntityType) parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(
									parsedORMModel.getFactss().get(0).getSubTypeMetaroleLocator().get(r.getRef()).getId());
				RoleImplementation ri = implementRoleInObjectTypeRelation(parsedORMModel,
																			role, 
																			player, 
																			subtype, 
																			subtype.getPreferredIdentifiers().size() == 0?true:false,
																			objectifiedType);
				roleImplementations.put(r.getId(), ri);
			}
		}
		roleImplementation.peerImplementation = roleImpl;
		return roleImplementation;
	}
	
	private RoleImplementation implementRoleInObjectifiedType(ORMModel parsedORMModel, Role role, EntityType player, String preferredUniquenessConstraintId, ObjectifiedType objectifiedType) {
		
		//get the name of the objectified type
		String objectifiedTypeName = parsedORMModel.getObjectss().get(0).getObjectifiedTypeLocator().get(
									parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId()).getId()).getName();
		
		//if the relation derived from the objectified type does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), objectifiedTypeName))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), objectifiedTypeName));
		}
		
		//the relation of the objectified type
		Relation objectifiedTypeRelation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), objectifiedTypeName));
		
		//if the relation derived from the entity type does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()));
		}
		
		//the relation of the player
		Relation entityTypeRelation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()));
		
		RoleImplementation roleImplementation = new RoleImplementation(objectifiedTypeRelation.name);
		
		//for each columns of the pitched entity type relation primary key
		for (ORMRBAColumn c : entityTypeRelation.primaryKeyColumns) {
			
			//prepare the column
			String columnName = !role.getName().isEmpty()?role.getName() + "_" + c.columnName:c.columnName;
			ORMRBAColumn column = new ORMRBAColumn(columnName,
													c.columnType,
													c.columnLenght,
													c.columnScale,
													false,
													c.columnValueConstraint);
					
			//add the column to the objectified type relation
			objectifiedTypeRelation.columns.add(column);
					
			//update the foreign key targeting pitched entity type relation from the fact type relation
			String foreignKeyName = !role.getName().isEmpty()?"FK_FT_" + role.getName():"FK_FT_" + entityTypeRelation.name;
			addColumnToForeignKey(foreignKeyName, objectifiedTypeRelation, entityTypeRelation.name, column, c);
			
			//add column to role implementation
			roleImplementation.columns.add(column);
			
			//if the player belongs to a preferred identifier
			if (preferredUniquenessConstraintId != null) {
				
				//if this is the preferred identifier of the objectified type (remember it could be a preferred identifier, but not of the 
				//objectified fact type. perhaps it is an external identifier of an entity type playing a role in this fact type)
				if (objectifiedType.getPreferredIdentifiers().size() > 0 && objectifiedType.getPreferredIdentifiers().get(0).getRef().equals(preferredUniquenessConstraintId)) {
					objectifiedTypeRelation.primaryKeyColumns.add(column);
				}
			}
		}
		return roleImplementation;
	}
	
	private RoleImplementation implementFunctionalRole(ORMModel parsedORMModel, Role role, EntityType coplayer, EntityType player) {
		
		//if the relation derived from the coplayer does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()));
		}
		
		//the relation of the coplayer
		Relation coplayerRelation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()));
		
		//if the relation derived from the player does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()));
		}
		
		//the relation of the player
		Relation playerRelation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()));
		
		//the fact type of the role
		Fact fact = parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId());
		
		//the implementation of the role
		RoleImplementation roleImplementation = new RoleImplementation(coplayerRelation.name);
		
		//always take the first reading
		String reading = (fact.getReadingOrderss().get(0).getReadingOrders().get(0).getReadingss().get(0).getReadings().get(0).getData()).replaceAll(" ", "_");
		
		for (ORMRBAColumn c : playerRelation.primaryKeyColumns) {
			
			String columnName = !role.getName().isEmpty()?role.getName() + "_with_" + c.columnName:player.getName() + "_with_" + c.columnName;
			
			//if the pitched entity type plays the first role in the sequence of the reading
			if (role.getId().equals(fact.getReadingOrderss().get(0).getReadingOrders().get(0).getRoleSequences().get(0).getRoles().get(0).getRef())) {
				columnName = reading.replace("{0}", columnName).replace("{1}", "This");
			}
			else {
				columnName = reading.replace("{0}", "This").replace("{1}", columnName);
			}
			
			//prepare the column
			ORMRBAColumn column = new ORMRBAColumn(columnName,
												c.columnType,
												c.columnLenght,
												c.columnScale,
												false,
												c.columnValueConstraint);
			
			//add the column to the relation
			coplayerRelation.columns.add(column);
			
			//update the foreign key targeted pitched entity type relation from the coplayer relation
			String foreignKeyName = !role.getName().isEmpty()?"FK_FR_" + role.getName():"FK_FR_" + fact.get_Name();
			addColumnToForeignKey(foreignKeyName, coplayerRelation, playerRelation.name, column, c);
			
			//add column to role implementation
			roleImplementation.columns.add(column);
		}
		return roleImplementation;		
	}
	
	private RoleImplementation implementRoleInObjectTypeRelation(ORMModel parsedORMModel, Role role, ObjectTypeElement player, ObjectTypeElement coplayer, Boolean columnIsPartOFThePrimaryKey, ObjectTypeElement superType) throws MultipleInheritanceUnsupportedException {
		
		//this call prevents multiple inheritance since objectTypeIsASubtype throws an exception if so
		objectTypeIsASubtype(coplayer);
		
		//if the relation derived from the coplayer type does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()));
		}
			
		//the relation of the coplayer
		Relation coplayerRelation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), coplayer.getName()));
		
		//if the relation derived from the player type does not exist
		if (!relationLocator.containsKey(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()))) {
			registerARelation(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()));
		}
			
		//the relation of the player
		Relation playerRelation = relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), player.getName()));
		
		//the implementation of the role
		RoleImplementation roleImplementation = new RoleImplementation(coplayerRelation.name);
			
		//copy the columns that are part of the reference scheme of the player to the coplayer relation
		for (ORMRBAColumn c : playerRelation.primaryKeyColumns) {
			
			//the column name is prefixed with the role name
			ORMRBAColumn targetColumn = new ORMRBAColumn(!role.getName().isEmpty()?role.getName() + "_" + c.columnName:c.columnName,
														c.columnType,
														c.columnLenght,
														c.columnScale,
														false,
														c.columnValueConstraint);
			coplayerRelation.columns.add(targetColumn);
			
			//if required, add the column to the primary key of the relation
			if (columnIsPartOFThePrimaryKey) {
				coplayerRelation.primaryKeyColumns.add(targetColumn);
				
				//if the call of this method was not invoked from a supertype (this is a root target entity type, so 
				//foreign keys must be created to the primary key providers entity types)
				if (superType == null) {
					addColumnToForeignKey("FK_RS_" + playerRelation.name, coplayerRelation, playerRelation.name, targetColumn, c);
				}
				//if the call of this method was invoked from a supertype
				else {
					addColumnToForeignKey("FK_ST_" + schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), coplayerRelation, schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), targetColumn, targetColumn);
				}
			}
			//add the column to a unique constraint
			else {
				if (superType != null) {
					addColumnToUniqueConstraint("UC_ST_" + coplayerRelation.name + "_" + playerRelation.name, coplayerRelation, targetColumn);
					addColumnToForeignKey("FK_ST_" + schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), coplayerRelation, schemaQualifiedRelationName(parsedORMModel.getName(), superType.getName()), targetColumn, targetColumn);
				}
			}
			roleImplementation.columns.add(targetColumn);
		}
		
		if (columnIsPartOFThePrimaryKey) {
			//scan sub types
			for (Role r : coplayer.getPlayedRoless().get(0).getSupertypeMetaRoles()) {
				
				//get subtype
				EntityType subtype = (EntityType) parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(
									parsedORMModel.getFactss().get(0).getSubTypeMetaroleLocator().get(r.getRef()).getId());
				RoleImplementation roleImpl = implementRoleInObjectTypeRelation(parsedORMModel,
																				role, 
																				player, 
																				subtype, 
																				subtype.getPreferredIdentifiers().size() == 0?true:false, 
																				coplayer);
				roleImplementations.put(r.getId(),roleImpl);
			}
		}
		return roleImplementation;
	}
	
	private RoleImplementation implementRoleInObjectification(ORMModel parsedORMModel, RoleImplementation roleImplementation, Role role, EntityType player, ObjectTypeElement coplayer, String preferredUniquenessConstraintId) throws MultipleInheritanceUnsupportedException {
		
		Boolean roleBelongsToAPrefferedIdentifier = parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId());
		Boolean roleBelongsToAnObjectTypePrefferedIdentifier = roleBelongsToAPrefferedIdentifier && 
				coplayer != null && 
				!coplayer.getClass().getName().contains("ValueType") &&
				(!parsedORMModel.getConstraintss().get(0).getInternalConstraintLocator().containsKey(preferredUniquenessConstraintId) ||
				parsedORMModel.getConstraintss().get(0).getConstraintLocator().get(preferredUniquenessConstraintId).getRoleSequences().get(0).getRoles().size() == 1);
		RoleImplementation roleImpl = null;
		ObjectifiedType objectifiedType = parsedORMModel.getObjectss().get(0).getObjectifiedTypeLocator().get(parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId()).getId());
		
		if (roleBelongsToAnObjectTypePrefferedIdentifier && 
				((EntityType) coplayer).getPreferredIdentifiers().size() > 0 
				&&  ((EntityType) coplayer).getPreferredIdentifiers().get(0).getRef().equals(preferredUniquenessConstraintId)) {
			roleImpl = implementRoleInObjectTypeRelation(parsedORMModel, role, (EntityType) player, (EntityType) coplayer, true, null);
		}
		
		//the role belongs to the preferred identifier of the objectification
		if (roleBelongsToAPrefferedIdentifier && 
			(objectifiedType.getPreferredIdentifiers().size() > 0 && parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().get(role.getId()).equals(objectifiedType.getPreferredIdentifiers().get(0).getRef()))) {
				
			//propagate the role along the subtypes of the objectification
			for (Role r : objectifiedType.getPlayedRoless().get(0).getSupertypeMetaRoles()) {
				
				//get subtype
				EntityType subtype = (EntityType) parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(
									parsedORMModel.getFactss().get(0).getSubTypeMetaroleLocator().get(r.getRef()).getId());
				RoleImplementation ri = implementRoleInObjectTypeRelation(parsedORMModel,
																		role, 
																		player, 
																		subtype, 
																		subtype.getPreferredIdentifiers().size() == 0?true:false,
																		objectifiedType);
				roleImplementations.put(r.getId(), ri);
			}
		}
		roleImplementation.peerImplementation = roleImpl;
		return roleImplementation;
	}
	
	private RoleImplementation implementUnaryRole(ORMModel parsedORMModel, Role role) throws ComposedValueTypesUnsupportedException {
		
		//get the player and its name
		ObjectTypeElement player = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId());
		String playerName = player.getName();
		
		//if the player is a value type
		if (player.getClass().getName().contains("ValueType"))
			throw new ComposedValueTypesUnsupportedException(playerName);
			
		//get the reading
		String reading = parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId()).
				getReadingOrderss().get(0).getReadingOrders().get(0).getReadingss().get(0).getReadings().get(0).getData();
		
		//column name
		String columnName = reading.replace("{0}", playerName).replaceAll(" ", "_");
		
		//column type
		String columnType = "co.syscoop.soberano.rules.enforcer.metamodel.TrueOrFalseLogicalDataType";
		
		//add the column to the proper relation
		ORMRBAColumn column = new ORMRBAColumn(columnName, columnType, null, null, null, null);
		relationLocator.get(schemaQualifiedRelationName(parsedORMModel.getName(), playerName)).columns.add(column);
		
		RoleImplementation roleImplementation = new RoleImplementation(schemaQualifiedRelationName(parsedORMModel.getName(), playerName));
		roleImplementation.columns.add(column);
		return roleImplementation;
	}
	
	private void implementRole(ORMModel parsedORMModel, Role role) throws ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException {
		
		String implementedRoleId = role.getId();
		
		//Stores the columns where the role is implemented.
		RoleImplementation roleImplementation = null;
		
		//the fact of the role
		Fact fact = parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId());
		
		//get the player of the role
		ObjectTypeElement player = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId());
		
		//if the fact is binary, get the coplayer
		ObjectTypeElement coplayer = null;
		String coroleId = "";
		if (fact.getFactRoless().get(0).getRoles().size() == 2) {
			coroleId = fact.getFactRoless().get(0).getRoles().get(0).getId();
			if (coroleId.equals(role.getId())) {
				coroleId = fact.getFactRoless().get(0).getRoles().get(1).getId();
			}
			coplayer = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(coroleId);
		}
		
		Boolean theRoleIsPlayedInAnObjectifiedFactType = parsedORMModel.getObjectss().get(0).getObjectifiedTypeLocator().containsKey(parsedORMModel.getFactss().get(0).getFactByRoleLocator().get(role.getId()).getId());
		ObjectifiedType objectifiedType = parsedORMModel.getObjectss().get(0).getObjectifiedTypeLocator().get(fact.getId());
		Boolean theRoleIsOfAnObjectification = parsedORMModel.getFactss().get(0).getObjectifiedFactTypeLocator().containsKey(fact.getId());
		Boolean roleBelongsToAPrefferedIdentifier = parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId());
		String preferredUniquenessConstraintId = parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().get(role.getId());
		Boolean roleBelongsToAnObjectTypePrefferedIdentifier = roleBelongsToAPrefferedIdentifier && 
																coplayer != null && 
																!coplayer.getClass().getName().contains("ValueType") &&
																(!parsedORMModel.getConstraintss().get(0).getInternalConstraintLocator().containsKey(preferredUniquenessConstraintId) ||
																parsedORMModel.getConstraintss().get(0).getConstraintLocator().get(preferredUniquenessConstraintId).getRoleSequences().get(0).getRoles().size() == 1); 
		
		//if the role player is a value type but it is not an implicit value of a boolean (unary fact type)
		if (player.getClass().getName().contains("ValueType") && ((ValueType) player).getIsImplicitBooleanValue() == null) {
			
			//if the role belongs to an objectified type (fact type for which a relation must be created)
			if (theRoleIsPlayedInAnObjectifiedFactType) {
				roleImplementation = implementRoleInObjectifiedType(parsedORMModel, role, (ValueType) player, preferredUniquenessConstraintId, objectifiedType);
			}
			//the role is a functional one
			else if (parsedORMModel.getFactss().get(0).getFunctionalFactTypeLocator().containsKey(fact.getId())) {
				
				//if the coplayer is also a value type
				if (coplayer.getClass().getName().contains("ValueType")) {
					throw new ComposedValueTypesUnsupportedException(coplayer.getName());
				}
				else {
					roleImplementation = implementFunctionalRole(parsedORMModel, role, (ValueType) player, (EntityType) coplayer);
					RoleImplementation peerRoleImplementation = new RoleImplementation(roleImplementation.relationName);
					peerRoleImplementation.columns = roleImplementation.columns;
					roleImplementation.peerImplementation = peerRoleImplementation;
					roleImplementations.put(coroleId, peerRoleImplementation);	
				}
			}
			else if (roleBelongsToAnObjectTypePrefferedIdentifier) {
				roleImplementation = implementRoleInObjectTypeRelation(parsedORMModel, role, (ValueType) player, coplayer, true, null);
			}
			
			if (theRoleIsOfAnObjectification) {
				roleImplementation = implementRoleInObjectification(parsedORMModel, roleImplementation, role, (ValueType) player, coplayer, preferredUniquenessConstraintId);
			}
		}
		//the role is played by an entity type. it could be an objectification
		else {
			//if the role belongs to an objectified type (fact type for which a relation must be created)
			if (theRoleIsPlayedInAnObjectifiedFactType) {
				roleImplementation = implementRoleInObjectifiedType(parsedORMModel, role, (EntityType) player, preferredUniquenessConstraintId, objectifiedType);
			}
			//the role is a functional one
			else if (parsedORMModel.getFactss().get(0).getFunctionalFactTypeLocator().containsKey(fact.getId())) {
							
				//if the role nor corole weren't processed
				if (!roleImplementations.containsKey(role.getId()) && !roleImplementations.containsKey(coroleId)) {
					String peerImplementedRoleId = "";
					
					//if this is not the functional role (the role under processing isn't constrained by an internal uniqueness constraint)
					String uniquenessConstraintId = parsedORMModel.getConstraintss().get(0).getUniquenessConstraintByRoleLocator().get(role.getId());
					if (uniquenessConstraintId == null || !parsedORMModel.getConstraintss().get(0).getInternalConstraintLocator().containsKey(uniquenessConstraintId)) {
						
						//the functional role is the corole, so pitch the player of the processed role to the coplayer 
						//through the role played by the latter
						roleImplementation = implementFunctionalRole(parsedORMModel, role, (EntityType) coplayer, (EntityType) player);
						peerImplementedRoleId = coroleId;
					}
					else {
						//the functional role is the role under processing, so pitch the player of the corole to the entity type
						//playing the processed role
						roleImplementation = implementFunctionalRole(parsedORMModel, fact.getFactRoless().get(0).getRoleLocator().get(coroleId), (EntityType) player, (EntityType) coplayer);
						implementedRoleId = coroleId;
						peerImplementedRoleId = role.getId();
					}
					RoleImplementation peerRoleImplementation = new RoleImplementation(roleImplementation.relationName);
					peerRoleImplementation.columns = roleImplementation.columns;
					roleImplementation.peerImplementation = peerRoleImplementation;
					roleImplementations.put(peerImplementedRoleId, peerRoleImplementation);
				}
			}
			else if (roleBelongsToAnObjectTypePrefferedIdentifier) {
				roleImplementation = implementRoleInObjectTypeRelation(parsedORMModel, role, (EntityType) player, (EntityType) coplayer, true, null);
			}
			
			if (theRoleIsOfAnObjectification) {
				
				//if it is a unary fact
				if (parsedORMModel.getFactss().get(0).getUnaryFactTypeLocator().containsKey(fact.getId())) {
					throw new ObjectifiedUnaryFactTypesUnsupportedException(fact.get_Name());
				}
				else {
					roleImplementation = implementRoleInObjectification(parsedORMModel, roleImplementation, role, (EntityType) player, coplayer, preferredUniquenessConstraintId);
				}
			}
			//if the fact type is unary
			else if (parsedORMModel.getFactss().get(0).getUnaryFactTypeLocator().containsKey(fact.getId())) {
				
				//avoid to process the role of the implicit boolean value type
				if (!player.getClass().getName().contains("ValueType")) {
					roleImplementation = implementUnaryRole(parsedORMModel, role);
				}
			}
		}
		//if the role was implemented
		if (roleImplementation != null) roleImplementations.put(implementedRoleId, roleImplementation);
	}
	
	private void processFacts(ORMModel parsedORMModel) throws ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException, SoberanoException {
		
		try {
			//first, process all the role played by value types that participate in reference schemes and that don't belong to objectified types
			for (FactElement factElement : parsedORMModel.getFactss().get(0).getFacts()) {
				
				//for each role
				for (Role role : factElement.getFactRoless().get(0).getRoles()) {

					//get the player of the role
					ObjectTypeElement player = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId());
					
					//if the role player is a value type that participate in a reference scheme and does not belong to objectified type
					if (player.getClass().getName().contains("ValueType") &&
						parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId()) &&
						!parsedORMModel.getObjectss().get(0).getObjectifiedTypeLocator().containsKey(factElement.getId())) {
						implementRole(parsedORMModel, role);
					}
				}
			}
			
			//second, process all the role played by value types that participate in reference schemes and that belong to objectified types
			for (FactElement factElement : parsedORMModel.getFactss().get(0).getFacts()) {
				
				//for each role
				for (Role role : factElement.getFactRoless().get(0).getRoles()) {

					//get the player of the role
					ObjectTypeElement player = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId());
					
					//if the role player is a value type that participate in a reference scheme and does not belong to objectified type
					if (player.getClass().getName().contains("ValueType") &&
						parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId()) &&
						parsedORMModel.getObjectss().get(0).getObjectifiedTypeLocator().containsKey(factElement.getId())) {
						implementRole(parsedORMModel, role);
					}
				}
			}
			
			//third, process all the role played by value types that do not participate in reference schemes
			for (FactElement factElement : parsedORMModel.getFactss().get(0).getFacts()) {
				
				//for each role
				for (Role role : factElement.getFactRoless().get(0).getRoles()) {

					//get the player of the role
					ObjectTypeElement player = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId());
					
					//if the role player is a value type but does not participate in a reference scheme
					if (player.getClass().getName().contains("ValueType") &&
						!parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId())) {
						implementRole(parsedORMModel, role);
					}
				}
			}
			
			//third, process all the role that aren't played by value types but that are preferred identifiers
			for (FactElement factElement : parsedORMModel.getFactss().get(0).getFacts()) {
				
				//for each role
				for (Role role : factElement.getFactRoless().get(0).getRoles()) {
					
					//get the player of the role
					ObjectTypeElement player = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId());
					
					//if the role player isn't a value type but it is in a preferred identifier
					if (!player.getClass().getName().contains("ValueType") &&
						parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId())) {
						implementRole(parsedORMModel, role);
					}
				}
			}
			
			//then, process the remainder fact types
			for (FactElement factElement : parsedORMModel.getFactss().get(0).getFacts()) {
						
				//for each role
				for (Role role : factElement.getFactRoless().get(0).getRoles()) {
					
					//get the player of the role
					ObjectTypeElement player = parsedORMModel.getObjectss().get(0).getRolePlayerLocator().get(role.getId());
					
					//if the role player isn't a value type and it isn't in a preferred identifier
					if (!player.getClass().getName().contains("ValueType") &&
						!parsedORMModel.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints().containsKey(role.getId())) {
						implementRole(parsedORMModel, role);
					}
				}
			}
		}
		catch(ComposedValueTypesUnsupportedException e) {
			throw e;
		}
		catch(ObjectifiedUnaryFactTypesUnsupportedException e) {
			throw e;
		}
		catch(MultipleInheritanceUnsupportedException e) {
			throw e;
		}
		catch(Exception cause) {
			throw new SoberanoException(cause);
		}
	}
	
	private void fillColumnsToBeCreatedArrays(Relation relation,
											ArrayList<String> columnNames, 
											ArrayList<String> columnTypes, 
											ArrayList<String> columnLengths, 
											ArrayList<Boolean> automaticSerialFlags, 
											ArrayList<ColumnValueConstraint> columnValueConstraints) {
		
		//for each column of the relation
		for (ORMRBAColumn c : relation.columns) {
			columnNames.add(c.columnName);
			columnTypes.add(c.columnType);
			columnLengths.add(c.columnLenght);
			automaticSerialFlags.add(c.automaticSerial);
			columnValueConstraints.add(c.columnValueConstraint);
		}
	}
	
	//createRelations generates relations with columns and add primary and foreign key constraints.
	private void createRelations() throws UnsupportedORMDatatypeException, SoberanoSQLException, SoberanoException {
		
		try {
			//for each relation
			for (Relation r : relations) {
				
				//fill the parallel column arrays
				ArrayList<String> columnNames = new ArrayList<String>();
				ArrayList<String> columnTypes = new ArrayList<String>();
				ArrayList<String> columnLengths = new ArrayList<String>();
				ArrayList<String> columnScales = new ArrayList<String>();
				ArrayList<Boolean> automaticSerialFlags = new ArrayList<Boolean>();
				ArrayList<ColumnValueConstraint> columnValueConstraints = new ArrayList<ColumnValueConstraint>();
				fillColumnsToBeCreatedArrays(r, columnNames, columnTypes, columnLengths, automaticSerialFlags, columnValueConstraints);
				
				//construct relation creation query
				Query query = new Query(getRelationCreationQuery(r.name, 
																columnNames, 
																columnTypes, 
																columnLengths, 
																columnScales,
																automaticSerialFlags, 
																columnValueConstraints, 
																constraintQueries), 
																false, 
																null, 
																dataSource, 
																true);
				
				//create relation
				query.executeQuery(true);
			}
			
			//create primary key for each relation
			for (Relation r : relations) {
				
				ArrayList<String> primaryKeyColumnsNames = new ArrayList<String>();
				
				//for each primary key column
				for (ORMRBAColumn pkc : r.primaryKeyColumns) {
					primaryKeyColumnsNames.add(pkc.columnName);
				}
				
				//construct the primary key creation query
				String qryStr = getPrimaryKeyCreationQueryString(r.name, primaryKeyColumnsNames);
				constraintQueries.add(qryStr);
			}
			
			//create unique constraint for each relation
			for (Relation r : relations) {
				
				//for each unique constraint
				for (UniqueConstraint uc : r.uniqueConstraints) {
					
					ArrayList<String> uniqueConstraintColumnsNames = new ArrayList<String>();
					
					//for each column of the constraint
					for (ORMRBAColumn c : uc.columns) {
						uniqueConstraintColumnsNames.add(c.columnName);
					}
					
					//construct the unique constraint creation query
					String qryStr = getUniqueConstraintCreationQueryString(r.name, uc.constraintName, uniqueConstraintColumnsNames);
					constraintQueries.add(qryStr);
				}
			}
			
			//create foreign keys for each relation
			for (Relation r : relations) {
				
				//for each foreign key
				for (ForeignKey fk : r.foreignKeys) {
					
					//fill the column names arrays
					ArrayList<String> localColumnsNames = new ArrayList<String>();
					ArrayList<String> foreignColumnsNames = new ArrayList<String>();
					for (int i = 0; i < fk.localColumns.size(); i ++) {
						localColumnsNames.add(fk.localColumns.get(i).columnName);
						foreignColumnsNames.add(fk.foreignColumns.get(i).columnName);
					}
					
					//construct foreign key creation query
					String qryStr = getForeignKeyCreationQueryString(fk.foreignKeyName, r.name, fk.foreignRelationName, localColumnsNames, foreignColumnsNames);
					constraintQueries.add(qryStr);
				}
			}
		}
		catch(UnsupportedORMDatatypeException e) {
			throw e;
		}
		catch(Exception cause) {
			throw new SoberanoException(cause);
		}
	}

	//generateRelationalModelUsingORMRBA generates the relational logical model following the Object-Role Modeling Role-Based.
	private void generateRelationalModelUsingORMRBA(ORMModel parsedORMModel) throws UnsupportedORMDatatypeException, ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException, SoberanoSQLException, SoberanoException {

		try {
			//create schema
			Query query = new Query(getSchemaCreationQuery(parsedORMModel.getName()),
														false, 
														null, 
														dataSource, 
														true);
			query.executeQuery(true);
			
			//process fact types
			processFacts(parsedORMModel);
		}
		catch (ComposedValueTypesUnsupportedException e) {
			throw e;
		}
		catch(ObjectifiedUnaryFactTypesUnsupportedException e) {
			throw e;
		}
		catch(MultipleInheritanceUnsupportedException e) {
			throw e;
		}
		catch(UnsupportedORMDatatypeException e) {
			throw e;
		}
		catch(SoberanoSQLException e) {
			throw e;
		}
		catch(Exception cause) {
			throw new SoberanoException(cause);
		}
	}
	
	//In the relational database, creates the schema metamodel and implement the Soberano Metamodel
	//Model in the relational way.
	private void implementMetamodel() throws ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException, UnsupportedORMDatatypeException, SoberanoSQLException, SoberanoException {
		
		try {
			//parse the metamodel
			portableFormatORMModelFileParser.setFilePath(metamodelFilePath);
			portableFormatORMModelFileParser.readAndParse();
			metamodelParsingResults = portableFormatORMModelFileParser.getParsedModel();
			
			//implement schema
			generateRelationalModelUsingORMRBA(metamodelParsingResults);
		}
		
		catch (ComposedValueTypesUnsupportedException e) {
			throw e;
		}
		catch(ObjectifiedUnaryFactTypesUnsupportedException e) {
			throw e;
		}
		catch(MultipleInheritanceUnsupportedException e) {
			throw e;
		}
		catch(UnsupportedORMDatatypeException e) {
			throw e;
		}
		catch(SoberanoSQLException e) {
			throw e;
		}
		catch(Exception cause) {
			throw new SoberanoException(cause);
		}
	}

	@Override
	public void generateLogicalModel() throws ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException, UnsupportedORMDatatypeException, SoberanoSQLException, SoberanoException {
		
		super.generateLogicalModel();
		TransactionStatus transactionStatus = null ;
		try {
			// TO DO backup current database
			
			// TO DO save current fact model
			
			//get the conceptual model name, that determines the name of the target relational database
			databaseName = parsingResults.getName();
			
			//delete current database
			deleteDatabase();
			
			//create destination relational database
			createDatabase();
			
			//change datasource's database that is to change the url property of the datasource
			changeDatabaseUrl();
			
			((DataSourceTransactionManager) transactionManager).setDataSource(dataSource);
			
			//transactionally generate logical model
			transactionStatus = transactionManager.getTransaction(transactionDefinition);
			
			//implement business model (business that is being automated)
			generateRelationalModelUsingORMRBA(parsingResults);
			
			//add the elements of the Soberano metamodel
			implementMetamodel();
			
			//create relations on the DBMS
			createRelations();
			
			implementConstraints();
			
			populateMetamodelImplementation();
			
			transactionManager.commit(transactionStatus);
			
			// TO DO restore fact model under the new conceptual scheme
			
			//transactionally apply restrictions
			transactionStatus = transactionManager.getTransaction(transactionDefinition);
			
			applyConstraints();
			
			//this must be done after applying constraint. it's a requirement of foreign keys
			runPostGenerationBatch();
			
			transactionManager.commit(transactionStatus);
		}
		catch (ComposedValueTypesUnsupportedException e) {
			throw e;
		}
		catch(ObjectifiedUnaryFactTypesUnsupportedException e) {
			throw e;
		}
		catch(MultipleInheritanceUnsupportedException e) {
			throw e;
		}
		catch(UnsupportedORMDatatypeException e) {
			throw e;
		}
		catch(SoberanoSQLException e) {
			throw e;
		}
		catch(Exception cause) {
			transactionManager.rollback(transactionStatus);
			throw new SoberanoException(cause);
		}		
	}
	
	private void implementInternalUniquenessConstraint(UniquenessConstraint uc, Fact fact, HashMap<String, String> preferredIdentifiers) throws SoberanoSQLException, SoberanoException, SQLException {
		
		//if the constraint spans only one role
		if (uc.getRoleSequences().get(0).getRoles().size() == 1) {
			
			//if the fact is binary
			if (fact.getFactRoless().get(0).getRoles().size() == 2) {
				
				//get the id of the corole
				String coroleId = uc.getRoleSequences().get(0).getRoles().get(0).getRef().equals(
									fact.getFactRoless().get(0).getRoles().get(0).getId())?
											fact.getFactRoless().get(0).getRoles().get(1).getId():
											fact.getFactRoless().get(0).getRoles().get(0).getId();
											
				//if the corole is a preferred identifier
				if (preferredIdentifiers.containsKey(coroleId) || roleImplementations.get(coroleId).peerImplementation != null) {
					
					//the constraint was already implemented
					return;
				}
			}
		}
		
		ArrayList<String> constrainedColumnNames = new ArrayList<String>();
		String relationName = roleImplementations.get(uc.getRoleSequences().get(0).getRoles().get(0).getRef()).relationName;
		for (Role r : uc.getRoleSequences().get(0).getRoles()) {
			for (ORMRBAColumn c : roleImplementations.get(r.getRef()).columns) {
				constrainedColumnNames.add(c.columnName);
			}
		}
		String constraintName = "UC_" + uc.getName() + "_" + relationName;
		Query query = new Query(getUniqueConstraintCreationQueryString(relationName,
																	constraintName, 
																	constrainedColumnNames),
								false, 
								null, 
								dataSource, 
								true);
		query.executeQuery(true);
	}
	
	private void implementExternalUniquenessConstraint(UniquenessConstraint uc) {
		// TO DO External uniqueness constraints.
	}
	
	private void implementUniquenessConstraints() throws SoberanoSQLException, SoberanoException, SQLException {
		
		List<UniquenessConstraint> uniquenessConstraints = new ArrayList<UniquenessConstraint>();
		uniquenessConstraints.addAll(parsingResults.getConstraintss().get(0).getUniquenessConstraints());
		uniquenessConstraints.addAll(metamodelParsingResults.getConstraintss().get(0).getUniquenessConstraints());
		HashMap<String, String> preferredIdentifiers = new HashMap<String, String>();
		preferredIdentifiers.putAll((parsingResults.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints()));
		preferredIdentifiers.putAll((metamodelParsingResults.getConstraintss().get(0).getPreferredIdentifierUniquenessConstraints()));
		
		//for each uniqueness constraint
		for (UniquenessConstraint uc : uniquenessConstraints) {
			
			//if the constraint isn't a preferred identifier: they were already implemented
			if (!preferredIdentifiers.containsValue(uc.getId())) {
				
				//if the constraint is internal
				if (uc.getIsInternal() != null && uc.getIsInternal().equals("true")) {
					HashMap<String, Fact> factByRoleLocator = new HashMap<String, Fact>();
					factByRoleLocator.putAll(parsingResults.getFactss().get(0).getFactByRoleLocator());
					factByRoleLocator.putAll(metamodelParsingResults.getFactss().get(0).getFactByRoleLocator());
					Fact fact = factByRoleLocator.get(uc.getRoleSequences().get(0).getRoles().get(0).getRef());
					
					//if the fact isn't a subtype nor a unary fact type
					if (fact != null &&
						!parsingResults.getFactss().get(0).getUnaryFactTypeLocator().containsKey(fact.getId()) &&
						!metamodelParsingResults.getFactss().get(0).getUnaryFactTypeLocator().containsKey(fact.getId())) {
						implementInternalUniquenessConstraint(uc, fact, preferredIdentifiers);
					}
				}
				//if it is external
				else {
					implementExternalUniquenessConstraint(uc);
				}
			}
		}
	}
	
	private void implementMandatoryConstraint() {
		
		// TO DO Mandatory constraints.
	}
	
	//Implements Object-Role Modeling constraints, that means to generate queries that enforce them in
	//the relational database.
	private void implementConstraints() throws SoberanoSQLException, SoberanoException, SQLException {
		
		implementUniquenessConstraints();
		
		implementMandatoryConstraint();
		
		//TO DO
		
		//value comparison
		
		//frequency
		
		//external constraints
		
		//exclusive or
		
		//inclusive or
		
		//subset
		
		//ring
		
		//multiplicity
		
		//cardinality
		
		//Other constraints.
	}
	
	private void populateDomain() throws SQLException {
		
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("domain_name", parsingResults.getName());
		KeyHolder keyHolder = dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".Domain",
										new String[] {"This_has_Name"},
										new String[] {":domain_name"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("domain_name", Types.VARCHAR)},
										new String[] {"DomainHasDomainId"});
		DomainId = (int) keyHolder.getKeys().get("DomainHasDomainId");
	}
	
	private void insertMeaning(String meaningId, String meaningName) throws SQLException {
		
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("domain_id", DomainId);
		parameterMap.put("meaning_id", meaningId);
		parameterMap.put("meaning_name", meaningName);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".Meaning",
										new String[] {"This_belongs_to_Domain_with_DomainHasDomainId",
														"MeaningHasMeaningId",
														"This_has_Name"},
										new String[] {":domain_id",
														":meaning_id",
														":meaning_name"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("domain_id", Types.INTEGER),
															new SqlParameter("meaning_id", Types.CHAR, 37),
															new SqlParameter("meaning_name", Types.VARCHAR)},
										null);
	}
	
	private void insertConcept(String meaningId, String meaningName) throws SQLException {
		
		insertMeaning(meaningId, meaningName);
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("meaning_id", meaningId);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".Concept",
										new String[] {"MeaningHasMeaningId"},
										new String[] {":meaning_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37)},
										null);
	}
	
	private void insertNounConcept(String meaningId, String meaningName) throws SQLException {
		
		insertConcept(meaningId, meaningName);
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("meaning_id", meaningId);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".NounConcept",
										new String[] {"MeaningHasMeaningId"},
										new String[] {":meaning_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37)},
										null);
	}
	
	private void insertObjectType(String meaningId, String meaningName) throws SQLException {
		
		insertNounConcept(meaningId, meaningName);
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("meaning_id", meaningId);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".ObjectType",
										new String[] {"MeaningHasMeaningId"},
										new String[] {":meaning_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37)},
										null);
	}
	
	private void insertRole(String meaningId, String meaningName, String factTypeId, String playerId) throws SQLException {
		
		insertNounConcept(meaningId, meaningName);
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("meaning_id", meaningId);
		parameterMap.put("facttype_id", factTypeId);
		parameterMap.put("player_id", playerId);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".Role",
										new String[] {"MeaningHasMeaningId",
														"This_is_in_FactType_with_MeaningHasMeaningId",
														"This_ranges_over_ObjectType_with_MeaningHasMeaningId"},
										new String[] {":meaning_id",
														":facttype_id",
														":player_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37),
															new SqlParameter("facttype_id", Types.CHAR, 37),
															new SqlParameter("player_id", Types.CHAR, 37)},
										null);
		
		//if the implementation of the role was registered
		if (roleImplementations.containsKey(meaningId)) {
			
			//insert role implementation
			parameterMap.clear();
			parameterMap.put("meaning_id", meaningId);
			String relationName = roleImplementations.get(meaningId).relationName;
			parameterMap.put("relation_name", relationName.length() > 63?relationName.substring(63):relationName);
			dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".RoleImplementation",
											new String[] {"MeaningHasMeaningId",
														"This_is_in_Relation_with_RelationHasRelationName"},
											new String[] {":meaning_id",
														":relation_name"}),
											parameterMap,
											new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37),
																new SqlParameter("relation_name", Types.VARCHAR, 63)},
											null);
		
			//insert role implementation column
			for (ORMRBAColumn column : roleImplementations.get(meaningId).columns) {
				parameterMap.clear();
				String columnName = column.columnName;
				parameterMap.put("column_name", columnName.length() > 63?columnName.substring(63):columnName);
				parameterMap.put("meaning_id", meaningId);
				dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".RoleImplementationIsByColumnWithColumnName",
										new String[] {"ColumnName",
														"MeaningHasMeaningId"},
										new String[] {":column_name",
														":meaning_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37),
															new SqlParameter("column_name", Types.VARCHAR, 63)},
										null);
			}
		}
	}
	
	private void insertEntityType(String meaningId, String meaningName) throws SQLException {
		
		insertObjectType(meaningId, meaningName);
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("meaning_id", meaningId);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".EntityType",
										new String[] {"MeaningHasMeaningId"},
										new String[] {":meaning_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37)},
										null);
	}
	
	private void insertValueType(String meaningId, String meaningName) throws SQLException {
		
		insertObjectType(meaningId, meaningName);
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("meaning_id", meaningId);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".ValueType",
										new String[] {"MeaningHasMeaningId"},
										new String[] {":meaning_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37)},
										null);
	}
	
	private void insertFactType(String meaningId, String meaningName) throws SQLException {
		
		insertConcept(meaningId, meaningName);
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("meaning_id", meaningId);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".FactType",
										new String[] {"MeaningHasMeaningId"},
										new String[] {":meaning_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37)},
										null);
		
		//if it is an objectified fact type
		if (parsingResults.getFactss().get(0).getObjectifiedFactTypeLocator().containsKey(meaningId)) {
			
			//get the id of the entity type that objectifies the fact type
			String objectifiedTypeId = parsingResults.getObjectss().get(0).getObjectifiedTypeLocator().get(meaningId).getId();
			
			//update the reference to the fact type from the entity type that objectifies it
			parameterMap.clear();
			parameterMap.put("meaning_id", objectifiedTypeId);
			parameterMap.put("objectified_facttype_id", meaningId);
			dao.update(getUpdatingQuery(metamodelParsingResults.getName() + ".EntityType", 
										new String[] {"This_objectifies_FactType_with_MeaningHasMeaningId"},
										new String[] {":objectified_facttype_id"},
										new String[] {"MeaningHasMeaningId"},
										new String[] {":meaning_id"}), 
										parameterMap, 
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37),
															new SqlParameter("objectified_facttype_id", Types.CHAR, 37)});
		}
	}
	
	private void insertSubtypingFactType(String meaningId, String meaningName, String supertypeRolePlayerId, String subtypeRolePlayerId) throws SQLException {
		
		//register the fact type
		insertFactType(meaningId, meaningName);
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("meaning_id", meaningId);
		parameterMap.put("supertype_roleplayer_id", supertypeRolePlayerId);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".SubtypingFactType",
										new String[] {"MeaningHasMeaningId",
													"EntityType_with_MeaningHasMeaningId_plays_supertype_role_in_Thi"},
										new String[] {":meaning_id",
													":supertype_roleplayer_id"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37),
															new SqlParameter("supertype_roleplayer_id", Types.CHAR, 37)},
										null);
		
		//update the entity type that plays the subtype role
		parameterMap.clear();
		parameterMap.put("meaning_id", subtypeRolePlayerId);
		parameterMap.put("subtyping_facttype_id", meaningId);
		dao.update(getUpdatingQuery(metamodelParsingResults.getName() + ".EntityType", 
									new String[] {"This_plays_subtype_role_in_SubtypingFactType_with_MeaningHasMea"},
									new String[] {":subtyping_facttype_id"},
									new String[] {"MeaningHasMeaningId"},
									new String[] {":meaning_id"}), 
									parameterMap, 
									new SqlParameter[] {new SqlParameter("meaning_id", Types.CHAR, 37),
														new SqlParameter("subtyping_facttype_id", Types.CHAR, 37)});
	}
	
	private void populateEntityTypes() throws SQLException {
		
		for (EntityType entityType : parsingResults.getObjectss().get(0).getEntityTypes()) {
			insertEntityType(entityType.getId(), entityType.getName());
		}
		for (EntityType entityType : parsingResults.getObjectss().get(0).getObjectifiedTypes()) {
			insertEntityType(entityType.getId(), entityType.getName());
		}
	}
	
	private void populateValueTypes() throws SQLException {
		
		for (ValueType valueType : parsingResults.getObjectss().get(0).getValueTypes()) {
			insertValueType(valueType.getId(), valueType.getName());
		}
	}
	
	private void populateFactTypes() throws SQLException {
		
		for (Fact fact : parsingResults.getFactss().get(0).getFacts()) {
			insertFactType(fact.getId(), fact.get_Name());
		}
		for (SubtypeFact stfact : parsingResults.getFactss().get(0).getSubtypeFacts()) {
			insertSubtypingFactType(stfact.getId(), 
									stfact.get_Name(), 
									stfact.getFactRoless().get(0).getSupertypeMetaRoles().get(0).getRolePlayers().get(0).getRef(),
									stfact.getFactRoless().get(0).getSubtypeMetaRoles().get(0).getRolePlayers().get(0).getRef());
		}
	}
	
	private void insertRelation(String relationName) throws SQLException {
		
		DaoBase dao = new DaoBase(dataSource);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("relation_name", relationName.length() > 63?relationName.substring(63):relationName);
		dao.insert(getInsertionQuery(metamodelParsingResults.getName() + ".Relation",
										new String[] {"RelationHasRelationName"},
										new String[] {":relation_name"}),
										parameterMap,
										new SqlParameter[] {new SqlParameter("relation_name", Types.VARCHAR, 63)},
										null);
	}
	
	private void populateRelations() throws SQLException {
		
		for (Relation relation : relations) {
			insertRelation(relation.name);
		}
	}
	
	private void populateRolesAndRoleImplementations() throws SQLException {
		
		Integer roleCount = 1;
		for (Fact fact : parsingResults.getFactss().get(0).getFacts()) {
			for (Role role : fact.getFactRoless().get(0).getRoles()) {
				insertRole(role.getId(), role.getName().isEmpty()?"role_" + roleCount.toString():role.getName(), fact.getId(), role.getRolePlayers().get(0).getRef());
				roleCount++;
			}
		}
	}
	
	private void populateMetamodelImplementation() throws SoberanoSQLException, SQLException {
		
		populateDomain();
		
		populateValueTypes();
		
		populateEntityTypes();
		
		populateFactTypes();
		
		populateRelations();
		
		populateRolesAndRoleImplementations();
	}
	
	private void applyConstraints() throws SoberanoSQLException, SoberanoException {
		
		try {
			for (int i = 0; i < constraintQueries.size(); i++) {
				Query query = new Query(constraintQueries.get(i), false, null, dataSource, true);
				query.executeQuery(true);
			}
		}
		catch(Exception cause) {
			throw new SoberanoException(cause);
		}
	}
	
	//Updates the URL that is used by the datasource for accessing a relational database with the same name as the Object-Role
	//Modeling conceptual whose relational logical model is being generated.
	private void changeDatabaseUrl() {
		
		//Creating a new data source is important because just changing the url property doesn't target another database.		
		BasicDataSource auxDataSource = new BasicDataSource();
		auxDataSource.setDriverClassName(dataSource.getDriverClassName());
		auxDataSource.setUrl(getDatabaseURL(DBMSUrl, databaseName));
		auxDataSource.setUsername(dataSource.getUsername());
		auxDataSource.setPassword(dataSource.getPassword());
		auxDataSource.setTestWhileIdle(auxDataSource.getTestWhileIdle());
		auxDataSource.setTestOnBorrow(auxDataSource.getTestOnBorrow());
		auxDataSource.setTestOnReturn(auxDataSource.getTestOnReturn());
		auxDataSource.setValidationQuery(dataSource.getValidationQuery());
		auxDataSource.setValidationQueryTimeout(dataSource.getValidationQueryTimeout());
		auxDataSource.setTimeBetweenEvictionRunsMillis(dataSource.getTimeBetweenEvictionRunsMillis());
		auxDataSource.setMaxTotal(dataSource.getMaxTotal());
		auxDataSource.setMinIdle(dataSource.getMinIdle());
		auxDataSource.setMaxWaitMillis(dataSource.getMaxWaitMillis());
		auxDataSource.setInitialSize(dataSource.getInitialSize());
		auxDataSource.setMinEvictableIdleTimeMillis(dataSource.getMinEvictableIdleTimeMillis());
		dataSource = auxDataSource;
	}

	//Delete a relational database accessible by means of the datasource, that holds the same name of the Object-Role
	//Modeling conceptual whose relational logical model is being generated.
	private void deleteDatabase() throws SoberanoSQLException, SoberanoException {
		
		try {
			Query query = new Query(getDatabaseExistenceTestQuery(databaseName), false, null, dataSource, false);
			CachedRowSet rows = query.executeQuery(false);
			if (rows.size() != 0) {
				query = new Query(getDatabaseDropQuery(databaseName), false, null, dataSource, true);
				query.executeQuery(false);
			}			
		}
		catch(Exception cause) {
			throw new SoberanoException(cause);
		}
	}

	//Returns the query that must be used by a specialized relational logical model generator to create the target relational
	//database.
	abstract public String getDatabaseCreationQuery(String databaseName);
	
	//Returns the query that must be used by a specialized relational logical model generator to drop the target relational
	//database.
	abstract public String getDatabaseDropQuery(String databaseName);
	
	//Returns the query that must be used by a specialized relational logical model generator to test the existence of the target
	//relational database.
	abstract public String getDatabaseExistenceTestQuery(String databaseName);
	
	//Get the database URL from the URL of the DBMS used initially by the data source and the name of the database.
	abstract public String getDatabaseURL(String DBMSUrl, String databaseName);
	
	//Returns the query for creating a relation  with a set of columns.
	//The new relation doesn't have constraints of any kinds.
	abstract public String getRelationCreationQuery(String relationName, ArrayList<String> columnNames, ArrayList<String> columnTypes, ArrayList<String> columnLengths, ArrayList<String> columnScales, ArrayList<Boolean> automaticSerialFlags, ArrayList<ColumnValueConstraint> columnValueConstraints, ArrayList<String> constraintQueries) throws UnsupportedORMDatatypeException;
	
	//Get the query string for creating a primary key constraint in a relation with the name relationName. The primary key will be composed by the
	//columns with the names passed in primaryKeyColumnsNames.
	abstract public String getPrimaryKeyCreationQueryString(String relationName, ArrayList<String> primaryKeyColumnsNames);
	
	//Get the query string for creating a foreign key constraint in a relation with the name locaTableName. The foreign key will target a relation of
	//name foreignTableName. The names of the local and foreign columns are passed in localColumnNames and foreignColumnNames respectively.
	abstract public String getForeignKeyCreationQueryString(String foreignKeyName, String locaTableName, String foreignTableName, ArrayList<String> localColumnNames, ArrayList<String> foreignColumnNames);
	
	//Get the query string for creating in a relation a unique constraint with a set of columns whose names are passed in uniqueConstraintColumnsNames.
	abstract public String getUniqueConstraintCreationQueryString(String relationName, String constraintName, ArrayList<String> uniqueConstraintColumnsNames);
	
	//Returns the query that must be used by a specialized relational logical model generator to create a schema.
	abstract public String getSchemaCreationQuery(String schemaName);
	
	//Returns the parameterized query for inserting rows in a relation. The indexes of the parameter names array must correspond
	//to the indexes of the column names array.
	abstract public String getInsertionQuery(String relationName, String[] columnNames, String[] parameterNames);
	
	//Returns the parameterized query for updating rows in a relation, meeting a criteria giving by a conjunction of
	//equalities between columns of the same relation and scalar values. columnToUpdateNames is for passing the names of
	//columns to be updated. In newValueParameterNames are passed the names of the parameters for giving new values to
	//the updated columns. criteriaColumnNames are the names of the columns of the relation that participate in the criteria.
	//criteriaParameterNames contains the names of the parameters for evaluating the columns (equality tests) in the criteria.
	abstract public String getUpdatingQuery(String relationName, String[] columnToUpdateNames, String[] newValueParameterNames, String[] criteriaColumnNames, String[] criteriaParameterNames);
	
	protected void runPostGenerationBatch() throws Exception {
		
		try {
			if (getPostGenerationBatch() != null && 
				getPostGenerationBatch().getBatch() != null) {
				for (int i = 0; i < getPostGenerationBatch().getBatch().length; i++) {				
					Query query = new Query(getPostGenerationBatch().getBatch()[i], false, null, dataSource, true);
					query.executeQuery(true);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}				
	}
}
