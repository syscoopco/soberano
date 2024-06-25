package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.ShiftClosure;

@SuppressWarnings({ "serial", "rawtypes" })
public class ShiftSalesBoxComposer extends SelectorComposer {
	
	@Wire
	private Decimalbox decShiftSales;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          try {
        	  decShiftSales.setValue(new ShiftClosure().getShiftSales());
          }
          catch(Exception ex) {
        	  Messagebox.show(ex.getMessage(), 
		  					Labels.getLabel("messageBoxTitle.Error"), 
							0, 
							Messagebox.ERROR);
  		}
    }
}