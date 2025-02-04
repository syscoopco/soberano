package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;

@SuppressWarnings({ "rawtypes", "serial" })
public class OpenShiftButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnOpenShift;
	
	@Wire
	private Button btnGoToClosures;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnOpenShift")
    public void btnOpenShift_onClick() throws Exception {
		
		try {
			Integer qryResult = new Order().openShift();
			((Window) btnOpenShift.getParent().getParent().getParent()).detach();
			if (qryResult == -1) {
				throw new NotEnoughRightsException();
			}
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.permissions.NotEnoughRights"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
	}
	
	@Listen("onClick = button#btnGoToClosures")
    public void btnGoToClosures_onClick() throws Exception {
		
		try {
			Executions.getCurrent().sendRedirect("/shift_closures.zul", "_blank");
			((Window) btnGoToClosures.getParent().getParent().getParent()).detach();
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
	}
}