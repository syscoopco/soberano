package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import co.syscoop.soberano.exception.SoberanoException;

@SuppressWarnings({ "serial" })
public class ShiftClosureHouseBillReportLoadingButtonComposer extends ShiftClosureReportLoadingButtonComposer {
	
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnHouseBill")
    public void btnRetrieve_onClick() throws SoberanoException {
		
		loadReport("housebill");
    }
}