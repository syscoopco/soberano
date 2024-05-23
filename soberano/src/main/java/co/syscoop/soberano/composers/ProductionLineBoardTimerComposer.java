package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

import co.syscoop.soberano.util.ui.ZKUtilitity;

@SuppressWarnings({ "rawtypes", "serial" })
public class ProductionLineBoardTimerComposer extends SelectorComposer {
	
	Integer productionLineId = 0;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);		
		try {
			if (ZKUtilitity.splitQuery().get("id") == null) {
				productionLineId = 0; 
			}
			else {
				productionLineId = Integer.parseInt(ZKUtilitity.splitQuery().get("id").get(0));
			}
		}
		catch(Exception ex) {
			productionLineId = 0; 
		}
	}
	
	@Listen("onTimer = timer#timerRefreshPage")
    public void timerRefreshPage_onTimer() {
		
		Executions.sendRedirect("/production_line_board.zul?id=" + productionLineId);
    }
}