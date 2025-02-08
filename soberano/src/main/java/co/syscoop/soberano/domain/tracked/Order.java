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
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.database.relational.ActivityMapper;
import co.syscoop.soberano.database.relational.CounterOrderMapper;
import co.syscoop.soberano.database.relational.InvoiceDataMapper;
import co.syscoop.soberano.database.relational.OrderedItemMapper;
import co.syscoop.soberano.database.relational.PrintableDataMapper;
import co.syscoop.soberano.database.relational.QueryBigDecimalResultMapper;
import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
import co.syscoop.soberano.database.relational.QueryResultWithReport;
import co.syscoop.soberano.database.relational.QueryResultWithReportMapper;
import co.syscoop.soberano.domain.untracked.ContactData;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.domain.untracked.helper.OrderItem;
import co.syscoop.soberano.enums.Stage;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Labels;

public class Order extends BusinessActivityTrackedObject {

	private String label = "";
	private ArrayList<String> counters = new ArrayList<String>();
	private String countersStr = "";
	private Integer customer = 0;
	private String customerStr = "";
	private String deliverTo = "";
	private String deliveryBy = "";
	private Integer deliveryById = 0;
	private Integer discount = 0;
	private BigDecimal amount = new BigDecimal(0); //in order currency
	private BigDecimal collectedAmount = new BigDecimal(0); //in order currency
	private BigDecimal amountToCollect = new BigDecimal(0); //in current system currency
	private ArrayList<String> categories = new ArrayList<String>();
	private String stage = "";
	private Stage stageId = Stage.ONGOING;
	
	private ContactData deliveryContactData = null;	
	
	//currently it's prevented to change the system currency while some order is ongoing (RULE_CONSTRAINT_15), so
	//order's process runs share the currency
	private String currencyCode = "";
	
	//the key is a category
	private HashMap<String, ArrayList<String>> descriptions = new HashMap<String, ArrayList<String>>();
	
	//the key is a category + a description 
	private HashMap<String, ArrayList<OrderItem>> orderItems = new HashMap<String, ArrayList<OrderItem>>();
	
	//additions
	private ArrayList<OrderItem> additions = new ArrayList<OrderItem>();
	
