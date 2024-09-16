package co.syscoop.soberano.composers;

import java.math.BigDecimal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.ui.helper.ProcessFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class AddProcessOutputButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren tchdnOutputs;
	
	@Wire
	private Combobox cmbOutput;
	
	@Wire 
	private Decimalbox decOutputQuantity;
	
	@Wire
	private Combobox cmbOutputUnit;
	
	@Wire
	private Intbox intWeight;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAddOutput")
    public void btnAddOutput_onClick() {
		
		try{
			//if the tree has no items,
			if (tchdnOutputs.getChildren().size() == 0) {
				ProcessFormHelper.addOutput(cmbOutput.getSelectedItem().getLabel(), 
											((DomainObject) cmbOutput.getSelectedItem().getValue()).getStringId(),
											decOutputQuantity.getValue(),
											cmbOutputUnit.getSelectedItem().getLabel(), 
											Integer.parseInt(cmbOutputUnit.getSelectedItem().getValue()),
											intWeight.getValue(),
											tchdnOutputs,
											false,
											new BigDecimal(1.0));
			}
			else {
				int i = 0;
				for (Component item : tchdnOutputs.getChildren()) {
					
					//if that item was already added,
					if ((((Treeitem) item).getValue()).equals(((DomainObject) cmbOutput.getSelectedItem().getValue()).getStringId())) {
						break;
					}
					else i++;
				}
				
				//if that item wasn't already added,
				if (i == tchdnOutputs.getChildren().size()) {
					ProcessFormHelper.addOutput(cmbOutput.getSelectedItem().getLabel(), 
												((DomainObject) cmbOutput.getSelectedItem().getValue()).getStringId(),
												decOutputQuantity.getValue(),
												cmbOutputUnit.getSelectedItem().getLabel(), 
												Integer.parseInt(cmbOutputUnit.getSelectedItem().getValue()),
												intWeight.getValue(),
												tchdnOutputs,
												false,
												new BigDecimal(1.0));
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