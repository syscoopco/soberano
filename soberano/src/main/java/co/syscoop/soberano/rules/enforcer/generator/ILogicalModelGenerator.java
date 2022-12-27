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
import co.syscoop.soberano.rules.enforcer.portable_file_parser.IPortableFormatORMModelFileParser;

/**
 * @author Josue Portal
 *
 * Every logical model generator must implement the interface ILogicalModelGenerator.
 */
public interface ILogicalModelGenerator {

	//Generates and deploys the logical model derived from the ORM model.
	abstract public void generateLogicalModel() throws ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException, UnsupportedORMDatatypeException, SoberanoSQLException, SoberanoException;
	
	//Sets a reference to the class that implements IPortableFormatORMModelFileParser for the ORM model that is being processed.
	abstract public void setPortableFormatORMModelFileParser(IPortableFormatORMModelFileParser portableFormatORMModelFileParser);
}
