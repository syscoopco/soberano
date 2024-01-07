package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.domain.tracked.Receivable;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.ReceivableRowData;

@SuppressWarnings("rawtypes")
public class ReceivablesGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings("unchecked")
	public void prepareRow(Row row, Object data) {
		
		ReceivableRowData receivable = (ReceivableRowData) data;
		
		//receivable id
		row.appendChild(new Label(receivable.getReceivableId().toString()));
		
		//recording date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(receivable.getRecordingDate())));
		
		//days delayed
		row.appendChild(new Label(receivable.getDaysDelayed().toString()));
		
		//customer
		row.appendChild(new Label(receivable.getCustomer()));
		
		//debtor
		row.appendChild(new Label(receivable.getDebtor()));
		
		//order
		row.appendChild(new Label(receivable.getOrder().toString()));
		
		//amount due
		row.appendChild(new Label(receivable.getAmountDue().toPlainString()));
		
		//history
		row.appendChild(new Label(receivable.getHistory()));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		Button btnCollect = new Button(Labels.getLabel("caption.action.collect"));
		btnCollect.setWidth("90%");
		btnCollect.setId(btnCollect.getUuid());
		
		//add listener to collect receivable
		btnCollect.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				Executions.getCurrent().sendRedirect("/cash_register.zul?oid=" + receivable.getOrder().toString(), "_blank");
			}
		});
		
		Button btnPrint = new Button(Labels.getLabel("caption.action.print"));
		btnPrint.setWidth("90%");
		btnPrint.setDisabled(true);
		btnPrint.setId(btnPrint.getUuid());
		Button btnDishonor = new Button(Labels.getLabel("caption.action.dishonor"));
		btnDishonor.setWidth("90%");
		btnDishonor.setId(btnDishonor.getUuid());
		
		//add listener to dishonor receivable
		btnDishonor.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {
					if (requestedActions.get(row) != null && requestedActions.get(row).equals(ActionRequested.DISABLE)) {
						int result = (new Receivable(receivable.getReceivableId())).dishonor();
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
			
		actionCell.appendChild(btnCollect);
		actionCell.appendChild(btnPrint);
		actionCell.appendChild(btnDishonor);
		row.appendChild(actionCell);
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
