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

import co.syscoop.soberano.ui.helper.WorkerFormHelper;

@SuppressWarnings({ "rawtypes", "serial" })
public class AssignResponsibilityButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren tchdnResponsibilities;
	
	@Wire
	private Combobox cmbResponsibilities;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAssignResponsibility")
    public void btnAssignResponsibility_onClick() {
		
		try{
			//if the tree has no responsibilities,
			if (tchdnResponsibilities.getChildren().size() == 0) {
				WorkerFormHelper.addResponsibilityItem(cmbResponsibilities.getSelectedItem().getLabel(), 
														cmbResponsibilities.getSelectedItem().getValue(), 
														tchdnResponsibilities);
			}
			else {
				int i = 0;
				for (Component item : tchdnResponsibilities.getChildren()) {
					
					//if that responsibility was already added,
					if (((String) ((Treeitem) item).getValue()).equals(cmbResponsibilities.getSelectedItem().getValue())) {
						break;
					}
					else i++;
				}
				
				//if that responsibility wasn't already added,
				if (i == tchdnResponsibilities.getChildren().size()) {
					WorkerFormHelper.addResponsibilityItem(cmbResponsibilities.getSelectedItem().getLabel(), 
															cmbResponsibilities.getSelectedItem().getValue(), 
															tchdnResponsibilities);
				}
			}
		}
		catch(Exception ex) {
			Messagebox.show(Labels.getLabel("message.validation.worker.SelectAResponsibility"), 
						  					Labels.getLabel("messageBoxTitle.Warning"), 
											0, 
											Messagebox.EXCLAMATION);
		}
    }
}