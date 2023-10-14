package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Balancing;
import co.syscoop.soberano.domain.tracked.CashRegister;
import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.domain.tracked.Deposit;
import co.syscoop.soberano.domain.tracked.Withdrawal;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.DisabledCurrencyException;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.vocabulary.Labels;

public class CashRegisterFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	private ArrayList<Integer> currencyIds = new ArrayList<Integer>();
	private ArrayList<BigDecimal> amounts = new ArrayList<BigDecimal>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initForm(Window wndContentPanel, Integer cashRegisterId) throws SQLException {
		
		((Intbox) wndContentPanel.query("#intSelectedCashRegister")).setValue(cashRegisterId);
		Hlayout hlayCurrencies = (Hlayout) wndContentPanel.query("#hlayCurrencies");
		CashRegister cashRegister = new CashRegister(cashRegisterId);
		cashRegister.get();
		List<Object> currencies = cashRegister.getCurrencies();
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
	
	private void fillAmounts(Box boxDetails) throws SQLException {
		
		CashRegister cashRegister = new CashRegister(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue());
		List<Object> currencies = cashRegister.getCurrencies();
		currencyIds.clear();
		amounts.clear();
		for (Object item : currencies) {
			currencyIds.add(((Currency) item).getId());
			String currCode = ((Currency) item).getStringId();
			amounts.add(((Decimalbox) boxDetails.query("#decEnteredAmount" + currCode)).getValue());
		}
	}
	
	public Integer deposit(Box boxDetails) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			fillAmounts(boxDetails);			
			qryResult = (new Deposit(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue(), 
									null, 
									null,
									currencyIds, 
									amounts)).record();
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
	
	public Integer withdraw(Box boxDetails) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			fillAmounts(boxDetails);			
			qryResult = (new Withdrawal(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue(), 
									null, 
									null,
									currencyIds, 
									amounts)).record();
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
	
	public Integer count(Box boxDetails) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			fillAmounts(boxDetails);			
			qryResult = (new Balancing(((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue(), 
									currencyIds, 
									amounts)).record();
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
