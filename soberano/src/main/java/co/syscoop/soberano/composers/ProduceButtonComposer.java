package co.syscoop.soberano.composers;

import java.text.SimpleDateFormat;
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

import co.syscoop.soberano.domain.tracked.CashRegister;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.tracked.ProcessRun;
import co.syscoop.soberano.domain.tracked.ProcessRunOutputAllocation;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtilitity;
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
			orderId = ZKUtilitity.getObjectIdFromURLQuery("id");
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
	
	@SuppressWarnings("unchecked")
	@Listen("onClick = button#btnProduce")
    public void btnProduce_onClick() throws Throwable {
		
		try {
			Vbox boxDetails = (Vbox) btnProduce.getParent().getParent().getParent().query("#wndContentPanel").query("#boxDetails");
			orderId = ((Intbox) boxDetails.query("#intObjectId")).getValue();
			Order order = new Order(orderId);
			order.get();		
			setFileToPrintFullPath(SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
					"records/production_lines/" + 
					"ORDER_" + orderId + "_ALLOCATIONS" + ".pdf");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String textToprint = orderId + " | " + order.getCountersStr() + " | " + dateFormat.format(new Date()) + "\n\n";
			
			if (!SpringUtility.underTesting()) {
				
				//print order's process run allocations
				try {
					for (Object object : (new ProcessRun()).getOrderProcessRunAllocations(orderId)) {
						try{
							Integer allocationId = ((ProcessRunOutputAllocation) object).getId();
							Integer productionLineId = ((ProcessRunOutputAllocation) object).getProductionLineId();
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
								
								//allocation was not printed yet
								if (!allocationWasPrinted) {									
									if (!description.isEmpty()) {
										textToprint = textToprint + description + "\n";
									}
									textToprint = textToprint + itemName + "\n\n";						
									productionLineAllocations.put(allocationId, true);
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
			
			CashRegister cashRegister = new CashRegister(1);
			cashRegister.get();			
			Printer.print(Translator.translate(textToprint),
												cashRegister, //order, 
												fileToPrintFullPath,
												true);
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.error.ConfigurePrinterProfile"), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
}
