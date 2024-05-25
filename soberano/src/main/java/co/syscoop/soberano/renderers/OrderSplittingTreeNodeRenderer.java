package co.syscoop.soberano.renderers;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import co.syscoop.soberano.models.NodeData;

public class OrderSplittingTreeNodeRenderer implements TreeitemRenderer<DefaultTreeNode<NodeData>> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void render(Treeitem item, DefaultTreeNode<NodeData> data, int index) throws Exception {
		
		String tableToHighlight = "";
		try{
			String queryString = Executions.getCurrent().getDesktop().getQueryString();
			tableToHighlight = (queryString.substring(queryString.indexOf("table=") + 6, queryString.length())).replace("%20", " ");
		}
		catch(StringIndexOutOfBoundsException ex) {}
		catch(NullPointerException ex) {}
		
		NodeData nodeData = data.getData();
		Treerow tr = new Treerow();
		item.appendChild(tr);
		tr.appendChild(new Treecell(nodeData.getLabel()));
		
		//highlight the node related to the table specified in the URL
		if (nodeData.getLabel().trim().equals(tableToHighlight.trim())) {
			item.setSelected(true);
		}
		
		//if it is an order node,
		if (nodeData.getValue() != null) {
			//add listener to click event
			item.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					Executions.sendRedirect("/split_order.zul?from=" + nodeData.getValue());	
				}
			});
		}
		//it is a table node
		else {
			//add listener to click event
			/* do nothing when clicking a table node
			item.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					Executions.sendRedirect("/split_order.zul?table=" + nodeData.getLabel());	
				}
			});
			*/
		}
		item.setOpen(true);
	}
}
