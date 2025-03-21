package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.ui.ZKUtility;

@SuppressWarnings({ "rawtypes", "serial" })
public class ProductionLineBoardTimerComposer extends SelectorComposer {
	
	Integer productionLineId = 0;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);		
		try {
			productionLineId = ZKUtility.getObjectIdFromURLQuery("id");
		}
		catch(Exception ex) {
			productionLineId = 0;
			ExceptionTreatment.log(ex);
		}
	}
	
	@Listen("onTimer = timer#timerRefreshPage")
    public void timerRefreshPage_onTimer() {
		
		Executions.sendRedirect("/production_line_board.zul?id=" + productionLineId);
    }
}