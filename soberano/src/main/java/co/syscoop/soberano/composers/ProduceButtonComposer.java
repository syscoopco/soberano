package co.syscoop.soberano.composers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vbox;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.tracked.ProcessRun;
import co.syscoop.soberano.domain.tracked.ProcessRunOutputAllocation;
import co.syscoop.soberano.domain.tracked.ProductionLine;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtility;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProduceButtonComposer extends SelectorComposer {
	
	private String fileToPrintFullPath = "";
	private Integer orderId = 0;
	
	@Wire
	protected Button btnProduce;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
		try {
			super.doAfterCompose(comp);
			orderId = ZKUtility.getObjectIdFromURLQuery("id");
			setFileToPrintFullPath(SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
									"records/production_lines/" + 
									"ORDER_" + orderId + "_ALLOCATIONS" + ".pdf");
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
    }

	public String getFileToPrintFullPath() {
		return fileToPrintFullPath;
	}

	public void setFileToPrintFullPath(String fileToPrintFullPath) {
		this.fileToPrintFullPath = fileToPrintFullPath;
	}
	
	private static int countLines(String str) {
		String[] lines = str.split("\r\n|\r|\n");
		return lines.length;
	}
	
	@SuppressWarnings("unchecked")
	public static void produce(Integer orderId, String fileToPrintFullPath) throws SoberanoException {
		
		try {
			Order order = new Order(orderId);
			order.get();		
						
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			String header = orderId + " | " + order.getCountersStr() + " | " + dateFormat.format(new Date()) + "\n\n";
									
			ArrayList<Integer> productionLineIds = new ArrayList<>();
			HashMap<Integer, String> textsToPrint = new HashMap<Integer, String>();
			if (!SpringUtility.underTesting()) {
				
				//print order's process run allocations
				try {
					ArrayList<Integer> printedGroupedproductionLineIds = new ArrayList<>();
					
					for (Object object : (new ProcessRun()).getOrderProcessRunAllocations(orderId)) {
						try{
							Integer allocationId = ((ProcessRunOutputAllocation) object).getId();
							Integer productionLineId = ((ProcessRunOutputAllocation) object).getProductionLineId();
							if (productionLineIds.indexOf(productionLineId) == -1) productionLineIds.add(productionLineId);
							
							String description = ((ProcessRunOutputAllocation) object).getDescription();
							String itemName = ((ProcessRunOutputAllocation) object).getItemName();
							
							//print allocation only in case it wasn't printed yet
							HashMap<Integer, HashMap<Integer, Boolean>> thisOrderPrintedAllocations = 
									((HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean>>>) Executions.
																						getCurrent().
																						getDesktop().
																						getWebApp().
																						getAttribute("printed_allocations")).
																							get(orderId);
							
							//order hasn't been collected. it's still open.
							if (thisOrderPrintedAllocations != null) {										
								HashMap<Integer, Boolean> productionLineAllocations = thisOrderPrintedAllocations.get(productionLineId);
								
								if (productionLineAllocations == null) {
									thisOrderPrintedAllocations.put(productionLineId, new HashMap<Integer, Boolean>());
									productionLineAllocations = thisOrderPrintedAllocations.get(productionLineId);
								}
								
								Boolean allocationWasPrinted = 
										productionLineAllocations.get(allocationId) == null ? false : productionLineAllocations.get(allocationId);
								
								//allocation was not printed yet, or it's grouped
								if (!allocationWasPrinted || allocationId == 0) {
									
									//allocations grouped
									if (allocationId == 0) {
										
										HashMap<Integer, String> thisOrderLastPrintedGroupedAllocations =
												((HashMap<Integer, HashMap<Integer, String>>) Executions.
																								getCurrent().
																								getDesktop().
																								getWebApp().
																								getAttribute("last_printed_grouped_allocations")).get(orderId);
										
										String thisProductionLineLastPrintedAllocationsForThatOrder = "";
										
										if (thisOrderLastPrintedGroupedAllocations == null) {											
											((HashMap<Integer, HashMap<Integer, String>>) Executions.
																							getCurrent().
																							getDesktop().
																							getWebApp().
																							getAttribute("last_printed_grouped_allocations")).put(orderId, new HashMap<Integer, String>());
											thisOrderLastPrintedGroupedAllocations = ((HashMap<Integer, HashMap<Integer, String>>) Executions.
													getCurrent().
													getDesktop().
													getWebApp().
													getAttribute("last_printed_grouped_allocations")).get(orderId);
										}
										else {
											thisProductionLineLastPrintedAllocationsForThatOrder = thisOrderLastPrintedGroupedAllocations.get(productionLineId);
										}										
										
										if (printedGroupedproductionLineIds.indexOf(productionLineId) == -1) {											
											
											String thisProductionLineCurrentAllocationsForThatOrder = (new Order(orderId)).getProductionLineGroupedAllocations(productionLineId);
											
											//allocations changed since last printing
											if (!thisProductionLineCurrentAllocationsForThatOrder.equals(thisProductionLineLastPrintedAllocationsForThatOrder)) {
												thisOrderLastPrintedGroupedAllocations.put(productionLineId, thisProductionLineCurrentAllocationsForThatOrder);
												textsToPrint.put(productionLineId, header + description + "\n" + thisProductionLineCurrentAllocationsForThatOrder);
											}
											printedGroupedproductionLineIds.add(productionLineId);
										}										
									}
									else {
										if (textsToPrint.get(productionLineId) == null) {
											textsToPrint.put(productionLineId, header + "\n");
										}
										
										if (!description.isEmpty()) {											
											textsToPrint.put(productionLineId, textsToPrint.get(productionLineId) + description + "\n");
										}
										
										productionLineAllocations.put(allocationId, true);							
										textsToPrint.put(productionLineId, textsToPrint.get(productionLineId) + itemName + "\n\n");
									}
								}
							}
						}
						catch(Exception ex) {
							throw ex;
						}
					}
				}
				catch(Exception ex) {
					throw ex;
				}
			}
			
			for (Integer plId : productionLineIds) {
				ProductionLine productionLine = new ProductionLine(plId);
				productionLine.get();
				
				if (textsToPrint.get(plId) != null && countLines(textsToPrint.get(plId)) > 3) {
					
					Printer.print(Translator.translate(textsToPrint.get(plId)),
							productionLine,
							fileToPrintFullPath + "-" + plId.toString(),
							false /*true better to use computer printer 
									settings to control feeds or blank 
									lines printing at the end of page 
									or document*/);
				}
			}
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.error.ConfigurePrinterProfile"), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
	
	@Listen("onClick = button#btnProduce")
    public void btnProduce_onClick() throws Throwable {
		
		try {
			Vbox boxDetails = (Vbox) btnProduce.getParent().getParent().getParent().query("#wndContentPanel").query("#boxDetails");
			orderId = ((Intbox) boxDetails.query("#intObjectId")).getValue();
			produce(orderId, fileToPrintFullPath);
		}
		catch(Exception ex)	{
			throw ex;
		}
	}
}
