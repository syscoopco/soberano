package co.syscoop.soberano.renderers;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import co.syscoop.soberano.util.rowdata.CounterRowData;

public class CountersGridRenderer extends DomainObjectRowRenderer {

	public void prepareRow(Row row, Object data) {
		
		CounterRowData counterRowData = (CounterRowData) data;
		
		Checkbox chkAddTable = new Checkbox();
		chkAddTable.setId("chk" + counterRowData.getCounterCode());
		chkAddTable.setChecked(false);
		row.appendChild(chkAddTable);
		
		//counter code
		row.appendChild(new Label(counterRowData.getCounterCode()));
		
		if (!counterRowData.getIsFree()) {
			row.setStyle("background-color: yellow;");
		}
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
