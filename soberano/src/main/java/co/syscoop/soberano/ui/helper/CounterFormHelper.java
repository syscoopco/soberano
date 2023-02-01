package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.Counter;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class CounterFormHelper {

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public void fillTheForm(Include incDetails, Treeitem treeItem) throws SQLException {
		
		Tree treeObjects = treeItem.getTree();
		TreeNode treeNode= (TreeNode) ZKUtilitity.getAssociatedNode(treeItem, treeObjects);
		fillTheForm(incDetails, (DefaultTreeNode<NodeData>) treeNode);
	}
	
	static public void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Counter counter = new Counter(((DomainObject) data.getData().getValue()).getId());
		counter.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(counter.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(counter.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), counter.getStringId());
		
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intNumberOfReceivers"), counter.getNumberOfReceivers());
		((Checkbox) incDetails.query("#chkIsSurcharged")).setChecked(counter.getIsSurcharged());
		((Checkbox) incDetails.query("#chkDisabled")).setChecked(!counter.getIsEnabled());
	}
}
