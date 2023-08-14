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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.CashRegister;
import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "rawtypes", "serial" })
public class CashRegisterComposer extends SelectorComposer {
	
	@Wire
	private Hbox boxDetails;
	
	@Wire
	private Intbox intSelectedCashRegister;
	
	@Wire
	private Textbox txtSelectedCurrencyCode;
	
	@Wire
	private Decimalbox decInput;
	
	@Wire
	private Decimalbox decCounted;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void updateAmounts() throws SQLException {
		
		Decimalbox decEnteredAmount = (Decimalbox) txtSelectedCurrencyCode.query("#decEnteredAmount" + txtSelectedCurrencyCode.getValue());
		decEnteredAmount.setValue(decEnteredAmount.getValue().add(decInput.getValue()));
		CashRegister cashRegister = new CashRegister(intSelectedCashRegister.getValue());
		List<Object> currencies = cashRegister.getCurrencies();
		BigDecimal totalEnteredAmountInSystemCurrency = new BigDecimal(0.0);
		for (Object item : currencies) {
			String currCode = ((Currency) item).getStringId();
			BigDecimal currExchRate = ((Currency) item).getExchangeRate();
			decEnteredAmount = (Decimalbox) txtSelectedCurrencyCode.query("#decEnteredAmount" + currCode);
			totalEnteredAmountInSystemCurrency = totalEnteredAmountInSystemCurrency.add(decEnteredAmount.getValue().multiply(currExchRate));
		}
		decCounted.setValue(totalEnteredAmountInSystemCurrency);
		decInput.setValue(new BigDecimal(0.0));
	}

	@Listen("onClick = button#btnCalc")
    public void btnCalc_onClick() throws SoberanoException {
		
		try {
			updateAmounts();	
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
		
		Executions.sendRedirect("/cash_register.zul?id=" + intSelectedCashRegister.getValue().toString());
    }
}
