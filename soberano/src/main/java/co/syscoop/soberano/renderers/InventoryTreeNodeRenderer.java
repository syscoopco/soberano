package co.syscoop.soberano.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.A;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import co.syscoop.soberano.models.NodeData;

public class InventoryTreeNodeRenderer implements TreeitemRenderer<DefaultTreeNode<NodeData>> {

	@Override
	public void render(Treeitem item, DefaultTreeNode<NodeData> data, int index) throws Exception {
		
		NodeData nodeData = data.getData();
		Treerow tr = new Treerow();
		item.appendChild(tr);
		if (nodeData.getValue() != null) {
			Treecell wareHouseSubnodeTreeCell = new Treecell();
			wareHouseSubnodeTreeCell.setStyle("background-color:#D3D3D3;");
			Hbox box = new Hbox();
			wareHouseSubnodeTreeCell.appendChild(box);
			tr.appendChild(wareHouseSubnodeTreeCell);
			A a = new A();
			a.setLabel(Labels.getLabel("caption.action.stock"));
			a.setHref("/stock.zul?id=" + nodeData.getValue());
			box.appendChild(a);
			box.appendChild(new Separator("horizontal"));
			a = new A();
			a.setLabel(Labels.getLabel("caption.action.spi"));
			a.setHref("/spi.zul?id=" + nodeData.getValue());
			box.appendChild(a);
			box.appendChild(new Separator("horizontal"));
			a = new A();
			a.setLabel(Labels.getLabel("caption.action.operation"));
			a.setHref("/inventory_operations.zul?id=" + nodeData.getValue());
			box.appendChild(a);
			wareHouseSubnodeTreeCell.appendChild(new Separator("horizontal"));
			wareHouseSubnodeTreeCell.appendChild(new Separator("horizontal"));
			wareHouseSubnodeTreeCell.appendChild(new Separator("horizontal"));
		}
		else {
			tr.appendChild(new Treecell(nodeData.getLabel()));	
		}
		item.setOpen(true);
	}
}
