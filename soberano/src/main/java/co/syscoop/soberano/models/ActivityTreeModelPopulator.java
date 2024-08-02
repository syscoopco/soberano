package co.syscoop.soberano.models;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeansException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.rowdata.OrderRowData;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.renderers.ActivityTreeNodeRenderer;

public class ActivityTreeModelPopulator {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TreeModel populateTreeModel() throws BeansException, SQLException {
		
		List<Object> orderObjects = (new Order()).getOngoing();
		
		String currentCounter = "";
		TreeNode currentNode = null;
		TreeNode rootNode = new DefaultTreeNode(null, (TreeNode[]) null);
		
		for (Object orderObject : orderObjects) {
			
			OrderRowData order = (OrderRowData) orderObject;	
			
			//there's not ZK web application context under testing
			if (!SpringUtility.underTesting()) {
				
				//in case ongoing object is an order,
				if (order.getObjectType() == 0) {					
					HashMap<Integer, HashMap<Integer, Boolean>> printedAllocationsStore = 
							((HashMap<Integer, HashMap<Integer, Boolean>>) Executions.getCurrent().
																					getDesktop().
																					getWebApp().
																					getAttribute("printed_allocations"));
					
					//in case printed allocations store doesn't include an entry for the order,					
					if (printedAllocationsStore.get(order.getOrderId()) == null) {
						
						//initialize order's printed allocations store
						((HashMap<Integer, HashMap<Integer, Boolean>>) Executions.
								getCurrent().
								getDesktop().
								getWebApp().
								getAttribute("printed_allocations")).put(order.getOrderId(), 
																		new HashMap<Integer, Boolean>());
					}
				}
			}	
					
			String orderId = order.getOrderId().toString(); 
			String orderLabel = order.getLabel() == null || order.getLabel().isEmpty() ? orderId : orderId + " (" + order.getLabel() + ")";
			TreeNode orderNode = new DefaultTreeNode(new NodeData(orderLabel, order));
			if (!currentCounter.equals(order.getCounter())) {
				currentCounter = order.getCounter();
				currentNode = new DefaultTreeNode(new NodeData(currentCounter, null), (TreeNode[]) null);
				rootNode.add(currentNode);
			}
			currentNode.add(orderNode);
		} return new DefaultTreeModel(rootNode);
	};
	
	public static void rerenderActivityTree(Tree activityTree) {
		
		try{
			activityTree.setModel(ActivityTreeModelPopulator.populateTreeModel());
			activityTree.setItemRenderer(new ActivityTreeNodeRenderer());
		}
		catch(Exception ex){
			try {
				ExceptionTreatment.logAndShow(ex, 
						ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
			} catch (SoberanoException e) {
				e.printStackTrace();
				e.fillInStackTrace();
			}
  		}
	}
}
