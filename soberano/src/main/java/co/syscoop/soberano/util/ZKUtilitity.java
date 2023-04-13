package co.syscoop.soberano.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

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
}
