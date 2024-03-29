package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Box;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.OrderFormHelper;

@SuppressWarnings({ "serial" })
public class BillButtonComposer extends BusinessActivityTrackedObjectButtonComposer {
	
	public BillButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new OrderFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnBill.query("#wndContentPanel").query("#boxDetails");
    }
	
	@Listen("onClick = button#btnBill")
    public void btnBill_onClick() {
	
		Executions.sendRedirect("/bill.zul?id=" + ((Intbox) boxDetails.query("#intObjectId")).getValue().toString());
	}
}
