package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import co.syscoop.soberano.exception.SoberanoException;

@SuppressWarnings({ "serial" })
public class ShiftClosureCashRegisterReportLoadingButtonComposer extends ShiftClosureReportLoadingButtonComposer {
	
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnCashRegister")
    public void btnRetrieve_onClick() throws SoberanoException {
		
		loadReport("cashregister");
    }
}