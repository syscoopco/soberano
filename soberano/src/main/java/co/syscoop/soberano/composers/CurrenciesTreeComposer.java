package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.models.CurrencyTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class CurrenciesTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new CurrencyTreeModelPopulator(new Currency(), treeObjects, "/currencies.zul").rerenderDomainObjectTree();
	}
}