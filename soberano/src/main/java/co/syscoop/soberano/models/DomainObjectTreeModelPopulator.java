package co.syscoop.soberano.models;

import java.sql.SQLException;
import java.util.List;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.renderers.DomainObjectTreeNodeRenderer;

public class DomainObjectTreeModelPopulator {
	
	private DomainObject doo = null;
	private Tree dooTree = null;
	private DomainObjectTreeNodeRenderer nodeRenderer = null;
	
	public DomainObjectTreeModelPopulator(DomainObject doo, Tree dooTree, DomainObjectTreeNodeRenderer nodeRenderer) {
		this.doo = doo;
		this.dooTree = dooTree;
		this.nodeRenderer = nodeRenderer;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TreeModel populateTreeModel() throws SQLException {
		
		List<DomainObject> doos = doo.getAll(false);
		TreeNode rootNode = new DefaultTreeNode(null, (TreeNode[]) null);
		for (DomainObject doo : doos) {
			TreeNode dooNode = new DefaultTreeNode(new NodeData(doo.getName(), doo));
			rootNode.add(dooNode);
		}
		return new DefaultTreeModel(rootNode);
	};
	
	public void rerenderDomainObjectTree() throws SQLException {
		
		dooTree.setModel(populateTreeModel());
		dooTree.setItemRenderer(nodeRenderer);
	}
}
