package co.syscoop.soberano.composers;

import java.util.Base64;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.beans.IPDFDocumentToPrint;
import co.syscoop.soberano.database.relational.QueryResultWithReport;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.tracked.PrinterProfile;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.DebtorRequiredException;
import co.syscoop.soberano.exception.DisabledCurrencyException;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.OrderAlreadyCollectedException;
import co.syscoop.soberano.exception.OrderCanceledException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.CashRegisterFormHelper;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings("serial")
public class CashRegisterCollectButtonComposer extends CashRegisterTrackedObjectRecordButtonComposer {
	
	@Wire
	private Button btnCollect;
	
	public CashRegisterCollectButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new CashRegisterFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnCollect.query("#wndContentPanel").query("#boxDetails");
    }
	
	@Listen("onClick = button#btnCollect")
    public void btnCollect_onClick() throws Throwable {
		
		try{
			QueryResultWithReport qrwr = ((CashRegisterFormHelper) trackedObjectFormHelper).collect(boxDetails);
			
			if (!qrwr.getReport().isEmpty()) {
				
				Integer orderId = ZKUtilitity.getObjectIdFromURLQuery("oid");
				String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/tickets/" + 
												"TICKET_" + orderId + ".pdf";
				try {
					//20240726: Barbosa requested not to print ticket on collecting. Better to add a global setting 
					//enabling / disabling this. This line was commented for just creating the file to print and 
					//navigating to the (just closed) order's management form.
					//Printer.print(Translator.translate(qrwr.getReport()), qrwr.getPrinterProfileId(), fileToPrintFullPath, "TICKET_" + orderId, false);
					
					/*20240727: replace this block to print from here (cash register form after order collecting)*/
					PrinterProfile printerProfile = new PrinterProfile(qrwr.getPrinterProfileId());
					printerProfile.get();
					Printer printer = new Printer(printerProfile);
					
					//there is a bean for more printing customization
					IPDFDocumentToPrint pp = null;
					try {
						pp = (IPDFDocumentToPrint) SpringUtility.applicationContext().getBean(printerProfile.getName().toLowerCase());
						pp.createPDFFile(new Order(orderId), fileToPrintFullPath);
					}
					catch(NoSuchBeanDefinitionException nsbdex) {			
						Printer.createFile(printer,
											Translator.translate(qrwr.getReport()),
											qrwr.getPrinterProfileId(),
											fileToPrintFullPath,
											false);
					}
					Executions.sendRedirect("/order.zul?id=" + orderId + 
														"&report=" + Base64.getEncoder().encodeToString(fileToPrintFullPath.getBytes()));
					/*****/
				}
				catch(Exception ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.error.ConfigurePrinterProfile"), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
				}				
			}				
			/*20240726: Uncomment to stay in cash register form after order collecting. 
			Executions.sendRedirect("/cash_register.zul?id=" + 
					((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue().toString() + "&oid=" +
					((Intbox) boxDetails.query("#intSelectedOrder")).getValue().toString());
			*/
		}
		catch(ConfirmationRequiredException ex) {
			return;
		}
		catch(DebtorRequiredException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.selectADebtor"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(DisabledCurrencyException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.operationRequiresEnabledCurrencies"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.permissions.NotEnoughRights"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(NullPointerException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(OrderAlreadyCollectedException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.orderAlreadyCollected"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(OrderCanceledException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.orderCanceled"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(SomeFieldsContainWrongValuesException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
}
