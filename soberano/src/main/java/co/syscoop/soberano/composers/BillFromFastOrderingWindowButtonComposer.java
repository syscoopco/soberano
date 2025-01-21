package co.syscoop.soberano.composers;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Box;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.OrderFormHelper;

@SuppressWarnings("serial")
public class BillFromFastOrderingWindowButtonComposer extends BusinessActivityTrackedObjectButtonComposer {
	
	public BillFromFastOrderingWindowButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new OrderFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnBill.query("#wndCompleteAddition").query("#boxDetails");
    }
	
	@Listen("onClick = button#btnBill")
    public void btnBill_onClick() {
	
		Integer orderId = (Integer) btnBill.getParent().getParent().getParent().query("#wndCompleteAddition").getAttribute("orderId");
        Order order = new Order(orderId);
		try {
			if (order.getCanceledRunsCount().compareTo(new BigDecimal(0)) > 0) {
				Executions.sendRedirect("/bill.zul?id=" + orderId.toString() + "&fast=true");
			}
			else {
				Executions.sendRedirect("/cash_register.zul?oid=" + orderId.toString() + "&fast=true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.fillInStackTrace();
		}
	}
}
