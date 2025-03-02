package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;

public class ActionTest {
	
	protected static ComponentAgent cmbIntelliSearchAgent = null;
	protected static Combobox cmbIntelliSearch = null;
	
	protected static ComponentAgent boxDetailsAgent = null;
	protected static Hbox boxDetails = null;
	protected static Vbox vboxDetails = null;
	
	protected void clickOnEndButton(DesktopAgent desktop) {
		ComponentAgent btnEnd = desktop.query("south").query("button").query("#btnEnd");
		btnEnd.click();			
	}

	protected void clickOnRecordButton(DesktopAgent desktop) {
		ComponentAgent btnRecord = desktop.query("south").query("button").query("#btnRecord");
		btnRecord.click();			
	}
	
	protected void clickOnCancelButton(DesktopAgent desktop) {
		ComponentAgent btnCancel = desktop.query("south").query("button").query("#btnCancel");
		btnCancel.click();			
	}
	
	protected void clickOnInputFormRecordButton(DesktopAgent desktop) {
		ComponentAgent btnRecord = desktop.query("datebox").query("#boxDetails").query("#btnRecord");
		btnRecord.click();			
	}
	
	protected void clickOnApplyButton(DesktopAgent desktop) {
		ComponentAgent btnApply = desktop.query("#incSouth").query("#btnApply");
		btnApply.click();			
	}
	
	protected void clickOnDepositButton(DesktopAgent desktop) {
		ComponentAgent btnDeposit = desktop.query("#incSouth").query("#btnDeposit");
		btnDeposit.click();			
	}
	
	protected void clickOnWithdrawButton(DesktopAgent desktop) {
		ComponentAgent btnWithdraw = desktop.query("#incSouth").query("#btnWithdraw");
		btnWithdraw.click();			
	}
	
	protected void clickOnCountButton(DesktopAgent desktop) {
		ComponentAgent btnCount = desktop.query("#incSouth").query("#btnCount");
		btnCount.click();			
	}
	
	protected void clickOnCollectButton(DesktopAgent desktop) {
		ComponentAgent btnCollect = desktop.query("#incSouth").query("#btnCollect");
		btnCollect.click();			
	}
	
	protected void testNotEnoughRightsException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.NotEnoughRightsException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.NotEnoughRightsException can be caught here.");
	}
	
	protected void testDuplicateKeyException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("org.springframework.dao.DuplicateKeyException", cause.getClass().getName(), "Only org.springframework.dao.DuplicateKeyException can be caught here.");
	}
	
	protected void selectComboitemByLabel(Combobox comp, String label) {
			
		try {
			for (Component co : comp.getChildren()) {
				Comboitem item = (Comboitem) co;
				if (((DomainObject) item.getValue()).getName().toLowerCase().equals(label.toLowerCase())) {
					comp.setSelectedItem(item);
					break;
				}
			}
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	protected void loadObjectDetails(String qualifiedName) {
		
		InputAgent cmbIntelliSearchInputAgent = cmbIntelliSearchAgent.as(InputAgent.class);
		cmbIntelliSearchInputAgent.typing(qualifiedName);
		selectComboitemByLabel(cmbIntelliSearch, qualifiedName);		
		cmbIntelliSearchAgent.click(); 	//needed to trigger cmbIntelliSearch's onClick event under testing
	}
	
	protected void testPasswordsMustMatchException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.PasswordsMustMatchException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.PasswordsMustMatchException can be caught here.");
	}
	
	protected void testSomeFieldsContainWrongValuesException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException can be caught here.");
	}
	
	protected void testShiftHasBeenClosedException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.ShiftHasBeenClosedException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.ShiftHasBeenClosedException can be caught here.");
	}
	
	protected void testWrongDateTimeException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.WrongDateTimeException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.WrongDateTimeException can be caught here.");
	}
	
	protected void testAtLeastOneInventoryItemMustBeMovedException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.AtLeastOneInventoryItemMustBeMovedException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.AtLeastOneInventoryItemMustBeMovedException can be caught here.");
	}
	
	protected void testWeightsMustSum100(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.WeightsMustSum100", cause.getClass().getName(), "Only co.syscoop.soberano.exception.WeightsMustSum100 can be caught here.");
	}
	
	protected void testDisabledCurrencyException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.DisabledCurrencyException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.DisabledCurrencyException can be caught here.");
	}
	
	protected void testOrderAlreadyCollectedException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.OrderAlreadyCollectedException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.OrderAlreadyCollectedException can be caught here.");
	}
	
	protected void testDebtorRequiredException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.DebtorRequiredException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.DebtorRequiredException can be caught here.");
	}
}
