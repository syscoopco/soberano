package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.domain.tracked.MaterialExpense;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.rowdata.ExpenseRowData;

@SuppressWarnings("rawtypes")
public class MaterialExpensesGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings("unchecked")
	public void prepareRow(Row row, Object data) {
		
		ExpenseRowData expense = (ExpenseRowData) data;
		
		//expense date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		row.appendChild(new Label(dateFormat.format(expense.getExpenseDate())));
		
		//payee
		row.appendChild(new Label(expense.getPayeeName()));
		
		//concept
		row.appendChild(new Label(expense.getConceptName()));
		
		//description
		row.appendChild(new Label(expense.getDescription()));
		
		//amount
		row.appendChild(new Label(expense.getAmount().toPlainString()));
		
		//currency
		row.appendChild(new Label(expense.getCurrency()));
		
		//reference
		row.appendChild(new Label(expense.getReference()));		
		
		//recording date
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		row.appendChild(new Label(dateFormat.format(expense.getRecordingDate())));
				
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
					Integer expenseId = expense.getExpenseId();					
					Printer.printReport(new MaterialExpense(expenseId), 
											SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
											"records/expenses/" + 
											"MATERIALEXP_" + expenseId + ".pdf",
											"MATERIALEXP_",
											false,
											true,
											false,
											false);
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
		
		Button btnUpload = new Button(Labels.getLabel("caption.action.upload"));
		btnUpload.setId(btnUpload.getUuid());
		btnUpload.setWidth("90%");
		btnUpload.setDisabled(true);
		Button btnDocument = new Button(Labels.getLabel("caption.action.document"));
		btnDocument.setId(btnDocument.getUuid());
		btnDocument.setWidth("90%");
		btnDocument.setDisabled(true);
		Button btnCancel = new Button(Labels.getLabel("caption.action.cancel"));
		btnCancel.setId(btnCancel.getUuid());
		btnCancel.setWidth("90%");
		
		//add listener to cancel the expense
		btnCancel.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {
					if (requestedActions.get(row) != null && requestedActions.get(row).equals(ActionRequested.DISABLE)) {
						int result = (new MaterialExpense(expense.getExpenseId())).disable();
						if (result == -1) {
							throw new NotEnoughRightsException();
						}
						else {					
							row.detach();
						}
					}
					else {
						requestDeletion(row);
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
			}
		});
				
		actionCell.appendChild(btnPrint);
		actionCell.appendChild(btnUpload);
		actionCell.appendChild(btnDocument);
		actionCell.appendChild(btnCancel);
		row.appendChild(actionCell);
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
