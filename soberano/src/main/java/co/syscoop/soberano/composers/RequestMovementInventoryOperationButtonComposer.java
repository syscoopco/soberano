package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
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

@SuppressWarnings({ "serial", "rawtypes" })
public class RequestMovementInventoryOperationButtonComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbMovementToWarehouse;
	
	@Wire
	private Combobox cmbMovementWorker;
	
	@Wire
	private Label lblMovementItemId;
	
	@Wire
	private Intbox intMovementUnitId;
	
	@Wire
	private Intbox intMovementFromWarehouse;
	
	@Wire
	private Decimalbox decMovementQuantity;
	
	@Wire
	private Decimalbox decMovementCurrentQuantity;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnMovementRequest")
    public void btnMovementRequest_onClick() throws Exception {
		
		try {
			ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
			ArrayList<Unit> units = new ArrayList<Unit>();
			ArrayList<BigDecimal> quantities = new ArrayList<BigDecimal>();
			
			inventoryItems.add(new InventoryItem(lblMovementItemId.getValue(), ""));
			units.add(new Unit(intMovementUnitId.getValue()));
			quantities.add(decMovementQuantity.getValue().compareTo(decMovementCurrentQuantity.getValue()) > 0 
							? decMovementQuantity.getValue().subtract(decMovementCurrentQuantity.getValue())
							: new BigDecimal(0));
			
			if (cmbMovementToWarehouse.getSelectedItem() == null || cmbMovementWorker.getSelectedItem() == null) {
				((Popup) cmbMovementToWarehouse.getParent().getParent().getParent().getParent().query("popup")).close();
				throw new SomeFieldsContainWrongValuesException(); 
			}
			else {
				Integer qryResult = (new InventoryOperation(intMovementFromWarehouse.getValue(),
															((DomainObject) cmbMovementToWarehouse.getSelectedItem().getValue()).getId(),
															((DomainObject) cmbMovementWorker.getSelectedItem().getValue()).getId(),
															inventoryItems,
															units,
															quantities)).request();
				((Popup) cmbMovementToWarehouse.getParent().getParent().getParent().getParent().query("popup")).close();
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
//				((Button) cmbMovementToWarehouse.getParent().getParent().getParent().getParent()
//						.getParent().getParent().getParent().getParent()
//						.getParent().getParent()
//						.query("north").query("hlayout").query("#btnAlert")).setVisible(true);
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