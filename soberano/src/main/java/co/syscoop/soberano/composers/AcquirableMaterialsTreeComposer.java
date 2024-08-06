package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.AcquirableMaterial;
import co.syscoop.soberano.models.AcquirableMaterialTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class AcquirableMaterialsTreeComposer extends SelectorComposer {
	
	@Wire
	private Tree treeObjects;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);	
        Checkbox chkShowOnlyUsedOnes = (Checkbox) treeObjects.getParent().getPreviousSibling().query("#chkShowOnlyUsedOnes");
        AcquirableMaterial am = new AcquirableMaterial(chkShowOnlyUsedOnes.isChecked());
        new AcquirableMaterialTreeModelPopulator(am, treeObjects, "/acquirable_materials.zul").rerenderDomainObjectTree();
	}
}