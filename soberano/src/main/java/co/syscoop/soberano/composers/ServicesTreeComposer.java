package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.Service;
import co.syscoop.soberano.models.ServiceTreeModelPopulator;

@SuppressWarnings({ "rawtypes", "serial" })
public class ServicesTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new ServiceTreeModelPopulator(new Service(), treeObjects, "/thirdparty_services.zul").rerenderDomainObjectTree();
	}
}