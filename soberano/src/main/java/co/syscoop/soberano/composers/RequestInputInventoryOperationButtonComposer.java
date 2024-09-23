package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;

import co.syscoop.soberano.domain.tracked.InventoryItem;
import co.syscoop.soberano.domain.tracked.InventoryOperation;
import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WrongDateTimeException;

@SuppressWarnings({ "serial" })
public class RequestInputInventoryOperationButtonComposer extends SPICellButtonComposer {
	
	@Wire
	private Combobox cmbInputFromWarehouse;
	
	@Wire
	private Combobox cmbInputWorker;
	
	@Wire
	private Label lblInputItemId;
	
	@Wire
	private Intbox intInputUnitId;
	
	@Wire
	private Intbox intInputToWarehouse;
	
	@Wire
	private Decimalbox decInputQuantity;
	
	@Wire
	private Decimalbox decInputCurrentQuantity;
	
	@Wire
	private Intbox intAcquirableMaterialId;
	
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnInputRequest")
    public void btnInputRequest_onClick() throws Exception {
		
		try {
			ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
			ArrayList<Unit> units = new ArrayList<Unit>();
			ArrayList<BigDecimal> quantities = new ArrayList<BigDecimal>();
			
			inventoryItems.add(new InventoryItem(lblInputItemId.getValue(), ""));
			units.add(new Unit(intInputUnitId.getValue()));
			
//			quantities.add(decInputQuantity.getValue().compareTo(decInputCurrentQuantity.getValue()) > 0 
//							? decInputQuantity.getValue().subtract(decInputCurrentQuantity.getValue())
//							: new BigDecimal(0));
			
			quantities.add(decInputQuantity.getValue().compareTo(new BigDecimal(0)) > 0 
							? decInputQuantity.getValue() 
							: new BigDecimal(0));
			
			if (cmbInputFromWarehouse.getSelectedItem() == null || cmbInputWorker.getSelectedItem() == null) {
				((Popup) cmbInputFromWarehouse.getParent().getParent().getParent().getParent().query("popup")).close();
				throw new SomeFieldsContainWrongValuesException(); 
			}
			else {
				Datebox dateShift = (Datebox) intAcquirableMaterialId.getParent().getParent().
																	getParent().getParent().
																	getParent().getParent().
																	getParent().query("#dateShift");
				Integer qryResult = (new InventoryOperation(((DomainObject) cmbInputFromWarehouse.getSelectedItem().getValue()).getId(),
															intInputToWarehouse.getValue(),
															((DomainObject) cmbInputWorker.getSelectedItem().getValue()).getId(),
															inventoryItems,
															units,
															quantities)).request(dateShift.getText());
				((Popup) cmbInputFromWarehouse.getParent().getParent().getParent().getParent().query("popup")).close();
				if (qryResult == -1) {
					throw new NotEnoughRightsException();
				}
				else if (qryResult == -2) {
					throw new WrongDateTimeException();
				}
				else if (qryResult == -3) {
					throw new ShiftHasBeenClosedException();
				}
				else if (qryResult == -4) {
					throw new SomeFieldsContainWrongValuesException();
				}				
				updateSPIRow(intAcquirableMaterialId);			
				
//				((Button) cmbInputFromWarehouse.getParent().getParent().getParent().getParent()
//												.getParent().getParent().getParent().getParent()
//												.getParent().getParent()
//												.query("north").query("hlayout").query("#btnAlert")).setVisible(true);
			}
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.permissions.NotEnoughRights"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(ShiftHasBeenClosedException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.shiftHasBeenClosed"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(WrongDateTimeException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.wrongBusinessEventOccurrenceDateTime"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(SomeFieldsContainWrongValuesException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
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