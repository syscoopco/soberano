package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.util.SpringUtility;

@Order(4)

@Disabled

class O4_WarehouseTest_search_showingAll {
	
	final private Integer warehouseCount = 10; //number of warehouses in this testing point
	final private Integer baseId = 1000; //the id of the first added warehouse is baseId + 1. the last one's id is basedId + warehouseCount

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		//Zats.init("./src/main/webapp");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
		Zats.cleanup();
		//Zats.end();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	private void testForAllowedUser(Integer userSuffix) {
		
		String qualifiedNamePattern = "warehouse#suffix# : w#suffix#";
		
		//test for a user
		String userName = "user" + userSuffix + "@soberano.syscoop.co";
		SpringUtility.setLoggedUserForTesting(userName);
		
		//showing all in search combobox
		TestUtilityCode.testSearchCombobox("/warehouses.zul", warehouseCount, userSuffix, baseId, qualifiedNamePattern);
		
		//showing all in tree
		TestUtilityCode.testShowingAllTree("/warehouses.zul", warehouseCount, userSuffix, baseId, qualifiedNamePattern);
	};
	
	private void testForDisallowedUser(Integer userSuffix) {
		
		String userName = "user" + userSuffix + "@soberano.syscoop.co";
		SpringUtility.setLoggedUserForTesting(userName);
		DesktopAgent desktop = Zats.newClient().connect("/warehouses.zul");
		ComponentAgent cmbIntelliSearch = desktop.query("center").query("combobox");
		Tree treeObjects = (Tree) cmbIntelliSearch.as(Combobox.class).query("#wndShowingAll").query("#treeObjects");		
		assertEquals(0,
					cmbIntelliSearch.as(Combobox.class).getModel().getSize() + treeObjects.getTreechildren().getItemCount(), 
					userName + " must not have access to any warehouses. However, it sees " 
							+ cmbIntelliSearch.as(Combobox.class).getModel().getSize() 
							+ " warehouses in search combobox, and " 
							+ treeObjects.getTreechildren().getItemCount()
							+ " in showing-all tree.");
	};
	
	@Test
	final void testUser1() {
		
		testForAllowedUser(1);
	}
	
	@Test
	final void testUser2() {
		
		testForAllowedUser(2);
	}
	
	@Test
	final void testUser3() {
		
		testForAllowedUser(3);
	}
	
	@Test
	final void testUser4() {
		
		testForAllowedUser(4);
	}
	
	@Test
	final void testUser5() {
		
		testForAllowedUser(5);
	}
	
	@Test
	final void testUser6() {
		
		testForAllowedUser(6);
	}
	
	@Test
	final void testUser7() {
		
		testForAllowedUser(7);
	}
	
	@Test
	final void testUser8() {
		
		testForAllowedUser(8);
	}
	
	@Test
	final void testUser9() {
		
		testForAllowedUser(9);
	}
	
	@Test
	final void testUser10() {
		
		testForAllowedUser(10);
	}
	
	@Test
	final void testUser11() {
		
		testForAllowedUser(11);
	}
	
	@Test
	final void testUser12() {
		
		testForAllowedUser(12);
	}
	
	@Test
	final void testUser13() {
		
		testForAllowedUser(13);
	}
	
	@Test
	final void testUser14() {
		
		testForAllowedUser(14);
	}
	
	@Test
	final void testUser15() {
		
		testForAllowedUser(15);
	}
	
	@Test
	final void testUser16() {
		
		testForAllowedUser(16);
	}
	
	@Test
	final void testUser17() {
		
		testForAllowedUser(17);
	}
	
	@Test
	final void testUser18() {
		
		testForDisallowedUser(18);
	}
	
	@Test
	final void testUser19() {
		
		testForAllowedUser(19);
	}
	
	@Test
	final void testUser20() {
		
		testForDisallowedUser(20);
	}
	
	@Test
	final void testUser21() {
		
		testForDisallowedUser(21);
	}
}
