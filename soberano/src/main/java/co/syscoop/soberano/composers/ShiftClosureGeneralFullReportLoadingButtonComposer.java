package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.Mobile;

@SuppressWarnings({ "serial" })
public class ShiftClosureGeneralFullReportLoadingButtonComposer extends ShiftClosureReportLoadingComposer {
	
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnGeneralFull")
    public void btnRetrieve_onClick() throws SoberanoException {
		
		Window wndShowingAll = null;
		if (Mobile.isMobile()) {
			wndShowingAll = (Window) btnGeneral.getParent().getParent().getParent().getParent().getParent().getParent().getParent().query("#wndShowingAll");
		}
		else {
			wndShowingAll = (Window) btnGeneral.getParent().getParent().getParent().getParent().query("#wndShowingAll");
		}
		
		loadReport((Textbox) wndShowingAll.query("#txtShownReport"),
					"generalfull", null);
		updateComponentStyles("btnGeneralFull");
    }
}