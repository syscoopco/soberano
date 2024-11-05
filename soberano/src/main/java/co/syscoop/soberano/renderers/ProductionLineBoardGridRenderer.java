package co.syscoop.soberano.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.tracked.ProcessRunOutputAllocation;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.rowdata.ProductionLineBoardRowData;

@SuppressWarnings("rawtypes")
public class ProductionLineBoardGridRenderer implements RowRenderer{
	
	@SuppressWarnings("unchecked")
	public void prepareRow(Row row, Object data) {
		
		ProductionLineBoardRowData plbRowData = (ProductionLineBoardRowData) data;
		
		//id column
		row.appendChild(new Label(new Integer(plbRowData.getAllocationId()).toString()));
		
		//quantity column
		row.appendChild(new Label(plbRowData.getAllocationQty()));
		
		//item column
		row.appendChild(new Label(plbRowData.getAllocationItem()));
		
		//instructions column
		row.appendChild(new Label(plbRowData.getAllocationInstructions()));
		
		//table column
		Label lblCounterCode = new Label(plbRowData.getAllocationCounter());
		lblCounterCode.setStyle("background: white;");
		Cell cellTableCell = new Cell();
		cellTableCell.appendChild(lblCounterCode);
		cellTableCell.setStyle("background: " + (String) Executions.getCurrent().getSession().getAttribute("color_counter_" + plbRowData.getAllocationCounterId()) + ";");
		row.appendChild(cellTableCell);
			
		//order column
		row.appendChild(new Label(plbRowData.getAllocationOrder()));
		
		//process run and its parent in case it is an addition
		row.appendChild(new Label(plbRowData.getProcessRunIdPair()));
		
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		Button btnDone = new Button(Labels.getLabel("pageProductionLineBoard.grid.Done"));
		btnDone.setId(btnDone.getUuid());
		btnDone.setWidth("90%");
		Button btnRemove = new Button();
		btnRemove.setId(btnRemove.getUuid());
		btnRemove.setWidth("90%");
		btnRemove.setImage("./images/delete.png");
		Button btnPrint = new Button(Labels.getLabel("caption.action.print"));
		btnPrint.setId(btnPrint.getUuid());
		btnPrint.setWidth("90%");
		
		btnPrint.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try{
					Integer opId = plbRowData.getAllocationId();
					ProcessRunOutputAllocation proa = new ProcessRunOutputAllocation();
					proa.setId(opId);
					Printer.printReport(proa, 
											SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
											"records/production_lines/" + 
											"ALLOCATION_" + opId + ".pdf",
											"ALLOCATION_",
											false,
											true,
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
				
		btnDone.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				try {
					if (!plbRowData.isConfirmationRequested()) {
						plbRowData.requestConfirmation((Row) btnRemove.getParent().getParent(), ActionRequested.DONE);
					}
					else {
						if (plbRowData.getActionRequested() == ActionRequested.DONE) {
							Integer result = (new ProcessRunOutputAllocation()).markAllocationAsProduced(plbRowData.getAllocationId());
							if (result == -1) {
								throw new NotEnoughRightsException();
							}
							else {
								btnDone.getParent().getParent().detach();
							}
						}
						else {
							plbRowData.restoreRowDefaultStyle((Row) btnRemove.getParent().getParent());
						}
					}
				}
				catch(NotEnoughRightsException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.permissions.NotEnoughRights"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
				catch(Exception ex)	{
					ExceptionTreatment.logAndShow(ex, 
							ex.getMessage(), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
				}
			}
		});
		
		btnRemove.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {
					if (!plbRowData.isConfirmationRequested()) {
						plbRowData.requestConfirmation((Row) btnRemove.getParent().getParent(), ActionRequested.CANCEL);
					}
					else {
						if (plbRowData.getActionRequested() == ActionRequested.CANCEL) {
							Integer result = (new ProcessRunOutputAllocation()).markAllocationAsOmitted(plbRowData.getAllocationId());
							if (result == -1) {
								throw new NotEnoughRightsException();
							}
							else {
								btnDone.getParent().getParent().detach();
							}
						}
						else {
							plbRowData.restoreRowDefaultStyle((Row) btnRemove.getParent().getParent());
						}
					}
				}
				catch(NotEnoughRightsException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.permissions.NotEnoughRights"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
				catch(Exception ex)	{
					ExceptionTreatment.logAndShow(ex, 
							ex.getMessage(), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
				}		
			}
		});
		
		actionCell.appendChild(btnDone);
		actionCell.appendChild(btnRemove);
		actionCell.appendChild(btnPrint);
		row.appendChild(actionCell);
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
