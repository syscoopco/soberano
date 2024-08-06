package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Tree;
import co.syscoop.soberano.domain.tracked.AcquirableMaterial;
import co.syscoop.soberano.models.AcquirableMaterialTreeModelPopulator;
import co.syscoop.soberano.view.viewmodel.AcquirableMaterialSelectionViewModel;

@SuppressWarnings({ "serial", "rawtypes" })
public class ShowOnlyUsedAcquirableMaterialsComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbIntelliSearch;
	
	@Wire
	private Checkbox chkShowOnlyUsedOnes;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onCheck = checkbox#chkShowOnlyUsedOnes")
    public void chkDishonored_onCheck() throws Throwable {
		AcquirableMaterial am = new AcquirableMaterial(chkShowOnlyUsedOnes.isChecked());
		Tree treeObjects = (Tree) chkShowOnlyUsedOnes.getParent().
														getParent().
														getParent().
														getParent().query("panelchildren").query("tree");
		new AcquirableMaterialTreeModelPopulator(am, treeObjects, "/acquirable_materials.zul").rerenderDomainObjectTree();
		AcquirableMaterialSelectionViewModel amSelectionViewModel = new AcquirableMaterialSelectionViewModel(chkShowOnlyUsedOnes.isChecked());
		cmbIntelliSearch.setModel(amSelectionViewModel.getModel());
	}
}