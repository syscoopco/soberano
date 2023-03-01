package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.CostCenter;
import co.syscoop.soberano.models.CostCenterTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class CostCentersTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new CostCenterTreeModelPopulator(new CostCenter(), treeObjects, "/cost_centers.zul").rerenderDomainObjectTree();
	}
}