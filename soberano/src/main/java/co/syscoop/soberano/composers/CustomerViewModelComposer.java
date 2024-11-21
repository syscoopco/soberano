package co.syscoop.soberano.composers;

import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import co.syscoop.soberano.models.CustomersGridModel;

@SuppressWarnings({ "serial", "rawtypes" })
public class CustomerViewModelComposer extends SelectorComposer {
	
	@Wire
	protected Combobox cmbIntelliSearch;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void processParamSelection() throws SQLException {
		
		CustomersGridModel gridModel = null;
		gridModel = new CustomersGridModel(cmbIntelliSearch.getText());			
		Grid grd = (Grid) cmbIntelliSearch.query("#wndShowingAll").query("#grd");
		grd.setModel(gridModel);
	}
	
	@Listen("onChange = combobox#cmbIntelliSearch")
    public void cmbIntelliSearch_onChange() throws SQLException {
	
		processParamSelection();
	}
	
	@Listen("onClick = combobox#cmbIntelliSearch")
    public void cmbIntelliSearch_onClick() throws SQLException {
	
		processParamSelection();
	}
	
	@Listen("onSelect = combobox#cmbIntelliSearch")
    public void cmbIntelliSearch_onSelect() throws SQLException {
	
		processParamSelection();
	}
}