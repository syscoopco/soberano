package co.syscoop.soberano.models;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.BeansException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.OrderedItem;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.renderers.OrderedItemTreeNodeRenderer;

public class OrderOrderedItemsTreeModelPopulator {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TreeModel populateTreeModel(int orderId) throws BeansException, SQLException {
		
		//get ordered items within that order. only items that are products and measured in piece.
		List<Object> orderedItems = (new Order(orderId)).getOrderedItems();
		
		TreeNode rootNode = new DefaultTreeNode(null, (TreeNode[]) null);
		
		//for each ordered item
		for (Object orderedItemRecord : orderedItems) {
			
			//form ordered item node
			TreeNode orderedItemNode = new DefaultTreeNode(new NodeData(((OrderedItem) orderedItemRecord).getProductName() + " : " + ((OrderedItem) orderedItemRecord).getInstructions(), orderedItemRecord));
			
			//add item node as a root node child
			rootNode.add(orderedItemNode);
		}
				
		//populate the tree model
		return new DefaultTreeModel(rootNode);
	};
	
	public static void rerenderOrderedItemsTree(Tree orderedItemsTree, int orderId, Integer originalOrder, Integer targetOrder) throws SoberanoException {
		
		try{
			orderedItemsTree.setModel(OrderOrderedItemsTreeModelPopulator.populateTreeModel(orderId));
			orderedItemsTree.setItemRenderer(new OrderedItemTreeNodeRenderer());
		}
		catch(NullPointerException ex){
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.pageSplitOrder.NoItemHasBeenOrderedYet"), 
					Labels.getLabel("messageBoxTitle.Information"),
					Messagebox.INFORMATION);
  		}
		catch(Exception ex){
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
  		}
	}
}
