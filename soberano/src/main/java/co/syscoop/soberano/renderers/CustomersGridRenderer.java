package co.syscoop.soberano.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.tracked.Customer;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.ui.helper.CustomerFormHelper;
import co.syscoop.soberano.util.rowdata.CustomerRowData;

public class CustomersGridRenderer extends DomainObjectRowRenderer {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepareRow(Row row, Object data) {
		
		CustomerRowData customerRowData = (CustomerRowData) data;
		
		//name
		row.appendChild(new Label(customerRowData.getDomainObjectName()));
		
		//id
		row.appendChild(new Intbox(customerRowData.getDomainObjectId()));
		
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		Button btnDisable = new Button();
		btnDisable.setId(btnDisable.getUuid());
		btnDisable.setImage("./images/delete.png");
		
		btnDisable.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				try {
					if (!customerRowData.isConfirmationRequested()) {
						customerRowData.requestConfirmation((Row) btnDisable.getParent(), ActionRequested.DISABLE);
					}
					else {
						if (customerRowData.getActionRequested() == ActionRequested.DISABLE) {
							Integer result = (new Customer(((Intbox) btnDisable.getParent().query("intbox")).getValue())).disable();
							if (result == -1) {
								throw new NotEnoughRightsException();
							}
							else {
								btnDisable.getParent().detach();
							}
						}
						else {
							customerRowData.restoreRowDefaultStyle((Row) btnDisable.getParent());
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
		row.appendChild(btnDisable);
		
		//row handler
		row.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				Include incDetails = (Include) row.query("#wndShowingAll").getParent().query("#incDetails");
				(new CustomerFormHelper()).fillForm(incDetails, ((Intbox) row.query("intbox")).getValue());
			}
		});
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
