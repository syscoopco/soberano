package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.exception.SoberanoException;

@SuppressWarnings({ "serial" })
public class ShiftClosureReceivablesReportLoadingButtonComposer extends ShiftClosureReportLoadingComposer {
	
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnReceivables")
    public void btnRetrieve_onClick() throws SoberanoException {
		
		loadReport((Textbox) btnReceivables.getParent().getParent().getParent().getParent().query("#wndShowingAll").query("#txtShownReport"),
					"receivables", 
					null);
		updateComponentStyles("btnReceivables");
    }
}