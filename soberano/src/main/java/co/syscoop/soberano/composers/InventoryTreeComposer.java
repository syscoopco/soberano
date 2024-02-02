package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import co.syscoop.soberano.models.WarehouseTreeModelPopulator;

@SuppressWarnings("serial")
public class InventoryTreeComposer extends GenericForwardComposer<Window> {
	
	private Tree treeInventory;
	
	@Override
	public void doAfterCompose(Window div) throws Exception{
		
		super.doAfterCompose(div);		
		WarehouseTreeModelPopulator.rerenderInventoryTree(treeInventory);
	}
}
