package co.syscoop.soberano.initiators;

import java.math.BigDecimal;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.ZKUtilitity;

public class BillInitiator implements Initiator, InitiatorExt {
	
	Integer orderId = 1;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Order order = new Order(orderId);
			if (order.getCanceledRunsCount().compareTo(new BigDecimal(0)) > 0) {
				OrderFormHelper form = new OrderFormHelper();
				form.initFormForBilling((Window) comps[1].getParent().getParent().getParent().getParent().query("#wndContentPanel"), orderId);
			}
			else {
				Executions.sendRedirect("/collect.zul?id=" + orderId);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			ex.fillInStackTrace();
		}		
	}
	
	@Override
	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}
	
	@Override
	public void doFinally() throws Exception {		
	}
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		try {
			orderId = Integer.parseInt(ZKUtilitity.parseURLQueryStringForParam("id"));
		}
		catch(Exception ex) {
			orderId = 0; 
		}
	}
}
