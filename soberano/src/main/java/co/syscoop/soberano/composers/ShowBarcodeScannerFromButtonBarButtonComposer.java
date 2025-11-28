package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Barcodescanner;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Tree;
import co.syscoop.soberano.models.ActivityTreeModelPopulator;
import co.syscoop.soberano.models.OrderSplittingTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes", "unused" })
public class ShowBarcodeScannerFromButtonBarButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnScanner;
	
	@Listen("onClick = button#btnScanner")
    public void btnScanner_onClick() {
		
		try {
			Borderlayout blayoutTicket = (Borderlayout) btnScanner.getParent().
																	getParent().
																	getParent().
																	getParent().
																		query("#wndContentPanel").
																		query("#wndOrderItems").
																		query("#blayoutTicket");

			Barcodescanner bcs = (Barcodescanner) blayoutTicket.query("barcodescanner");
			
			if ((Boolean) (Executions.getCurrent().getSession().getAttribute("barcode_scanner_is_enabled"))) {
				bcs.setEnable(false);
				bcs.setVisible(false);
				Executions.getCurrent().getSession().setAttribute("barcode_scanner_is_enabled", false);
			}
				else {
				bcs.setEnable(true);
				bcs.setVisible(true);
				Executions.getCurrent().getSession().setAttribute("barcode_scanner_is_enabled", true);			
			}
		}
		catch(Exception ex) {}
    }
}
