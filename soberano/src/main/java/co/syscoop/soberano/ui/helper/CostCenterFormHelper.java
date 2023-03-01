package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.CostCenter;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class CostCenterFormHelper {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void fillTheForm(Include incDetails, Treeitem treeItem) throws SQLException {
		
		Tree treeObjects = treeItem.getTree();
		TreeNode treeNode= (TreeNode) ZKUtilitity.getAssociatedNode(treeItem, treeObjects);
		fillTheForm(incDetails, (DefaultTreeNode<NodeData>) treeNode);
	}

	public static void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		CostCenter costCenter = new CostCenter(((DomainObject) data.getData().getValue()).getId());
		costCenter.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(costCenter.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(costCenter.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), costCenter.getName());
		
		if (costCenter.getInputWarehouse() > 0) 
			ZKUtilitity.setValueWOValidation((Combobox) incDetails.query("#cmbInputWarehouse"), costCenter.getInputWarehouse());
		else
			((Combobox) incDetails.query("#cmbInputWarehouse")).setSelectedItem(null);
			
		if (costCenter.getOutputWarehouse() > 0) 
			ZKUtilitity.setValueWOValidation((Combobox) incDetails.query("#cmbOutputWarehouse"), costCenter.getOutputWarehouse());
		else
			((Combobox) incDetails.query("#cmbOutputWarehouse")).setSelectedItem(null);
	}
}
