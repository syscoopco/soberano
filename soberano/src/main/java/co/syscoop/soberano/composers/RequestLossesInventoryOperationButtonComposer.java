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
import co.syscoop.soberano.exception.SameWarehouseException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WrongDateTimeException;

@SuppressWarnings({ "serial" })
public class RequestLossesInventoryOperationButtonComposer extends SPICellButtonComposer {
	
	@Wire
	private Combobox cmbLossesToWarehouse;
	
	@Wire
	private Combobox cmbLossesWorker;
	
	@Wire
	private Label lblLossesItemId;
	
	@Wire
	private Intbox intLossesUnitId;
	
	@Wire
	private Intbox intLossesFromWarehouse;
	
	@Wire
	private Decimalbox decLossesQuantity;
	
	@Wire
	private Decimalbox decLossesCurrentQuantity;
	
	@Wire
	private Intbox intAcquirableMaterialId;
	
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnLossesRequest")
    public void btnLossesRequest_onClick() throws Exception {
		
		try {
			ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
			ArrayList<Unit> units = new ArrayList<Unit>();
			ArrayList<BigDecimal> quantities = new ArrayList<BigDecimal>();
			
			inventoryItems.add(new InventoryItem(lblLossesItemId.getValue(), ""));
			units.add(new Unit(intLossesUnitId.getValue()));
			
//			quantities.add(decLossesQuantity.getValue().compareTo(decLossesCurrentQuantity.getValue()) > 0 
//							? decLossesQuantity.getValue().subtract(decLossesCurrentQuantity.getValue())
//							: new BigDecimal(0));
			
			quantities.add(decLossesQuantity.getValue().compareTo(new BigDecimal(0)) > 0 
					? decLossesQuantity.getValue() 
					: new BigDecimal(0));
			
			if (cmbLossesToWarehouse.getSelectedItem() == null || cmbLossesWorker.getSelectedItem() == null) {
				((Popup) cmbLossesToWarehouse.getParent().getParent().getParent().getParent().query("popup")).close();
				throw new SomeFieldsContainWrongValuesException(); 
			}
			else {
				if (intLossesFromWarehouse.getValue().equals(((DomainObject) cmbLossesToWarehouse.getSelectedItem().getValue()).getId())) {
					throw new SameWarehouseException();
				}
				
				Datebox dateShift = (Datebox) intAcquirableMaterialId.getParent().getParent().
																	getParent().getParent().
																	getParent().getParent().
																	getParent().query("#dateShift");
				Integer qryResult = (new InventoryOperation(intLossesFromWarehouse.getValue(),
															((DomainObject) cmbLossesToWarehouse.getSelectedItem().getValue()).getId(),
															((DomainObject) cmbLossesWorker.getSelectedItem().getValue()).getId(),
															inventoryItems,
															units,
															quantities)).request(dateShift.getText());
				((Popup) cmbLossesToWarehouse.getParent().getParent().getParent().getParent().query("popup")).close();
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
				
//				((Button) cmbLossesToWarehouse.getParent().getParent().getParent().getParent()
//						.getParent().getParent().getParent().getParent()
//						.getParent().getParent()
//						.query("north").query("hlayout").query("#btnAlert")).setVisible(true);
			}
		}
		catch(SameWarehouseException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.OriginAndDestinationMustNotBeTheSame"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
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