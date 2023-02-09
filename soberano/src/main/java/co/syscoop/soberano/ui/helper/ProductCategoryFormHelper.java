package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.ProductCategory;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class ProductCategoryFormHelper {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void fillTheForm(Include incDetails, Treeitem treeItem) throws SQLException {
		
		Tree treeObjects = treeItem.getTree();
		TreeNode treeNode= (TreeNode) ZKUtilitity.getAssociatedNode(treeItem, treeObjects);
		fillTheForm(incDetails, (DefaultTreeNode<NodeData>) treeNode);
	}

	public static void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		ProductCategory category = new ProductCategory(((DomainObject) data.getData().getValue()).getId());
		category.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(category.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(category.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), category.getName());
		
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPosition"), category.getPosition());
		((Checkbox) incDetails.query("#chkDisabled")).setChecked(!category.getIsEnabled());
	}
}
