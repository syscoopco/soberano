package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;

public class MaterialExpense extends BusinessActivityTrackedObject {
	
	private MaterialAcquisition acquisition = new MaterialAcquisition();
	
	public MaterialExpense() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_MaterialExpense_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_MaterialExpense_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public MaterialExpense(Integer id) {
		this.setId(id);
	}
	
	public MaterialExpense(Date expenseDate,
							Integer provider,
							Integer material,
							BigDecimal quantity,
							Integer unit,
							BigDecimal amount,
							Integer currency,
							String reference) {
		super.setOccurrenceTime(expenseDate);
		acquisition.getExpense().getProvider().setId(provider);
		acquisition.getMaterial().setId(material);
		acquisition.setQuantity(quantity);
		acquisition.setUnit(new Unit(unit));
		acquisition.getExpense().setAmount(amount);
		acquisition.getExpense().getCurrency().setId(currency);
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
		recordQuery = "SELECT soberano.\"fn_MaterialExpense_create\"(:acquirableMaterial, "
				+ "											:unit, "
				+ "											:quantity, "
				+ "											:provider, "
				+ "											:reference, "
				+ "											:expenseDate, "
				+ "											:amount, "
				+ "											:currency, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("acquirableMaterial", this.getAcquisition().getMaterial().getId());
		recordParameters.addValue("unit", this.getAcquisition().getUnit().getId());
		recordParameters.addValue("quantity", this.getAcquisition().getQuantity());
		recordParameters.addValue("provider", this.getAcquisition().getExpense().getProvider().getId());
		recordParameters.addValue("reference", super.getReference());
		recordParameters.addValue("expenseDate", super.getOccurrenceTime());
		recordParameters.addValue("amount", this.getAcquisition().getExpense().getAmount());
		recordParameters.addValue("currency", this.getAcquisition().getExpense().getCurrency().getId());
		
		return super.record();
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_MaterialExpense_disable\"(:materialExpenseId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("materialExpenseId", this.getId());
		
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

	public MaterialAcquisition getAcquisition() {
		return acquisition;
	}

	public void setAcquisition(MaterialAcquisition acquisition) {
		this.acquisition = acquisition;
	}	
}
