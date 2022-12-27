/**
 * 
 */
package co.syscoop.soberano.rules.enforcer.portable_file_parser;

import co.syscoop.soberano.exception.*;
import co.syscoop.soberano.rules.enforcer.metamodel.ORMModel;

/**
 * @author Josue Portal
 *
 * PortableFormatORMModelFileParser encapsulates all the properties and methods that must characterize the basic structure and behavior of 
 * any parsers of a portable format file containing an Object-Role Modeling model to process by Soberano. Every parser must inherit 
 * from PortableFormatORMModelFileParser.
 */
public abstract class PortableFormatORMModelFileParser implements IPortableFormatORMModelFileParser {

	//Path in the local system of the file containing the Object-Role Modeling model in a portable format.
	protected String filePath = null;
	
	//Sets the local system path of the portable format file that contains the Object-Role Modeling model.
	@Override
	public void setFilePath(String portableModelFilePath) {
		
		filePath = portableModelFilePath;
	}
	
	//Reads and parses the portable format file that contains the Object-Role Modeling model.
	@Override
	public abstract void readAndParse() throws SoberanoException;
	
	@Override
	public abstract ORMModel getParsedModel();
}
