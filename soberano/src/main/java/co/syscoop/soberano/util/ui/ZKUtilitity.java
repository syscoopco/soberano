package co.syscoop.soberano.util.ui;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

public class ZKUtilitity {
	
	public static void setValueWOValidation(Textbox comp, String value) {
		Constraint constraint = comp.getConstraint();
		comp.setConstraint((Constraint) null);
		comp.setValue(value);
		comp.setConstraint(constraint);
	}
	
	public static void setValueWOValidation(Doublebox comp, Double value) {
		Constraint constraint = comp.getConstraint();
		comp.setConstraint((Constraint) null);
		comp.setValue(value);
		comp.setConstraint(constraint);
	}
	
	public static void setValueWOValidation(Intbox comp, Integer value) {
		Constraint constraint = comp.getConstraint();
		comp.setConstraint((Constraint) null);
		comp.setValue(value);
		comp.setConstraint(constraint);
	}
	
	public static void setValueWOValidation(Decimalbox comp, BigDecimal value) {
		Constraint constraint = comp.getConstraint();
		comp.setConstraint((Constraint) null);
		comp.setValue(value);
		comp.setConstraint(constraint);
	}
	
	public static void setValueWOValidation(Combobox comp, String value) {
		Constraint constraint = comp.getConstraint();
		comp.setConstraint((Constraint) null);
		comp.setSelectedItem(ComboboxHelper.getItemByValue(comp, value));
		comp.setConstraint(constraint);
	}
	
	public static void setValueWOValidation(Combobox comp, Integer value) {
		Constraint constraint = comp.getConstraint();
		comp.setConstraint((Constraint) null);
		comp.setSelectedItem(ComboboxHelper.getItemByValue(comp, value));
		comp.setConstraint(constraint);
	}
	
	private static int[] getTreeitemPath(Component root, Component lastNode) {
		List<Integer> l = new ArrayList<Integer>();
		Component curNode = lastNode;
		while (!root.equals(curNode)) {
			if (curNode instanceof Treeitem) {
				l.add(0, new Integer(((Treeitem) curNode).getIndex()));
			}
			curNode = curNode.getParent();
		}
		final Integer[] objs = l.toArray(new Integer[l.size()]);
		final int[] path = new int[objs.length];
		for (int i = 0; i < objs.length; i++)
			path[i] = objs[i].intValue();
		return path;
	}
	
	public static Object getAssociatedNode(Treeitem ti, Tree t) {
		  return t.getModel().getChild(getTreeitemPath(t, ti));
	}
	
	public static String parseURLQueryStringForParam(String paramName) {
		try{
			String paramValue = "";
			String queryString = Executions.getCurrent().getDesktop().getQueryString();
			if (queryString!= null && queryString.indexOf(paramName + "=") > -1) {				
				paramValue = queryString.substring(queryString.indexOf(paramName + "=") + paramName.length() + 1, queryString.length());
			}
			return paramValue;
		}
		catch(Exception ex) {
			return "";
		}
	}
	
	public static Map<String, List<String>> splitQuery() throws UnsupportedEncodingException {
		String queryString = Executions.getCurrent().getDesktop().getQueryString();
		final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
		final String[] pairs = queryString.split("&");
		for (String pair : pairs) {
			final int idx = pair.indexOf("=");
		    final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
		    if (!query_pairs.containsKey(key)) {
		    	query_pairs.put(key, new LinkedList<String>());
		    }
		    final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
		    query_pairs.get(key).add(value);
		 }
		 return query_pairs;
	}
	
	public static void processItemSelection(Combobox cmbIntelliSearch) {
		Tree treeObjects = (Tree) cmbIntelliSearch.query("#wndShowingAll").query("#treeObjects");
		for (Component comp : treeObjects.getTreechildren().getChildren()) {
			Treeitem ti = (Treeitem) comp;
			Treerow tr = (Treerow) (ti).getChildren().get(0);
			Treecell tc = (Treecell) (tr).getChildren().get(0);
			if (tc.getLabel().equals(cmbIntelliSearch.getText()) ) {
				treeObjects.setSelectedItem(ti);
				
				//call if abstract class version fillForm((Include) cmbIntelliSearch.query("#incDetails"), ti);
				
				//sendEvent is called in non abstract class version
				Events.sendEvent(Events.ON_CLICK, ti, null);
				
				Clients.scrollIntoView(ti);
				break;
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addRowDeletionButton(String buttonId, Component parentComponent) {
		
		Button btnDelete = new Button();
		btnDelete.setId(buttonId);
		btnDelete.setImage("./images/delete.png");
		btnDelete.setClass("ContextualButton");
		btnDelete.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				Button btnDelete = (Button) event.getTarget();
				btnDelete.getParent().getParent().getParent().getParent().detach();
			}
		});		
		parentComponent.appendChild(btnDelete);
	}
	
	public static void selectComboitemByLabel(Combobox comboBox, String label) {
		for(Component ci : comboBox.getChildren()) {
			if(((Comboitem) ci).getLabel().equals(label)) {
				comboBox.setSelectedItem((Comboitem) ci);
				break;
			}
		}
	}
}
