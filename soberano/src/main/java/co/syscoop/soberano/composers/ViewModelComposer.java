package co.syscoop.soberano.composers;

import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ZKUtilitity;

@SuppressWarnings({ "serial", "rawtypes" })
public class ViewModelComposer extends SelectorComposer {
	
	@Wire
	protected Combobox cmbIntelliSearch;
	
	protected void fillForm(Include incDetails, Treeitem treeItem) throws SQLException {};
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onChange = combobox#cmbIntelliSearch")
    public void cmbIntelliSearch_onChange() throws SQLException {
	
		ZKUtilitity.processItemSelection(cmbIntelliSearch);
	}
	
	/*
	 * Needed for testing. 
	 * cmbIntelliSearch_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbIntelliSearch")
    public void cmbIntelliSearch_onClick() throws SQLException {
	
		if (SpringUtility.underTesting()) ZKUtilitity.processItemSelection(cmbIntelliSearch);
	}
}