package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
import co.syscoop.soberano.util.SpringUtility;

public class Order extends BusinessActivityTrackedObject {

	private String label = "";
	private ArrayList<String> counters = new ArrayList<String>();
	private Integer customer = 0;
	
	public Order() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_Order_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_Order_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public Order(Integer id) {
		super(id);
	}
	
	public Order(String label, ArrayList<String> counters, Integer customer) {
		this.label = label;
		this.counters = counters;
		this.customer = customer;
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Order_create\"(:label, "
				+ "											:counters, "						
				+ "											:customer, "	
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("label", this.getLabel());	
		recordParameters.addValue("counters", createArrayOfSQLType("varchar", this.getCounters().toArray()));
		recordParameters.addValue("customer", this.getCustomer());		
		return super.record();
	}
	
	@Override
	public void get() throws SQLException {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public Integer print() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void copyFrom(Object object) {
		// TODO Auto-generated method stub		
	}
	
	public Integer make(Integer itemId, String description, Integer runs) throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_make\"(:orderId, "
							+ "								:itemId, "
							+ "								:description, "
							+ "								:runs, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("itemId", itemId);
		parametersMap.put("description", description);
		parametersMap.put("runs", runs);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public ArrayList<String> getCounters() {
		return counters;
	}
	
	public void setCounters(ArrayList<String> counters) {
		this.counters = counters;
	}
	
	public Integer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
