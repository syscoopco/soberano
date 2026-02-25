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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.tracked.InventoryOperation;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.rowdata.InventoryOperationRowData;

public class InventoryOperationsGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void prepareRow(Row row, Object data) {
		
		InventoryOperationRowData inventoryOperation = (InventoryOperationRowData) data;
		
		//operation id
		row.appendChild(new Label(inventoryOperation.getInventoryOperationId().toString()));
		
		//from warehouse
		row.appendChild(new Label(inventoryOperation.getFromWarehouse()));
		
		//to warehouse
		row.appendChild(new Label(inventoryOperation.getToWarehouse()));
		
		//worker
		row.appendChild(new Label(inventoryOperation.getWorker()));
		
		//description
		Textbox txtDescription = new Textbox(inventoryOperation.getDescription());
		txtDescription.setMultiline(true);
		txtDescription.setRows(15);
		txtDescription.setReadonly(true);		
		row.appendChild(txtDescription);
		
		//recording date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(inventoryOperation.getRecordingDate())));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		Button btnPrint = new Button(Labels.getLabel("caption.action.print"));
		btnPrint.setId(btnPrint.getUuid());
		btnPrint.setWidth("90%");
		
		btnPrint.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try{
					Integer opId = inventoryOperation.getInventoryOperationId();				
					Printer.printReport(new InventoryOperation(opId), 
											SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
											"records/inventory_operations/" + 
											"INVOPERATION_" + opId + ".pdf",
											"INVOPERATION_",
											false,
											true,
											false,
											false);
				}
				catch(NotEnoughRightsException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.permissions.NotEnoughRights"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
				catch(Exception ex) {
					ExceptionTreatment.logAndShow(ex, 
								ex.getMessage(), 
								Labels.getLabel("messageBoxTitle.Error"),
								Messagebox.ERROR);
				}				
			}
		});	
		
		Button btnUpload = new Button(Labels.getLabel("caption.action.upload"));
		btnUpload.setId(btnUpload.getUuid());
		btnUpload.setWidth("90%");
		btnUpload.setDisabled(true);
		Button btnDocument = new Button(Labels.getLabel("caption.action.document"));
		btnDocument.setId(btnDocument.getUuid());
		btnDocument.setWidth("90%");
		btnDocument.setDisabled(true);
				
		actionCell.appendChild(btnPrint);
		actionCell.appendChild(btnUpload);
		actionCell.appendChild(btnDocument);
		row.appendChild(actionCell);
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
