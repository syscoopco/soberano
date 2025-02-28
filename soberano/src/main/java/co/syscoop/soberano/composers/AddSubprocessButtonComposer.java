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
import co.syscoop.soberano.ui.helper.ProcessFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class AddSubprocessButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren tchdnSubprocesses;
	
	@Wire
	private Combobox cmbSubprocess;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAddSubprocess")
    public void btnAddSubprocess_onClick() {
		
		try{
			//if the tree has no items,
			if (tchdnSubprocesses.getChildren().size() == 0) {
				ProcessFormHelper.addSubprocess(cmbSubprocess.getSelectedItem().getLabel(), 
											((DomainObject) cmbSubprocess.getSelectedItem().getValue()).getId(),
											tchdnSubprocesses);
			}
			else {
				int i = 0;
				for (Component item : tchdnSubprocesses.getChildren()) {
					
					//if that item was already added,
					if ((((Treeitem) item).getValue()).equals(((DomainObject) cmbSubprocess.getSelectedItem().getValue()).getId())) {
						break;
					}
					else i++;
				}
				
				//if that item wasn't already added,
				if (i == tchdnSubprocesses.getChildren().size()) {
					ProcessFormHelper.addSubprocess(cmbSubprocess.getSelectedItem().getLabel(), 
												((DomainObject) cmbSubprocess.getSelectedItem().getValue()).getId(),
												tchdnSubprocesses);
				}
			}
		}
		catch(Exception ex) {
			Messagebox.show(Labels.getLabel("message.validation.selectAnItemFromTheList"), 
						  					Labels.getLabel("messageBoxTitle.Warning"), 
											0, 
											Messagebox.EXCLAMATION);
		}
    }
}