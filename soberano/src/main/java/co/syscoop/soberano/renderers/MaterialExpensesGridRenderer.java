package co.syscoop.soberano.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import co.syscoop.soberano.util.ExpenseRowData;

@SuppressWarnings("rawtypes")
public class MaterialExpensesGridRenderer implements RowRenderer {

	@SuppressWarnings("unchecked")
	public void prepareRow(Row row, Object data) {
		
		ExpenseRowData expense = (ExpenseRowData) data;
		
		//concept
		row.appendChild(new Label(expense.getConceptName()));
		
		//description
		row.appendChild(new Label(expense.getDescription()));
		
		//payee
		row.appendChild(new Label(expense.getPayeeName()));
		
		//reference
		row.appendChild(new Label(expense.getReference()));
		
		//expense date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		row.appendChild(new Label(dateFormat.format(expense.getExpenseDate())));
		
		//recording date
		row.appendChild(new Label(dateFormat.format(expense.getRecordingDate())));
		
		//amount
		row.appendChild(new Label(expense.getAmount().toPlainString()));
		
		
		//currency
		row.appendChild(new Label(expense.getCurrency()));
		
		//id
		Label lblId = new Label(expense.getExpenseId().toString());
		lblId.setId("lblExpenseId" + expense.getExpenseId().toString());
		row.appendChild(lblId);
		
		//add listener to click event on row
		row.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				//TODO
			}
		});
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
