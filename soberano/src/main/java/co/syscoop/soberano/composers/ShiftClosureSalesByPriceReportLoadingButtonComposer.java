package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.exception.SoberanoException;

@SuppressWarnings({ "serial" })
public class ShiftClosureSalesByPriceReportLoadingButtonComposer extends ShiftClosureReportLoadingComposer {
	
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnSalesByPrice")
    public void btnRetrieve_onClick() throws SoberanoException {
		
		loadReport((Textbox) btnSalesByPrice.getParent().getParent().getParent().getParent().query("#wndShowingAll").query("#txtShownReport"),
					"salesbyprice", 
					null);
		updateComponentStyles("btnSalesByPrice");
    }
}