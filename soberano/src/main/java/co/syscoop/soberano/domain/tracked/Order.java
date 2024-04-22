package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.ActivityMapper;
import co.syscoop.soberano.database.relational.QueryBigDecimalResultMapper;
import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
import co.syscoop.soberano.database.relational.QueryResultWithReport;
import co.syscoop.soberano.database.relational.QueryResultWithReportMapper;
import co.syscoop.soberano.domain.untracked.helper.OrderItem;
import co.syscoop.soberano.enums.Stage;
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
	private BigDecimal amount = new BigDecimal(0); //in order currency
	private BigDecimal collectedAmount = new BigDecimal(0); //in order currency
	private BigDecimal amountToCollect = new BigDecimal(0); //in current system currency
	private ArrayList<String> categories = new ArrayList<String>();
	private String stage = "";
	private Stage stageId = Stage.ONGOING;
	
	//currently it's prevented to change the system currency while some order is ongoing (RULE_CONSTRAINT_15), so
	//order's process runs share the currency
	private String currencyCode = "";
	
	//the key is a category
	private HashMap<String, ArrayList<String>> descriptions = new HashMap<String, ArrayList<String>>();
	
	//the key is a category + a description 
	private HashMap<String, ArrayList<OrderItem>> orderItems = new HashMap<String, ArrayList<OrderItem>>();
	
	public Order(Integer id,
				String label,
				String counters,
				String customer,
				Integer customerId,
				String deliverTo,
				Integer orderDiscount,
				BigDecimal orderAmount,
				BigDecimal collectedAmount,
				BigDecimal amountToCollect,
				String stage,
				Stage stageId) {
		super(id);
		this.label = label;
		this.countersStr = counters;
		this.customerStr = customer;
		this.customer = customerId;
		this.deliverTo = deliverTo;
		this.discount = orderDiscount;
		this.amount = orderAmount;
		this.collectedAmount = collectedAmount;
		this.amountToCollect = amountToCollect;
		this.stage = stage;
		this.stageId = stageId;
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
	        							rs.getInt("customerId"),
	        							rs.getString("deliverTo"),
	        							rs.getInt("orderDiscount"),
	        							rs.getBigDecimal("orderAmount"),
	        							rs.getBigDecimal("collectedAmount"),
	        							rs.getBigDecimal("amountToCollect"),
	        							Labels.getLabel("translation.stage." + rs.getString("stage")),
	        							Stage.values()[rs.getInt("stageId")]);
	        	}
	        	if (categoryCurrentlyBeingExtracted.isEmpty() || !categoryCurrentlyBeingExtracted.equals(rs.getString("category"))) {
	        		categoryCurrentlyBeingExtracted = rs.getString("category");
	        		descriptionCurrentlyBeingExtracted = null;
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
	        	Integer processRunId = rs.getInt("processRunId");
	        	orderItem.setProcessRunId(processRunId);
	        	orderItem.setInventoryItemCode(processRunId == 0 ? "" : rs.getString("inventoryItemCode"));
	        	orderItem.setProductName(processRunId == 0 ? "" : rs.getString("productName"));
	        	orderItem.setProductQuantity(processRunId == 0 ? new BigDecimal(0) : rs.getBigDecimal("productQuantity"));
	        	orderItem.setProductUnit(processRunId == 0 ? "" : rs.getString("productUnit"));
	        	orderItem.setDescription(processRunId == 0 ? "" : rs.getString("description"));
	        	orderItem.setOrderedRuns(processRunId == 0 ? new BigDecimal(0) : rs.getBigDecimal("orderedRuns"));
	        	orderItem.setCanceledRuns(processRunId == 0 ? new BigDecimal(0) : rs.getBigDecimal("canceledRuns"));
	        	orderItem.setDiscountedRuns(processRunId == 0 ? new BigDecimal(0) : rs.getBigDecimal("discountedRuns"));
	        	orderItem.setEndedRuns(processRunId == 0 ? new BigDecimal(0) : rs.getBigDecimal("endedRuns"));	
	        	orderItem.setCurrency(processRunId == 0 ? "" : rs.getString("currency"));
	        	
	        	//currently it's prevented to change the system currency while some order is ongoing (RULE_CONSTRAINT_15), so
	        	//order's process runs share the currency
	        	order.setCurrencyCode(orderItem.getCurrency());
	        	
	        	orderItem.setOneRunQuantity(processRunId == 0 ? new BigDecimal(0) : rs.getBigDecimal("oneRunQuantity"));
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
		setCollectedAmount((sourceOrder).getCollectedAmount());
		setAmountToCollect((sourceOrder).getAmountToCollect());
		setCurrencyCode((sourceOrder).getCurrencyCode());
		setCategories((sourceOrder).getCategories());
		setStage((sourceOrder).getStage());
		setStageId((sourceOrder).getStageId());
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
	
	public Integer cancel(Integer processRunId, String inventoryItemCode, BigDecimal runsToCancel) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_cancel\"(:lang, "
							+ "								:orderId, "
							+ "								:processRunId, "
							+ "								:inventoryItemCode, "
							+ "								:runsToCancel, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("orderId", this.getId());
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("inventoryItemCode", inventoryItemCode);
		parametersMap.put("runsToCancel", runsToCancel);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer reorder(Integer processRunId, String inventoryItemCode, BigDecimal runsToReorder) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_reorder\"(:lang, "
							+ "								:orderId, "
							+ "								:processRunId, "
							+ "								:inventoryItemCode, "
							+ "								:runsToReorder, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("orderId", this.getId());
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("inventoryItemCode", inventoryItemCode);
		parametersMap.put("runsToReorder", runsToReorder);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer applyItemDiscount(Integer processRunId, String inventoryItemCode, BigDecimal discount) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_applyItemDiscount\"(:orderId, "
							+ "								:processRunId, "
							+ "								:inventoryItemCode, "
							+ "								:discount, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("inventoryItemCode", inventoryItemCode);
		parametersMap.put("discount", discount);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer applyDiscount(Integer discount) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_applyDiscount\"(:orderId, "
							+ "								:discount, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("discount", discount);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	//retrieve amount in order currency
	public BigDecimal retrieveAmount() throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_getAmount\"(:orderId, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (BigDecimal) super.query(qryStr, parametersMap, new QueryBigDecimalResultMapper()).get(0);
	}
	
	public BigDecimal getCanceledRunsCount() throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_getCanceledRunsCount\"(:orderId, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (BigDecimal) super.query(qryStr, parametersMap, new QueryBigDecimalResultMapper()).get(0);
	}
	
	public Integer endCanceledRuns(Integer processRunId, String inventoryItemCode, BigDecimal runsToEnd) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_endCanceledRuns\"(:orderId, "
							+ "								:processRunId, "
							+ "								:inventoryItemCode, "
							+ "								:runsToEnd, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("inventoryItemCode", inventoryItemCode);
		parametersMap.put("runsToEnd", runsToEnd);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public List<Object> getOngoing() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Activity_getOngoing\"(:lang, :loginname)";	
		return query(qryStr, trackedObjectDao.addLoginname(qryStr, getAllQueryNamedParameters), new ActivityMapper());
	}
	
	public QueryResultWithReport collect(Integer cashRegisterId,
							Integer orderId,
							ArrayList<Integer> currencyIds, 
							ArrayList<BigDecimal> amounts,
							Integer customer) throws SQLException, Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Order_collect\"(:cashRegisterId, "
				+ "											:orderId, "
				+ "											:lang, "
				+ "											:currencyIds, "
				+ "											:amounts, "
				+ "											:customer, "
				+ "											:loginname) AS queryresult";
		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("cashRegisterId", cashRegisterId);
		parametersMap.put("orderId", orderId);
		parametersMap.put("lang", Locales.getCurrent().getLanguage());	
		parametersMap.put("currencyIds", createArrayOfSQLType("integer", currencyIds.toArray()));
		parametersMap.put("amounts", createArrayOfSQLType("numeric", amounts.toArray()));
		parametersMap.put("customer", customer);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (QueryResultWithReport) super.query(qryStr, parametersMap, new QueryResultWithReportMapper()).get(0);
	}
	
	public QueryResultWithReport cancel(Integer orderId) throws SQLException, Exception {

		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Order_cancelOrder\"(:orderId, "
		+ "													:loginname) AS queryresult";
		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", orderId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (QueryResultWithReport) super.query(qryStr, parametersMap, new QueryResultWithReportMapper()).get(0);
	}
	
	public Integer switchCustomer(Integer orderId, Integer customerId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_switchCustomer\"(:orderId, "
							+ "										:customerId, "
							+ "										:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", orderId);
		parametersMap.put("customerId", customerId);
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

	public BigDecimal getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(BigDecimal collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getAmountToCollect() {
		return amountToCollect;
	}

	public void setAmountToCollect(BigDecimal amountToCollect) {
		this.amountToCollect = amountToCollect;
	}

	public Stage getStageId() {
		return stageId;
	}

	public void setStageId(Stage stageId) {
		this.stageId = stageId;
	}
}
