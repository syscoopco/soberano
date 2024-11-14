package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

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
	
	@Wire
	private Button btnLossesRequest;
	
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
			
			quantities.add(decLossesQuantity.getValue() != null ? 
								decLossesQuantity.getValue().compareTo(new BigDecimal(0)) > 0 ? decLossesQuantity.getValue() : new BigDecimal(0) 
																: new BigDecimal(0));
			
			if (cmbLossesToWarehouse.getSelectedItem() == null || cmbLossesWorker.getSelectedItem() == null) {
				((Window) btnLossesRequest.getParent().getParent().getParent()).detach();
				throw new SomeFieldsContainWrongValuesException(); 
			}
			else {
				if (intLossesFromWarehouse.getValue().equals(((DomainObject) cmbLossesToWarehouse.getSelectedItem().getValue()).getId())) {
					throw new SameWarehouseException();
				}
				
				Datebox dateShift = (Datebox) btnLossesRequest.getParent().getParent().getParent().getParent().query("#dateShift");
				Integer qryResult = (new InventoryOperation(intLossesFromWarehouse.getValue(),
															((DomainObject) cmbLossesToWarehouse.getSelectedItem().getValue()).getId(),
															((DomainObject) cmbLossesWorker.getSelectedItem().getValue()).getId(),
															inventoryItems,
															units,
															quantities)).request(dateShift.getText());
				((Window) btnLossesRequest.getParent().getParent().getParent()).detach();
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
				updateSPIRow((Combobox) dateShift.query("#cmbWarehouse"), intAcquirableMaterialId);
			}
		}
		catch(CannotAcquireLockException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.database.CannotAcquireLockException"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(ConcurrencyFailureException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.database.ConcurrencyFailureException"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(DuplicateKeyException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.thereIsAlreadyAnObjectWithThatId"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(DataIntegrityViolationException ex)	{
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(DataAccessException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.DataAccessException"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
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