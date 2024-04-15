package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.PrinterProfile;
import co.syscoop.soberano.models.PrinterProfileTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class PrinterProfilesTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
		new PrinterProfileTreeModelPopulator(new PrinterProfile(), treeObjects, "/printer_profiles.zul").rerenderDomainObjectTree();
	}
}