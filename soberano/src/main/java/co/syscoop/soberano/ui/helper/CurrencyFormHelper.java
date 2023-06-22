package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class CurrencyFormHelper extends TrackedObjectFormHelper {
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Currency currency = new Currency(((DomainObject) data.getData().getValue()).getId());
		currency.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(currency.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(currency.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), currency.getName());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), currency.getStringId());
		
		((Checkbox) incDetails.query("#chkIsSystemCurrency")).setChecked(currency.getIsSystemCurrency());
		((Checkbox) incDetails.query("#chkIsPriceReferenceCurrency")).setChecked(currency.getIsPriceReferenceCurrency());
		
		Boolean isCash = currency.getIsCash();
		((Checkbox) incDetails.query("#chkIsCash")).setChecked(isCash);
		((Combobox) incDetails.query("#cmbPaymentProcessor")).setDisabled(isCash);		
		
		if (currency.getPaymentProcessor() > 0) 
			ZKUtilitity.setValueWOValidation((Combobox) incDetails.query("#cmbPaymentProcessor"), currency.getPaymentProcessor());
		else
			((Combobox) incDetails.query("#cmbPaymentProcessor")).setSelectedIndex(-1);
		
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decExchangeRate"), currency.getExchangeRate());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtExchangeRateExpression"), currency.getExchangeRate().toString());
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPosition"), currency.getPosition());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), "");
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		((Checkbox) incDetails.query("#chkIsSystemCurrency")).setChecked(false);
		((Checkbox) incDetails.query("#chkIsPriceReferenceCurrency")).setChecked(false);
		((Checkbox) incDetails.query("#chkIsCash")).setChecked(true);
		((Combobox) incDetails.query("#cmbPaymentProcessor")).setDisabled(true);
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decExchangeRate"), new BigDecimal(1.0));
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Comboitem selPPItem = ((Combobox) incDetails.query("#cmbPaymentProcessor")).getSelectedItem();
		return new Currency(0,
					0,
					((Textbox) incDetails.query("#txtCode")).getValue(),					
					((Textbox) incDetails.query("#txtName")).getValue(),
					((Checkbox) incDetails.query("#chkIsSystemCurrency")).isChecked(),
					((Checkbox) incDetails.query("#chkIsPriceReferenceCurrency")).isChecked(),
					((Checkbox) incDetails.query("#chkIsCash")).isChecked(),
					((Decimalbox) incDetails.query("#decExchangeRate")).getValue(),
					((Intbox) incDetails.query("#intPosition")).getValue(),
					selPPItem == null ? 1 : Integer.parseInt(selPPItem.getValue()))
			.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		Comboitem selPPItem = ((Combobox) incDetails.query("#cmbPaymentProcessor")).getSelectedItem();
		super.setTrackedObject(new Currency(((Intbox) incDetails.getParent().query("#intId")).getValue(),
											0,
											((Textbox) incDetails.query("#txtCode")).getValue(),					
											((Textbox) incDetails.query("#txtName")).getValue(),
											((Checkbox) incDetails.query("#chkIsSystemCurrency")).isChecked(),
											((Checkbox) incDetails.query("#chkIsPriceReferenceCurrency")).isChecked(),
											((Checkbox) incDetails.query("#chkIsCash")).isChecked(),
											((Decimalbox) incDetails.query("#decExchangeRate")).getValue(),
											((Intbox) incDetails.query("#intPosition")).getValue(),
											selPPItem == null ? 1 : Integer.parseInt(selPPItem.getValue())));
		return super.getTrackedObject().modify();
	}
}
