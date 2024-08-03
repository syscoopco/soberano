package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.database.relational.QueryResultWithReport;
import co.syscoop.soberano.domain.tracked.Balancing;
import co.syscoop.soberano.domain.tracked.CashRegister;
import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.domain.tracked.Deposit;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.tracked.Withdrawal;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.enums.Stage;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.DebtorRequiredException;
import co.syscoop.soberano.exception.DisabledCurrencyException;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.OrderAlreadyCollectedException;
import co.syscoop.soberano.exception.OrderCanceledException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.UndeterminedErrorException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Labels;

public class CashRegisterFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	private ArrayList<Integer> currencyIds = new ArrayList<Integer>();
	private ArrayList<BigDecimal> amounts = new ArrayList<BigDecimal>();
	private String notes = "";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void showOrderCollectingComponents(Window wndContentPanel, CashRegister cashRegister, Integer orderId) throws SQLException {
		
		if (orderId != null) {
			((Intbox) wndContentPanel.query("#intSelectedOrder")).setValue(orderId);
			wndContentPanel.query("#hboxChange").setVisible(true);
			Hbox hboxDecisionButtons = (Hbox) wndContentPanel.getParent().query("#incSouth").query("#hboxDecisionButtons");
			hboxDecisionButtons.query("#btnDeposit").setVisible(false);
			hboxDecisionButtons.query("#btnWithdraw").setVisible(false);
			hboxDecisionButtons.query("#btnCount").setVisible(false);
			Hbox hboxCustomer = (Hbox) hboxDecisionButtons.query("#hboxCustomer");
			hboxCustomer.setVisible(true);
			
			List<Object> noCashcurrencies = cashRegister.getCurrencies(true);
			for (Object item : noCashcurrencies) {
				Button btnNoCashCurrencyButton = new Button(((Currency) item).getName());
				btnNoCashCurrencyButton.setAttribute("currencyId", ((Currency) item).getId());
				btnNoCashCurrencyButton.setSclass("DecisionButton");
				btnNoCashCurrencyButton.addEventListener("onClick", new EventListener() {

					@Override
					public void onEvent(Event event) throws Exception {

						PaymentProcessorWindow wndPaymentProcessorWindow = new PaymentProcessorWindow((Currency) item, 
																										wndContentPanel,
																										orderId);
						wndPaymentProcessorWindow.setPage(wndContentPanel.getPage());
						wndPaymentProcessorWindow.doModal();
					}
				});
				hboxDecisionButtons.insertBefore(btnNoCashCurrencyButton, hboxCustomer);
			}
			Order order = new Order(orderId);
			order.get();
			((Decimalbox) wndContentPanel.query("#decToCollect")).setValue(order.getAmountToCollect());
			if (order.getCustomer() != 0) {
				ZKUtilitity.setValueWOValidation((Combobox) hboxCustomer.query("#cmbCustomer"), order.getCustomer());
			}
			if (order.getStageId() == Stage.ONGOING || order.getStageId() == Stage.CLOSED_NOT_COLLECTED) {
				wndContentPanel.query("#hboxToCollect").setVisible(true);
				((Button) hboxDecisionButtons.query("#btnCollect")).setVisible(true);
				if (order.getStageId() == Stage.CLOSED_NOT_COLLECTED) {
					
					//cancel order disabled, untested
					((Button) hboxDecisionButtons.query("#btnCancel")).setVisible(false);
				}
			}
			else {
				wndContentPanel.query("#hboxToCollect").setVisible(false); 
				((Button) hboxDecisionButtons.query("#btnCollect")).setVisible(false);
				if (order.getStageId() == Stage.CLOSED) {
					
					//cancel order disabled, untested
					((Button) hboxDecisionButtons.query("#btnCancel")).setVisible(false);
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initForm(Window wndContentPanel, Integer cashRegisterId, Integer orderId) throws SQLException {
		
		CashRegister cashRegister = new CashRegister(cashRegisterId);	
		showOrderCollectingComponents(wndContentPanel, cashRegister, orderId);		
			
		((Intbox) wndContentPanel.query("#intSelectedCashRegister")).setValue(cashRegisterId);
		Hlayout hlayCurrencies = (Hlayout) wndContentPanel.query("#hlayCurrencies");
		cashRegister.get();
		List<Object> currencies = cashRegister.getCurrencies(false);
		for (Object item : currencies) {			
			
			Vbox vbox = new Vbox();
			vbox.setHflex("1");
			vbox.setAlign("center");
			hlayCurrencies.appendChild(vbox);
			
			Cell cellButton = new Cell();
			cellButton.setHflex("1");
			cellButton.setAlign("center");
			vbox.appendChild(cellButton);
			
			Currency curr = (Currency) item;
			Button button = new Button(curr.getStringId());
			if (!curr.getIsCash()) {button.setDisabled(true);}
			button.setId("btn" + curr.getStringId());
			if (curr.getIsSystemCurrency()) {
				((Textbox) wndContentPanel.query("#txtSelectedCurrencyCode")).setValue(button.getLabel());
				button.setSclass("CollectingButtonPushed");
			}
			else
				button.setSclass("CollectingButton");
			button.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {

					Button targetCurrencyButton = (Button) event.getTarget();					
					for (Component comp : targetCurrencyButton.getParent().getParent().getParent().queryAll("button")) {
						((Button) comp).setClass("CollectingButton");
					}
					targetCurrencyButton.setClass("CollectingButtonPushed");
					((Textbox) targetCurrencyButton.query("#txtSelectedCurrencyCode")).setValue(targetCurrencyButton.getLabel());
					Decimalbox decInput = (Decimalbox) targetCurrencyButton.query("#decInput");
					decInput.setValue(new BigDecimal(0.0));
					Textbox txtInputExpression = (Textbox) targetCurrencyButton.query("#txtInputExpression");
					txtInputExpression.setValue("");
					txtInputExpression.focus();
				}
			});
			cellButton.appendChild(button);
						
			Cell cellBalance = new Cell();
			cellBalance.setHflex("1");
			cellBalance.setAlign("center");
			vbox.appendChild(cellBalance);
			
			Decimalbox decBalance = new Decimalbox(cashRegister.getBalances().get(curr.getStringId()));
			decBalance.setId("decBalance" + curr.getStringId());
			decBalance.setFormat("####.########");
			decBalance.setReadonly(true);
			decBalance.setSclass("CountingBalanceBox");
			decBalance.setStyle("color:blue;");
			cellBalance.appendChild(decBalance);
			
			cellBalance.setVisible(orderId == null);
			
			Cell cellEnteredAmount = new Cell();
			cellEnteredAmount.setHflex("1");
			cellEnteredAmount.setAlign("center");
			vbox.appendChild(cellEnteredAmount);
			
			Decimalbox decEnteredAmount = new Decimalbox(new BigDecimal(0.0));
			decEnteredAmount.setId("decEnteredAmount" + curr.getStringId());
			decEnteredAmount.setFormat("####.########");
			decEnteredAmount.setReadonly(true);
			decEnteredAmount.setSclass("CountingCountedBox");
			cellEnteredAmount.appendChild(decEnteredAmount);
			
			Clients.scrollIntoView(hlayCurrencies);
			Decimalbox decInput = (Decimalbox) hlayCurrencies.query("#decInput");
			decInput.setValue(new BigDecimal(0.0));
			Textbox txtInputExpression = (Textbox) hlayCurrencies.query("#txtInputExpression");
			txtInputExpression.focus();
		}
	}

	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public void cleanForm(Box boxDetails) {		
		
		Executions.sendRedirect("/cash_register.zul?id=" + 
				((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue().toString());
	}
	
	private void fillAmounts(Box boxDetails, Boolean excludeCash) throws SQLException {
		
		CashRegister cashRegister = new CashRegister(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue());
		List<Object> currencies = cashRegister.getCurrencies(excludeCash);
		currencyIds.clear();
		amounts.clear();
		for (Object item : currencies) {
			currencyIds.add(((Currency) item).getId());
			String currCode = ((Currency) item).getStringId();
			amounts.add(((Decimalbox) boxDetails.query("#decEnteredAmount" + currCode)).getValue());
		}
		notes = ((Textbox) boxDetails.query("#txtNotes")).getValue();
	}
	
	public Integer deposit(Box boxDetails, Boolean excludeCash) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			fillAmounts(boxDetails, excludeCash);			
			qryResult = (new Deposit(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue(), 
									null, 
									null,
									currencyIds, 
									amounts,
									notes)).record();
			if (qryResult == -1) {
				throw new NotEnoughRightsException();						
			}
			if (qryResult == -2) {
				throw new DisabledCurrencyException();
			}
			requestedAction = ActionRequested.NONE;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnDeposit")).setLabel(Labels.getLabel("caption.action.deposit"));
			return qryResult;
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnDeposit")).setLabel(Labels.getLabel("caption.action.confirmDeposit"));
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnWithdraw")).setDisabled(true);
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnCount")).setDisabled(true);
			throw new ConfirmationRequiredException();
		}
	}
	
	public Integer withdraw(Box boxDetails, Boolean excludeCash) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			fillAmounts(boxDetails, excludeCash);			
			qryResult = (new Withdrawal(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue(), 
									null, 
									null,
									currencyIds, 
									amounts,
									notes)).record();
			if (qryResult == -1) {
				throw new NotEnoughRightsException();						
			}
			if (qryResult == -2) {
				throw new DisabledCurrencyException();
			}
			requestedAction = ActionRequested.NONE;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnWithdraw")).setLabel(Labels.getLabel("caption.action.withdraw"));
			return qryResult;
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnDeposit")).setDisabled(true);
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnWithdraw")).setLabel(Labels.getLabel("caption.action.confirmWithdrawal"));
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnCount")).setDisabled(true);
			throw new ConfirmationRequiredException();
		}
	}
	
	public Integer count(Box boxDetails, Boolean excludeCash) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			fillAmounts(boxDetails, excludeCash);			
			qryResult = (new Balancing(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue(), 
									currencyIds, 
									amounts,
									notes)).record();
			if (qryResult == -1) {
				throw new NotEnoughRightsException();						
			}
			if (qryResult == -2) {
				throw new DisabledCurrencyException();
			}
			requestedAction = ActionRequested.NONE;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnCount")).setLabel(Labels.getLabel("caption.action.count"));
			
			return qryResult;
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnDeposit")).setDisabled(true);
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnWithdraw")).setDisabled(true);
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnCount")).setLabel(Labels.getLabel("caption.action.confirmBalancing"));
			throw new ConfirmationRequiredException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public QueryResultWithReport collect(Box boxDetails) throws Exception {
		
		Combobox cmbCustomer = ((Combobox) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#cmbCustomer"));
		QueryResultWithReport qrwr;
		
		//not enough money entered
		if (((Decimalbox) boxDetails.query("#decCounted")).getValue().
				compareTo(((Decimalbox) boxDetails.query("#decToCollect")).getValue()) < 0) {
			
			//customer left in blank
			if (cmbCustomer.getSelectedItem() == null) {
				Messagebox.show(Labels.getLabel("message.validation.selectADebtor"), 
								Labels.getLabel("messageBoxTitle.Warning"),
								0,
								Messagebox.EXCLAMATION);
			}
		}
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			fillAmounts(boxDetails, false);
			Integer orderId = ((Intbox) boxDetails.query("#intSelectedOrder")).getValue();
			qrwr = (new Order(orderId).collect(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue(),
										currencyIds, 
										amounts,
										notes,
										cmbCustomer.getSelectedItem() == null ? null : ((DomainObject) cmbCustomer.getSelectedItem().getValue()).getId()));
			if (qrwr.getResult() == -1) {
				throw new NotEnoughRightsException();						
			}
			if (qrwr.getResult() == -2) {
				throw new DisabledCurrencyException();
			}
			if (qrwr.getResult() == -3) {
				throw new DebtorRequiredException();
			}
			if (qrwr.getResult() == -4) {
				throw new OrderAlreadyCollectedException();
			}
			if (qrwr.getResult() == -5) {
				throw new OrderCanceledException();
			}
			if (qrwr.getResult() == null) {
				throw new UndeterminedErrorException();
			}
			
			//there's not ZK web application context under testing
			if (!SpringUtility.underTesting()) {
				
				//deallocate order's printed allocations store
				((HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean>>>) Executions.getCurrent().
																							getDesktop().
																							getWebApp().
																							getAttribute("printed_allocations")).remove(orderId);
			}
			
			requestedAction = ActionRequested.NONE;
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnCollect")).setLabel(Labels.getLabel("caption.action.confirm"));
			throw new ConfirmationRequiredException();
		}
		return qrwr;
	}
	
	public QueryResultWithReport cancel(Box boxDetails) throws Exception {
		
		QueryResultWithReport qrwr;
		
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			qrwr = (new Order(((Intbox) boxDetails.query("#intSelectedOrder")).getValue())).cancel();
			if (qrwr.getResult() == -1) {
				throw new NotEnoughRightsException();						
			}
			if (qrwr.getResult() == -2) {
				throw new ShiftHasBeenClosedException();
			}			
			if (qrwr.getResult() == null) {
				throw new UndeterminedErrorException();
			}
			requestedAction = ActionRequested.NONE;
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnCancel")).setLabel(Labels.getLabel("caption.action.confirm"));
			throw new ConfirmationRequiredException();
		}
		return qrwr;
	}

	public ArrayList<BigDecimal> getAmounts() {
		return amounts;
	}

	public void setAmounts(ArrayList<BigDecimal> amounts) {
		this.amounts = amounts;
	}

	public ArrayList<Integer> getCurrencyIds() {
		return currencyIds;
	}

	public void setCurrencyIds(ArrayList<Integer> currencyIds) {
		this.currencyIds = currencyIds;
	}

	@Override
	public Integer cancelFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public Integer closeFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public Integer billFromForm(Box boxDetails) {
		return null;
	}

	@Override
	public Integer makeFromForm(Box boxDetails) {
		return null;
	}
}
