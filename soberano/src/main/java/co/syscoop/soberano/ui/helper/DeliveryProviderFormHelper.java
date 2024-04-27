package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.DeliveryProvider;
import co.syscoop.soberano.domain.untracked.DeliveryFee;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class DeliveryProviderFormHelper extends TrackedObjectFormHelper {
	
	private ArrayList<String> feeCountries = new ArrayList<String>();
	private ArrayList<String> feePostalCodes = new ArrayList<String>();
	private ArrayList<BigDecimal> fees = new ArrayList<BigDecimal>();
	
	public static void addDeliveryFee(String country,
										String postalCode,
										BigDecimal fee,
										Treechildren tchdnFees) {
		
			Treeitem treeItem = new Treeitem(country + " : " + postalCode, new DeliveryFee(country, postalCode, fee));
			Treecell treeCell = new Treecell();
			
			Hbox hbox = new Hbox();
			treeCell.appendChild(hbox);
			
			Decimalbox decFee = new Decimalbox(fee);
			decFee.setFormat("####.########");
			decFee.setReadonly(true);
			hbox.appendChild(decFee);
			
			ZKUtilitity.addRowDeletionButton("btnInputRowDeletion" + country + "_" + postalCode, hbox);
			
			treeItem.getTreerow().appendChild(treeCell);
			tchdnFees.appendChild(treeItem);
	}
		
	static private void fillArrays(Include incDetails,
									ArrayList<String> feeCountries,
									ArrayList<String> feePostalCodes,
									ArrayList<BigDecimal> fees) {
		Treechildren tchdnFees = (Treechildren) incDetails.query("#tchdnFees");
		feeCountries.clear();
		feePostalCodes.clear();
		fees.clear();
		for (Component item : tchdnFees.getChildren()) {
			feeCountries.add(((DeliveryFee) ((Treeitem) item).getValue()).getCountry());
			feePostalCodes.add(((DeliveryFee) ((Treeitem) item).getValue()).getPostalCode());
			fees.add(((DeliveryFee) ((Treeitem) item).getValue()).getFee());
		}
	}
	
	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtName"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		((Checkbox) incDetails.query("#chkIsReseller")).setChecked(false);
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblRate"), 0.0);
		((Treechildren) incDetails.query("#tchdnFees")).getChildren().clear();
	}
	
	public void fillForm(Include incDetails, Integer id) throws SQLException {
		
		DeliveryProvider deliveryProvider = new DeliveryProvider(id);
		deliveryProvider.get();		
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(deliveryProvider.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(deliveryProvider.getStringId());
			
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), deliveryProvider.getName());
		((Checkbox) incDetails.query("#chkIsReseller")).setChecked(deliveryProvider.getIsReseller());
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblRate"), deliveryProvider.getRate());
						
		Treechildren tchdnFees = (Treechildren) incDetails.query("#tchdnFees");
		tchdnFees.getChildren().clear();
		for (Object deliveryFee : deliveryProvider.getDeliveryFees()) {
			addDeliveryFee(((DeliveryFee) deliveryFee).getCountry(),
							((DeliveryFee) deliveryFee).getPostalCode(),
							((DeliveryFee) deliveryFee).getFee(),
							tchdnFees);
		}
	}
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		fillForm(incDetails, ((DomainObject) data.getData().getValue()).getId());
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Doublebox dblRate = ((Doublebox) incDetails.query("#dblRate"));
		Double rate = dblRate.getValue();
		if (rate < 0 || rate > 100) {
			throw new WrongValueException(dblRate, Labels.getLabel("message.validation.aRateMustBeBetween0And100"));
		}
		else {
			fillArrays(incDetails,
					feeCountries,
					feePostalCodes,
					fees);
			return (new DeliveryProvider(0,
										0,
										((Textbox) incDetails.query("#txtName")).getValue(),
										rate,
										((Checkbox) incDetails.query("#chkIsReseller")).isChecked(),
										feeCountries,
										feePostalCodes,
										fees)).record();
		}
	}
	
	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		Doublebox dblRate = ((Doublebox) incDetails.query("#dblRate"));
		Double rate = dblRate.getValue();
		if (rate < 0 || rate > 100) {
			throw new WrongValueException(dblRate, Labels.getLabel("message.validation.aRateMustBeBetween0And100"));
		}
		else {
			fillArrays(incDetails,
					feeCountries,
					feePostalCodes,
					fees);
			super.setTrackedObject(new DeliveryProvider(((Intbox) incDetails.getParent().query("#intId")).getValue(),
													0,
													((Textbox) incDetails.query("#txtName")).getValue(),
													rate,
													((Checkbox) incDetails.query("#chkIsReseller")).isChecked(),
													feeCountries,
													feePostalCodes,
													fees));
			return super.getTrackedObject().modify();
		}
	}
	
	public void initForm(Include incDetails, Integer deliveryProviderId) throws Exception {
		
		fillForm(incDetails, deliveryProviderId);
	}

	public ArrayList<String> getFeeCountries() {
		return feeCountries;
	}

	public void setFeeCountries(ArrayList<String> feeCountries) {
		this.feeCountries = feeCountries;
	}

	public ArrayList<String> getFeePostalCodes() {
		return feePostalCodes;
	}

	public void setFeePostalCodes(ArrayList<String> feePostalCodes) {
		this.feePostalCodes = feePostalCodes;
	}

	public ArrayList<BigDecimal> getFees() {
		return fees;
	}

	public void setFees(ArrayList<BigDecimal> fees) {
		this.fees = fees;
	}
}
