package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@SuppressWarnings({ "serial", "rawtypes" })
public class TicketTextboxComposer extends SelectorComposer {
	
	@Wire
	private Window wndTicket;
	
	@Wire
	private Textbox txtTicket;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onDoubleClick = textbox#txtTicket")
    public void txtTicket_onDoubleClick() throws SQLException {
		
		try {
			Window window = (Window) Executions.createComponents("./zoomed_in_ticket.zul", wndTicket, null);
			window.setTitle("_");
			
			Textbox txtZoomedInTicket = (Textbox) window.query("#txtZoomedInTicket");
			txtZoomedInTicket.setValue(txtTicket.getValue());			
			
			window.doModal();
		}
		catch(Exception ex) {
		}	
	}
}