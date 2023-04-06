package co.syscoop.soberano.composers;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.Utils;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class CurrencyFormComposer extends SelectorComposer {
	
	@Wire
	private Checkbox chkIsCash;
	
	@Wire
	private Decimalbox decExchangeRate;
	
	@Wire
	private Combobox cmbPaymentProcessor;
	
	@Wire
	private Textbox txtExchangeRateExpression;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = checkbox#chkIsCash")
    public void chkIsCash_onClick() {
		
		try{
			if (chkIsCash.isChecked()) {
				cmbPaymentProcessor.setDisabled(true);
			}
			else {
				cmbPaymentProcessor.setDisabled(false);
			}
		}
		catch(Exception ex) {return;};
    }
	
	@Listen("onChange = textbox#txtExchangeRateExpression")
    public void txtExchangeRateExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtExchangeRateExpression.getValue()));
			decExchangeRate.setValue(new BigDecimal(evalResult));
			txtExchangeRateExpression.setValue(decExchangeRate.getValue().toString());
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
}