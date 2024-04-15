package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.ProductionLine;
import co.syscoop.soberano.models.ProductionLineTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProductionLinesTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new ProductionLineTreeModelPopulator(new ProductionLine(), treeObjects, "/production_lines.zul").rerenderDomainObjectTree();
	}
}