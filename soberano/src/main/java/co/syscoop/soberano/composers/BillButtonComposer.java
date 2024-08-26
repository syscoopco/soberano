package co.syscoop.soberano.composers;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Box;
import org.zkoss.zul.Intbox;
import co.syscoop.soberano.domain.tracked.Order;
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
	
		Integer orderId = ((Intbox) boxDetails.query("#intObjectId")).getValue();		
		Order order = new Order(orderId);
		try {
			if (order.getCanceledRunsCount().compareTo(new BigDecimal(0)) > 0) {
				Executions.sendRedirect("/bill.zul?id=" + orderId.toString());
			}
			else {
				Executions.sendRedirect("/cash_register.zul?oid=" + orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.fillInStackTrace();
		}
	}
}
