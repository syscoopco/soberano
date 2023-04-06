package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

public class CurrencyActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Textbox txtCode = null;
	protected static Checkbox chkIsSystemCurrency = null;
	protected static Checkbox chkIsPriceReferenceCurrency = null;
	protected static Checkbox chkIsCash = null;
	protected static Combobox cmbPaymentProcessor = null;
	protected static Textbox txtExchangeRateExpression = null;
	protected static Decimalbox decExchangeRate = null;
	protected static Intbox intPosition = null;
	
	protected static CurrencyForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		CurrencyForm currencyForm = new CurrencyForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#chkIsSystemCurrency").as(Checkbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#chkIsCash").as(Checkbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#cmbPaymentProcessor").as(Combobox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#txtExchangeRateExpression").as(Textbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#decExchangeRate").as(Decimalbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#intPosition").as(Intbox.class));
		return currencyForm;
	}
	
	protected void checkCurrency(String name,
							String code,
							Boolean isSystemCurrency,
							Boolean isPriceReferenceCurrency,
							Boolean isCash,
							Integer paymentProcessor,
							BigDecimal exchangeRate,
							Integer position) {
		
		String qualifiedName = name + " : " + code;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for currency " +  qualifiedName);
		assertEquals(code.toLowerCase(), txtCode.getText().toLowerCase(), "Wrong code shown for currency " +  qualifiedName);
		assertEquals(isSystemCurrency, chkIsSystemCurrency.isChecked(), "Currency " + qualifiedName + " is wrongly shown as system currency: " + chkIsSystemCurrency.isChecked());
		assertEquals(isPriceReferenceCurrency, chkIsPriceReferenceCurrency.isChecked(), "Currency " + qualifiedName + " is wrongly shown as price reference currency: " + chkIsPriceReferenceCurrency.isChecked());
		assertEquals(isCash, chkIsCash.isChecked(), "Currency " + qualifiedName + " is wrongly shown as cash currency: " + chkIsCash.isChecked());
		assertEquals(paymentProcessor, Integer.parseInt(cmbPaymentProcessor.getSelectedItem().getValue().toString()), "Wrong payment processor shown for currency " + qualifiedName);
		assertEquals(exchangeRate.subtract(decExchangeRate.getValue()).abs().doubleValue() < 0.00000001, true, "Wrong exchange rate shown for currency " + qualifiedName);
		assertEquals(position, intPosition.getValue(), "Wrong position for currency " +  qualifiedName);
	}
}
