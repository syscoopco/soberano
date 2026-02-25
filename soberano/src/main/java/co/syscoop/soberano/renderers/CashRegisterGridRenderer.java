package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.tracked.Balancing;
import co.syscoop.soberano.domain.tracked.Deposit;
import co.syscoop.soberano.domain.tracked.Withdrawal;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.rowdata.CashRegisterOperationRowData;
import co.syscoop.soberano.vocabulary.Translator;

public class CashRegisterGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepareRow(Row row, Object data) {
		
		CashRegisterOperationRowData cashRegisterOperation = (CashRegisterOperationRowData) data;
		
		//operation
		row.appendChild(new Label(Translator.translate(cashRegisterOperation.getOperation())));
		
		//worker
		row.appendChild(new Label(cashRegisterOperation.getWorker()));
		
		//description
		row.appendChild(new Label(Translator.translate(cashRegisterOperation.getDescription())));
		
		//recording date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(cashRegisterOperation.getRecordingDate())));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		Button btnPrint = new Button(Labels.getLabel("caption.action.print"));
		btnPrint.setId(btnPrint.getUuid());
		btnPrint.setWidth("90%");
		
		btnPrint.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try{
					Integer cashRegisterOperationId = cashRegisterOperation.getCashRegisterOperationId();
					if (cashRegisterOperation.getOperation().equals("tt_DEPOSIT_tt")) {
						Deposit deposit = new Deposit();
						deposit.setId(cashRegisterOperationId);
						Printer.printReport(deposit, 
												SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/cash_register/" + 
												"DEPOSIT_" + cashRegisterOperationId + ".pdf",
												"DEPOSIT_",
												false,
												true,
												false,
												false);
					}
					else if (cashRegisterOperation.getOperation().equals("tt_WITHDRAWAL_tt")) {
						Withdrawal withdrawal = new Withdrawal();
						withdrawal.setId(cashRegisterOperationId);
						Printer.printReport(withdrawal, 
												SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/cash_register/" + 
												"WITHDRAWAL_" + cashRegisterOperationId + ".pdf",
												"WITHDRAWAL_",
												false,
												true,
												false,
												false);
					}
					else if (cashRegisterOperation.getOperation().equals("tt_BALANCING_tt")) {
						Balancing balancing = new Balancing();
						balancing.setId(cashRegisterOperationId);
						Printer.printReport(balancing, 
												SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/cash_register/" + 
												"BALANCING_" + cashRegisterOperationId + ".pdf",
												"BALANCING_",
												false,
												true,
												false,
												false);
					}
				}
				catch(NotEnoughRightsException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.permissions.NotEnoughRights"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
				catch(Exception ex) {
					ExceptionTreatment.logAndShow(ex, 
								ex.getMessage(), 
								Labels.getLabel("messageBoxTitle.Error"),
								Messagebox.ERROR);
				}				
			}
		});
		
		Button btnCancel = new Button(Labels.getLabel("caption.action.cancel"));
		btnCancel.setId(btnCancel.getUuid());
		btnCancel.setWidth("90%");
		
		//if operation is cancelled,
		if (cashRegisterOperation.getStageId() == 5) {
			btnCancel.setDisabled(true);
			btnCancel.setLabel(Labels.getLabel("translation.stage.Canceled"));
		}
		
		btnCancel.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try{
					Integer cashRegisterOperationId = cashRegisterOperation.getCashRegisterOperationId();
					if (cashRegisterOperation.getOperation().equals("tt_DEPOSIT_tt")) {
						Deposit deposit = new Deposit();
						deposit.setId(cashRegisterOperationId);
						if (requestedActions.get(row) != null && requestedActions.get(row).equals(ActionRequested.DISABLE)) {
							int result = deposit.disable();
							if (result == -1) {
								throw new NotEnoughRightsException();
							}
							else {					
								Executions.sendRedirect("/cash_register.zul?id=1");
							}
						}
						else {
							requestDeletion(row);
						}
					}
					else if (cashRegisterOperation.getOperation().equals("tt_WITHDRAWAL_tt")) {
						Withdrawal withdrawal = new Withdrawal();
						withdrawal.setId(cashRegisterOperationId);
						if (requestedActions.get(row) != null && requestedActions.get(row).equals(ActionRequested.DISABLE)) {
							int result = withdrawal.disable();
							if (result == -1) {
								throw new NotEnoughRightsException();
							}
							else {					
								Executions.sendRedirect("/cash_register.zul?id=1");
							}
						}
						else {
							requestDeletion(row);
						}
					}
					else if (cashRegisterOperation.getOperation().equals("tt_BALANCING_tt")) {
						Balancing balancing = new Balancing();
						balancing.setId(cashRegisterOperationId);
						if (requestedActions.get(row) != null && requestedActions.get(row).equals(ActionRequested.DISABLE)) {
							int result = balancing.disable();
							if (result == -1) {
								throw new NotEnoughRightsException();
							}
							else {					
								Executions.sendRedirect("/cash_register.zul?id=1");
							}
						}
						else {
							requestDeletion(row);
						}
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
				catch(Exception ex) {
					ExceptionTreatment.logAndShow(ex, 
								ex.getMessage(), 
								Labels.getLabel("messageBoxTitle.Error"),
								Messagebox.ERROR);
				}				
			}
		});	
		
		Button btnUpload = new Button(Labels.getLabel("caption.action.upload"));
		btnUpload.setId(btnUpload.getUuid());
		btnUpload.setWidth("90%");
		btnUpload.setDisabled(true);
		Button btnDocument = new Button(Labels.getLabel("caption.action.document"));
		btnDocument.setId(btnDocument.getUuid());
		btnDocument.setWidth("90%");
		btnDocument.setDisabled(true);
			
		actionCell.appendChild(btnPrint);
		actionCell.appendChild(btnCancel);
		actionCell.appendChild(btnUpload);
		actionCell.appendChild(btnDocument);
		row.appendChild(actionCell);
		
		//operation id
		row.appendChild(new Intbox(cashRegisterOperation.getCashRegisterOperationId()));
		
		//entity type instance id
		row.appendChild(new Intbox(cashRegisterOperation.getEntityTypeInstanceId()));
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
