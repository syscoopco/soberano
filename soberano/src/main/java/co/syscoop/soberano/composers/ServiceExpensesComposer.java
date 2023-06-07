package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.Utils;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public /*abstract*/ class ServiceExpensesComposer extends SelectorComposer {
	
	@Wire
	private Decimalbox decAmount;
	
	@Wire
	private Textbox txtAmountExpression;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onChange = textbox#txtAmountExpression")
    public void txtAmountExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtAmountExpression.getValue()));
			decAmount.setValue(new BigDecimal(evalResult));
			txtAmountExpression.setValue(decAmount.getValue().toString());
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
}