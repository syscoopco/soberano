package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Tree;
import co.syscoop.soberano.models.ActivityTreeModelPopulator;
import co.syscoop.soberano.models.OrderSplittingTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes", "unused" })
public class ShowTicketFromButtonBarButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnShowTicket;
	
	@Listen("onClick = button#btnShowTicket")
    public void btnShowTicket_onClick() {
		
		Component blayoutOrderItems = btnShowTicket.getParent().
											getParent().
											getParent().
											getParent().
												query("#wndContentPanel").
												query("#wndOrderItems").
												query("#blayoutOrderItems");
		
		if (blayoutOrderItems.isVisible()) {
			
			blayoutOrderItems.setVisible(false);
			
			btnShowTicket.getParent().
							getParent().
							getParent().
							getParent().
								query("#wndContentPanel").
								query("#wndOrderItems").
								query("#blayoutTicket").setVisible(true);
		}
		else {
			blayoutOrderItems.setVisible(true);
			
			btnShowTicket.getParent().
							getParent().
							getParent().
							getParent().
								query("#wndContentPanel").
								query("#wndOrderItems").
								query("#blayoutTicket").setVisible(false);
		}		
    }
}
