package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

public class CurrencyForm extends ConstrainedForm {
	
	private Textbox txtName;
	private Textbox txtCode;
	private Checkbox chkIsSystemCurrency;
	private Checkbox chkIsPriceReferenceCurrency;
	private Checkbox chkIsCash;
	private Combobox cmbPaymentProcessor;
	private Textbox txtExchangeRateExpression;
	private Decimalbox decExchangeRate;
	private Intbox intPosition;

	public CurrencyForm(DesktopAgent desktop,
						Textbox txtName,
						Textbox txtCode,
						Checkbox chkIsSystemCurrency,
						Checkbox chkIsPriceReferenceCurrency,
						Checkbox chkIsCash,
						Combobox cmbPaymentProcessor,
						Textbox txtExchangeRateExpression,
						Decimalbox decExchangeRate,
						Intbox intPosition) {
		
		this.constrainedComponents = Arrays.asList("txtName",
													"txtCode",
													"txtExchangeRateExpression",
													"decExchangeRate",
													"intPosition");
		
		this.setDesktop(desktop);
		
		this.setTxtName(txtName);
		this.constrainableComponents.add(txtName);
		this.constrainableComponentById.put("txtName", txtName);
		
		this.setTxtCode(txtCode);
		this.constrainableComponents.add(txtCode);
		this.constrainableComponentById.put("txtCode", txtCode);
		
		this.setTxtExchangeRateExpression(txtExchangeRateExpression);
		this.constrainableComponents.add(txtExchangeRateExpression);
		this.constrainableComponentById.put("txtExchangeRateExpression", txtExchangeRateExpression);
		
		this.setDecExchangeRate(decExchangeRate);
		this.constrainableComponents.add(decExchangeRate);
		this.constrainableComponentById.put("decExchangeRate", decExchangeRate);
		
		this.setIntPosition(intPosition);
		this.constrainableComponents.add(intPosition);
		this.constrainableComponentById.put("intPosition", intPosition);
				
		this.setChkIsSystemCurrency(chkIsSystemCurrency);
		this.setChkIsPriceReferenceCurrency(chkIsPriceReferenceCurrency);
		this.setChkIsCash(chkIsCash);
		this.setCmbPaymentProcessor(cmbPaymentProcessor);		
	}

	public Textbox getTxtName() {
		return txtName;
	}

	public void setTxtName(Textbox txtName) {
		this.txtName = txtName;
	}

	public Textbox getTxtCode() {
		return txtCode;
	}

	public void setTxtCode(Textbox txtCode) {
		this.txtCode = txtCode;
	}

	public Checkbox getChkIsSystemCurrency() {
		return chkIsSystemCurrency;
	}

	public void setChkIsSystemCurrency(Checkbox chkIsSystemCurrency) {
		this.chkIsSystemCurrency = chkIsSystemCurrency;
	}

	public Checkbox getChkIsPriceReferenceCurrency() {
		return chkIsPriceReferenceCurrency;
	}

	public void setChkIsPriceReferenceCurrency(Checkbox chkIsPriceReferenceCurrency) {
		this.chkIsPriceReferenceCurrency = chkIsPriceReferenceCurrency;
	}

	public Checkbox getChkIsCash() {
		return chkIsCash;
	}

	public void setChkIsCash(Checkbox chkIsCash) {
		this.chkIsCash = chkIsCash;
	}

	public Decimalbox getDecExchangeRate() {
		return decExchangeRate;
	}

	public void setDecExchangeRate(Decimalbox decExchangeRate) {
		this.decExchangeRate = decExchangeRate;
	}

	public Combobox getCmbPaymentProcessor() {
		return cmbPaymentProcessor;
	}

	public void setCmbPaymentProcessor(Combobox cmbPaymentProcessor) {
		this.cmbPaymentProcessor = cmbPaymentProcessor;
	}

	public Intbox getIntPosition() {
		return intPosition;
	}

	public void setIntPosition(Intbox intPosition) {
		this.intPosition = intPosition;
	}

	public Textbox getTxtExchangeRateExpression() {
		return txtExchangeRateExpression;
	}

	public void setTxtExchangeRateExpression(Textbox txtExchangeRateExpression) {
		this.txtExchangeRateExpression = txtExchangeRateExpression;
	}
}
