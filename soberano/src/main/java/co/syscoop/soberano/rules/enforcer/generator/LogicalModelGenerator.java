/**
 * 
 */
package co.syscoop.soberano.rules.enforcer.generator;

import co.syscoop.soberano.exception.ComposedValueTypesUnsupportedException;
import co.syscoop.soberano.exception.MultipleInheritanceUnsupportedException;
import co.syscoop.soberano.exception.ObjectifiedUnaryFactTypesUnsupportedException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.exception.SoberanoSQLException;
import co.syscoop.soberano.exception.UnsupportedORMDatatypeException;
import co.syscoop.soberano.rules.enforcer.metamodel.ORMModel;
import co.syscoop.soberano.rules.enforcer.portable_file_parser.IPortableFormatORMModelFileParser;

/**
 * @author Josue Portal
 *
 * LogicalModelGenerator encapsulates properties and methods that must characterize the basic structure and behavior of 
 * any logical model generators of Soberano. Every logical model generator class must inherit from LogicalModelGenerator
 * and implement the abstract methods of it. The descendant of LogicalModelGenerator consume the result of parsing the portable 
 * format Object-Role Modeling model through a reference to some class that implements the interface IPortableFormatORMModelFileParser.
 */
public abstract class LogicalModelGenerator implements ILogicalModelGenerator {
	
	//Path to the Object-Role Modeling Soberano metamodel in portable format.
	protected String metamodelFilePath = "";
	
	//A place to store the results of parsing the Object-Role Modeling conceptual model of the business being automated.
	protected ORMModel parsingResults = null;
	
	//A place to store the results of parsing the Object-Role Modeling of the Soberano metamodel in portable format.
	protected ORMModel metamodelParsingResults = null;
	
	//A reference to a class that implements IPortableFormatORMModelFileParser interface. This interface's methods deliver information
	//related to the Object-Role Modeling model from which the logical model will be generated. This information complies with the
	//Soberano conceptual metamodel.
	protected IPortableFormatORMModelFileParser portableFormatORMModelFileParser = null;
	
	protected LogicalQueriesBatch postGenerationBatch = null;
	
	public void setMetamodelFilePath(String metamodelFileName) {
		this.metamodelFilePath = this.getClass().getClassLoader().getResource(metamodelFileName).getPath();
	}
	
	//Sets a reference to the class that implements IPortableFormatORMModelFileParser for the ORM model that is being processed.
	@Override
	public void setPortableFormatORMModelFileParser(
			IPortableFormatORMModelFileParser portableFormatORMModelFileParser) {
		this.portableFormatORMModelFileParser = portableFormatORMModelFileParser;
	}

	//Generates and deploys the logical model derived from the ORM model. This method must be invoked after parsing a model in
	//portable format. Specifically, LogicalModelGenerator.generateLogicalModel() must be invoked by the overrode versions of
	//this method.
	@Override
	public void generateLogicalModel() throws ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException, UnsupportedORMDatatypeException, SoberanoSQLException, SoberanoException {
		parsingResults = portableFormatORMModelFileParser.getParsedModel();
	}
	
	//Called after generating the logical model. This is a batch of queries on the generated logical model, for populating it, 
	//creating indexes and other optimizations, creating stored procedures and triggers for enforcing rules, etc. The batch is
	//useful not only for populating the Soberano metamodel but also for pre configuring cases of Soberano (Soberano instances).
	abstract protected void runPostGenerationBatch() throws Exception;

	public LogicalQueriesBatch getPostGenerationBatch() {
		return postGenerationBatch;
	}

	public void setPostGenerationBatch(LogicalQueriesBatch postGenerationBatch) {
		this.postGenerationBatch = postGenerationBatch;
	}
}
