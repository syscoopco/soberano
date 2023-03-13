package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.ui.helper.WarehouseFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class AddEntryProcessButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren tchdnEntryProcesses;
	
	@Wire
	private Combobox cmbEntryProcesses;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAddEntryProcess")
    public void btnAddEntryProcess_onClick() {
		
		try{
			//if the tree has no processes,
			if (tchdnEntryProcesses.getChildren().size() == 0) {
				WarehouseFormHelper.addEntryProcessItem(cmbEntryProcesses.getSelectedItem().getLabel(), 
														((DomainObject) cmbEntryProcesses.getSelectedItem().getValue()).getId().toString(), 
														tchdnEntryProcesses);
			}
			else {
				int i = 0;
				for (Component item : tchdnEntryProcesses.getChildren()) {
					
					//if that process was already added,
					if (((String) ((Treeitem) item).getValue()).equals(((DomainObject) cmbEntryProcesses.getSelectedItem().getValue()).getId().toString())) {
						break;
					}
					else i++;
				}
				
				//if that process wasn't already added,
				if (i == tchdnEntryProcesses.getChildren().size()) {
					WarehouseFormHelper.addEntryProcessItem(cmbEntryProcesses.getSelectedItem().getLabel(), 
															((DomainObject) cmbEntryProcesses.getSelectedItem().getValue()).getId().toString(), 
															tchdnEntryProcesses);
				}
			}
		}
		catch(Exception ex) {
			Messagebox.show(Labels.getLabel("message.validation.warehouse.SelectAProcess"), 
						  					Labels.getLabel("messageBoxTitle.Warning"), 
											0, 
											Messagebox.EXCLAMATION);
		}
    }
}