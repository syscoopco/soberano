package co.syscoop.soberano.renderers;

import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import co.syscoop.soberano.util.rowdata.SPIRowData;

public class SPIGridRenderer extends DomainObjectRowRenderer {

	public void prepareRow(Row row, Object data) {
		
		SPIRowData spiRowData = (SPIRowData) data;
		
		//item name
		row.appendChild(new Label(spiRowData.getInventoryItemName()));
		
		//unit
		row.appendChild(new Label(spiRowData.getUnit()));
		
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
		row.appendChild(decInput);
		
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
