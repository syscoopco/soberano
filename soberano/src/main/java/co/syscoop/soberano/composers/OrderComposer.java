package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class OrderComposer extends SelectorComposer {
	
	@Wire
	private Intbox intObjectId; 
	
	@Wire
	private Button btnDec;
	
	@Wire
	private Intbox intRuns;
	
	@Wire
	private Button btnMake;
	
	private void checkRuns() {
		
		if (intRuns.getValue() <= 0) {
			intRuns.setValue(0);
			btnDec.setDisabled(true);
			btnMake.setDisabled(true);
		}
		else {
			btnDec.setDisabled(false);
			btnMake.setDisabled(false);
		}
	}
	
	@Listen("onChange = intbox#intRuns")
    public void intRuns_onChange() {
		
		checkRuns();
    }
	
	@Listen("onClick = button#btnInc")
    public void btnInc_onClick() {
		
		intRuns.setValue(intRuns.getValue() + 1);
		checkRuns();
    }
	
	@Listen("onClick = button#btnDec")
    public void btnDec_onClick() {
		
		intRuns.setValue(intRuns.getValue() - 1);
		checkRuns();
    }
	
	@Listen("onClick = button#btnMake")
    public void btnMake_onClick() throws SoberanoException {
		
		try {
			btnMake.setDisabled(true);
			if (intRuns.getValue() > 0) {	
				Vbox boxDetails = (Vbox) btnMake.query("#boxDetails");
				OrderFormHelper orderFormHelper = new OrderFormHelper();
				if (orderFormHelper.makeFromForm(boxDetails) == -1) {
					btnMake.setDisabled(false);
					throw new NotEnoughRightsException();						
				}
				else {
					Executions.sendRedirect("/order.zul?id=" + intObjectId.getValue());
				}
			}
		}
		catch(NotEnoughRightsException ex) {
			btnMake.setDisabled(false);
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.permissions.NotEnoughRights"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(Exception ex)	{
			btnMake.setDisabled(false);
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
    }
}