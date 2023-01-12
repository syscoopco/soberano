package co.syscoop.soberano.composers;

import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

@SuppressWarnings({ "serial", "rawtypes" })
public /*abstract*/ class ViewModelComposer extends SelectorComposer {
	
	@Wire
	protected Combobox cmbIntelliSearch;
	
	protected /*abstract*/ void fillTheForm(Include incDetails, Treeitem treeItem) throws SQLException {};
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void processItemSelection() {
		Tree treeObjects = (Tree) cmbIntelliSearch.query("#wndShowingAll").query("#treeObjects");
		for (Component comp : treeObjects.getTreechildren().getChildren()) {
			Treeitem ti = (Treeitem) comp;
			Treerow tr = (Treerow) (ti).getChildren().get(0);
			Treecell tc = (Treecell) (tr).getChildren().get(0);
			if (tc.getLabel().equals(cmbIntelliSearch.getText()) ) {
				treeObjects.setSelectedItem(ti);
				
				//call if abstract class version fillTheForm((Include) cmbIntelliSearch.query("#incDetails"), ti);
				
				//sendEvent is called in non abstract class version
				Events.sendEvent(Events.ON_CLICK, ti, null);
				
				Clients.scrollIntoView(ti);
				break;
			}
		}
	}
	
	@Listen("onChange = combobox#cmbIntelliSearch")
    public void cmbIntelliSearch_onChange() throws SQLException {
	
		processItemSelection();
	}
	
	/*
	 * Needed for testing. 
	 * cmbIntelliSearch_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbIntelliSearch")
    public void cmbIntelliSearch_onClick() throws SQLException {
	
		processItemSelection();
	}
}