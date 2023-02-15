package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.Provider;
import co.syscoop.soberano.models.ProviderTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProvidersTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new ProviderTreeModelPopulator(new Provider(), treeObjects, "/providers.zul").rerenderDomainObjectTree();
	}
}