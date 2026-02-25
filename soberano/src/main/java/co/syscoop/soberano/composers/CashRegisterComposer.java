package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.CashRegister;
import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.domain.untracked.helper.SystemCurrencies;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotCurrenciesConfiguredException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.Utils;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "rawtypes", "serial" })
public class CashRegisterComposer extends SelectorComposer {
	
	@Wire
	private Label lblSystemCurrency;
	
	@Wire
	private Hbox boxDetails;
	
	@Wire
	private Intbox intSelectedCashRegister;
	
	@Wire
	private Intbox intSelectedOrder;
	
	@Wire
	private Textbox txtSelectedCurrencyCode;
	
	@Wire
	private Decimalbox decInput;
	
	@Wire
	private Textbox txtInputExpression;
	
	@Wire
	private Decimalbox decCounted;
	
	@Wire
	private Decimalbox decToCollect;
	
	@Wire
	private Decimalbox decChange;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	try {
          super.doAfterCompose(comp);
          SystemCurrencies sysCurrs = (new Currency()).getSystemCurrencies();
			lblSystemCurrency.setValue(sysCurrs.getSystemCurrencyCode());
    	}
    	catch(NotCurrenciesConfiguredException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.error.NotCurrenciesConfiguredException"), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
    	catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.error.Undetermined"), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
    }
	
	private void updateAmounts() throws SQLException {
		
		Decimalbox decEnteredAmount = (Decimalbox) txtSelectedCurrencyCode.query("#decEnteredAmount" + txtSelectedCurrencyCode.getValue());
		decEnteredAmount.setValue(decEnteredAmount.getValue().add(decInput.getValue()));
		CashRegister cashRegister = new CashRegister(intSelectedCashRegister.getValue());
		List<Object> currencies = cashRegister.getCurrencies(false);
		BigDecimal totalEnteredAmountInSystemCurrency = new BigDecimal(0.0);
		for (Object item : currencies) {
			String currCode = ((Currency) item).getStringId();
			BigDecimal currExchRate = ((Currency) item).getExchangeRate();
			decEnteredAmount = (Decimalbox) txtSelectedCurrencyCode.query("#decEnteredAmount" + currCode);
			totalEnteredAmountInSystemCurrency = totalEnteredAmountInSystemCurrency.add(decEnteredAmount.getValue().multiply(currExchRate));
		}
		decCounted.setValue(totalEnteredAmountInSystemCurrency);
		decInput.setValue(new BigDecimal(0.0));
		decChange.setValue(decCounted.getValue().subtract(decToCollect.getValue()));
		if (decCounted.getValue().subtract(decToCollect.getValue()).compareTo(new BigDecimal(0)) < 0) {
			decChange.setStyle("background-color: red;");
		}
		else {
			decChange.setStyle("");
		}
		txtInputExpression.setValue("");
	}

	@Listen("onClick = button#btnCalc")
    public void btnCalc_onClick() throws SoberanoException {
		
		try {
			updateAmounts();
			txtInputExpression.focus();
		}
		catch(Exception ex) {
			decCounted.setValue(new BigDecimal(0.0));
			decInput.setValue(new BigDecimal(0.0));
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.cashRegister.SelectACurrency"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
    }
	
	@Listen("onClick = button#btnClear")
    public void btnClear_onClick() throws SoberanoException {
		
		if (intSelectedOrder.getValue() > 0) 
			Executions.sendRedirect("/cash_register.zul?id=" + intSelectedCashRegister.getValue() + "&oid=" + intSelectedOrder.getValue());
		else
			Executions.sendRedirect("/cash_register.zul?id=" + intSelectedCashRegister.getValue());
    }
	
	@Listen("onChange = textbox#txtInputExpression")
    public void txtInputExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtInputExpression.getValue()));
			decInput.setValue(new BigDecimal(evalResult));
			txtInputExpression.setValue(decInput.getValue().toString());
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
	
	@Listen("onOK = textbox#txtInputExpression")
	public void onOK() throws SoberanoException {
		btnCalc_onClick();
	}
}
