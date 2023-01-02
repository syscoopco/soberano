package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;

public class Test_record {

	protected void clickOnRecordButton(DesktopAgent desktop) {
		ComponentAgent btnRecord = desktop.query("south").query("button");
		btnRecord.click();			
	}
}
