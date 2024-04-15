package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Doublebox;
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
import co.syscoop.soberano.util.ZKUtilitity;

public class PrinterProfileFormHelper extends TrackedObjectFormHelper {
	
	private ArrayList<Integer> objectUsingThisIds = new ArrayList<Integer>();
	private ArrayList<String> objectUsingThisNames = new ArrayList<String>();
	
	public static void addPrintJobsSource(String objectQualifiedName,
										Integer entityTypeInstanceId,
										Treechildren treePrintJobsSources) {
		
			Treeitem treeItem = new Treeitem(objectQualifiedName, entityTypeInstanceId);
			Treecell treeCell = new Treecell();
			
			Hbox hbox = new Hbox();
			treeCell.appendChild(hbox);
			
			ZKUtilitity.addRowDeletionButton("btnInputRowDeletion" + entityTypeInstanceId, hbox);
			
			treeItem.getTreerow().appendChild(treeCell);
			treePrintJobsSources.appendChild(treeItem);
	}
		
	static private void fillArrays(Include incDetails,
								ArrayList<Integer> objectUsingThisIds,
								ArrayList<String> objectUsingThisNames) {
		Treechildren treePrintJobsSources = (Treechildren) incDetails.query("#treePrintJobsSources");
		objectUsingThisIds.clear();
		objectUsingThisNames.clear();
		for (Component item : treePrintJobsSources.getChildren()) {
			objectUsingThisIds.add(((Treeitem) item).getValue());
			objectUsingThisNames.add(((Treeitem) item).getLabel());
		}
	}
	
	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtName"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtPrinterPath"), "");
		((Checkbox) incDetails.query("#chkIsDefaultPrinter")).setChecked(false);
		((Checkbox) incDetails.query("#chkIsManagementPrinter")).setChecked(false);
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblFontSize"), 0.0);
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblPageWitdth"), 0.0);
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblPageHeight"), 0.0);
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblPageMargin"), 0.0);
		((Checkbox) incDetails.query("#chkCompactFormat")).setChecked(false);
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtHeader"), "");
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtFooter"), "");
		((Treechildren) incDetails.query("#treePrintJobsSources")).getChildren().clear();
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
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), printerProfile.getQualifiedName());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtPrinterPath"), printerProfile.getPrinterPath());
		((Checkbox) incDetails.query("#chkIsDefaultPrinter")).setChecked(printerProfile.getIsDefaultPrinter());
		((Checkbox) incDetails.query("#chkIsManagementPrinter")).setChecked(printerProfile.getIsManagementPrinter());
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblFontSize"), printerProfile.getFontSize());
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblPageWitdth"), printerProfile.getPageWidth());
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblPageHeight"), printerProfile.getPageHeight());
		ZKUtilitity.setValueWOValidation((Doublebox) incDetails.query("#dblPageMargin"), printerProfile.getMargin());
		((Checkbox) incDetails.query("#chkCompactFormat")).setChecked(printerProfile.getCompactFormat());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtHeader"), printerProfile.getHeader());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtFooter"), printerProfile.getFooter());
			
		Treechildren treePrintJobsSources = (Treechildren) incDetails.query("#treePrintJobsSources");
		treePrintJobsSources.getChildren().clear();
		for (Object printJobsSource : printerProfile.getObjectsUsingThis()) {
			addPrintJobsSource(((DomainObject) printJobsSource).getQualifiedName(),
									((DomainObject) printJobsSource).getEntityTypeInstanceId(),
									treePrintJobsSources);
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
									((Doublebox) incDetails.query("#dblFontSize")).getValue(),
									((Doublebox) incDetails.query("#dblPageWitdth")).getValue(),
									((Doublebox) incDetails.query("#dblPageHeight")).getValue(),
									((Doublebox) incDetails.query("#dblPageMargin")).getValue(),
									((Textbox) incDetails.query("#txtHeader")).getValue(),
									((Textbox) incDetails.query("#txtFooter")).getValue(),
									((Checkbox) incDetails.query("#chkCompactFormat")).getValue(),
									((Checkbox) incDetails.query("#chkIsDefaultPrinter")).getValue(),
									((Checkbox) incDetails.query("#chkIsManagementPrinter")).getValue(),
									((Textbox) incDetails.query("#txtPrinterPath")).getValue(),
									objectUsingThisIds,
									objectUsingThisNames)).record();
	}
	
	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		fillArrays(incDetails,
				objectUsingThisIds,
				objectUsingThisNames);
		super.setTrackedObject(new PrinterProfile(0,
												0,
												((Textbox) incDetails.query("#txtName")).getValue(),
												((Doublebox) incDetails.query("#dblFontSize")).getValue(),
												((Doublebox) incDetails.query("#dblPageWitdth")).getValue(),
												((Doublebox) incDetails.query("#dblPageHeight")).getValue(),
												((Doublebox) incDetails.query("#dblPageMargin")).getValue(),
												((Textbox) incDetails.query("#txtHeader")).getValue(),
												((Textbox) incDetails.query("#txtFooter")).getValue(),
												((Checkbox) incDetails.query("#chkCompactFormat")).getValue(),
												((Checkbox) incDetails.query("#chkIsDefaultPrinter")).getValue(),
												((Checkbox) incDetails.query("#chkIsManagementPrinter")).getValue(),
												((Textbox) incDetails.query("#txtPrinterPath")).getValue(),
												objectUsingThisIds,
												objectUsingThisNames));
		return super.getTrackedObject().modify();
	}
	
	public void initForm(Include incDetails, Integer printerProfileId) throws Exception {
		
		PrinterProfile printerProfile = new PrinterProfile(printerProfileId);
		printerProfile.get();		
		fillForm(incDetails, printerProfile.getId());
	}
}
