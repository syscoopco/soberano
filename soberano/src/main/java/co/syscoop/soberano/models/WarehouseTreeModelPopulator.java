package co.syscoop.soberano.models;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeansException;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

import co.syscoop.soberano.domain.tracked.Warehouse;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.renderers.InventoryTreeNodeRenderer;
import co.syscoop.soberano.renderers.WarehouseTreeNodeRenderer;

public class WarehouseTreeModelPopulator extends DomainObjectTreeModelPopulator {

	//pageToRefreshZulURI is for operations requiring to refresh a page after they complete, like object deletion
	public WarehouseTreeModelPopulator(DomainObject doo, Tree dooTree, String pageToRefreshZulURI) {
		super(doo, dooTree, new WarehouseTreeNodeRenderer(pageToRefreshZulURI));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static TreeModel populateInventoryTreeModel() throws BeansException, SQLException {
		
		List<DomainObject> warehouseObjects = (new Warehouse()).getAll(false);
		TreeNode rootNode = new DefaultTreeNode(null, (TreeNode[]) null);		
		for (DomainObject warehouseObject : warehouseObjects) {
			TreeNode currentNode = new DefaultTreeNode(new NodeData(warehouseObject.getName(), null), (TreeNode[]) null);
			currentNode.add(new DefaultTreeNode(new NodeData("", warehouseObject.getId())));
			rootNode.add(currentNode);			
		} return new DefaultTreeModel(rootNode);
	};
	
	public static void rerenderInventoryTree(Tree treeInventory) throws SQLException {
		
		treeInventory.setModel(populateInventoryTreeModel());
		treeInventory.setItemRenderer(new InventoryTreeNodeRenderer());
	}
}
