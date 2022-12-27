package co.syscoop.soberano.rules.enforcer;

import co.syscoop.soberano.exception.ComposedValueTypesUnsupportedException;
import co.syscoop.soberano.exception.MultipleInheritanceUnsupportedException;
import co.syscoop.soberano.exception.ObjectifiedUnaryFactTypesUnsupportedException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.exception.SoberanoSQLException;
import co.syscoop.soberano.exception.UnsupportedORMDatatypeException;
import co.syscoop.soberano.rules.enforcer.generator.ILogicalModelGenerator;
import co.syscoop.soberano.rules.enforcer.portable_file_parser.IPortableFormatORMModelFileParser;

/**
 * @author Josue Portal
 *
 * IORMConceptualModel encapsulates a set of methods for processing models in Object-Role Modeling notation (ORM). It assumes the models are stored in
 * files of some portable format, like XML.
 */
public interface IORMConceptualModel {

	//Sets the path, in the local system, of the file in portable format that contains the ORM model.
	public void setPortableModelFilePath(String portableModelFilePath);
	
	//Gets the path, in the local system, of the file in portable format that contains the ORM model.
	public String getPortableModelFilePath();
	
	//Reads and parses the file in portable format with the ORM model.
	void readAndParse() throws SoberanoException;
	
	//Generates and deploys a logical model (possibly, relational database) from the ORM model. 
	void generateLogicalModel() throws ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException, UnsupportedORMDatatypeException, SoberanoSQLException, SoberanoException;
	
	//Sets the parser to be used for reading and parsing the file in portable format that contains the ORM model.
	public void setPFORMMFileParser(IPortableFormatORMModelFileParser PFORMMFileParser);
	
	//Gets the parser to be used for reading and parsing the file in portable format that contains the ORM model.
	public IPortableFormatORMModelFileParser getPFORMMFileParser();
	
	//Sets the generator to be used for generating and deploying a logical model from the ORM model.
	public void setLMGenerator(ILogicalModelGenerator LMGenerator);
	
	//Gets the generator to be used for generating and deploying a logical model from the ORM model.
	public ILogicalModelGenerator getLMGenerator();
}