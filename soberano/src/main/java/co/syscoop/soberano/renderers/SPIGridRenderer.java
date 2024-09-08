package co.syscoop.soberano.renderers;

import java.math.BigDecimal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Group;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.util.rowdata.SPIRowData;

public class SPIGridRenderer extends DomainObjectRowRenderer {
	
	private void requestClicHandler(Row spiRow,
									SPIRowData spiRowData,
									Popup popup,
									Decimalbox decRowQty,
									Label lblItemName,
									Label lblItemId,
									Label lblUnitAcronym,
									Intbox intUnitId,
									Decimalbox decPopupQty,
									Decimalbox decPopupCurrentQty,
									BigDecimal currentQty,
									Intbox intCounterWarehouseId,
									Intbox intAcquirableMaterialId) throws SoberanoException {
		
		try{
			popup.open(decRowQty);			
			popup.setAttribute("SPIRow", spiRow);
			
			lblItemName.setValue(spiRowData.getInventoryItemName());
			lblItemId.setValue(spiRowData.getInventoryItemCode());
			lblUnitAcronym.setValue(spiRowData.getUnit());
			intUnitId.setValue(spiRowData.getUnitId());
			decPopupQty.setValue(new BigDecimal(0));
			decPopupCurrentQty.setValue(currentQty);
			intAcquirableMaterialId.setValue(spiRowData.getAcquirableMaterialId());
			
			try {
				Combobox cmbWarehouse = (Combobox) popup.getParent().getParent().getParent().query("#cmbWarehouse");					
				intCounterWarehouseId.setValue(((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId());
			}
			catch(Exception ex) {
				popup.close();
				throw new SomeFieldsContainWrongValuesException();
			}
		}
		catch(SomeFieldsContainWrongValuesException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
						ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepareRow(Row row, Object data) {
		
		SPIRowData spiRowData = (SPIRowData) data;
		
		//item
		row.appendChild(new Label(spiRowData.getInventoryItemName()));
		Label lblInventoryItemCode = new Label(spiRowData.getInventoryItemCode());
		row.appendChild(lblInventoryItemCode);
		
		//unit
		row.appendChild(new Label(spiRowData.getUnit()));
		Intbox intUnitId = new Intbox(spiRowData.getUnitId());
		row.appendChild(intUnitId);
		
		//opening
		Decimalbox decOpening = new Decimalbox(spiRowData.getOpening());
		decOpening.setFormat("####.########");
		decOpening.setReadonly(true);
		decOpening.setWidth("100%");
		row.appendChild(decOpening);
		
		//input
		Decimalbox decInput = new Decimalbox(spiRowData.getInput());
		decInput.setFormat("####.########");
		decInput.setReadonly(true);
		decInput.setWidth("100%");
		decInput.setConstraint("no negative");		
		decInput.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				Popup popup = (Popup) decInput.query("#ppInput");
				requestClicHandler(row,
									spiRowData,
									popup,
									decInput,
									(Label) popup.query("window").query("#lblInputItem"),
									(Label) popup.query("window").query("#lblInputItemId"),
									(Label) popup.query("window").query("#lblInputUnit"),
									(Intbox) popup.query("window").query("#intInputUnitId"),
									(Decimalbox) popup.query("window").query("#decInputQuantity"),
									(Decimalbox) popup.query("window").query("#decInputCurrentQuantity"),
									spiRowData.getInput(),
									(Intbox) popup.query("window").query("#intInputToWarehouse"),
									(Intbox) popup.query("window").query("#intAcquirableMaterialId"));			
			}
		});
		row.appendChild(decInput);
		
		//losses
		Decimalbox decLosses = new Decimalbox(spiRowData.getLosses());
		decLosses.setFormat("####.########");
		decLosses.setReadonly(true);
		decLosses.setWidth("100%");
		decLosses.setConstraint("no negative");
		decLosses.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				Popup popup = (Popup) decInput.query("#ppLosses");
				requestClicHandler(row,
									spiRowData,
									popup,
									decLosses,
									(Label) popup.query("window").query("#lblLossesItem"),
									(Label) popup.query("window").query("#lblLossesItemId"),
									(Label) popup.query("window").query("#lblLossesUnit"),
									(Intbox) popup.query("window").query("#intLossesUnitId"),
									(Decimalbox) popup.query("window").query("#decLossesQuantity"),
									(Decimalbox) popup.query("window").query("#decLossesCurrentQuantity"),
									spiRowData.getLosses(),
									(Intbox) popup.query("window").query("#intLossesFromWarehouse"),
									(Intbox) popup.query("window").query("#intAcquirableMaterialId"));
			}
		});		
		row.appendChild(decLosses);
		
		//movement
		Decimalbox decMovement = new Decimalbox(spiRowData.getMovement());
		decMovement.setFormat("####.########");
		decMovement.setReadonly(true);
		decMovement.setWidth("100%");
		decMovement.setConstraint("no negative");
		decMovement.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				Popup popup = (Popup) decInput.query("#ppMovement");
				requestClicHandler(row,
									spiRowData,
									popup,
									decMovement,
									(Label) popup.query("window").query("#lblMovementItem"),
									(Label) popup.query("window").query("#lblMovementItemId"),
									(Label) popup.query("window").query("#lblMovementUnit"),
									(Intbox) popup.query("window").query("#intMovementUnitId"),
									(Decimalbox) popup.query("window").query("#decMovementQuantity"),
									(Decimalbox) popup.query("window").query("#decMovementCurrentQuantity"),
									spiRowData.getMovement(),
									(Intbox) popup.query("window").query("#intMovementFromWarehouse"),
									(Intbox) popup.query("window").query("#intAcquirableMaterialId"));	
			}
		});	
		row.appendChild(decMovement);
		
		//available
		Decimalbox decAvailable = new Decimalbox(spiRowData.getAvailable());
		decAvailable.setFormat("####.########");
		decAvailable.setReadonly(true);
		decAvailable.setWidth("100%");
		row.appendChild(decAvailable);
		
		//output
		Decimalbox decOutput = new Decimalbox(spiRowData.getOutput());
		decOutput.setFormat("####.########");
		decOutput.setReadonly(true);
		decOutput.setWidth("100%");
		row.appendChild(decOutput);
		
		//ending
		Decimalbox decEnding = new Decimalbox(spiRowData.getEnding());
		decEnding.setFormat("####.########");
		decEnding.setReadonly(true);
		decEnding.setWidth("100%");
		row.appendChild(decEnding);
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
