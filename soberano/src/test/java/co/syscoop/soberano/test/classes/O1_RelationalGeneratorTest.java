package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import co.syscoop.soberano.rules.enforcer.IORMConceptualModel;
import co.syscoop.soberano.rules.enforcer.generator.RelationalGenerator;
import co.syscoop.soberano.test.generation.LogicalQueriesForSoberanoInstance;
import co.syscoop.soberano.test.helper.TestClass;
import co.syscoop.soberano.test.helper.TestUtilityCode;

/**
* @author Josue Portal
*
* This class is used to logically implement (to generate and populate a relational db) the Soberano metamodel and
* a generic instance of this metamodel. The db's name is "soberano". It has two schemas: "metamodel" and "soberano".
*/
@Order(1)

//TODO: enable test
//@Disabled

class O1_RelationalGeneratorTest extends TestClass {
	
	private static IORMConceptualModel TestCaseORMConceptualModelInstance = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		try{
			applicationContext = TestUtilityCode.springContext();
			soberanoDatasource = TestUtilityCode.soberanoDatasource(applicationContext);
			TestCaseORMConceptualModelInstance = (IORMConceptualModel) applicationContext.getBean("ORMConceptualModel");
			((RelationalGenerator) TestCaseORMConceptualModelInstance.getLMGenerator()).setPostGenerationBatch(new LogicalQueriesForSoberanoInstance());
		}
		catch(Exception e){
			e.printStackTrace();
			fail("Unpexpected exception.");
		}
	}
	
	@Test
	final void testReadAndParse() {
		
		try {
			TestCaseORMConceptualModelInstance.readAndParse();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unpexpected exception.");
		}
	}
	
	@Test
	final void testGenerateLogicalModel() {
		
		try {
			TestCaseORMConceptualModelInstance.generateLogicalModel();
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Unpexpected exception.");
		}
	}
}
