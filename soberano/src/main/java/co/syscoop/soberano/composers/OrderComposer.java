package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })
public class OrderComposer extends SelectorComposer {
	
	@Wire
	protected Intbox intObjectId; 
	
	@Wire 
	protected Intbox intDiscountTop;
	
	@Wire 
	protected Intbox intDiscountBottom;
	
	@Wire
	protected Decimalbox decAmountTop;
	
	@Wire
	protected Decimalbox decAmountBottom;
	
	protected void updateAmounts(BigDecimal amount) throws NotEnoughRightsException, Exception, SQLException {
													
		if (amount.compareTo(new BigDecimal(0)) < 0) {
			throw new NotEnoughRightsException();
		}
		else {
			decAmountTop.setValue(amount);
			decAmountBottom.setValue(amount);
			
			//update order ticket
			((Textbox) decAmountTop.query("#wndContentPanel").query("#wndOrderItems").
									query("#wndTicket").query("#txtTicket")).
										setValue(Translator.translate((new Order(intObjectId.getValue()).
												retrieveTicket(new BigDecimal(0), new BigDecimal(0))).getTextToPrint()));
		}
	}
	
	protected void applyOrderDiscount(Boolean top) throws SoberanoException {
		
		try {
			Order order = new Order(intObjectId.getValue());
			Integer discountToApply = top ? intDiscountTop.getValue() : intDiscountBottom.getValue();
			if (discountToApply < 0) {
				discountToApply = 0;
				if (top) 
					intDiscountTop.setValue(0); 
				else 
					intDiscountBottom.setValue(0);
			}
			else if (discountToApply > 100) {
				discountToApply = 100;
				if (top) 
					intDiscountTop.setValue(100); 
				else 
					intDiscountBottom.setValue(100);
			}
			if (order.applyDiscount(discountToApply) == -1) {
				throw new NotEnoughRightsException();
			}
			else {
				if (top) 
					intDiscountBottom.setValue(discountToApply); 
				else 
					intDiscountTop.setValue(discountToApply);
				updateAmounts(order.retrieveAmount());				
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
	
	@Listen("onChange = intbox#intDiscountTop")
    public void intDiscountTop_onChange() throws SoberanoException {
		
		applyOrderDiscount(true);
    }
	
	@Listen("onChange = intbox#intDiscountBottom")
    public void intDiscountBottom_onChange() throws SoberanoException {
		
		applyOrderDiscount(false);
    }
	
	/*
	 * Needed for testing. 
	 * onChange event isn't triggered under testing.
	 */
	@Listen("onClick = intbox#intDiscountTop")
	public void intDiscountTop_onClick() throws SoberanoException {
		
		if (SpringUtility.underTesting()) applyOrderDiscount(true);
    }
	
	/*
	 * Needed for testing. 
	 * onChange event isn't triggered under testing.
	 */
	@Listen("onClick = intbox#intDiscountBottom")
	public void intDiscountBottom_onClick() throws SoberanoException {
		
		if (SpringUtility.underTesting()) applyOrderDiscount(false);
    }
}