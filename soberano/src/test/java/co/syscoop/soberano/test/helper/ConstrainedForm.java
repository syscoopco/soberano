package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.ExceptionTreatment;

public class ConstrainedForm {

	protected List<Component> constrainableComponents = new ArrayList<Component>(); //store the constrainable components (textbox, combobox, decimalbox, doublebox, intbox, ...)
	protected HashMap<String, Component> constrainableComponentById = new HashMap<String, Component>();
	protected List<String> constrainedComponents = null; //store the ids of the zk form's components with constraint attribute set.
	
	public Boolean componentIsConstrained(String compId) {
		return constrainedComponents.contains(compId);
	}
	
	public void setComponentValue(Textbox comp, String value) {
	
		try {
			comp.setValue(value);
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	public void setComponentValue(Doublebox comp, Double value) {
		
		try {
			comp.setValue(value);
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	public void setComponentValue(Combobox comp, String value) {
		
		try {
			for (Component co : comp.getChildren()) {
				Comboitem item = (Comboitem) co;
				if (((DomainObject) item.getValue()).getStringId().equals(value)) {
					comp.setSelectedItem(item);
					break;
				}
			}
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	public void selectComboitemByLabel(Combobox comp, String label) {
		
		try {
			for (Component co : comp.getChildren()) {
				Comboitem item = (Comboitem) co;
				if (item.getLabel().equals(label)) {
					comp.setSelectedItem(item);
					break;
				}
			}
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	public void testWrongValueException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals(cause.getClass().getName(), "org.zkoss.zk.ui.WrongValueException","Only org.zkoss.zk.ui.WrongValueException can be caught here.");
		if (!this.componentIsConstrained(((WrongValueException) cause).getComponent().getId())) {
			fail("org.zkoss.zk.ui.WrongValueException catched for an unconstrained component. Compare the elements of ConstrainedForm.constrainedComponents with the zul file.");
		}
	}
	
	public void testEachConstrainedObjectIsDeclared() {
		Boolean constrained = false;
		for (Component comp : constrainableComponents) {
			try {
				constrained = ((Textbox) comp).getConstraint() != null;
			}
			catch(ClassCastException ex) {
				constrained = ((Doublebox) comp).getConstraint() != null;					
			}
			if (constrained) {
				if (!this.componentIsConstrained(comp.getId())) {
					fail("A constrained component isn't include in ConstrainedForm.constrainedComponents. Component: " + comp.getId());
				}
			}
		}
	}
	
	public void testEachDeclaredConstrainedObjectIsActuallyConstrained() {
		Boolean constrained = false;
		for (Component comp : constrainableComponents) {
			if (componentIsConstrained(comp.getId())) {
				try {
					constrained = ((Textbox) comp).getConstraint() != null;
				}
				catch(ClassCastException ex) {
					constrained = ((Doublebox) comp).getConstraint() != null;					
				}
			}
			if (!constrained) {
				fail("A component included in ConstrainedForm.constrainedComponents isn't actually constrained. Check the zul file. Component: " + comp.getId());
			}
		}
	}		
}
