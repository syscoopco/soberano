package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial" })
public class OrderComposer extends BusinessActivityTrackedObjectComposer {
	
	@Wire
	private Intbox intObjectId; 
	
	@Wire
	private Label lblDeliveryTo;
	
	@Wire 
	private Textbox txtDeliveryTo;	
	
	@Wire
	private Combobox cmbItemToOrder;
	
	@Wire
	private Textbox txtSpecialInstructions;
	
	@Wire
	private Button btnInc;
	
	@Wire
	private Button btnDec;
	
	@Wire
	private Intbox intRuns;
	
	@Wire
	private Button btnMake;
	
	@Wire
	private Intbox intDiscountTop;
	
	@Wire
	private Intbox intDiscountBottom;
	
	@Wire
	private Decimalbox decAmountTop;
	
	@Wire
	private Decimalbox decAmountBottom;
	
	@Wire
	private Grid gridOrderItems;
	
	public OrderComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new OrderFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnBill.query("#wndContentPanel").query("#boxDetails");
    }
	
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
			btnDec.setDisabled(true);
			btnMake.setDisabled(true);
			if (intRuns.getValue() > 0) {			
				if (trackedObjectFormHelper.makeFromForm(boxDetails) == -1) {
					throw new NotEnoughRightsException();						
				}
				else {
					Executions.sendRedirect("/order.zul?id=" + intObjectId.getValue());
				}
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
}