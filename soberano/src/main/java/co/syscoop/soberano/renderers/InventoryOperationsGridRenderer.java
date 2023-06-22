package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.tracked.InventoryOperation;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.InventoryOperationRowData;

public class InventoryOperationsGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepareRow(Row row, Object data) {
		
		InventoryOperationRowData inventoryOperation = (InventoryOperationRowData) data;
		
		//operation date
		row.appendChild(new Label(inventoryOperation.getInventoryOperationId().toString()));
		
		//from warehouse
		row.appendChild(new Label(inventoryOperation.getFromWarehouse()));
		
		//to warehouse
		row.appendChild(new Label(inventoryOperation.getToWarehouse()));
		
		//worker
		row.appendChild(new Label(inventoryOperation.getWorker()));
		
		//description
		row.appendChild(new Label(inventoryOperation.getDescription()));
		
		//recording date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		row.appendChild(new Label(dateFormat.format(inventoryOperation.getRecordingDate())));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		Button btnPrint = new Button(Labels.getLabel("caption.action.print"));
		btnPrint.setWidth("90%");
		btnPrint.setDisabled(true);
		btnPrint.setId(btnPrint.getUuid());
		Button btnUpload = new Button(Labels.getLabel("caption.action.upload"));
		btnUpload.setWidth("90%");
		btnUpload.setDisabled(true);
		btnUpload.setId(btnUpload.getUuid());
		Button btnDocument = new Button(Labels.getLabel("caption.action.document"));
		btnDocument.setWidth("90%");
		btnDocument.setDisabled(true);
		btnDocument.setId(btnDocument.getUuid());
		Button btnCancel = new Button(Labels.getLabel("caption.action.cancel"));
		btnCancel.setWidth("90%");
		btnCancel.setDisabled(true);
		btnCancel.setId(btnCancel.getUuid());
		
		//add listener to cancel the expenditure
		btnCancel.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {
					if (requestedActions.get(row) != null && requestedActions.get(row).equals(ActionRequested.DISABLE)) {
						int result = (new InventoryOperation(inventoryOperation.getInventoryOperationId())).disable();
						if (result == -1) {
							throw new NotEnoughRightsException();
						}
						else {					
							row.detach();
						}
					}
					else {
						requestDeletion(row);
					}
				}
				catch(NotEnoughRightsException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.permissions.NotEnoughRights"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
			}
		});
				
		actionCell.appendChild(btnPrint);
		actionCell.appendChild(btnUpload);
		actionCell.appendChild(btnDocument);
		actionCell.appendChild(btnCancel);
		row.appendChild(actionCell);
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
