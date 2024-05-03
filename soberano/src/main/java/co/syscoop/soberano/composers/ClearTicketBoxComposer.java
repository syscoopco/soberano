package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

@SuppressWarnings({ "rawtypes", "serial" })
public class ClearTicketBoxComposer extends SelectorComposer {
	
	@Wire
	private Textbox txtReport;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onChange = intbox#intOrderNumber")
    public void intOrderNumber_onChange() {
		
		txtReport.setValue("");
    }
}