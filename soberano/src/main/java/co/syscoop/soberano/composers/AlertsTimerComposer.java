package co.syscoop.soberano.composers;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

@SuppressWarnings({ "serial", "rawtypes", "unused" })
public class AlertsTimerComposer extends SelectorComposer {
	
	@Wire
	private Button btnAlert;
	
	@Listen("onTimer = timer#timerAlerts")
    public void timerAlerts_onTimer() {
		
		//TODO
    }
}