package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.dao.DataIntegrityViolationException;
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
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.InventoryItem;
import co.syscoop.soberano.domain.tracked.ProcessRun;
import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.RunningOutOfInventoryException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WeightsMustSum100;
import co.syscoop.soberano.exception.WrongProcessSpecificationException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.util.rowdata.ProcessIORowData;
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
				requestedAction = ActionRequested.NONE;
				((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.run"));
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
	
	public void initForm(Window wndContentPanel, Integer processRunId) throws Exception {
		
		ProcessRun processRun = new ProcessRun(processRunId);
		processRun.get();
		
		((Intbox) wndContentPanel.query("#intObjectId")).setValue(processRunId);
		((Textbox) wndContentPanel.query("#txtCode")).setValue(processRun.getStringId());
		((Textbox) wndContentPanel.query("#txtProcess")).setValue(processRun.getProcess().getName());
		((Textbox) wndContentPanel.query("#txtCostCenter")).setValue(processRun.getCostCenter().getName());
		
		Vbox boxDetails = (Vbox) wndContentPanel.query("#boxDetails");
		
		if (processRun.getCurrentStageName().equals("Ongoing")) {
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnEnd")).setVisible(true);
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnCancel")).setVisible(true);
		}
		
		try {
			BigDecimal estimateCost = processRun.estimateCost(processRun.getProcess().getId(), 
																processRun.getCostCenter().getId());
			((Decimalbox) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#decEstimatedCost")).setValue(estimateCost);
		}
		//to avoid division-by-zero halt due to quantities equal to zero
		catch(DataIntegrityViolationException ex) {
			((Decimalbox) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#decEstimatedCost")).setValue(new BigDecimal(0));
		}
		
		Treechildren tchdnInputs = (Treechildren) boxDetails.query("#incProcessIOs").query("#tchdnInputs");
		((Button) tchdnInputs.query("#btnAddInput")).setDisabled(true);
		tchdnInputs.getChildren().clear();
		for (Object input : processRun.getProcessInputs(processRunId)) {
			try {
				ProcessFormHelper.addInput(((ProcessIORowData) input).getItemName(),
											((ProcessIORowData) input).getItemId(),
											((ProcessIORowData) input).getQuantity(),
											((ProcessIORowData) input).getUnitAcron(),
											((ProcessIORowData) input).getUnitId(),
											tchdnInputs,
											true,
											new BigDecimal(1.0));
			} catch (SomeFieldsContainWrongValuesException e) {
				e.printStackTrace();
			}
		}
		
		Treechildren tchdnOutputs = (Treechildren) boxDetails.query("#incProcessIOs").query("#tchdnOutputs");
		((Button) tchdnOutputs.query("#btnAddOutput")).setDisabled(true);
		tchdnOutputs.getChildren().clear();
		for (Object output : processRun.getProcessOutputs(processRunId)) {
			try {
				ProcessFormHelper.addOutput(((ProcessIORowData) output).getItemName(),
											((ProcessIORowData) output).getItemId(),
											((ProcessIORowData) output).getQuantity(),
											((ProcessIORowData) output).getUnitAcron(),
											((ProcessIORowData) output).getUnitId(),
											((ProcessIORowData) output).getWeight(),
											tchdnOutputs,
											true,
											new BigDecimal(1.0));
			} catch (SomeFieldsContainWrongValuesException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Integer cancelFromForm(Box boxDetails) throws Exception {
		
		if (requestedAction != null && requestedAction.equals(ActionRequested.CANCEL)) {
			
			ProcessRun processRun = new ProcessRun(((Intbox) boxDetails.query("#intObjectId")).getValue());
			processRun.get();
			return processRun.cancel();
		}
		else {
			requestedAction = ActionRequested.CANCEL;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnCancel")).setLabel(Labels.getLabel("caption.action.confirm"));
			throw new ConfirmationRequiredException();
		}
	}
	
	@Override
	public Integer closeFromForm(Box boxDetails) throws Exception {
		
		if (requestedAction != null && requestedAction.equals(ActionRequested.CLOSE)) {
			
			fillArrays(inputItems,
					inputQuantities,
					inputUnits,
					outputItems,
					outputQuantities,
					outputUnits,
					weights,
					boxDetails);			
			Integer qryResult = (new ProcessRun(((Intbox) boxDetails.query("#intObjectId")).getValue(),
												outputItems,
												outputQuantities,
												outputUnits,
												weights)).close();			
			if (qryResult == -2) {
				throw new WrongProcessSpecificationException();
			}		
			else if (qryResult == -3) {
				throw new RunningOutOfInventoryException();
			}
			else if (qryResult == -4) {
				throw new WeightsMustSum100();
			}
			return qryResult;
		}
		else {
			requestedAction = ActionRequested.CLOSE;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnEnd")).setLabel(Labels.getLabel("caption.action.confirm"));
			throw new ConfirmationRequiredException();
		}
	}

	@Override
	public Integer billFromForm(Box boxDetails) {
		return null;
	}

	@Override
	public Integer makeFromForm(Component boxDetails) {
		return null;
	}
}
