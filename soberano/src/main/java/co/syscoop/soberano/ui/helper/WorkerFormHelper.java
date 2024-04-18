package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.domain.untracked.Authority;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.Responsibility;
import co.syscoop.soberano.exception.PasswordsMustMatchException;
import co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class WorkerFormHelper extends TrackedObjectFormHelper {
	
	private ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
	private ArrayList<Authority> authorities = new ArrayList<Authority>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addResponsibilityItem(String responsibilityName,
											String responsibilityId,
											Treechildren tchdnResponsibilities) {
		Treeitem positionItem = new Treeitem(responsibilityName, responsibilityId);
		Treecell itemCell = new Treecell();
		Button btnDelete = new Button();
		btnDelete.setId("btnRowDeletion" + responsibilityId);
		btnDelete.setImage("./images/delete.png");
		btnDelete.setClass("ContextualButton");
		btnDelete.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				Button btnDelete = (Button) event.getTarget();
				btnDelete.getParent().getParent().getParent().detach();
			}
		});		
		itemCell.appendChild(btnDelete);
		positionItem.getTreerow().appendChild(itemCell);
		tchdnResponsibilities.appendChild(positionItem);
	}
	
	static public String passwordsMatch(Include incDetails) throws PasswordsMustMatchException {
		String pwd = ((Textbox) incDetails.query("#txtPassword")).getValue();
		if (!pwd.equals(((Textbox) incDetails.query("#txtConfirmPassword")).getValue())) {
			throw new PasswordsMustMatchException();
		}
		return pwd;
	}
	
	static private void fillAssigmentArrays(ArrayList<Responsibility> responsibilities, 
											ArrayList<Authority> authorities,
											Include incDetails) throws WorkerMustBeAssignedToAResponsibilityException {
		Treechildren tchdnResponsibilities = (Treechildren) incDetails.query("#tchdnResponsibilities");
		if (tchdnResponsibilities.getChildren().size() > 0) {
			responsibilities.clear();
			authorities.clear();
			for (Component item : tchdnResponsibilities.getChildren()) {
				Integer respId = Integer.parseInt(((Treeitem) item).getValue());
				responsibilities.add(new Responsibility(respId, ""));
				authorities.add(new Authority(1, "soberano.authority.top"));
			}
		}
		else {
			throw new WorkerMustBeAssignedToAResponsibilityException();	
		}
	}

	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Worker worker = new Worker(((DomainObject) data.getData().getValue()).getId());
		worker.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(worker.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(worker.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtUserName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtUserName"), worker.getLoginName());
		
		//disable password constraints
		((Textbox) incDetails.query("#txtPassword")).setConstraint("");
		((Textbox) incDetails.query("#txtConfirmPassword")).setConstraint("");
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtFirstName"), worker.getFirstName());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtLastName"), worker.getLastName());
		
		Treechildren tchdnResponsibilities = (Treechildren) incDetails.query("#tchdnResponsibilities");
		tchdnResponsibilities.getChildren().clear();
		for (Responsibility resp : worker.getResponsibilities()) {
			addResponsibilityItem(resp.getName(), resp.getId().toString(), tchdnResponsibilities);
		}
		
		Include incContactData = (Include) incDetails.query("#incContactData");
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtPhoneNumber"), worker.getContactData().getMobilePhoneNumber());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtEmailAddress"), worker.getContactData().getEmailAddress());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtAddress"), worker.getContactData().getAddress());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#cmbPostalCode"), worker.getContactData().getPostalCode());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtTown"), worker.getContactData().getTown());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtCity"), worker.getContactData().getCity());
		Combobox cmbCountry = (Combobox) incContactData.query("#cmbCountry");
		ZKUtilitity.setValueWOValidation(cmbCountry, worker.getContactData().getCountryCode());
		Combobox cmbProvince = (Combobox) incContactData.query("#cmbProvince");
		Combobox cmbPostalCode = (Combobox) incContactData.query("#cmbPostalCode");
		CountryComboboxHelper.processCountrySelection(cmbCountry, cmbProvince, cmbPostalCode);
		ZKUtilitity.setValueWOValidation(cmbProvince, worker.getContactData().getProvinceId().toString());
		Combobox cmbMunicipality = (Combobox) incContactData.query("#cmbMunicipality");
		ProvinceComboboxHelper.processProvinceSelection(cmbProvince, cmbMunicipality);
		ZKUtilitity.setValueWOValidation(cmbMunicipality, worker.getContactData().getMunicipalityId().toString());
		ZKUtilitity.setValueWOValidation((Doublebox) incContactData.query("#dblLatitude"), worker.getContactData().getLatitude());
		ZKUtilitity.setValueWOValidation((Doublebox) incContactData.query("#dblLongitude"), worker.getContactData().getLongitude());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Executions.sendRedirect("/new_worker.zul");
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		String pwd = passwordsMatch(incDetails);
		fillAssigmentArrays(responsibilities, authorities, incDetails);
		Include incContactData = (Include) incDetails.query("#incContactData");
		return (new Worker(0,
							0,
							((Textbox) incDetails.query("#txtUserName")).getValue(),
							((Textbox) incDetails.query("#txtFirstName")).getValue(),
							((Textbox) incDetails.query("#txtLastName")).getValue(),
							pwd,
							((Textbox) incContactData.query("#txtPhoneNumber")).getValue(),
							((DomainObject) (((Combobox) incContactData.query("#cmbCountry")).getSelectedItem().getValue())).getStringId(),
							((Textbox) incContactData.query("#txtAddress")).getValue(),
							((Textbox) incContactData.query("#cmbPostalCode")).getValue(),
							((Textbox) incContactData.query("#txtTown")).getValue(),
							((Combobox) incContactData.query("#cmbMunicipality")).getSelectedItem().getValue(),
							((Textbox) incContactData.query("#txtCity")).getValue(),
							((Combobox) incContactData.query("#cmbProvince")).getSelectedItem().getValue(),
							((Doublebox) incContactData.query("#dblLatitude")).getValue(),
							((Doublebox) incContactData.query("#dblLongitude")).getValue(),
							responsibilities,
							authorities))
					.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		String pwd = passwordsMatch(incDetails);		
		fillAssigmentArrays(responsibilities, authorities, incDetails);
		Include incContactData = (Include) incDetails.query("#incContactData");
		super.setTrackedObject(new Worker(((Intbox) incDetails.getParent().query("#intId")).getValue(),
											0,
											((Textbox) incDetails.query("#txtUserName")).getValue(),
											((Textbox) incDetails.query("#txtFirstName")).getValue(),
											((Textbox) incDetails.query("#txtLastName")).getValue(),
											pwd,
											((Textbox) incContactData.query("#txtPhoneNumber")).getValue(),
											((DomainObject) (((Combobox) incContactData.query("#cmbCountry")).getSelectedItem().getValue())).getStringId(),
											((Textbox) incContactData.query("#txtAddress")).getValue(),
											((Textbox) incContactData.query("#cmbPostalCode")).getValue(),
											((Textbox) incContactData.query("#txtTown")).getValue(),
											((Combobox) incContactData.query("#cmbMunicipality")).getSelectedItem().getValue(),
											((Textbox) incContactData.query("#txtCity")).getValue(),
											((Combobox) incContactData.query("#cmbProvince")).getSelectedItem().getValue(),
											((Doublebox) incContactData.query("#dblLatitude")).getValue(),
											((Doublebox) incContactData.query("#dblLongitude")).getValue(),
											responsibilities,
											authorities));
		return super.getTrackedObject().modify();
	}
}
