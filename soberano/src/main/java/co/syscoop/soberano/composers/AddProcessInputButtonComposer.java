package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.ui.helper.ProcessFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class AddProcessInputButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren tchdnInputs;
	
	@Wire
	private Combobox cmbInput;
	
	@Wire 
	private Decimalbox decInputQuantity;
	
	@Wire
	private Combobox cmbInputUnit;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAddInput")
    public void btnAddInput_onClick() {
		
		try{
			//if the tree has no items,
			if (tchdnInputs.getChildren().size() == 0) {
				ProcessFormHelper.addInput(cmbInput.getSelectedItem().getLabel(), 
											((DomainObject) cmbInput.getSelectedItem().getValue()).getStringId(),
											decInputQuantity.getValue(),
											cmbInputUnit.getSelectedItem().getLabel(), 
											Integer.parseInt(cmbInputUnit.getSelectedItem().getValue()),
											tchdnInputs);
			}
			else {
				int i = 0;
				for (Component item : tchdnInputs.getChildren()) {
					
					//if that item was already added,
					if ((((Treeitem) item).getValue()).equals(((DomainObject) cmbInput.getSelectedItem().getValue()).getStringId())) {
						break;
					}
					else i++;
				}
				
				//if that item wasn't already added,
				if (i == tchdnInputs.getChildren().size()) {
					ProcessFormHelper.addInput(cmbInput.getSelectedItem().getLabel(), 
												((DomainObject) cmbInput.getSelectedItem().getValue()).getStringId(),
												decInputQuantity.getValue(),
												cmbInputUnit.getSelectedItem().getLabel(), 
												Integer.parseInt(cmbInputUnit.getSelectedItem().getValue()),
												tchdnInputs);
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