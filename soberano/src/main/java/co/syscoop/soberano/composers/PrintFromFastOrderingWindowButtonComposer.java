package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class PrintFromFastOrderingWindowButtonComposer extends SelectorComposer {
	
	@Wire
	protected Button btnPrint;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
		try {
			super.doAfterCompose(comp);
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
    }

	@Listen("onClick = button#btnPrint")
    public void btnProduce_onClick() throws Throwable {
		
		try {
			Integer orderId = (Integer) btnPrint.query("#" + btnPrint.getAttribute("parentWindow")).getAttribute("orderId");
			PrintTicketButtonComposer.print(orderId,
											SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
											"records/tickets/" + 
											"TICKET_" + orderId + ".pdf", 
											"TICKET_" + orderId);
		}
		catch(Exception ex)	{
			throw ex;
		}
	}
}
