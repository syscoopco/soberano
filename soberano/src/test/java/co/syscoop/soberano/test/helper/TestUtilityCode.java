package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.beans.SoberanoDatasource;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ZKUtilitity;

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
	
	private static Map<Integer, DomainObject> convertListToMap(ListModel<Object> list) {
	    Map<Integer, DomainObject> map = new HashMap<>();
	    for (int i = 0; i < list.getSize(); i++) {
	    	DomainObject doo = (DomainObject) list.getElementAt(i);
	        map.put(doo.getId(), doo);
	    }
	    return map;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<Integer, DomainObject> convertListToMap(List<Object> treeNodes) {
	    Map<Integer, DomainObject> map = new HashMap<>();
	    for (Object obj : treeNodes) {
	    	DomainObject doo = (DomainObject) ((NodeData) (((TreeNode) obj).getData())).getValue();
	        map.put(((DomainObject) doo).getId(), doo);
	    }
	    return map;
	}
	
	public static void testSearchCombobox(String pageURIStr, Integer expectedAccessibleObjectCount, Integer userIdSuffix, Integer objectBaseId, String qualifiedNamePattern) {
		
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
		
		Map<Integer, DomainObject> comboModelMap = convertListToMap(cmbIntelliSearch.as(Combobox.class).getModel());		
		for (Integer i = 1; i <= cmbIntelliSearch.as(Combobox.class).getModel().getSize(); i++) {
			DomainObject doo = comboModelMap.get(objectBaseId + i);
			assertEquals(qualifiedNamePattern.replace("#suffix#", i.toString()), doo.getName(), "When populating search combobox, user" 
																	+ userIdSuffix + "@soberano.syscoop.co"
																	+ " retrieves wrong name for object with name " 
																	+ doo.getName());
			assertEquals(objectBaseId + i, doo.getId(), "When populating search combobox, user" 
													+ userIdSuffix + "@soberano.syscoop.co"
													+ " retrieves wrong id for object with id " 
													+ doo.getId());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void testShowingAllTree(String pageURIStr, Integer expectedAccessibleObjectCount, Integer userIdSuffix, Integer objectBaseId, String qualifiedNamePattern) {
		
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
		Map<Integer, DomainObject> treeNodesModelMap = convertListToMap(rootNode.getChildren());
		for (Integer i = 1; i <= rootNode.getChildren().size(); i++) {
			DomainObject doo = treeNodesModelMap.get(objectBaseId + i);
			assertEquals(qualifiedNamePattern.replace("#suffix#", i.toString()), doo.getName(), "When populating showing-all tree, user" 
																				+ userIdSuffix + "@soberano.syscoop.co"
																				+ " retrieves wrong name for object with name " 
																				+ doo.getName());
			assertEquals(objectBaseId + i, doo.getId(), "When populating showing-all tree, user" 
														+ userIdSuffix + "@soberano.syscoop.co"
														+ " retrieves wrong id for object with id " 
														+ doo.getId());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void testDisablingObject(String formURL, String userToLogin, String objectToDisableTextIdFragment, Integer expectedFinalObjectCount) {
		
		SpringUtility.setLoggedUserForTesting(userToLogin);
		DesktopAgent desktop = Zats.newClient().connect(formURL);
		ComponentAgent cmbIntelliSearchAgent = desktop.query("combobox");
		ComponentAgent treeChildrenAgent = cmbIntelliSearchAgent.query("#wndShowingAll").query("#treeObjects").query("Treechildren");
		Tree treeObjects = (Tree) cmbIntelliSearchAgent.as(Combobox.class).query("#wndShowingAll").query("#treeObjects");		
		Treechildren treeChildren = (Treechildren) treeObjects.query("Treechildren");		
		for (Component comp : treeChildren.getChildren()) {
			Treeitem item = (Treeitem) comp;
			if (((Treeitem) comp).getLabel().toLowerCase().contains(objectToDisableTextIdFragment.toLowerCase())) {				
				DefaultTreeNode<NodeData> nodeData = (DefaultTreeNode<NodeData>) ((TreeNode) ZKUtilitity.getAssociatedNode(item, treeObjects));
				DomainObject doo = (DomainObject) nodeData.getData().getValue();
				ComponentAgent btnDisableAgent = treeChildrenAgent.query("#btnDisable" + doo.getId().toString());
				btnDisableAgent.click();
				if (!((Treecell) item.query("treecell")).getStyle().equals("background-color:yellow;")) {
					fail("Disabling button did not change the containing component's style when the button is clicked for the first time.");
				}
				btnDisableAgent.click();
				break;
			}			
		}
		
		//reload the form and check the object was actually disabled
		desktop = Zats.newClient().connect(formURL);
		cmbIntelliSearchAgent = desktop.query("combobox");
		treeChildrenAgent = cmbIntelliSearchAgent.query("#wndShowingAll").query("#treeObjects").query("Treechildren");
		treeObjects = (Tree) cmbIntelliSearchAgent.as(Combobox.class).query("#wndShowingAll").query("#treeObjects");		
		treeChildren = (Treechildren) treeObjects.query("Treechildren");		
		
		//check the tree is enough because every showing up request processing ends up in a selection of the corresponding tree item
		for (Component comp : treeChildren.getChildren()) {
			String userNodelabel = ((Treeitem) comp).getLabel();
			String shownUserName = userNodelabel.substring(userNodelabel.indexOf(": ") + 1);
			
			if (shownUserName.toLowerCase().equals(objectToDisableTextIdFragment.toLowerCase())) {				
				fail("Object was not disabled. It is wrongly being loaded and shown in the showing-all tree. Object: " + objectToDisableTextIdFragment);
			}			
		}
		
		//expectedFinalObjectCount in intelli search combo and tree
		assertEquals(2 * expectedFinalObjectCount,
				cmbIntelliSearchAgent.as(Combobox.class).getModel().getSize() + treeObjects.getTreechildren().getItemCount(), 
				"Wrong number of objects shown after disabling the object: " + objectToDisableTextIdFragment 
					+ ". Expected number: " + expectedFinalObjectCount.toString()
					+ ". Actual number: " + new Integer(treeObjects.getTreechildren().getItemCount()).toString());
	};
	
	public static void testExpense(Row row, 
									String expectedPayeeName,
									String expectedConceptName,
									String expectedDescription,
									Double expectedAmount,
									String expectedCurrency,
									String expectedReference) {
	
		//payee
		if (!((Label) row.getChildren().get(1)).getValue().equals(expectedPayeeName)) {
			fail("Wrong payee for expense with row index " + row.getIndex() + ". Expected: " + expectedPayeeName + ". It was: " + ((Label) row.getChildren().get(1)).getValue());
		}
		
		//concept
		if (!((Label) row.getChildren().get(2)).getValue().equals(expectedConceptName)) {
			fail("Wrong concept for expense with row index " + row.getIndex() + ". Expected: " + expectedConceptName + ". It was: " + ((Label) row.getChildren().get(2)).getValue());
		}
		
		//description
		if (!((Label) row.getChildren().get(3)).getValue().equals(expectedDescription)) {
			fail("Wrong description for expense with row index " + row.getIndex() + ". Expected: " + expectedDescription + ". It was: " + ((Label) row.getChildren().get(3)).getValue());
		}
		
		//amount
		if (Math.abs(Double.parseDouble(((Label) row.getChildren().get(4)).getValue()) - expectedAmount) > 0.00000001) {
			fail("Wrong amount for expense with row index " + row.getIndex() + ". Expected: " + expectedAmount + ". It was: " + ((Label) row.getChildren().get(4)).getValue());
		}

		//currency
		if (!((Label) row.getChildren().get(5)).getValue().equals(expectedCurrency)) {
			fail("Wrong currency for expense with row index " + row.getIndex() + ". Expected: " + expectedCurrency + ". It was: " + ((Label) row.getChildren().get(5)).getValue());
		}
		
		//reference
		if (!((Label) row.getChildren().get(6)).getValue().equals(expectedReference)) {
			fail("Wrong reference for expense with row index " + row.getIndex() + ". Expected: " + expectedReference + ". It was: " + ((Label) row.getChildren().get(6)).getValue());
		}
	}
}
