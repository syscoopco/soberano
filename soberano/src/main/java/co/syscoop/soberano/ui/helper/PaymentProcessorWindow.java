package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.util.HashMap;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.*;

import co.syscoop.soberano.beans.IPaymentProcessor;
import co.syscoop.soberano.domain.tracked.CashRegister;
import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.Parameter;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.PaymentLink;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings("serial")
public class PaymentProcessorWindow extends Window {
	
	private Currency currency = null;
	private Window wndCashRegisterContentPanel = null;
	private Integer orderId = 0;
	
	private void calcReceivedBalances(Event clicEvent, Window wndContentPanel, Decimalbox decToCollect) {
		Events.sendEvent(Events.ON_CLICK, ((Button) wndContentPanel.query("#btn" + currency.getStringId())), null);
		Textbox txtInputExpression = (Textbox) wndContentPanel.query("#txtInputExpression");
		txtInputExpression.setValue(decToCollect.getValue().toPlainString());
		Events.sendEvent(Events.ON_CHANGE, txtInputExpression, null);
		Events.sendEvent(Events.ON_CLICK, ((Button) wndContentPanel.query("#btnCalc")), null);
		clicEvent.getTarget().getParent().getParent().getParent().detach();
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaymentProcessorWindow(Currency currency, Window wndContentPanel, Integer orderId) {
		
		this.setCurrency(currency);
		this.setWndCashRegisterContentPanel(wndContentPanel);
		this.setOrderId(orderId);
		
		BigDecimal toCollectInSystemCurrency = ((Decimalbox) wndContentPanel.query("#decToCollect")).getValue();
		BigDecimal countedInSystemCurrency = ((Decimalbox) wndContentPanel.query("#decCounted")).getValue();
		BigDecimal toCollect = null;
		if (toCollectInSystemCurrency != null && countedInSystemCurrency != null) {
			toCollect = (toCollectInSystemCurrency.subtract(countedInSystemCurrency)).divide(currency.getExchangeRate(), 8, BigDecimal.ROUND_HALF_EVEN);
		}
		else {
			toCollect = new BigDecimal(0);
		}
		
		this.setTitle(Labels.getLabel("CollectionByMeansOfPaymentProcessor") + " " + currency.getName());
		this.setBorder("normal");
		this.setSizable(true);
		this.setClosable(true);
		
		Vbox vbox = new Vbox();
		vbox.setVflex("1");
		vbox.setHflex("1");
		vbox.setParent(this);
		
		Hbox hboxAmount = new Hbox();
		hboxAmount.setHflex("1");
		hboxAmount.setParent(vbox);
		hboxAmount.setAlign("start");
		Label lblToCollect = new Label(Labels.getLabel("caption.field.ToCollect"));
		lblToCollect.setParent(hboxAmount);
		Separator sep = new Separator();
		sep.setParent(hboxAmount);				
		Decimalbox decToCollect = new Decimalbox(toCollect);
		decToCollect.setReadonly(false);
		decToCollect.setConstraint("no negative");
		decToCollect.setHflex("1");
		decToCollect.setParent(hboxAmount);
		
		Hbox hboxActions = new Hbox();
		hboxActions.setHflex("1");
		hboxActions.setParent(vbox);
		hboxActions.setAlign("end");
		
		Button btnCollect = new Button(Labels.getLabel("caption.action.collect"));
		btnCollect.setParent(hboxActions);
		btnCollect.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				calcReceivedBalances(event, wndContentPanel, decToCollect);
			}
		});
		
		Button btnQR = new Button(Labels.getLabel("caption.action.QR"));
		btnQR.setParent(hboxActions);
		btnQR.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				try {
					String ticket = Translator.translate(new Order(orderId).retrieveTicket(new BigDecimal(0), new BigDecimal(0)).getTextToPrint());
					IPaymentProcessor pp = (IPaymentProcessor) SpringUtility.applicationContext().getBean(currency.getPaymentProcessorName().toLowerCase());
					HashMap<String, String> ppParams = new HashMap<String, String>();
					for (Object paramObject : new CashRegister().getPaymentProcessorParameters(currency.getPaymentProcessor())) {
						ppParams.put(((Parameter) paramObject).getParamName(), 
									((Parameter) paramObject).getParamValue());
					}
					pp.setParameters(ppParams);
					PaymentLink paymentLink = pp.createPaymentLink(orderId, ticket, decToCollect.getValue());
					calcReceivedBalances(event, wndContentPanel, decToCollect);
					if (!paymentLink.getPaymentLinkURL().isEmpty()) {
						if (!pp.openPaymentLinkInNewWindow()) {
							PaymentLinkWindow wndPaymentLinkWindow = new PaymentLinkWindow(currency.getPaymentProcessorName(),
									paymentLink.getPaymentLinkURL(),
									paymentLink.getPaymentLinkQRImage());
							wndPaymentLinkWindow.setPage(wndContentPanel.getPage());
							wndPaymentLinkWindow.doModal();
						}
						else {
							Executions.getCurrent().sendRedirect(paymentLink.getPaymentLinkURL(), "_blank");
						}
					}
					
				}
				catch(Exception ex) {
					ExceptionTreatment.logAndShow(ex, 
							ex.getMessage(), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
				}				
			}
		});
		
		Button btnClose = new Button(Labels.getLabel("caption.action.close"));
		btnClose.setParent(hboxActions);
		btnClose.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				event.getTarget().getParent().getParent().getParent().detach();
			}
		});
		
		this.setWidth("30%");
		this.setHeight("30%");
	}
	
	public Window getWndCashRegisterContentPanel() {
		return wndCashRegisterContentPanel;
	}

	public void setWndCashRegisterContentPanel(Window wndCashRegisterContentPanel) {
		this.wndCashRegisterContentPanel = wndCashRegisterContentPanel;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
