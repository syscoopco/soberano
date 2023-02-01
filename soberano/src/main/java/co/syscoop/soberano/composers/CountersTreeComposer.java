package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.Counter;
import co.syscoop.soberano.models.CounterTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class CountersTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new CounterTreeModelPopulator(new Counter(), treeObjects, "/counters.zul").rerenderDomainObjectTree();
	}
}