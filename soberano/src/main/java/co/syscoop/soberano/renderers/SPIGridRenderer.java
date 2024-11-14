package co.syscoop.soberano.renderers;

import java.util.HashMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Group;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.rowdata.SPIRowData;

public class SPIGridRenderer extends DomainObjectRowRenderer {
	
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
				
				Combobox cmbWarehouse = (Combobox) row.getParent().getParent().getParent().getParent().getParent().query("#cmbWarehouse");
				
				HashMap<String, Object> args = new HashMap<String, Object>();
				args.put("inputItemId", spiRowData.getInventoryItemCode());
				args.put("inputItemName", spiRowData.getInventoryItemName());
				args.put("inputItemUnitId", spiRowData.getUnitId());
				args.put("inputItemUnitName", spiRowData.getUnit());
				args.put("inputAcquirableMaterialId", spiRowData.getAcquirableMaterialId());
				args.put("inputToWarehouse", ((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId());
				Window window = (Window) Executions.createComponents("./spi_input_spec.zul", cmbWarehouse.getParent(), args);
				window.setAttribute("SPIRow", row);
		        window.doModal();	
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
				
				Combobox cmbWarehouse = (Combobox) row.getParent().getParent().getParent().getParent().getParent().query("#cmbWarehouse");
				
				HashMap<String, Object> args = new HashMap<String, Object>();
				args.put("lossesItemId", spiRowData.getInventoryItemCode());
				args.put("lossesItemName", spiRowData.getInventoryItemName());
				args.put("lossesItemUnitId", spiRowData.getUnitId());
				args.put("lossesItemUnitName", spiRowData.getUnit());
				args.put("lossesAcquirableMaterialId", spiRowData.getAcquirableMaterialId());
				args.put("lossesFromWarehouse", ((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId());
				Window window = (Window) Executions.createComponents("./spi_losses_spec.zul", cmbWarehouse.getParent(), args);
				window.setAttribute("SPIRow", row);
				window.doModal();
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
				
				Combobox cmbWarehouse = (Combobox) row.getParent().getParent().getParent().getParent().getParent().query("#cmbWarehouse");
				
				HashMap<String, Object> args = new HashMap<String, Object>();
				args.put("movementItemId", spiRowData.getInventoryItemCode());
				args.put("movementItemName", spiRowData.getInventoryItemName());
				args.put("movementItemUnitId", spiRowData.getUnitId());
				args.put("movementItemUnitName", spiRowData.getUnit());
				args.put("movementAcquirableMaterialId", spiRowData.getAcquirableMaterialId());
				args.put("movementFromWarehouse", ((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId());
				Window window = (Window) Executions.createComponents("./spi_movement_spec.zul", cmbWarehouse.getParent(), args);
				window.setAttribute("SPIRow", row);
				window.doModal();
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
