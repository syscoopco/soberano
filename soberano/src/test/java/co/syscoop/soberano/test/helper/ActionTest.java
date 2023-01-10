package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import co.syscoop.soberano.util.ExceptionTreatment;

public class ActionTest {

	protected void clickOnRecordButton(DesktopAgent desktop) {
		ComponentAgent btnRecord = desktop.query("south").query("button");
		btnRecord.click();			
	}
	
	public void testPasswordsMustMatchException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals(cause.getClass().getName(), "co.syscoop.soberano.exception.PasswordsMustMatchException","Only co.syscoop.soberano.exception.PasswordsMustMatchException can be caught here.");
	}
	
	public void testNotEnoughRightsException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals(cause.getClass().getName(), "co.syscoop.soberano.exception.NotEnoughRightsException","Only co.syscoop.soberano.exception.NotEnoughRightsException can be caught here.");
	}
	
	public void testWorkerMustBeAssignedToAResponsibilityException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals(cause.getClass().getName(), "co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException","Only co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException can be caught here.");
	}
	
	public void testDuplicateKeyException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals(cause.getClass().getName(), "org.springframework.dao.DuplicateKeyException","Only org.springframework.dao.DuplicateKeyException can be caught here.");
	}
}
