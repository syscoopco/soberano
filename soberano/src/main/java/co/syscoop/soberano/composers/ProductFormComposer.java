package co.syscoop.soberano.composers;

import java.math.RoundingMode;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.domain.untracked.helper.SystemCurrencies;
import co.syscoop.soberano.exception.NotCurrenciesConfiguredException;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProductFormComposer extends SelectorComposer {
	
	@Wire
	private Label lblSystemCurrency;
	
	@Wire
	private Label lblReferenceCurrency;
	
	@Wire
	private Decimalbox decReferencePriceExchangeRate;
	
	@Wire
	private Decimalbox decPrice;
	
	@Wire
	private Decimalbox decReferencePrice;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
		try {
			 super.doAfterCompose(comp);
			 SystemCurrencies sysCurrs = (new Currency()).getSystemCurrencies();
			 lblSystemCurrency.setValue(sysCurrs.getSystemCurrencyCode());
			 lblReferenceCurrency.setValue(sysCurrs.getReferenceCurrencyCode());
			 decReferencePriceExchangeRate.setValue(sysCurrs.getReferenceCurrencyExchangeRate());
		}
		catch(NotCurrenciesConfiguredException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.error.NotCurrenciesConfiguredException"), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
		catch(Exception ex) {
			throw ex;
		}
    }
	
	@Listen("onChange = decimalbox#decPrice")
    public void decPrice_onChange() throws Throwable {
		
		decReferencePrice.setValue(decPrice.getValue().divide(decReferencePriceExchangeRate.getValue(), 23, RoundingMode.HALF_DOWN));
	}
	
	@Listen("onChange = decimalbox#decReferencePrice")
    public void decReferencePrice_onChange() throws Throwable {
		
		decPrice.setValue(decReferencePrice.getValue().multiply(decReferencePriceExchangeRate.getValue()));
	}
}