package co.syscoop.soberano.composers;

import java.math.BigDecimal;

import org.zkoss.json.JSONObject;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Barcodescanner;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Product;

import org.zkoss.zul.Decimalbox;

@SuppressWarnings({ "serial", "rawtypes" })
public class BarcodeScannerComposer extends SelectorComposer {
	
	@Wire
	private Barcodescanner bcs;
	
	@SuppressWarnings({ "unchecked" })
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
	}
	
	@Listen("onDetect = barcodescanner#bcs")
    public void bcs_onDetect(Event event) {
		
		try {
			String codeType = ((JSONObject) event.getData()).get("type").toString();
	        String codeContent = ((JSONObject) event.getData()).get("result").toString();		
	        Clients.log(codeType + " " + codeContent);
	        
	        Combobox cmbItemToOrder = (Combobox) bcs.getParent().getParent().getParent().getParent().getParent().getParent().getParent().query("#cmbItemToOrder");
	        cmbItemToOrder.setText(codeContent);
	        
	        InputEvent inputEventCombo = new InputEvent(Events.ON_CHANGING, cmbItemToOrder, cmbItemToOrder.getText(), null);
	        Events.postEvent(inputEventCombo);
	        
	        Textbox txtQuantityExpression = (Textbox) bcs.getParent().getParent().getParent().getParent().getParent().getParent().getParent().query("#txtQuantityExpression");
	        Decimalbox decQuantity = (Decimalbox) bcs.getParent().getParent().getParent().getParent().getParent().getParent().getParent().query("#decQuantity");
	        if (txtQuantityExpression.getText().equals("0") || txtQuantityExpression.getText().isEmpty()) {
	        	txtQuantityExpression.setText("1");
	        	decQuantity.setValue(new BigDecimal(1));
	        }
	        
	        InputEvent inputEventQty = new InputEvent(Events.ON_CHANGING, txtQuantityExpression, txtQuantityExpression.getText(), null);
	        Events.sendEvent(inputEventQty);
	        cmbItemToOrder.open();
	        cmbItemToOrder.setSelectedItem((Comboitem) cmbItemToOrder.getFirstChild());
	        Decimalbox decOneRunQuantity = (Decimalbox) bcs.getParent().getParent().getParent().getParent().getParent().getParent().getParent().query("#decOneRunQuantity");
	        decOneRunQuantity.setValue(((Product) cmbItemToOrder.getSelectedItem().getValue()).getOneRunQuantity());
	        Button btnMake = (Button) bcs.getParent().getParent().getParent().getParent().getParent().getParent().getParent().query("#btnMake");
	        Events.postEvent(Events.ON_CLICK, btnMake, null);
		}
        catch(Exception e) {
        	Messagebox.show(Labels.getLabel("error.pageOrder.ErrorWhileScanningProductCode"), 
						  					Labels.getLabel("messageBoxTitle.Error"), 
											0, 
											Messagebox.ERROR);
        }
    }
}