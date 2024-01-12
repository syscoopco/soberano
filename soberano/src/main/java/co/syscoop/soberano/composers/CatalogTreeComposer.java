package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import co.syscoop.soberano.models.CatalogTreeModelPopulator;

@SuppressWarnings("serial")
public class CatalogTreeComposer extends GenericForwardComposer<Window> {
	
	private Tree treeCatalog;
	
	@Override
	public void doAfterCompose(Window div) throws Exception{
		
		super.doAfterCompose(div);		
		CatalogTreeModelPopulator.rerenderCatalogTree(treeCatalog);
	}
}
