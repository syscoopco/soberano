package co.syscoop.soberano.renderers;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import co.syscoop.soberano.util.rowdata.OrderRowData;

@SuppressWarnings("rawtypes")
public class CounterOrdersRenderer implements ComboitemRenderer {
	
	public void render(Comboitem item, Object data, int index) throws Exception {
		String orderId = ((Integer) ((OrderRowData) data).getOrderId()).toString();
		String orderLabel = ((OrderRowData) data).getLabel();
		String counterCode = ((OrderRowData) data).getCounter();
		item.setLabel(orderLabel == null || orderLabel.isEmpty() ? counterCode + " : " + orderId : counterCode + " : " + orderId + " (" + orderLabel + ")");
		item.setValue(orderId);
	}
}