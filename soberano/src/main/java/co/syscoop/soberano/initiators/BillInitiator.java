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
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.Mobile;
import co.syscoop.soberano.util.ui.ZKUtility;

public class BillInitiator implements Initiator, InitiatorExt {
	
	private Integer orderId = 1;
	private Boolean fast = false;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Order order = new Order(orderId);
			if (order.getCanceledRunsCount().compareTo(new BigDecimal(0)) > 0) {
				OrderFormHelper form = new OrderFormHelper();
				form.initFormForBilling((Window) comps[1].getParent().getParent().getParent().getParent().query("#wndContentPanel"), orderId, Mobile.isMobile());
			}
			else {
				Executions.sendRedirect("/cash_register.zul?oid=" + orderId + "&fast=" + fast.toString());
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
			orderId = ZKUtility.getObjectIdFromURLQuery("id");
			fast = ZKUtility.getBooleanParamFromURLQuery("fast");
		}
		catch(Exception ex) {
			orderId = 0; 
			fast = false;
			ExceptionTreatment.log(ex);
		}
	}

	public Boolean getFast() {
		return fast;
	}

	public void setFast(Boolean fast) {
		this.fast = fast;
	}
}
