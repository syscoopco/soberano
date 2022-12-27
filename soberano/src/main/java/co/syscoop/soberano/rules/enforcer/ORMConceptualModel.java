/**
 * @author Josue Portal 
 */
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
 *
 * @author Josue Portal
 *
 * ORMConceptualModel represents a conceptual model in Object-Role Modeling (ORM) notation. It encapsulates the implementation of a set 
 * of methods for processing ORM models. The input for processing is a path to a local system file, which contains the model in
 * some portable format, like XML. It is assumed that model is a valid model created by means of some ORM model editor, like NORMA.
 * 
 * The intending processing consists of two stages. First, to read and parse the portable file. Second, to generate and deploy
 * an equivalent logical model, like a relational database.
 * 
 * During the generation stage, it is also filled a logical implementation (possibly, under a relational DBMS) of the Soberano 
 * conceptual metamodel. This is for, subsequently, managing the configuration of the conceptual and generated logical models. 
 */
public class ORMConceptualModel implements IORMConceptualModel {

	//Points to a portable format ORM model file parser.
	private IPortableFormatORMModelFileParser PFORMMFileParser = null;
	
	//Points to a logical model generator.
	private ILogicalModelGenerator LMGenerator = null;
	
	//Path to the local system file which contains the ORM model in portable format.
	private String portableModelFilePath = null;
	
	//During the construction are settled the parser and the logical model generator.
	ORMConceptualModel(IPortableFormatORMModelFileParser PFORMMFileParser, 
						ILogicalModelGenerator LMGenerator,
						String portableModelFileName) {
		this.setPFORMMFileParser(PFORMMFileParser);
		this.setLMGenerator(LMGenerator);
		this.setPortableModelFilePath(this.getClass().getClassLoader().getResource(portableModelFileName).getPath());
	}

	//Reads and parses the portable format file with the ORM model.
	public void readAndParse() throws SoberanoException {
	
		try {
			PFORMMFileParser.setFilePath(portableModelFilePath);
			PFORMMFileParser.readAndParse();
		}
		catch(Exception cause) {
			throw new SoberanoException(cause);
		}
	}
		
	//Generates and deploys the logical model from the results of parsing the portable format file with the ORM model.
	public void generateLogicalModel() throws ComposedValueTypesUnsupportedException, ObjectifiedUnaryFactTypesUnsupportedException, MultipleInheritanceUnsupportedException, UnsupportedORMDatatypeException, SoberanoSQLException, SoberanoException {
		
		try {
			LMGenerator.setPortableFormatORMModelFileParser(PFORMMFileParser);
			LMGenerator.generateLogicalModel();
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
		catch(SoberanoException e) {
			throw e;	
		}
	}
	
	public String getPortableModelFilePath() {
		return portableModelFilePath;
	}

	public void setPortableModelFilePath(String portableModelFilePath) {
		this.portableModelFilePath = portableModelFilePath;
	}

	public ILogicalModelGenerator getLMGenerator() {
		return LMGenerator;
	}

	public void setLMGenerator(ILogicalModelGenerator LMGenerator) {
		this.LMGenerator = LMGenerator;
	}

	public IPortableFormatORMModelFileParser getPFORMMFileParser() {
		return PFORMMFileParser;
	}

	public void setPFORMMFileParser(IPortableFormatORMModelFileParser PFORMMFileParser) {
		this.PFORMMFileParser = PFORMMFileParser;
	}
}
