package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;
import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.models.WorkerTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class WorkersTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new WorkerTreeModelPopulator(new Worker(), treeObjects, "/workers.zul").rerenderDomainObjectTree();
	}
}