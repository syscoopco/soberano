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
import co.syscoop.soberano.ui.helper.ProductionLineFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class AddCostCenterToProductionLineButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren tchdnCostCenters;
	
	@Wire
	private Combobox cmbCostCenter;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAddCostCenter")
    public void btnAddCostCenter_onClick() {
		
		try{
			//if the tree has no items,
			if (tchdnCostCenters.getChildren().size() == 0) {
				ProductionLineFormHelper.addCostCenter(cmbCostCenter.getSelectedItem().getLabel(), 
															((DomainObject) cmbCostCenter.getSelectedItem().getValue()).getId(),
															tchdnCostCenters);
			}
			else {
				int i = 0;
				for (Component item : tchdnCostCenters.getChildren()) {
					
					//if that item was already added,
					if ((((Treeitem) item).getValue()).equals(((DomainObject) cmbCostCenter.getSelectedItem().getValue()).getId())) {
						break;
					}
					else i++;
				}
				
				//if that item wasn't already added,
				if (i == tchdnCostCenters.getChildren().size()) {
					ProductionLineFormHelper.addCostCenter(cmbCostCenter.getSelectedItem().getLabel(),
															((DomainObject) cmbCostCenter.getSelectedItem().getValue()).getId(),
															tchdnCostCenters);
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