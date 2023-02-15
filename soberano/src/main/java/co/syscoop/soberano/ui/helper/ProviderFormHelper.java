package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.Provider;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class ProviderFormHelper {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void fillTheForm(Include incDetails, Treeitem treeItem) throws SQLException {
		
		Tree treeObjects = treeItem.getTree();
		TreeNode treeNode= (TreeNode) ZKUtilitity.getAssociatedNode(treeItem, treeObjects);
		fillTheForm(incDetails, (DefaultTreeNode<NodeData>) treeNode);
	}

	public static void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Provider provider = new Provider(((DomainObject) data.getData().getValue()).getId());
		provider.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(provider.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(provider.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), provider.getName());
	}
}
