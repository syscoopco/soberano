package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;

import org.zkoss.zul.Box;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.helper.OrderItem;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;

public class OrderFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public void cleanForm(Box boxDetails) {}
	
	public void initForm(Window wndContentPanel, Integer orderId) throws Exception {
		
		Order order = new Order(orderId);
		order.get();
		
		((Intbox) wndContentPanel.query("#intObjectId")).setValue(orderId);
		
		Vbox boxDetails = (Vbox) wndContentPanel.query("#boxDetails");
		((Intbox) wndContentPanel.query("#intObjectId")).setValue(orderId);
		((Textbox) wndContentPanel.query("#txtLabel")).setValue(order.getLabel());
		((Textbox) wndContentPanel.query("#txtCounters")).setValue(order.getCountersStr());
		((Textbox) wndContentPanel.query("#txtCustomer")).setValue(order.getCustomerStr());
		
		//it's a delivery
		if (order.getDeliverTo() != null) {
			((Textbox) wndContentPanel.query("#txtDeliverTo")).setValue(order.getDeliverTo());
			((Label) wndContentPanel.query("#lblDeliverTo")).setVisible(true);
			((Textbox) wndContentPanel.query("#txtDeliverTo")).setVisible(true);
		}
		((Intbox) wndContentPanel.query("#intDiscountTop")).setValue(order.getDiscount());
		((Decimalbox) wndContentPanel.query("#decAmountTop")).setValue(order.getAmount());
		((Intbox) wndContentPanel.query("#intDiscountBottom")).setValue(order.getDiscount());
		((Decimalbox) wndContentPanel.query("#decAmountBottom")).setValue(order.getAmount());
		((Textbox) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#txtStage")).setValue(order.getStage());
		
		Div divOrderItems = (Div) wndContentPanel.query("#divOrderItems");		
		for (String cat : order.getCategories()) {
			Tree catTree = new Tree();
			divOrderItems.appendChild(catTree);
			Treecols treeCols = new Treecols();
			catTree.appendChild(treeCols);
			Treecol treeCol = new Treecol();
			treeCols.appendChild(treeCol);
			Treechildren catChdn = new Treechildren();
			catTree.appendChild(catChdn);
			Treeitem catItem = new Treeitem();
			catChdn.appendChild(catItem);
			Treerow catRow = new Treerow();
			catItem.appendChild(catRow);
			Treecell catCell = new Treecell(cat);
			catRow.appendChild(catCell);
			Treechildren descChdn = new Treechildren();
			catItem.appendChild(descChdn);
			catItem.setOpen(true);
			for (String desc : order.getDescriptions().get(cat)) {
				Treeitem descItem = new Treeitem();
				descChdn.appendChild(descItem);
				Treerow descRow = new Treerow();
				descItem.appendChild(descRow);
				Treecell descCell = new Treecell(desc);
				descRow.appendChild(descCell);
				Treechildren oicChdn = new Treechildren();
				descItem.appendChild(oicChdn);
				descItem.setOpen(true);
				for (OrderItem oi : order.getOrderItems().get(cat + ":" + desc)) {
					Treeitem oiItem = new Treeitem();
					oicChdn.appendChild(oiItem);
					Treerow oiRow = new Treerow();
					oiItem.appendChild(oiRow);
					Treecell oiCell = new Treecell();
					Hbox oiBox = new Hbox();
					oiBox.setAlign("center");
					
					Decimalbox decQty = new Decimalbox(oi.getProductQuantity().stripTrailingZeros());
					decQty.setReadonly(true);
					oiBox.appendChild(new Label(oi.getEndedRuns().toString()));
					oiBox.appendChild(new Label("/"));
					oiBox.appendChild(new Label(new Double((oi.getOrderedRuns() - oi.getCanceledRuns())).toString()));
					oiBox.appendChild((new Separator("horizontal")));
					oiBox.appendChild(new Label(oi.getProductUnit()));
					oiBox.appendChild(new Separator("horizontal"));
					oiBox.appendChild(new Label(oi.getProductName()));
					
					oiBox.setId("cellOrderItemProcessRun" + oi.getProcessRunId());
					oiCell.appendChild(oiBox);
					oiRow.appendChild(oiCell);
				}
			}
		}
	}

	@Override
	public Integer cancelFromForm(Box boxDetails) throws Exception {
		return null;
	}
	
	@Override
	public Integer closeFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public Integer billFromForm(Box boxDetails) {
		return null;
	}
	
	@Override
	public Integer makeFromForm(Box boxDetails) throws Exception {
		
		Comboitem cmbiItemToOrder = ((Combobox) boxDetails.query("#cmbItemToOrder")).getSelectedItem();
		Decimalbox decQuantity = (Decimalbox) boxDetails.query("#decQuantity");
		Decimalbox decOneRunQuantity = (Decimalbox) boxDetails.query("#decOneRunQuantity");
		Textbox txtSpecialInstructions = (Textbox) boxDetails.query("#txtSpecialInstructions");
		BigDecimal runs = decQuantity.getValue().divide(decOneRunQuantity.getValue());
		if (cmbiItemToOrder == null || runs.compareTo(new BigDecimal(0)) <= 0) {
			throw new SomeFieldsContainWrongValuesException();
		}
		else {
			return new Order(((Intbox) boxDetails.query("#intObjectId")).getValue())
						.make(((DomainObject) cmbiItemToOrder.getValue()).getId(),
								txtSpecialInstructions.getValue(),
								runs);
		}		
	}
}
