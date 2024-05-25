package co.syscoop.soberano.models;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.renderers.OrderSplittingTreeNodeRenderer;
import co.syscoop.soberano.util.rowdata.OrderRowData;
import co.syscoop.soberano.vocabulary.Labels;

public class OrderSplittingTreeModelPopulator {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TreeModel populateTreeModel() throws SQLException {
		
		List<Object> orderObjects = (new Order()).getOngoing();
		
		String currentCounter = "";
		TreeNode currentNode = null;
		TreeNode rootNode = new DefaultTreeNode(null, (TreeNode[]) null);
		
		//for each current order
		for (Object orderObject : orderObjects) {
			
			OrderRowData order = (OrderRowData) orderObject;
			
			//form order node label
			String orderId = Integer.toString(order.getOrderId()); 
			String orderNodeLabel = (order.getLabel() == null ? orderId : orderId + " (" + order.getLabel() + ")");
			TreeNode orderNode = new DefaultTreeNode(new NodeData(orderNodeLabel, orderId));
			
			//if the table node wasn't already added
			if (!currentCounter.equals(order.getCounter())) {
				
				//update current counter
				currentCounter = order.getCounter();
				
				//add counter node
				currentNode = new DefaultTreeNode(new NodeData(currentCounter, null), (TreeNode[]) null);
				
				//add counter node as a root node child
				rootNode.add(currentNode);
			}
			
			//add order node as a child of table node
			currentNode.add(orderNode);
		}
				
		//populate the tree model
		return new DefaultTreeModel(rootNode);
	};
	
	public static void rerenderActivityTree(Tree activityTree) {
		
		try{
			activityTree.setModel(OrderSplittingTreeModelPopulator.populateTreeModel());
			activityTree.setItemRenderer(new OrderSplittingTreeNodeRenderer());
		}
		catch(NullPointerException ex){
  			Messagebox.show(Labels.getLabel("message.information.TheresNoCurrentOrders"), 
		  					Labels.getLabel("messageBoxTitle.Information"), 
							0, 
							Messagebox.INFORMATION);
  		}
		catch(Exception ex){
  			Messagebox.show(org.zkoss.util.resource.Labels.getLabel("error.pageActivity.PopulatingActivityTree"), 
  					org.zkoss.util.resource.Labels.getLabel("messageBoxTitle.Error"), 
					0, 
					Messagebox.ERROR);
  		}
	}
}
