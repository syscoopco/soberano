package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Box;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.ui.ZKUtility;

@SuppressWarnings({ "serial" })
public class CollectButtonComposer extends BusinessActivityTrackedObjectButtonComposer {
	
	public CollectButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new OrderFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnCollect.query("#wndContentPanel").query("#boxDetails");
    }
	
	@Listen("onClick = button#btnCollect")
    public void btnCollect_onClick() {
	
		try {
			Executions.sendRedirect("/cash_register.zul?oid=" + ((Intbox) boxDetails.query("#intObjectId")).getValue().toString() + "&fast=" + ZKUtility.getBooleanParamFromURLQuery("fast"));
		} catch (Exception e) {
			Executions.sendRedirect("/cash_register.zul?oid=" + ((Intbox) boxDetails.query("#intObjectId")).getValue().toString() + "&fast=false");
		}
	}
}
