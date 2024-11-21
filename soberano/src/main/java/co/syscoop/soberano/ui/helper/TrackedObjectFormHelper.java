package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treeitem;
import co.syscoop.soberano.domain.tracked.TrackedObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public abstract class TrackedObjectFormHelper {
	
	private TrackedObject trackedObject = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void fillForm(Include incDetails, Treeitem treeItem) throws SQLException {
		
		Tree treeObjects = treeItem.getTree();
		TreeNode treeNode= (TreeNode) ZKUtilitity.getAssociatedNode(treeItem, treeObjects);
		fillForm(incDetails, (DefaultTreeNode<NodeData>) treeNode);
	}
	
	public void updateObjectTreeitemLabel(Include incDetails) {
		
		Tree treeObjects = (Tree) incDetails.getParent().query("#wndShowingAll").query("#treeObjects");
		
		//selected item is null when the object is loaded with an id passed
		//through URL query string, instead of clicking on a tree item.
		if (treeObjects != null && treeObjects.getSelectedItem() != null) {
			treeObjects.getSelectedItem().setLabel(trackedObject.getQualifiedName());
		}
	}

	public abstract void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException;
	
	public abstract void cleanForm(Include incDetails);
	
	public abstract Integer recordFromForm(Include incDetails) throws Exception;
	
	public abstract Integer modifyFromForm(Include incDetails) throws Exception;

	public TrackedObject getTrackedObject() {
		return trackedObject;
	}

	public void setTrackedObject(TrackedObject trackedObject) {
		this.trackedObject = trackedObject;
	}
}
