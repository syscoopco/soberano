package co.syscoop.soberano.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.tracked.Warehouse;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.util.rowdata.StockRowData;

public class StockGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepareRow(Row row, Object data) {
		
		StockRowData stockRowData = (StockRowData) data;
		
		//item code
		row.appendChild(new Label(stockRowData.getInventoryItemCode()));
		
		//item name
		row.appendChild(new Label(stockRowData.getInventoryItemName()));
		
		//quantity
		Decimalbox decQuantity = new Decimalbox(stockRowData.getQuantity());
		decQuantity.setFormat("####.########");
		decQuantity.setReadonly(true);
		decQuantity.setWidth("100%");
		row.appendChild(decQuantity);
		
		//unit
		row.appendChild(new Label(stockRowData.getUnit()));
		
		//unit value
		Decimalbox decUnitValue = new Decimalbox(stockRowData.getUnitValue());
		decUnitValue.setFormat("####.########");
		decUnitValue.setReadonly(false);
		decUnitValue.setWidth("100%");
		row.appendChild(decUnitValue);
		
		decUnitValue.addEventListener("onChange", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				try{
					Decimalbox decStockUnitValue = (Decimalbox) event.getTarget();
					DomainObject domainObject = (DomainObject) ((Combobox) decStockUnitValue.getParent().
																					getParent().
																					getParent().
																						query("grid").
																							getParent().
																							getParent().
																							getParent().
																							getParent().
																								query("#cmbWarehouse")
													).getSelectedItem().getValue();
					
					//update the unit stock value of the item
					Integer qryResult = (new Warehouse()).forceStockAdjustment(domainObject.getId(), 
																				stockRowData.getInventoryItemCode(),
																				decStockUnitValue.getValue());						
					if (qryResult != 0) {
						throw new NotEnoughRightsException();
					}
				}
				catch(NotEnoughRightsException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.permissions.NotEnoughRights"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
				catch(NullPointerException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.validation.selectAWarehouseFromTheList"), 
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
