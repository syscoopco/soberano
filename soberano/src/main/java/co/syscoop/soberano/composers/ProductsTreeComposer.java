package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.Product;
import co.syscoop.soberano.models.ProductTreeModelPopulator;

@SuppressWarnings({ "rawtypes", "serial" })
public class ProductsTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new ProductTreeModelPopulator(new Product(), treeObjects, "/products.zul").rerenderDomainObjectTree();
	}
}