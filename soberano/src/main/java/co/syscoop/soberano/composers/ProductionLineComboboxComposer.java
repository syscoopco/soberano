package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProductionLineComboboxComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbProductionLine;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void showProductionLine() throws Exception {
		
		try {
			if (cmbProductionLine.getSelectedItem() != null) {
				Executions.getCurrent().sendRedirect("/production_line_board.zul?id=" + ((DomainObject) cmbProductionLine.getSelectedItem().getValue()).getId().toString(), "_blank");
			}
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
	}
	
	@Listen("onSelect = combobox#cmbProductionLine")
    public void cmbProductionLine_onSelect() throws Exception {
		showProductionLine();		
    }
    	
	@Listen("onChange = combobox#cmbProductionLine")
    public void cmbProductionLine_onChange() throws Exception {
		showProductionLine();		
	}
}