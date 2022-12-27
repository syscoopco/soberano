/**
 * 
 */
package co.syscoop.soberano.rules.enforcer.portable_file_parser;

import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.rules.enforcer.metamodel.ORMModel;

/**
 * @author Josue Portal
 *
 * Every portable format Object-Role Modeling model file parser must implement IPortableFormatORMModelFileParser.
 */
public interface IPortableFormatORMModelFileParser {

	//Sets the local system path of the portable format file that contains the Object-Role Modeling model.
	public abstract void setFilePath(String portableModelFilePath);
	
	//Reads and parses the portable format file that contains the Object-Role Modeling model.
	public abstract void readAndParse() throws SoberanoException;
	
	//Get the results of parsing in a format compatible with SYSCOOP conceptual metamodel.
	public abstract ORMModel getParsedModel();
}
