package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.Warehouse;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.tracked.Process;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class WarehouseFormHelper extends TrackedObjectFormHelper {
	
	private ArrayList<Process> entryProcesses = new ArrayList<Process>();
	
	static private void fillEntryProcessesArray(ArrayList<Process> entryProcesses,
											Include incDetails) {
		Treechildren tchdnEntryProcesses = (Treechildren) incDetails.query("#tchdnEntryProcesses");
		entryProcesses.clear();
		for (Component item : tchdnEntryProcesses.getChildren()) {
			Integer processId = Integer.parseInt(((Treeitem) item).getValue());
			entryProcesses.add(new Process(processId));
		}
	}
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Warehouse warehouse = new Warehouse(((DomainObject) data.getData().getValue()).getId());
		warehouse.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(warehouse.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(warehouse.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), warehouse.getName());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), warehouse.getStringId());
		
		((Checkbox) incDetails.query("#chkProcurementWarehouse")).setChecked(warehouse.getIsProcurementWarehouse());
		((Checkbox) incDetails.query("#chkSalesWarehouse")).setChecked(warehouse.getIsSalesWarehouse());
		
		Treechildren tchdnEntryProcesses = (Treechildren) incDetails.query("#tchdnEntryProcesses");
		tchdnEntryProcesses.getChildren().clear();
		for (Process pr : warehouse.getEntryProcesses()) {
			addEntryProcessItem(pr.getName(), pr.getId().toString(), tchdnEntryProcesses);
		}
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), "");
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		((Checkbox) incDetails.query("#chkProcurementWarehouse")).setChecked(false);
		((Checkbox) incDetails.query("#chkSalesWarehouse")).setChecked(false);
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		fillEntryProcessesArray(entryProcesses, incDetails);
		return new Warehouse(0,
					0,
					((Textbox) incDetails.query("#txtName")).getValue(),
					((Textbox) incDetails.query("#txtCode")).getValue(),
					((Checkbox) incDetails.query("#chkProcurementWarehouse")).isChecked(),
					((Checkbox) incDetails.query("#chkSalesWarehouse")).isChecked(),
					entryProcesses)
			.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		fillEntryProcessesArray(entryProcesses, incDetails);
		super.setTrackedObject(new Warehouse(((Intbox) incDetails.getParent().query("#intId")).getValue(),
											0,
											((Textbox) incDetails.query("#txtName")).getValue(),
											((Textbox) incDetails.query("#txtCode")).getValue(),
											((Checkbox) incDetails.query("#chkProcurementWarehouse")).isChecked(),
											((Checkbox) incDetails.query("#chkSalesWarehouse")).isChecked(),
											entryProcesses));
		return super.getTrackedObject().modify();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addEntryProcessItem(String processName,
											String processId,
											Treechildren tchdnEntryProcesses) {
		Treeitem processItem = new Treeitem(processName, processId);
		Treecell itemCell = new Treecell();
		Button btnDelete = new Button();
		btnDelete.setId("btnRowDeletion" + processId);
		btnDelete.setImage("./images/delete.png");
		btnDelete.setClass("ContextualButton");
		btnDelete.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				Button btnDelete = (Button) event.getTarget();
				btnDelete.getParent().getParent().getParent().detach();
			}
		});		
		itemCell.appendChild(btnDelete);
		processItem.getTreerow().appendChild(itemCell);
		tchdnEntryProcesses.appendChild(processItem);
	}
}
