package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import co.syscoop.soberano.domain.tracked.InventoryItem;
import co.syscoop.soberano.domain.tracked.InventoryOperation;
import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.AtLeastOneInventoryItemMustBeMovedException;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WrongDateTimeException;
import co.syscoop.soberano.models.InventoryOperationsGridModel;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Labels;

public class InventoryOperationFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	private ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
	private ArrayList<Unit> units = new ArrayList<Unit>();
	private ArrayList<BigDecimal> quantities = new ArrayList<BigDecimal>();
		
	public static void addItemToMove(String inventoryItemName,
									String inventoryItemId,
									BigDecimal quantity,
									String unitName,
									Integer unitId,
									Treechildren tchdnMove) throws SomeFieldsContainWrongValuesException {
		if (quantity.compareTo(new BigDecimal(0)) <= 0) {
			Messagebox.show(Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Warning"), 
					0, 
					Messagebox.EXCLAMATION);
		}
		else {
			Treeitem treeItem = new Treeitem(inventoryItemName, inventoryItemId);
			Treecell treeCell = new Treecell();
			
			Hbox hbox = new Hbox();
			treeCell.appendChild(hbox);
			
			Decimalbox decQuantity = new Decimalbox(quantity);
			decQuantity.setId("decQuantity" + inventoryItemId);
			decQuantity.setReadonly(true);
			decQuantity.setFormat("####.########");
			hbox.appendChild(decQuantity);
			
			Label lblUnit = new Label(unitName);
			lblUnit.setId("lblUnit" + inventoryItemId);
			hbox.appendChild(lblUnit);
			hbox.setPack("center");
			hbox.setAlign("center");
			
			Intbox intUnitId = new Intbox(unitId);
			intUnitId.setId("intUnitId" + inventoryItemId);
			intUnitId.setVisible(false);
			hbox.appendChild(intUnitId);
			
			ZKUtilitity.addRowDeletionButton("btnRowDeletion" + inventoryItemId, hbox);
			
			treeItem.getTreerow().appendChild(treeCell);
			tchdnMove.appendChild(treeItem);
		}
	}
	
	static private void fillArrays(ArrayList<InventoryItem> inventoryItems,
											ArrayList<Unit> units,
											ArrayList<BigDecimal> quantities,
											Box boxDetails) throws AtLeastOneInventoryItemMustBeMovedException {
		Treechildren tchdnMove = (Treechildren) boxDetails.query("#tchdnMove");
		if (tchdnMove.getChildren().size() > 0) {
			inventoryItems.clear();
			units.clear();
			quantities.clear();
			for (Component item : tchdnMove.getChildren()) {
				String inventoryItemId = ((Treeitem) item).getValue();
				inventoryItems.add(new InventoryItem(inventoryItemId, 
													((Treeitem) item).getLabel()));
				units.add(new Unit(((Intbox) item.query("#intUnitId" +  inventoryItemId)).getValue(),						
						((Label) item.query("#lblUnit" + inventoryItemId)).getValue()));
				quantities.add(((Decimalbox) item.query("#decQuantity" +  inventoryItemId)).getValue());
			}
		}
		else {
			throw new AtLeastOneInventoryItemMustBeMovedException();	
		}
	}

	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			Comboitem cmbiFromWarehouse = ((Combobox) boxDetails.query("#cmbFromWarehouse")).getSelectedItem();
			Comboitem cmbiToWarehouse = ((Combobox) boxDetails.query("#cmbToWarehouse")).getSelectedItem();
			Comboitem cmbiWorker = ((Combobox) boxDetails.query("#cmbWorker")).getSelectedItem();
			
			if (cmbiFromWarehouse == null ||
				cmbiToWarehouse == null ||
				cmbiWorker == null) {
				throw new SomeFieldsContainWrongValuesException();
			}
			else {
				fillArrays(inventoryItems, units, quantities, boxDetails);			
				qryResult = (new InventoryOperation(((DomainObject) cmbiFromWarehouse.getValue()).getId(),
															((DomainObject) cmbiToWarehouse.getValue()).getId(),
															((DomainObject) cmbiWorker.getValue()).getId(),
															inventoryItems,
															units,
															quantities)).record();
				if (qryResult == -2) {
					throw new WrongDateTimeException();
				}
				else if (qryResult == -3) {
					throw new ShiftHasBeenClosedException();
				}
				else if (qryResult == -4) {
					throw new SomeFieldsContainWrongValuesException();
				}
				requestedAction = ActionRequested.NONE;
				((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.record"));
				return qryResult;
			}
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.confirm"));
			throw new ConfirmationRequiredException();
		}
	}

	@Override
	public void cleanForm(Box boxDetails) {
		
		Clients.scrollIntoView(boxDetails.query("#grid"));
		((Treechildren) boxDetails.query("#tchdnMove")).getChildren().clear();
		((Grid) boxDetails.getParent().getParent().getParent().query("center").query("window").query("grid")).setModel(new InventoryOperationsGridModel(false));
		requestedAction = ActionRequested.NONE;
		((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.record"));
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
	public Integer makeFromForm(Box boxDetails) {
		return null;
	}
}
