package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import co.syscoop.soberano.beans.SoberanoDatasource;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;

public class TestUtilityCode {

	public static XmlWebApplicationContext springContext() {
		
		String[] paths = new String[] {"classpath:applicationContext.xml"};
		XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
		applicationContext.setConfigLocations(paths);
		applicationContext.setServletContext(new MockServletContext(""));
		applicationContext.refresh();
		return applicationContext;
	}
	
	public static SoberanoDatasource soberanoDatasource(XmlWebApplicationContext springContext) {
		
		return (SoberanoDatasource) springContext.getBean("soberanoDatasource");
	}
	
	public static void testSearchCombobox(String pageURIStr, Integer expectedAccessibleObjectCount, Integer userIdSuffix, Integer objectBaseId) {
		
		DesktopAgent desktop = Zats.newClient().connect(pageURIStr);
		ComponentAgent cmbIntelliSearch = desktop.query("combobox");
		assertEquals(expectedAccessibleObjectCount, 
					cmbIntelliSearch.as(Combobox.class).getModel().getSize(), 
					"User" 
						+ userIdSuffix + "@soberano.syscoop.co" + " must have access to " 
						+ expectedAccessibleObjectCount 
						+ " objects. It sees " 
						+ cmbIntelliSearch.as(Combobox.class).getModel().getSize() 
						+ " objects in search combobox.");
		for (Integer i = 1; i <= cmbIntelliSearch.as(Combobox.class).getModel().getSize(); i++) {
			DomainObject doo = ((DomainObject) cmbIntelliSearch.as(Combobox.class).getModel().getElementAt(i - 1));
			assertEquals("user" + i + "fn user" + i +"ln : user" + i + "@soberano.syscoop.co", doo.getName(), "When populating search combobox, user" 
																					+ userIdSuffix + "@soberano.syscoop.co"
																					+ " retrieves wrong name for object with name " 
																					+ doo.getName());
			assertEquals(objectBaseId + i, doo.getId(), "When populating search combobox, user" 
													+ userIdSuffix + "@soberano.syscoop.co"
													+ " retrieves wrong id for object with id " 
													+ doo.getId());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void testShowingAllTree(String pageURIStr, Integer expectedAccessibleObjectCount, Integer userIdSuffix, Integer objectBaseId) {
		
		DesktopAgent desktop = Zats.newClient().connect(pageURIStr);
		ComponentAgent cmbIntelliSearch = desktop.query("combobox");		
		Tree treeObjects = (Tree) cmbIntelliSearch.as(Combobox.class).query("#wndShowingAll").query("#treeObjects");		
		assertEquals(expectedAccessibleObjectCount, 
					treeObjects.getTreechildren().getItemCount(), 
					"User" 
						+ userIdSuffix + "@soberano.syscoop.co" + " must have access to " 
						+ expectedAccessibleObjectCount 
						+ " objects. It sees " 
						+ treeObjects.getTreechildren().getItemCount() 
						+ " objects in showing-all tree.");
		DefaultTreeModel treeModel = (DefaultTreeModel) treeObjects.getModel();
		TreeNode rootNode = (TreeNode) treeModel.getRoot();	
		for (Integer i = 1; i <= rootNode.getChildren().size(); i++) {
			Object objectNode = rootNode.getChildren().get(i - 1);
			DomainObject doo = (DomainObject) ((NodeData) (((TreeNode) objectNode).getData())).getValue();
			assertEquals("user" + i + "fn user" + i +"ln : user" + i + "@soberano.syscoop.co", doo.getName(), "When populating showing-all tree, user" 
																				+ userIdSuffix + "@soberano.syscoop.co"
																				+ " retrieves wrong name for object with name " 
																				+ doo.getName());
			assertEquals(objectBaseId + i, doo.getId(), "When populating showing-all tree, user" 
																				+ userIdSuffix + "@soberano.syscoop.co"
																				+ " retrieves wrong id for object with id " 
																				+ doo.getId());
		}
	}
}