	public Order(Integer id,
				String label,
				String counters,
				String customer,
				Integer customerId,
				String deliverTo,
				String deliveryBy,
				Integer orderDiscount,
				BigDecimal orderAmount,
				BigDecimal collectedAmount,
				BigDecimal amountToCollect,
				String stage,
				Stage stageId,
				String emailAddress,
				String mobilePhoneNumber,
				String countryCode,
				String address,
				String postalCode,
				String town,
				Integer municipality,
				String city,
				Integer province,
				Double latitude,
				Double longitude,
				Integer printerProfile) {
		super(id);
		this.label = label;
		this.countersStr = counters;
		this.customerStr = customer;
		this.customer = customerId;
		this.deliverTo = deliverTo;
		this.deliveryBy = deliveryBy;
		this.discount = orderDiscount;
		this.amount = orderAmount;
		this.collectedAmount = collectedAmount;
		this.amountToCollect = amountToCollect;
		this.stage = stage;
		this.stageId = stageId;
		this.setDeliveryContactData(new ContactData(emailAddress,
											 mobilePhoneNumber,
											 "",
											 countryCode,
											 address,
											 postalCode,
											 town,
											 municipality,
											 city,
											 province,
											 latitude,
											 longitude));
		this.setPrinterProfile(printerProfile);
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
	
	public Order(String label, ArrayList<String> counters, Integer customer, Integer provider) {
		this(label,counters, customer);
		this.deliveryById = provider;
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Order_create\"(:label, "
				+ "											:counters, "						
				+ "											:customer, "
				+ "											:provider, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("label", this.getLabel());	
		recordParameters.addValue("counters", createArrayOfSQLType("varchar", this.getCounters().toArray()));
		recordParameters.addValue("customer", this.getCustomer());
		recordParameters.addValue("provider", this.getDeliveryById());		
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
	        							rs.getString("deliveryBy"),
	        							rs.getInt("orderDiscount"),
	        							rs.getBigDecimal("orderAmount"),
	        							rs.getBigDecimal("collectedAmount"),
	        							rs.getBigDecimal("amountToCollect"),
	        							Labels.getLabel("translation.stage." + rs.getString("stage")),
	        							Stage.values()[rs.getInt("stageId")],
	        							rs.getString("emailAddress"),
										rs.getString("mobilePhoneNumber"),
										rs.getString("countryCode"),
										rs.getString("address"),
										rs.getString("postalCode"),
										rs.getString("town"),
										rs.getInt("municipalityId"),
										rs.getString("city"),
										rs.getInt("provinceId"),
										rs.getDouble("latitude"),
										rs.getDouble("longitude"),
										rs.getInt("printerProfile"));
	        	}
	        	if (categoryCurrentlyBeingExtracted.isEmpty() || !categoryCurrentlyBeingExtracted.equals(rs.getString("category"))) {
	        		categoryCurrentlyBeingExtracted = rs.getString("category");
	        		descriptionCurrentlyBeingExtracted = null;
	        		
	        		//addition categories are ignored
	        		if (!(rs.getInt("thisIsAnAdditionOf") > 0)) {
	        			order.getCategories().add(categoryCurrentlyBeingExtracted);
		        		order.getDescriptions().put(categoryCurrentlyBeingExtracted, new ArrayList<String>());
		        		order.getOrderItems().put(categoryCurrentlyBeingExtracted, new ArrayList<OrderItem>());
	        		}
	        	}
	        	if (descriptionCurrentlyBeingExtracted == null || !descriptionCurrentlyBeingExtracted.equals(rs.getString("description"))) {
	        		
	        		//addition descriptions are ignored
	        		if (!(rs.getInt("thisIsAnAdditionOf") > 0)) {
	        			descriptionCurrentlyBeingExtracted = rs.getString("description");
		        		order.getDescriptions().get(categoryCurrentlyBeingExtracted).add(descriptionCurrentlyBeingExtracted);	        		
		        		order.getOrderItems().put(categoryCurrentlyBeingExtracted + ":" + descriptionCurrentlyBeingExtracted, new ArrayList<OrderItem>());
	        		}
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
	        	orderItem.setThisIsAnAdditionOf(rs.getInt("thisIsAnAdditionOf"));
	        	if (rs.getInt("thisIsAnAdditionOf") > 0) {order.getAdditions().add(orderItem);} 
	        	
	        	//currently it's prevented to change the system currency while some order is ongoing (RULE_CONSTRAINT_15), so
	        	//order's process runs share the currency
	        	order.setCurrencyCode(orderItem.getCurrency());
	        	
	        	orderItem.setOneRunQuantity(processRunId == 0 ? new BigDecimal(0) : rs.getBigDecimal("oneRunQuantity"));
	        	
	        	if (!(rs.getInt("thisIsAnAdditionOf") > 0)) {
	        		order.getOrderItems().get(categoryCurrentlyBeingExtracted + ":" + descriptionCurrentlyBeingExtracted).add(orderItem);
	        	}	        	
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
	public Integer print() throws SoberanoException {
		
		String printJobName = "ORDER_" + this.getId();
		try {
			PrinterProfile printerProfile = new PrinterProfile(this.getPrinterProfile());
			printerProfile.get();
			Printer printer = new Printer(printerProfile);			
			String textToPrint = this.retrieveTicket(new BigDecimal(0), new BigDecimal(0)).getTextToPrint();
			String fileFullPath = "./records/orders/" + "ORDER_" + this.getId().toString();
			printer.createPDFFile(textToPrint, fileFullPath);
			printer.printPDFFile(textToPrint, fileFullPath, printJobName);
		} 
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.permissions.NotEnoughRights"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
}
		
		catch (Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
						Labels.getLabel("error.print.ErrorWhilePrintingDocument") + " PRINT JOB: " + printJobName + ". DETAILS: " + ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
		}
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
		setDeliveryBy((sourceOrder).getDeliveryBy());
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
		deliveryContactData = new ContactData(sourceOrder.getDeliveryContactData());
		setPrinterProfile((sourceOrder).getPrinterProfile());
		setAdditions((sourceOrder).getAdditions());
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
	
	public Integer make(Integer itemId, String description, BigDecimal runs, ArrayList<Integer> additions) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_make\"(:orderId, "
							+ "								:itemId, "
							+ "								:description, "
							+ "								:runs, "
							+ "								:additions, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("itemId", itemId);
		parametersMap.put("description", description);
		parametersMap.put("runs", runs);
		parametersMap.put("additions", createArrayOfSQLType("integer", additions.toArray()));
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer orderAddition(Integer itemId, Integer processRunId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_orderAddition\"(:orderId, "
							+ "								:itemId, "
							+ "								:processRunId, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("itemId", itemId);
		parametersMap.put("processRunId", processRunId);
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
	
	public Integer cancelAddition(Integer processRunId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_cancelAddition\"(:lang, "
							+ "								:processRunId, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("processRunId", processRunId);
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
	
	public PrintableData retrieveTicket(BigDecimal receivedAMount, BigDecimal change) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Order_getTicket\"(:orderId, "
							+ "								:receivedAMount, "
							+ "								:change, "
							+ "								:lang, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("receivedAMount", receivedAMount);
		parametersMap.put("change", change);
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(qryStr, parametersMap, new PrintableDataMapper()).get(0);
	}
	
	public PrintableData retrieveTicket() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Order_getTicket\"(:orderId, "
							+ "								:lang, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(qryStr, parametersMap, new PrintableDataMapper()).get(0);
	}
	
	@Override
	public PrintableData getReportFull() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Order_getReport\"(:orderId, "
							+ "								:lang, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(qryStr, parametersMap, new PrintableDataMapper()).get(0);
	}
	
