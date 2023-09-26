package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;

public class ProcessRunForm extends ConstrainedForm {
	
	private Combobox cmbProcess;
	private Combobox cmbCostCenter;
	private Intbox intRuns;
	private Button btnRecord;
	private Grid grd;

	public ProcessRunForm(DesktopAgent desktop,
									Combobox cmbProcess,
									Combobox cmbCostCenter,
									Intbox intRuns,
									Button btnRecord,
									Grid grd) {
		
		this.constrainedComponents = Arrays.asList("intRuns");
		
		this.setDesktop(desktop);
		this.setCmbProcess(cmbProcess);
		this.setCmbCostCenter(cmbCostCenter);
		
		this.setIntRuns(intRuns);
		this.constrainableComponents.add(intRuns);
		this.constrainableComponentById.put("intRuns", intRuns);
		
		this.btnRecord = btnRecord;
		this.grd = grd;
	}

	public Grid getGrd() {
		return grd;
	}

	public void setGrd(Grid grd) {
		this.grd = grd;
	}

	public Button getBtnRecord() {
		return btnRecord;
	}

	public void setBtnRecord(Button btnRecord) {
		this.btnRecord = btnRecord;
	}

	public Combobox getCmbProcess() {
		return cmbProcess;
	}

	public void setCmbProcess(Combobox cmbProcess) {
		this.cmbProcess = cmbProcess;
	}

	public Combobox getCmbCostCenter() {
		return cmbCostCenter;
	}

	public void setCmbCostCenter(Combobox cmbCostCenter) {
		this.cmbCostCenter = cmbCostCenter;
	}

	public Intbox getIntRuns() {
		return intRuns;
	}

	public void setIntRuns(Intbox intRuns) {
		this.intRuns = intRuns;
	}
}
