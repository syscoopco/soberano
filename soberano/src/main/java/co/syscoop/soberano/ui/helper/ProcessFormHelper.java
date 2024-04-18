package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import co.syscoop.soberano.domain.tracked.InventoryItem;
import co.syscoop.soberano.domain.tracked.Process;
import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WeightsMustSum100;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.rowdata.ProcessIORowData;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Labels;

public class ProcessFormHelper extends TrackedObjectFormHelper {
	
	private ArrayList<InventoryItem> inputItems = new ArrayList<InventoryItem>();
	private ArrayList<Unit> inputUnits = new ArrayList<Unit>();
	private ArrayList<BigDecimal> inputQuantities = new ArrayList<BigDecimal>();
	private ArrayList<InventoryItem> outputItems = new ArrayList<InventoryItem>();
	private ArrayList<Unit> outputUnits = new ArrayList<Unit>();
	private ArrayList<BigDecimal> outputQuantities = new ArrayList<BigDecimal>();
	private ArrayList<Integer> weights = new ArrayList<Integer>();
		
	public static void addInput(String inventoryItemName,
								String inventoryItemId,
								BigDecimal quantity,
								String unitName,
								Integer unitId,
								Treechildren tchdnInputs,
								Boolean runMode,
								Integer runs) throws SomeFieldsContainWrongValuesException {
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
			
			Decimalbox decQuantity = new Decimalbox(quantity.multiply(new BigDecimal(runs)));
			decQuantity.setId("decInputQuantity" + inventoryItemId);
			decQuantity.setReadonly(true);
			decQuantity.setFormat("####.########");
			hbox.appendChild(decQuantity);
			
			Label lblUnit = new Label(unitName);
			lblUnit.setId("lblInputUnit" + inventoryItemId);
			hbox.appendChild(lblUnit);
			hbox.setPack("center");
			hbox.setAlign("center");
			
			Intbox intUnitId = new Intbox(unitId);
			intUnitId.setId("intInputUnitId" + inventoryItemId);
			intUnitId.setVisible(false);
			hbox.appendChild(intUnitId);
			
			if (!runMode) {
				ZKUtilitity.addRowDeletionButton("btnInputRowDeletion" + inventoryItemId, hbox);
			}
			
			treeItem.getTreerow().appendChild(treeCell);
			tchdnInputs.appendChild(treeItem);
		}
	}
	
	public static void addOutput(String inventoryItemName,
								String inventoryItemId,
								BigDecimal quantity,
								String unitName,
								Integer unitId,
								Integer weight,
								Treechildren tchdnOutputs,
								Boolean runMode,
								Integer runs) throws SomeFieldsContainWrongValuesException {
		if (quantity.compareTo(new BigDecimal(0)) <= 0 || weight < 0) {
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
			
			Decimalbox decQuantity = new Decimalbox(quantity.multiply(new BigDecimal(runs)));
			decQuantity.setReadonly(!runMode);
			decQuantity.setId("decOutputQuantity" + inventoryItemId);
			decQuantity.setFormat("####.########");
			decQuantity.setConstraint("no negative, no empty");
			hbox.appendChild(decQuantity);
			
			Label lblUnit = new Label(unitName);
			lblUnit.setId("lblOutputUnit" + inventoryItemId);
			hbox.appendChild(lblUnit);
			hbox.setPack("center");
			hbox.setAlign("center");
			
			Intbox intUnitId = new Intbox(unitId);
			intUnitId.setId("intOutputUnitId" + inventoryItemId);
			intUnitId.setVisible(false);
			hbox.appendChild(intUnitId);
			
			Intbox intWeight = new Intbox(weight);
			intWeight.setReadonly(!runMode);
			intWeight.setId("intWeight" + inventoryItemId);
			intWeight.setConstraint("no negative, no empty");
			hbox.appendChild(intWeight);
			
			Label lblWeightUnit = new Label("%");
			lblWeightUnit.setId("lblWeightUnit" + inventoryItemId);
			hbox.appendChild(lblWeightUnit);
			
			if (!runMode) {
				ZKUtilitity.addRowDeletionButton("btnOutputRowDeletion" + inventoryItemId, hbox);
			}			
			
			treeItem.getTreerow().appendChild(treeCell);
			tchdnOutputs.appendChild(treeItem);
		}
	}
	
	static private void fillArrays(ArrayList<InventoryItem> inputInventoryItems,
									ArrayList<Unit> inputUnits,
									ArrayList<BigDecimal> inputQuantities,
									ArrayList<InventoryItem> outputInventoryItems,
									ArrayList<Unit> outputUnits,
									ArrayList<BigDecimal> outputQuantities,
									ArrayList<Integer> weights,
									Include incDetails) {
		Treechildren tchdnInputs = (Treechildren) incDetails.query("#incProcessIOs").query("#tchdnInputs");
		inputInventoryItems.clear();
		inputUnits.clear();
		inputQuantities.clear();
		for (Component item : tchdnInputs.getChildren()) {
			String inventoryItemId = ((Treeitem) item).getValue();
			inputInventoryItems.add(new InventoryItem(inventoryItemId, 
									((Treeitem) item).getLabel()));
			inputUnits.add(new Unit(((Intbox) item.query("#intInputUnitId" +  inventoryItemId)).getValue(),						
						((Label) item.query("#lblInputUnit" + inventoryItemId)).getValue()));
			inputQuantities.add(((Decimalbox) item.query("#decInputQuantity" +  inventoryItemId)).getValue());
		}
		
		Treechildren tchdnOutputs = (Treechildren) incDetails.query("#incProcessIOs").query("#tchdnOutputs");
		outputInventoryItems.clear();
		outputUnits.clear();
		outputQuantities.clear();
		weights.clear();
		for (Component item : tchdnOutputs.getChildren()) {
			String inventoryItemId = ((Treeitem) item).getValue();
			outputInventoryItems.add(new InventoryItem(inventoryItemId, 
									((Treeitem) item).getLabel()));
			outputUnits.add(new Unit(((Intbox) item.query("#intOutputUnitId" +  inventoryItemId)).getValue(),						
						((Label) item.query("#lblOutputUnit" + inventoryItemId)).getValue()));
			outputQuantities.add(((Decimalbox) item.query("#decOutputQuantity" +  inventoryItemId)).getValue());
			weights.add(((Intbox) item.query("#intWeight" +  inventoryItemId)).getValue());
		}
	}
	
	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtName"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decFixedCost"), new BigDecimal(0.0));
		((Treechildren) incDetails.query("#incProcessIOs").query("#tchdnInputs")).getChildren().clear();
		((Treechildren) incDetails.query("#incProcessIOs").query("#tchdnOutputs")).getChildren().clear();
	}
	
	public void fillForm(Include incDetails, Integer id, Boolean runMode, Integer runs) throws SQLException {
		
		Process process = new Process(id);
		process.get();
		
		if (!runMode) {
			//store in the form the ids of shown object for subsequent modification
			((Intbox) incDetails.getParent().query("#intId")).setValue(process.getId());
			((Textbox) incDetails.getParent().query("#txtStringId")).setText(process.getStringId());
			
			incDetails.setVisible(true);
			Clients.scrollIntoView(incDetails.query("#txtName"));
			((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
			
			ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), process.getName());
			ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decFixedCost"), process.getFixedCost());
		}
		
		Treechildren tchdnInputs = (Treechildren) incDetails.query("#incProcessIOs").query("#tchdnInputs");
		tchdnInputs.getChildren().clear();
		for (Object input : process.getProcessInputs(id)) {
			try {
				addInput(((ProcessIORowData) input).getItemName(),
						((ProcessIORowData) input).getItemId(),
						((ProcessIORowData) input).getQuantity(),
						((ProcessIORowData) input).getUnitAcron(),
						((ProcessIORowData) input).getUnitId(),
						tchdnInputs,
						runMode,
						runs);
			} catch (SomeFieldsContainWrongValuesException e) {
				e.printStackTrace();
			}
		}
		
		Treechildren tchdnOutputs = (Treechildren) incDetails.query("#incProcessIOs").query("#tchdnOutputs");
		tchdnOutputs.getChildren().clear();
		for (Object output : process.getProcessOutputs(id)) {
			try {
				addOutput(((ProcessIORowData) output).getItemName(),
						((ProcessIORowData) output).getItemId(),
						((ProcessIORowData) output).getQuantity(),
						((ProcessIORowData) output).getUnitAcron(),
						((ProcessIORowData) output).getUnitId(),
						((ProcessIORowData) output).getWeight(),
						tchdnOutputs,
						runMode,
						runs);
			} catch (SomeFieldsContainWrongValuesException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		fillForm(incDetails, ((DomainObject) data.getData().getValue()).getId(), false, 1);
	}
	
	private Boolean weightsSum100() {
		Integer weightsSum = 0;
		for (Integer weight : weights) {
			weightsSum = weightsSum + weight;
		}
		if (outputItems.size() > 0 && weightsSum != 100) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		fillArrays(inputItems,
					inputUnits, 
					inputQuantities, 
					outputItems, 
					outputUnits, 
					outputQuantities, 
					weights, 
					incDetails);
		if (weightsSum100()) {
			return (new Process(0,
					0,
					((Textbox) incDetails.query("#txtName")).getValue(),
					((Decimalbox) incDetails.query("#decFixedCost")).getValue(),
					inputItems,
					inputUnits,
					inputQuantities,
					outputItems,
					outputUnits,
					outputQuantities,
					weights)).record();
		}
		else throw new WeightsMustSum100();
	}
	
	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		fillArrays(inputItems,
				inputUnits, 
				inputQuantities, 
				outputItems, 
				outputUnits, 
				outputQuantities, 
				weights, 
				incDetails);
		super.setTrackedObject(new Process(((Intbox) incDetails.getParent().query("#intId")).getValue(),
											0,
											((Textbox) incDetails.query("#txtName")).getValue(),
											((Decimalbox) incDetails.query("#decFixedCost")).getValue(),
											inputItems,
											inputUnits,
											inputQuantities,
											outputItems,
											outputUnits,
											outputQuantities,
											weights));
		if (weightsSum100()) {
			return super.getTrackedObject().modify();
		}
		else throw new WeightsMustSum100();
	}
	
	public void initForm(Include incDetails, Integer processId) throws Exception {
		
		fillForm(incDetails, processId, false, 1);
	}
}
