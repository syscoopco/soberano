package co.syscoop.soberano.rules.enforcer.portable_file_parsers.norma;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;
import co.syscoop.soberano.exception.SoberanoException;

import co.syscoop.soberano.rules.enforcer.metamodel.*;
import co.syscoop.soberano.rules.enforcer.portable_file_parser.PortableFormatORMModelFileParser;

/**
 * @author Josue Portal
 *
 * NORMAFileParser has a field to store the results of parsing: all the elements of an XML file created by the Object-Role
 * Modeling (ORM) NORMA tool. It also encapsulates operations to locate and parse that file, and to provide the modeling 
 * information according to the Soberano conceptual metamodel. NORMAFileParser assumes, both, the ORM model and
 * the XML file are valid.
 */
public class NORMAFileParser extends PortableFormatORMModelFileParser {

	//The result (XML elements) of an XML file created with NORMA tool.
	private ORMModel parsingResult = null;
	
	@Override
	public void readAndParse() throws SoberanoException {

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();
	        XMLReader parser = saxParser.getXMLReader();
	        NORMAModelBuilder modelBuilder = new NORMAModelBuilder();
	        parser.setContentHandler(modelBuilder);
	        parser.parse(filePath);
	        parsingResult = (ORMModel) modelBuilder.getModel();
	        
	        //update role player locators for properly processing the reference schemes 
	        //of object types that inherit them from their ancestors
	        parsingResult.updateSupertypeMetaroleLocator();
	        parsingResult.updateFunctionalFactLocators();
	        parsingResult.updateObjectifiedFactTypeLocator();
		} catch (Exception cause) {
			throw new SoberanoException(cause);
		}
	}

	@Override
	public ORMModel getParsedModel() {
		return parsingResult;
	}
}
