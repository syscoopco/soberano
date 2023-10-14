package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.Expense;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;

public class PayrollExpense extends BusinessActivityTrackedObject {
	
	private Worker worker = new Worker();
	private Expense expense = new Expense();
	
	public PayrollExpense() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_PayrollExpense_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_PayrollExpense_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public PayrollExpense(Integer id) {
		this.setId(id);
	}
	
	public PayrollExpense(Date expenseDate,
							Integer worker,
							BigDecimal amount,
							Integer currency,
							String reference) {
		super.setOccurrenceTime(expenseDate);
		this.worker.setId(worker);
		this.expense.setAmount(amount);
		this.expense.setCurrency(new Currency(currency));
		super.setReference(reference);
	}

	@Override
	public void get() throws SQLException {
	}

	@Override
	public Integer print() throws SQLException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_PayrollExpense_create\"(:worker, "
				+ "											:reference, "
				+ "											:expenseDate, "
				+ "											:amount, "
				+ "											:currency, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("worker", this.worker.getId());
		recordParameters.addValue("reference", super.getReference());
		recordParameters.addValue("expenseDate", super.getOccurrenceTime());
		recordParameters.addValue("amount", this.expense.getAmount());
		recordParameters.addValue("currency", this.expense.getCurrency().getId());
		
		return super.record();
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_PayrollExpense_disable\"(:payrollExpenseId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("payrollExpenseId", this.getId());
		
		Integer qryResult = super.disable();
		if (qryResult == -3) {
			throw new ShiftHasBeenClosedException();
		}
		return qryResult;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
}
