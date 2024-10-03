package co.syscoop.soberano.composers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

@SuppressWarnings({ "serial", "rawtypes" })
public class SystemTimeTimerComposer extends SelectorComposer {
	
	@Wire
	private Label lblSystemTime;
	
	@Listen("onTimer = timer#timerSystemTime")
    public void timerSystemTime_onTimer() {
		lblSystemTime.setValue(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
	}
}