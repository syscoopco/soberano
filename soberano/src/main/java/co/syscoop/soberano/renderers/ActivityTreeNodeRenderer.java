package co.syscoop.soberano.renderers;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.rowdata.OrderRowData;

public class ActivityTreeNodeRenderer implements TreeitemRenderer<DefaultTreeNode<NodeData>> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void render(Treeitem item, DefaultTreeNode<NodeData> data, int index) throws Exception {
		
		NodeData nodeData = data.getData();
		Treerow tr = new Treerow();
		item.appendChild(tr);
		tr.appendChild(new Treecell(nodeData.getLabel()));
		if (nodeData.getValue() != null) {
			
			//highlight the node related to the order specified in the URL
			String orderToHighlight = "";
			try{
				String queryString = Executions.getCurrent().getDesktop().getQueryString();
				orderToHighlight = (queryString.substring(queryString.indexOf("id=") + 3, queryString.length())).replace("%20", " ");
			}
			catch(StringIndexOutOfBoundsException ex) {}
			catch(NullPointerException ex) {}
			if (nodeData.getLabel().trim().contains(orderToHighlight.trim())) {
				item.setSelected(true);
				Clients.scrollIntoView(item);
			}
			
			item.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					OrderRowData ord = (OrderRowData) nodeData.getValue();
					if (ord.getObjectType() == 0)
						Executions.sendRedirect("/order.zul?id=" + ord.getOrderId());
					else if (ord.getObjectType() == 1)
						Executions.sendRedirect("/process_run.zul?id=" + ord.getOrderId());
				}
			});
		}
		else {
			item.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
				}
			});			
		}
		item.setOpen(true);
	}
}