	@Override
	public String getReport() throws SQLException {
		return retrieveTicket(new BigDecimal(0), new BigDecimal(0)).getTextToPrint();
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
							ArrayList<Integer> currencyIds, 
							ArrayList<BigDecimal> amounts,
							String notes,
							BigDecimal tip,
							Integer customer) throws SQLException, Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Order_collect\"(:cashRegisterId, "
				+ "											:orderId, "
				+ "											:lang, "
				+ "											:currencyIds, "
				+ "											:amounts, "
				+ "											:tip, "
				+ "											:notes, "
				+ "											:customer, "
				+ "											:loginname) AS queryresult";
		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("cashRegisterId", cashRegisterId);
		parametersMap.put("orderId", this.getId());
		parametersMap.put("lang", Locales.getCurrent().getLanguage());	
		parametersMap.put("currencyIds", createArrayOfSQLType("integer", currencyIds.toArray()));
		parametersMap.put("amounts", createArrayOfSQLType("numeric", amounts.toArray()));
		parametersMap.put("tip", tip);
		parametersMap.put("notes", notes);
		parametersMap.put("customer", customer);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (QueryResultWithReport) super.query(qryStr, parametersMap, new QueryResultWithReportMapper()).get(0);
	}
	
	public QueryResultWithReport cancel() throws SQLException, Exception {

		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Order_cancelOrder\"(:orderId, "
		+ "													:loginname) AS queryresult";
		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (QueryResultWithReport) super.query(qryStr, parametersMap, new QueryResultWithReportMapper()).get(0);
	}
	
	public Integer changeCustomer(Integer customerId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_changeCustomer\"(:orderId, "
							+ "										:customerId, "
							+ "										:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("customerId", customerId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer changeDeliveryProvider(Integer providerId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_changeDeliveryProvider\"(:orderId, "
									+ "										:providerId, "
									+ "										:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("providerId", providerId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer checkDeliveryProviderZone(Integer providerId, Integer customerId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_checkDeliveryProviderZone\"(:providerId, "
									+ "										:customerId) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("providerId", providerId);
		parametersMap.put("customerId", customerId);
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer changeDeliveryAddress(String emailAddress,
										String mobilePhoneNumber,
										String address,
										String postalCode,
										String town,
										Integer municipalityId,
										String city,
										Double latitude,
										Double longitude) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_changeDeliveryAddress\"(:orderId, "
								+ "											:emailAddress, "
								+ "											:mobilePhoneNumber, "
								+ "											:address, "
								+ "											:postalCode, "
								+ "											:town, "
								+ "											:municipalityId, "
								+ "											:city, "
								+ "											:latitude, "
								+ "											:longitude, "
								+ "											:loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("emailAddress", emailAddress);
		parametersMap.put("mobilePhoneNumber", mobilePhoneNumber);
		parametersMap.put("address", address);
		parametersMap.put("postalCode", postalCode);
		parametersMap.put("town", town);
		parametersMap.put("municipalityId", municipalityId);
		parametersMap.put("city", city);
		parametersMap.put("latitude", latitude);
		parametersMap.put("longitude", longitude);		
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer reopen() throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_reopen\"(:orderId, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public List<Object> getCurrentOrdersOnCounter() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Order_getCurrentOrdersOnCounter\"()";	
		return query(qryStr, new HashMap<String, Object>(), new CounterOrderMapper());
	}
	
	public int getCurrentOrdersOnCounterCount() throws Exception {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Order_getCurrentOrdersOnCounterCount\"() AS queryresult";		
		return (Integer) super.query(qryStr, new HashMap<String, Object>(), new QueryObjectResultMapper()).get(0);
	}
	
	public List<Object> getOrderedItems() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Order_getOrderedItems\"(:orderid, :lang)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderid", this.getId());
		parametersMap.put("lang", Locales.getCurrent().getLanguage());		
		return query(qryStr, parametersMap, new OrderedItemMapper());
	}
	
	public List<Object> getInvoiceData() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Order_getInvoiceData\"(:orderid, :lang, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("orderid", this.getId());
		parametersMap.put("lang", Locales.getCurrent().getLanguage());	
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return query(qryStr, parametersMap, new InvoiceDataMapper());
	}
	
	public Integer openShift() throws SQLException {
		
		String qryStr = "SELECT soberano.\"fn_Order_openShift\"(:loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer moveOrderedItemToOrder(Integer fromOrderId, Integer toOrderId, Integer processRunId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_moveOrderedItemToOrder\"(:fromOrderId, "
							+ "								:toOrderId, "
							+ "								:processRunId, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fromOrderId", fromOrderId);
		parametersMap.put("toOrderId", toOrderId);
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer moveAllOrderedItemsToOrder(Integer fromOrderId, Integer toOrderId, Integer processRunId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_moveAllOrderedItemsToOrder\"(:fromOrderId, "
							+ "								:toOrderId, "
							+ "								:processRunId, "
							+ "								:loginname) AS queryresult";	
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fromOrderId", fromOrderId);
		parametersMap.put("toOrderId", toOrderId);
		parametersMap.put("processRunId", processRunId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	public Integer moveOrderToCounter(Integer fromOrderId, String counterCode) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Order_moveToCounter\"(:fromOrderId, "
							+ "								:counterCode, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fromOrderId", fromOrderId);
		parametersMap.put("counterCode", counterCode);
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

	public String getDeliveryBy() {
		return deliveryBy;
	}

	public void setDeliveryBy(String deliveryBy) {
		this.deliveryBy = deliveryBy;
	}

	public ContactData getDeliveryContactData() {
		return deliveryContactData;
	}

	public void setDeliveryContactData(ContactData deliveryContactData) {
		this.deliveryContactData = deliveryContactData;
	}

	public ArrayList<OrderItem> getAdditions() {
		return additions;
	}

	public void setAdditions(ArrayList<OrderItem> additions) {
		this.additions = additions;
	}

	public Integer getDeliveryById() {
		return deliveryById;
	}

	public void setDeliveryById(Integer deliveryById) {
		this.deliveryById = deliveryById;
	}
}
