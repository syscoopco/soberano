package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
import co.syscoop.soberano.domain.untracked.helper.OrderItem;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Labels;

public class Order extends BusinessActivityTrackedObject {

	private String label = "";
	private ArrayList<String> counters = new ArrayList<String>();
	private String countersStr = "";
	private Integer customer = 0;
	private String customerStr = "";
	private String deliverTo = "";
	private Integer discount = 0;
	private BigDecimal amount = new BigDecimal(0);	
	private ArrayList<String> categories = new ArrayList<String>();
	private String stage = "";
	
	//the key is a category
	private HashMap<String, ArrayList<String>> descriptions = new HashMap<String, ArrayList<String>>();
	
	//the key is a category + a description 
	private HashMap<String, ArrayList<OrderItem>> orderItems = new HashMap<String, ArrayList<OrderItem>>();
	
	public Order(Integer id,
				String label,
				String counters,
				String customer,
				String deliverTo,
				Integer orderDiscount,
				BigDecimal orderAmount,
				String stage) {
		super(id);
		this.label = label;
		this.countersStr = counters;
		this.customerStr = customer;
		this.deliverTo = deliverTo;
		this.discount = orderDiscount;
		this.amount = orderAmount;
		this.stage = stage;
	}
	
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
	
	public final class OrderExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Order extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Order order = null;
			Integer orderCurrentlyBeingExtractedId = -1;
			String categoryCurrentlyBeingExtracted = "";
			String descriptionCurrentlyBeingExtracted = null;
	        while (rs.next()) {
	        	if (orderCurrentlyBeingExtractedId != rs.getInt("orderId")) {
	        		orderCurrentlyBeingExtractedId = rs.getInt("orderId");
	        		order = new Order(rs.getInt("orderId"),
	        							rs.getString("orderLabel"),
	        							rs.getString("counters"),
	        							rs.getString("customer"),
	        							rs.getString("deliverTo"),
	        							rs.getInt("orderDiscount"),
	        							rs.getBigDecimal("orderAmount"),
	        							Labels.getLabel("translation.stage." + rs.getString("stage")));
	        	}
	        	if (categoryCurrentlyBeingExtracted.isEmpty() || !categoryCurrentlyBeingExtracted.equals(rs.getString("category"))) {
	        		categoryCurrentlyBeingExtracted = rs.getString("category");
	        		order.getCategories().add(categoryCurrentlyBeingExtracted);
	        		order.getDescriptions().put(categoryCurrentlyBeingExtracted, new ArrayList<String>());
	        		order.getOrderItems().put(categoryCurrentlyBeingExtracted, new ArrayList<OrderItem>());
	        	}
	        	if (descriptionCurrentlyBeingExtracted == null || !descriptionCurrentlyBeingExtracted.equals(rs.getString("description"))) {
	        		descriptionCurrentlyBeingExtracted = rs.getString("description");
	        		order.getDescriptions().get(categoryCurrentlyBeingExtracted).add(descriptionCurrentlyBeingExtracted);	        		
	        		order.getOrderItems().put(categoryCurrentlyBeingExtracted + ":" + descriptionCurrentlyBeingExtracted, new ArrayList<OrderItem>());   
	        	}
	        	OrderItem orderItem = new OrderItem();
	        	orderItem.setProcessRunId(rs.getInt("processRunId"));
	        	orderItem.setProductName(rs.getString("productName"));
	        	orderItem.setProductQuantity(rs.getBigDecimal("productQuantity"));
	        	orderItem.setProductUnit(rs.getString("productUnit"));
	        	orderItem.setDescription(rs.getString("description"));
	        	orderItem.setOrderedRuns(rs.getInt("orderedRuns"));
	        	orderItem.setCanceledRuns(rs.getInt("canceledRuns"));
	        	orderItem.setDiscountedRuns(rs.getInt("discountedRuns"));
	        	orderItem.setEndedRuns(rs.getInt("endedRuns"));	
	        	orderItem.setCurrency(rs.getString("currency"));
	        	order.getOrderItems().get(categoryCurrentlyBeingExtracted + ":" + descriptionCurrentlyBeingExtracted).add(orderItem);
	        }
	        return order;
		}
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Order_get\"(:lang, :orderId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("lang", Locales.getCurrent().getLanguage());
		getParameters.put("orderId", this.getId());
		super.get(new OrderExtractor());
	}
	
	@Override
	public Integer print() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void copyFrom(Object sourceObject) {
		Order sourceOrder = (Order) sourceObject;
		setId(sourceOrder.getId());
		setEntityTypeInstanceId(sourceOrder.getEntityTypeInstanceId());
		setLabel((sourceOrder).getLabel());
		setCounters((sourceOrder).getCounters());
		setCountersStr((sourceOrder).getCountersStr());
		setCustomer((sourceOrder).getCustomer());
		setCustomerStr((sourceOrder).getCustomerStr());
		setDeliverTo((sourceOrder).getDeliverTo());
		setDiscount((sourceOrder).getDiscount());
		setAmount((sourceOrder).getAmount());
		setCategories((sourceOrder).getCategories());
		setStage((sourceOrder).getStage());
		setDescriptions((sourceOrder).getDescriptions());
		setOrderItems((sourceOrder).getOrderItems());
	}
	
	public Integer make(Integer itemId, String description, BigDecimal runs) throws Exception {
					
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

	public String getCountersStr() {
		return countersStr;
	}

	public void setCountersStr(String countersStr) {
		this.countersStr = countersStr;
	}

	public String getDeliverTo() {
		return deliverTo;
	}

	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCustomerStr() {
		return customerStr;
	}

	public void setCustomerStr(String customerStr) {
		this.customerStr = customerStr;
	}

	public ArrayList<String> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	public HashMap<String, ArrayList<OrderItem>> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(HashMap<String, ArrayList<OrderItem>> orderItems) {
		this.orderItems = orderItems;
	}

	public HashMap<String, ArrayList<String>> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(HashMap<String, ArrayList<String>> descriptions) {
		this.descriptions = descriptions;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
}
