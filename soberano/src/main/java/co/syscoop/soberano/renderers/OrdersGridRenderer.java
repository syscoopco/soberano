package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.rowdata.OrderRowData;
import co.syscoop.soberano.vocabulary.Translator;

public class OrdersGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepareRow(Row row, Object data) {
		
		OrderRowData orderRowData = (OrderRowData) data;
		
		//operation id
		row.appendChild(new Label(orderRowData.getOrderId().toString()));
		
		//label
		row.appendChild(new Label(orderRowData.getLabel()));
		
		//customer
		row.appendChild(new Label(orderRowData.getCustomer()));
		
		//counter
		row.appendChild(new Label(orderRowData.getCounter()));
		
		//stage
		row.appendChild(new Label(Labels.getLabel("translation.stage." + orderRowData.getStage())));
		
		//description
		Textbox txtDescription = new Textbox(Translator.translate(orderRowData.getDescription()));
		txtDescription.setMultiline(true);
		txtDescription.setRows(15);
		txtDescription.setReadonly(true);		
		row.appendChild(txtDescription);
		
		//history
		Textbox txtHistory = new Textbox(orderRowData.getHistory());
		txtHistory.setMultiline(true);
		txtHistory.setRows(15);
		txtHistory.setReadonly(true);		
		row.appendChild(txtHistory);
		
		//recording date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(orderRowData.getRecordingDate())));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		
		Button btnManage = new Button(Labels.getLabel("caption.action.manage"));
		btnManage.setWidth("90%");
		btnManage.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				Executions.getCurrent().sendRedirect("/order.zul?id=" + orderRowData.getOrderId().toString(), "_blank");
			}
		});		
		
		Button btnPrint = new Button(Labels.getLabel("caption.action.print"));
		btnPrint.setWidth("90%");
		btnPrint.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try{
					Integer orderId = orderRowData.getOrderId();					
					Printer.printReport(new Order(orderId), 
											SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
											"records/reports/" + 
											"ORDER_" + orderId + ".pdf",
											"ORDER_",
											false,
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
		btnUpload.setWidth("90%");
		btnUpload.setDisabled(true);
		
		Button btnDocument = new Button(Labels.getLabel("caption.action.document"));
		btnDocument.setWidth("90%");
		btnDocument.setDisabled(true);
		
		actionCell.appendChild(btnManage);
		actionCell.appendChild(btnPrint);
		actionCell.appendChild(btnUpload);
		actionCell.appendChild(btnDocument);
		row.appendChild(actionCell);
		
		//entity type instance id
		row.appendChild(new Intbox(orderRowData.getEntityTypeInstanceId()));
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
