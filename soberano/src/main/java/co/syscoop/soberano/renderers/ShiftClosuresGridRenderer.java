package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.ShiftClosure;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.ui.helper.ShiftClosureFormHelper;
import co.syscoop.soberano.util.rowdata.ShiftClosureRowData;

public class ShiftClosuresGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepareRow(Row row, Object data) {
		
		ShiftClosureRowData shift = (ShiftClosureRowData) data;
		
		//id
		row.appendChild(new Label(shift.getShiftClosureId().toString()));
		
		//shift
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String shiftStr = dateFormat.format(shift.getShift());
		row.appendChild(new Label(shiftStr));	
		
		//closure time		
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(shift.getClosureTime())));
		
		//recording date		
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(shift.getRecordingDate())));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		Button btnGo = new Button(Labels.getLabel("caption.action.go"));
		btnGo.setId(btnGo.getUuid());
		btnGo.setWidth("90%");
		
		//add listener to go to closure
		btnGo.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				try {
					Window wndShowingAll = (Window) event.getTarget().
														getParent().
														getParent().
														getParent().
														getParent().
														getParent().
														getParent().
														getParent().
														getParent().query("#wndShowingAll");
					Textbox txtShownReport = (Textbox) wndShowingAll.query("#txtShownReport");
					Combobox cmbCostCenter = (Combobox) wndShowingAll.query("#boxDetails").getParent().getParent().query("#incSouth").query("#cmbCostCenter");
					String costCenterName = "";
					try {
						costCenterName = cmbCostCenter.getSelectedItem() != null ? cmbCostCenter.getText() : "";
					}
					catch(Exception e) {}
					ShiftClosureFormHelper.loadReport(txtShownReport,
														(Textbox) wndShowingAll.query("#txtReport"),
														txtShownReport.getText(), 
														costCenterName,
														shift.getShiftClosureId());
				}
				catch(Exception ex) {
					Executions.getCurrent().sendRedirect("/shift_closures.zul?id=" + shift.getShiftClosureId(), "_blank");
				}
			}
		});
		
		Button btnSPI = new Button(Labels.getLabel("caption.action.spi"));
		btnSPI.setId(btnSPI.getUuid());
		btnSPI.setWidth("90%");
		btnSPI.setHref("/spi.zul?sd=" + shiftStr);

/*
		//add listener to spi loading button
		btnSPI.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				//this is not working since cors
				//(new Utils()).redirect("/spi.zul?sd=" + shiftStr);
				
				Executions.getCurrent().sendRedirect("/spi.zul?sd=" + shiftStr, "_blank");
			}
		});
*/	
		Button btnDocument = new Button(Labels.getLabel("caption.action.document"));
		btnDocument.setId(btnDocument.getUuid());
		btnDocument.setWidth("90%");
		btnDocument.setDisabled(true);
		Button btnCancel = new Button(Labels.getLabel("caption.action.cancel"));
		btnCancel.setId(btnCancel.getUuid());
		btnCancel.setWidth("90%");
		
		//add listener to cancel the closure
		btnCancel.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {
					if (requestedActions.get(row) != null && requestedActions.get(row).equals(ActionRequested.DISABLE)) {
						int result = (new ShiftClosure(shift.getShiftClosureId())).disable();
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
				
		actionCell.appendChild(btnGo);
		actionCell.appendChild(btnSPI);
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
