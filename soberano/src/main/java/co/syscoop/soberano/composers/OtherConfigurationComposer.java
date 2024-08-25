package co.syscoop.soberano.composers;

import java.math.BigDecimal;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import co.syscoop.soberano.exception.SoberanoException;

@SuppressWarnings({ "serial", "rawtypes" })
public class OtherConfigurationComposer extends SelectorComposer {
	
	@Wire
	protected Decimalbox decSurcharge; 
	
	@Wire 
	protected Intbox intHour;
	
	@Wire 
	protected Intbox intMinutes;
	
	@Listen("onChange = decimalbox#decSurcharge")
    public void decSurcharge_onChange() throws SoberanoException {
		
		if (decSurcharge.getValue().compareTo(new BigDecimal(0)) < 0) {
			decSurcharge.setValue(new BigDecimal(0));
		}
		else if (decSurcharge.getValue().compareTo(new BigDecimal(100)) > 0) {
			decSurcharge.setValue(new BigDecimal(100));
		}
    }
	
	@Listen("onChange = intbox#intHour")
    public void intHour_onChange() throws SoberanoException {
		
		if (intHour.getValue() < 0) {
			intHour.setValue(0);
		}
		else if (intHour.getValue() > 23) {
			intHour.setValue(0);
		}
    }
	
	@Listen("onChange = intbox#intMinutes")
    public void intMinutes_onChange() throws SoberanoException {
		
		if (intMinutes.getValue() < 0) {
			intMinutes.setValue(0);
		}
		else if (intMinutes.getValue() > 59) {
			intMinutes.setValue(0);
		}
    }
}