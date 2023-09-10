package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import co.syscoop.soberano.domain.tracked.InventoryItem;
import co.syscoop.soberano.domain.tracked.ProcessRun;
import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WrongProcessSpecificationException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.vocabulary.Labels;

public class ProcessRunFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	private ArrayList<InventoryItem> inputItems = new ArrayList<InventoryItem>();
	private ArrayList<BigDecimal> inputQuantities = new ArrayList<BigDecimal>();
	private ArrayList<Unit> inputUnits = new ArrayList<Unit>();
	private ArrayList<InventoryItem> outputItems = new ArrayList<InventoryItem>();
	private ArrayList<BigDecimal> outputQuantities = new ArrayList<BigDecimal>();
	private ArrayList<Unit> outputUnits = new ArrayList<Unit>();
	private ArrayList<Integer> weights = new ArrayList<Integer>();
			
	static private void fillArrays(ArrayList<InventoryItem> inputItems,
									ArrayList<BigDecimal> inputQuantities,
									ArrayList<Unit> inputUnits,
									ArrayList<InventoryItem> outputItems,
									ArrayList<BigDecimal> outputQuantities,
									ArrayList<Unit> outputUnits,
									ArrayList<Integer> weights,
									Box boxDetails) {
		
		Treechildren tchdnInputs = (Treechildren) boxDetails.query("#incProcessIOs").query("#tchdnInputs");
		if (tchdnInputs.getChildren().size() > 0) {
			inputItems.clear();
			inputUnits.clear();
			inputQuantities.clear();
			for (Component item : tchdnInputs.getChildren()) {
				String inventoryItemId = ((Treeitem) item).getValue();
				inputItems.add(new InventoryItem(inventoryItemId, 
								((Treeitem) item).getLabel()));
				inputQuantities.add(((Decimalbox) item.query("#decInputQuantity" +  inventoryItemId)).getValue());
				inputUnits.add(new Unit(((Intbox) item.query("#intInputUnitId" +  inventoryItemId)).getValue(),						
										((Label) item.query("#lblInputUnit" + inventoryItemId)).getValue()));				
			}
		}
		
		Treechildren tchdnOutputs = (Treechildren) boxDetails.query("#incProcessIOs").query("#tchdnOutputs");
		if (tchdnOutputs.getChildren().size() > 0) {
			outputItems.clear();
			outputUnits.clear();
			outputQuantities.clear();
			weights.clear();
			for (Component item : tchdnOutputs.getChildren()) {
				String inventoryItemId = ((Treeitem) item).getValue();
				outputItems.add(new InventoryItem(inventoryItemId, 
								((Treeitem) item).getLabel()));
				outputQuantities.add(((Decimalbox) item.query("#decOutputQuantity" +  inventoryItemId)).getValue());
				outputUnits.add(new Unit(((Intbox) item.query("#intOutputUnitId" +  inventoryItemId)).getValue(),						
								((Label) item.query("#lblOutputUnit" + inventoryItemId)).getValue()));
				weights.add(((Intbox) item.query("#intWeight" +  inventoryItemId)).getValue());
			}
		}
	}

	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			Comboitem cmbiProcess = ((Combobox) boxDetails.query("#cmbProcess")).getSelectedItem();
			Comboitem cmbiCostCenter = ((Combobox) boxDetails.query("#cmbCostCenter")).getSelectedItem();
			
			if (cmbiProcess == null ||
				cmbiCostCenter == null) {
				throw new SomeFieldsContainWrongValuesException();
			}
			else {
				fillArrays(inputItems,
							inputQuantities,
							inputUnits,
							outputItems,
							outputQuantities,
							outputUnits,
							weights,
							boxDetails);			
				qryResult = (new ProcessRun(((Textbox) boxDetails.query("#txtCode")).getValue(),
											((DomainObject) cmbiProcess.getValue()).getId(),
											((DomainObject) cmbiCostCenter.getValue()).getId(),
											inputItems,
											inputQuantities,
											inputUnits,
											outputItems,
											outputQuantities,
											outputUnits,
											weights)).record();
				
				if (qryResult == -2) {
					throw new WrongProcessSpecificationException();
				}				
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
		
		Executions.sendRedirect("/process_runs.zul");
	}
}
