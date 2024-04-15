package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.ProductionLine;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class ProductionLineFormHelper extends TrackedObjectFormHelper {
	
	private ArrayList<Integer> objectUsingThisIds = new ArrayList<Integer>();
	private ArrayList<String> objectUsingThisNames = new ArrayList<String>();
	
	public static void addCostCenter(String objectQualifiedName,
										Integer entityTypeInstanceId,
										Treechildren treeCostCenters) {
		
			Treeitem treeItem = new Treeitem(objectQualifiedName, entityTypeInstanceId);
			Treecell treeCell = new Treecell();
			
			Hbox hbox = new Hbox();
			treeCell.appendChild(hbox);
			
			ZKUtilitity.addRowDeletionButton("btnInputRowDeletion" + entityTypeInstanceId, hbox);
			
			treeItem.getTreerow().appendChild(treeCell);
			treeCostCenters.appendChild(treeItem);
	}
		
	static private void fillArrays(Include incDetails,
								ArrayList<Integer> objectUsingThisIds,
								ArrayList<String> objectUsingThisNames) {
		Treechildren treeCostCenters = (Treechildren) incDetails.query("#treeCostCenters");
		objectUsingThisIds.clear();
		objectUsingThisNames.clear();
		for (Component item : treeCostCenters.getChildren()) {
			objectUsingThisIds.add(((Treeitem) item).getValue());
			objectUsingThisNames.add(((Treeitem) item).getLabel());
		}
	}
	
	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtName"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		((Treechildren) incDetails.query("#treeCostCenters")).getChildren().clear();
	}
	
	public void fillForm(Include incDetails, Integer id) throws SQLException {
		
		ProductionLine productionLine = new ProductionLine(id);
		productionLine.get();		
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(productionLine.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(productionLine.getStringId());
			
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), productionLine.getQualifiedName());
					
		Treechildren treeCostCenters = (Treechildren) incDetails.query("#treeCostCenters");
		treeCostCenters.getChildren().clear();
		for (Object printJobsSource : productionLine.getObjectsUsingThis()) {
			addCostCenter(((DomainObject) printJobsSource).getQualifiedName(),
									((DomainObject) printJobsSource).getEntityTypeInstanceId(),
									treeCostCenters);
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
		return (new ProductionLine(0,
									0,
									((Textbox) incDetails.query("#txtName")).getValue(),
									objectUsingThisIds,
									objectUsingThisNames)).record();
	}
	
	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		fillArrays(incDetails,
				objectUsingThisIds,
				objectUsingThisNames);
		super.setTrackedObject(new ProductionLine(0,
												0,
												((Textbox) incDetails.query("#txtName")).getValue(),
												objectUsingThisIds,
												objectUsingThisNames));
		return super.getTrackedObject().modify();
	}
	
	public void initForm(Include incDetails, Integer productionLineId) throws Exception {
		
		ProductionLine productionLine = new ProductionLine(productionLineId);
		productionLine.get();		
		fillForm(incDetails, productionLine.getId());
	}
}
