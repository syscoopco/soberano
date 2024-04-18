package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.PrinterProfile;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class PrinterProfileFormHelper extends TrackedObjectFormHelper {
	
	private ArrayList<Integer> objectUsingThisIds = new ArrayList<Integer>();
	private ArrayList<String> objectUsingThisNames = new ArrayList<String>();
	
	public static void addPrintJobsSource(String objectQualifiedName,
										Integer entityTypeInstanceId,
										Treechildren tchdnPrintJobsSources) {
		
			Treeitem treeItem = new Treeitem(objectQualifiedName, entityTypeInstanceId);
			Treecell treeCell = new Treecell();
			
			Hbox hbox = new Hbox();
			treeCell.appendChild(hbox);
			
			ZKUtilitity.addRowDeletionButton("btnInputRowDeletion" + entityTypeInstanceId, hbox);
			
			treeItem.getTreerow().appendChild(treeCell);
			tchdnPrintJobsSources.appendChild(treeItem);
	}
		
	static private void fillArrays(Include incDetails,
								ArrayList<Integer> objectUsingThisIds,
								ArrayList<String> objectUsingThisNames) {
		Treechildren tchdnPrintJobsSources = (Treechildren) incDetails.query("#tchdnPrintJobsSources");
		objectUsingThisIds.clear();
		objectUsingThisNames.clear();
		for (Component item : tchdnPrintJobsSources.getChildren()) {
			objectUsingThisIds.add(((Treeitem) item).getValue());
			objectUsingThisNames.add(((Treeitem) item).getLabel());
		}
	}
	
	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtName"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtPrintServer"), "");
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtPrinterName"), "");
		((Checkbox) incDetails.query("#chkIsDefaultPrinter")).setChecked(false);
		((Checkbox) incDetails.query("#chkIsManagementPrinter")).setChecked(false);
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intFontSize"), 0);
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPageWitdth"), 0);
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPageHeight"), 0);
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPageMargin"), 0);
		((Checkbox) incDetails.query("#chkCompactFormat")).setChecked(false);
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtHeader"), "");
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtFooter"), "");
		((Treechildren) incDetails.query("#tchdnPrintJobsSources")).getChildren().clear();
	}
	
	public void fillForm(Include incDetails, Integer id) throws SQLException {
		
		PrinterProfile printerProfile = new PrinterProfile(id);
		printerProfile.get();		
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(printerProfile.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(printerProfile.getStringId());
			
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), printerProfile.getName());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtPrintServer"), printerProfile.getPrintServer());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtPrinterName"), printerProfile.getPrinterName());
		((Checkbox) incDetails.query("#chkIsDefaultPrinter")).setChecked(printerProfile.getIsDefaultPrinter());
		((Checkbox) incDetails.query("#chkIsManagementPrinter")).setChecked(printerProfile.getIsManagementPrinter());
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intFontSize"), printerProfile.getFontSize());
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPageWitdth"), printerProfile.getPageWidth());
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPageHeight"), printerProfile.getPageHeight());
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPageMargin"), printerProfile.getMargin());
		((Checkbox) incDetails.query("#chkCompactFormat")).setChecked(printerProfile.getCompactFormat());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtHeader"), printerProfile.getHeader());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtFooter"), printerProfile.getFooter());
			
		Treechildren tchdnPrintJobsSources = (Treechildren) incDetails.query("#tchdnPrintJobsSources");
		tchdnPrintJobsSources.getChildren().clear();
		for (Object printJobsSource : printerProfile.getObjectsUsingThis()) {
			addPrintJobsSource(((DomainObject) printJobsSource).getQualifiedName(),
									((DomainObject) printJobsSource).getEntityTypeInstanceId(),
									tchdnPrintJobsSources);
		}
	}
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		fillForm(incDetails, ((DomainObject) data.getData().getValue()).getId());
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		fillArrays(incDetails,
				objectUsingThisIds,
				objectUsingThisNames);
		return (new PrinterProfile(0,
									0,
									((Textbox) incDetails.query("#txtName")).getValue(),
									((Intbox) incDetails.query("#intFontSize")).getValue(),
									((Intbox) incDetails.query("#intPageWitdth")).getValue(),
									((Intbox) incDetails.query("#intPageHeight")).getValue(),
									((Intbox) incDetails.query("#intPageMargin")).getValue(),
									((Textbox) incDetails.query("#txtHeader")).getValue(),
									((Textbox) incDetails.query("#txtFooter")).getValue(),
									((Checkbox) incDetails.query("#chkCompactFormat")).isChecked(),
									((Checkbox) incDetails.query("#chkIsDefaultPrinter")).isChecked(),
									((Checkbox) incDetails.query("#chkIsManagementPrinter")).isChecked(),
									((Textbox) incDetails.query("#txtPrintServer")).getValue(),
									((Textbox) incDetails.query("#txtPrinterName")).getValue(),
									objectUsingThisIds,
									objectUsingThisNames)).record();
	}
	
	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		fillArrays(incDetails,
				objectUsingThisIds,
				objectUsingThisNames);
		super.setTrackedObject(new PrinterProfile(((Intbox) incDetails.getParent().query("#intId")).getValue(),
												0,
												((Textbox) incDetails.query("#txtName")).getValue(),
												((Intbox) incDetails.query("#intFontSize")).getValue(),
												((Intbox) incDetails.query("#intPageWitdth")).getValue(),
												((Intbox) incDetails.query("#intPageHeight")).getValue(),
												((Intbox) incDetails.query("#intPageMargin")).getValue(),
												((Textbox) incDetails.query("#txtHeader")).getValue(),
												((Textbox) incDetails.query("#txtFooter")).getValue(),
												((Checkbox) incDetails.query("#chkCompactFormat")).isChecked(),
												((Checkbox) incDetails.query("#chkIsDefaultPrinter")).isChecked(),
												((Checkbox) incDetails.query("#chkIsManagementPrinter")).isChecked(),
												((Textbox) incDetails.query("#txtPrintServer")).getValue(),
												((Textbox) incDetails.query("#txtPrinterName")).getValue(),
												objectUsingThisIds,
												objectUsingThisNames));
		return super.getTrackedObject().modify();
	}
	
	public void initForm(Include incDetails, Integer printerProfileId) throws Exception {
		
		fillForm(incDetails, printerProfileId);
	}
}
