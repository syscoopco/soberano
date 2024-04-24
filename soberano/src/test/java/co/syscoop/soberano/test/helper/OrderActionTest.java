package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.util.SpringUtility;

public class OrderActionTest extends ActionTest {
	
	protected static Textbox txtLabel;
	protected static Textbox txtCounters;
	protected static Combobox cmbCustomer;
	protected static Combobox cmbItemToOrder;
	protected static Textbox txtSpecialInstructions;
	protected static Decimalbox decQuantity;
	protected static Combobox cmbUnit;
	protected static Button btnMake;
	protected static Intbox intDiscountTop;
	protected static Decimalbox decAmountTop;
	protected static Intbox intDiscountBottom;
	protected static Decimalbox decAmountBottom;
	protected static Div divOrderItems;
	protected static Textbox txtStage;
		
	protected static OrderForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		boxDetailsAgent = desktop.query("#boxDetails");
		vboxDetails = boxDetailsAgent.as(Vbox.class);
		OrderForm orderForm = new OrderForm(desktop,
											boxDetailsAgent.query("#txtLabel").as(Textbox.class),
											boxDetailsAgent.query("#txtCounters").as(Textbox.class),
											boxDetailsAgent.query("#cmbCustomer").as(Combobox.class),
											boxDetailsAgent.query("#cmbItemToOrder").as(Combobox.class),
											boxDetailsAgent.query("#txtSpecialInstructions").as(Textbox.class),
											boxDetailsAgent.query("#decQuantity").as(Decimalbox.class),																					
											boxDetailsAgent.query("#cmbUnit").as(Combobox.class),
											boxDetailsAgent.query("#btnMake").as(Button.class),
											boxDetailsAgent.query("#intDiscountTop").as(Intbox.class),
											boxDetailsAgent.query("#decAmountTop").as(Decimalbox.class),
											boxDetailsAgent.query("#intDiscountBottom").as(Intbox.class),
											boxDetailsAgent.query("#decAmountBottom").as(Decimalbox.class),
											boxDetailsAgent.query("#divOrderItems").as(Div.class),
											boxDetailsAgent.query("#txtStage").as(Textbox.class));
		return orderForm;
	}
	
	protected void testOrder(OrderForm orderForm,
								Integer orderId,
								String label,
								Integer discount,
								BigDecimal amount,
								String counter,
								String customer,
								ArrayList<Integer> processRuns,
								HashMap<Integer, String> categories,
								HashMap<Integer, String> instructions,					
								HashMap<Integer, String> itemNames,
								HashMap<Integer, String> units,
								HashMap<Integer, BigDecimal> processRunServed,
								HashMap<Integer, String> processRunOrdered,
								HashMap<Integer, BigDecimal> processRunDiscounted) {
		
		//label
		if (!(orderForm.getTxtLabel().getValue()).equals(label)) {
			fail("Wrong label for order with id " + orderId + ". Expected: " + label + ". It was: " + orderForm.getTxtLabel().getValue());
		}
		
		//discount bottom
		if (!(orderForm.getIntDiscountBottom().getValue()).equals(discount)) {
			fail("Wrong discount for order with id " + orderId + " shown on bottom. Expected: " + discount + ". It was: " + orderForm.getIntDiscountBottom().getValue());
		}
		
		//discount top
		if (!(orderForm.getIntDiscountTop().getValue()).equals(discount)) {
			fail("Wrong discount for order with id " + orderId + " shown on top. Expected: " + discount + ". It was: " + orderForm.getIntDiscountTop().getValue());
		}
		
		//amount bottom
		if (!(orderForm.getDecAmountBottom().getValue().subtract(amount).abs().compareTo(new BigDecimal(0.00000001)) <= 0 )) {
			fail("Wrong amount for order with id " + orderId + " shown on bottom. Expected: " + amount + ". It was: " + orderForm.getDecAmountBottom().getValue());
		}
		
		//amount top
		if (!(orderForm.getDecAmountTop().getValue().subtract(amount).abs().compareTo(new BigDecimal(0.00000001)) <= 0 )) {
			fail("Wrong amount for order with id " + orderId + " shown on top. Expected: " + amount + ". It was: " + orderForm.getDecAmountTop().getValue());
		}
		
		//counter
		if (!(orderForm.getTxtCounters().getValue() + ",,").equals(counter + " ,,")) {
			fail("Wrong counter for order with id " + orderId + ". Expected: " + counter + ". It was: " + orderForm.getTxtCounters().getValue());
		}
		
		//customer
		if (!(orderForm.getCmbCustomer().getText()).equals(customer)) {
			fail("Wrong customer for order with id " + orderId + ". Expected: " + customer + ". It was: " + orderForm.getCmbCustomer().getText());
		}		
		
		Integer shownOrderItems = 0;
		for (Component treeCat : orderForm.getDivOrderItems().query("vbox").getChildren()) {			
			for (Component chdnCat : treeCat.query("treechildren").getChildren()) {			
				for (Component chdnDesc : ((Treeitem) chdnCat).getTreechildren().getChildren()) {
					for (Component chdnOic : ((Treeitem) chdnDesc).getTreechildren().getChildren()) {
						for (Component oiItem : chdnOic.getChildren()) {
							shownOrderItems++;
							Treerow rowOi = (Treerow) oiItem.query("treerow");						
							if (processRuns.indexOf(Integer.parseInt(rowOi.getId())) == -1) {
								fail("Wrong orderItem (rowOi.getId()) shown for order with id " + orderId);
							}
							
							if (categories.get(Integer.parseInt(rowOi.getId())) == null || !categories.get(Integer.parseInt(rowOi.getId())).equals(((Treeitem) chdnCat).getLabel())) {
								fail("Item " + rowOi.getId() + " shown in wrong category for order with id " + orderId + ". Expected: " + categories.get(Integer.parseInt(rowOi.getId())) + ". It was: " + ((Treeitem) chdnCat).getLabel());
							}
							
							if (instructions.get(Integer.parseInt(rowOi.getId())) == null || !instructions.get(Integer.parseInt(rowOi.getId())).equals(((Treeitem) chdnDesc).getLabel())) {
								fail("Item " + rowOi.getId() + " shown with wrong instructions for order with id " + orderId + ". Expected: " + instructions.get(Integer.parseInt(rowOi.getId())) + ". It was: " + ((Treeitem) chdnDesc).getLabel());
							}
							
							Label lblProductName = (Label) rowOi.query("#lblProductName" + rowOi.getId());						
							if (itemNames.get(Integer.parseInt(rowOi.getId())) == null || !itemNames.get(Integer.parseInt(rowOi.getId())).equals(lblProductName.getValue())) {
								fail("Item " + rowOi.getId() + " shown with wrong name for order with id " + orderId + ". Expected: " + itemNames.get(Integer.parseInt(rowOi.getId())) + ". It was: " + lblProductName.getValue());
							}
							
							Label lblProductUnit = (Label) rowOi.query("#lblProductUnit" + rowOi.getId());						
							if (units.get(Integer.parseInt(rowOi.getId())) == null || !units.get(Integer.parseInt(rowOi.getId())).equals(lblProductUnit.getValue())) {
								fail("Item " + rowOi.getId() + " shown with wrong unit for order with id " + orderId + ". Expected: " + units.get(Integer.parseInt(rowOi.getId())) + ". It was: " + lblProductUnit.getValue());
							}
							
							Label lblProductUnit1 = (Label) rowOi.query("#lblProductUnit1" + rowOi.getId());						
							if (units.get(Integer.parseInt(rowOi.getId())) == null || !units.get(Integer.parseInt(rowOi.getId())).equals(lblProductUnit1.getValue())) {
								fail("Item " + rowOi.getId() + " shown with wrong unit for order with id " + orderId + ". Expected: " + units.get(Integer.parseInt(rowOi.getId())) + ". It was: " + lblProductUnit1.getValue());
							}
							
							Decimalbox decServedItems = (Decimalbox) rowOi.query("#decServedItems" + rowOi.getId());					
							if (processRunServed.get(Integer.parseInt(rowOi.getId())) == null || processRunServed.get(Integer.parseInt(rowOi.getId())).compareTo(decServedItems.getValue()) != 0) {
								fail("Item " + rowOi.getId() + " shown with wrong served qty for order with id " + orderId + ". Expected: " + processRunServed.get(Integer.parseInt(rowOi.getId())).toString() + ". It was: " + decServedItems.getValue().toString());
							}
							
							Label lblOrderedItems = (Label) rowOi.query("#lblOrderedItems" + rowOi.getId());						
							if (processRunOrdered.get(Integer.parseInt(rowOi.getId())) == null || !processRunOrdered.get(Integer.parseInt(rowOi.getId())).equals(lblOrderedItems.getValue())) {
								fail("Item " + rowOi.getId() + " shown with wrong ordered qty for order with id " + orderId + ". Expected: " + processRunOrdered.get(Integer.parseInt(rowOi.getId())) + ". It was: " + lblOrderedItems.getValue());
							}
							
							Decimalbox decDiscount = (Decimalbox) rowOi.query("#decDiscount" + rowOi.getId());							
							do {
								try {
									if (processRunDiscounted.get(Integer.parseInt(rowOi.getId())) == null || processRunDiscounted.get(Integer.parseInt(rowOi.getId())).compareTo(decDiscount.getValue()) != 0) {
										fail("Item " + rowOi.getId() + " shown with wrong discounted qty for order with id " + orderId + ". Expected: " + processRunDiscounted.get(Integer.parseInt(rowOi.getId())).toString() + ". It was: " + decDiscount.getValue().toString());
									}
									break;
								}
								catch (IllegalStateException ex) {										
								}
							} while (true);
						}
					}
				}
			}
		}
		if (!shownOrderItems.equals(processRuns.size())) {
			fail("Wrong items amount shown for order with id " + orderId + ". Expected: " + processRuns.size() + ". It was: " + shownOrderItems);
		}
	}	
}
