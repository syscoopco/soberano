package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.Responsibility;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class WorkerFormHelper {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addResponsibilityItem(String responsibilityName,
											String responsibilityId,
											Treechildren tchdnResponsibilities) {
		Treeitem positionItem = new Treeitem(responsibilityName, responsibilityId);
		Treecell itemCell = new Treecell();
		Button btnDelete = new Button();
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public void fillTheForm(Include incDetails, Treeitem treeItem) throws SQLException {
		
		Tree treeObjects = treeItem.getTree();
		TreeNode treeNode= (TreeNode) ZKUtilitity.getAssociatedNode(treeItem, treeObjects);
		fillTheForm(incDetails, (DefaultTreeNode<NodeData>) treeNode);
	}
	
	static public void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Worker worker = new Worker(((DomainObject) data.getData().getValue()).getId());
		worker.get();
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtUserName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtUserName"), worker.getLoginName());
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
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtPostalCode"), worker.getContactData().getPostalCode());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtTown"), worker.getContactData().getTown());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtCity"), worker.getContactData().getCity());
		Combobox cmbCountry = (Combobox) incContactData.query("#cmbCountry");
		ZKUtilitity.setValueWOValidation(cmbCountry, worker.getContactData().getCountryCode());
		Combobox cmbProvince = (Combobox) incContactData.query("#cmbProvince");
		CountryComboboxHelper.processCountrySelection(cmbCountry, cmbProvince);
		ZKUtilitity.setValueWOValidation(cmbProvince, worker.getContactData().getProvinceId().toString());
		Combobox cmbMunicipality = (Combobox) incContactData.query("#cmbMunicipality");
		ProvinceComboboxHelper.processProvinceSelection(cmbProvince, cmbMunicipality);
		ZKUtilitity.setValueWOValidation(cmbMunicipality, worker.getContactData().getMunicipalityId().toString());
		ZKUtilitity.setValueWOValidation((Doublebox) incContactData.query("#dblLatitude"), worker.getContactData().getLatitude());
		ZKUtilitity.setValueWOValidation((Doublebox) incContactData.query("#dblLongitude"), worker.getContactData().getLongitude());
	}
}
