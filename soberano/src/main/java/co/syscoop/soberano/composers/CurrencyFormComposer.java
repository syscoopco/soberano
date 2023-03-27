package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;

@SuppressWarnings({ "serial", "rawtypes" })
public class CurrencyFormComposer extends SelectorComposer {
	
	@Wire
	private Checkbox chkIsCash;
	
	@Wire
	private Combobox cmbPaymentProcessor;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = checkbox#chkIsCash")
    public void chkIsCash_onClick() {
		
		try{
			if (chkIsCash.isChecked()) {
				cmbPaymentProcessor.setDisabled(true);
			}
			else {
				cmbPaymentProcessor.setDisabled(false);
			}
		}
		catch(Exception ex) {return;};
    }
}