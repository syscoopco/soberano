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
import co.syscoop.soberano.ui.helper.InventoryOperationFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class AddInventoryItemToMoveRowButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren tchdnMove;
	
	@Wire
	private Combobox cmbMaterial;
	
	@Wire 
	private Decimalbox decQuantity;
	
	@Wire
	private Combobox cmbUnit;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAddItemToMoveRow")
    public void btnAddItemToMoveRow_onClick() {
		
		try{
			//if the tree has no items,
			if (tchdnMove.getChildren().size() == 0) {
				InventoryOperationFormHelper.addItemToMove(cmbMaterial.getSelectedItem().getLabel(), 
															((DomainObject) cmbMaterial.getSelectedItem().getValue()).getStringId(),
															decQuantity.getValue(),
															cmbUnit.getSelectedItem().getLabel(), 
															Integer.parseInt(cmbUnit.getSelectedItem().getValue()),
															tchdnMove);
			}
			else {
				int i = 0;
				for (Component item : tchdnMove.getChildren()) {
					
					//if that item was already added,
					if ((((Treeitem) item).getValue()).equals(((DomainObject) cmbMaterial.getSelectedItem().getValue()).getStringId())) {
						break;
					}
					else i++;
				}
				
				//if that item wasn't already added,
				if (i == tchdnMove.getChildren().size()) {
					InventoryOperationFormHelper.addItemToMove(cmbMaterial.getSelectedItem().getLabel(), 
																((DomainObject) cmbMaterial.getSelectedItem().getValue()).getStringId(),
																decQuantity.getValue(),
																cmbUnit.getSelectedItem().getLabel(), 
																Integer.parseInt(cmbUnit.getSelectedItem().getValue()),
																tchdnMove);
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