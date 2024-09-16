package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;

public class ProcessRunForm extends ConstrainedForm {
	
	private Combobox cmbProcess;
	private Combobox cmbCostCenter;
	private Decimalbox decRuns;
	private Button btnRecord;
	private Grid grd;

	public ProcessRunForm(DesktopAgent desktop,
									Combobox cmbProcess,
									Combobox cmbCostCenter,
									Decimalbox decimalbox,
									Button btnRecord,
									Grid grd) {
		
		this.constrainedComponents = Arrays.asList("decRuns");
		
		this.setDesktop(desktop);
		this.setCmbProcess(cmbProcess);
		this.setCmbCostCenter(cmbCostCenter);
		
		this.setDecRuns(decimalbox);
		this.constrainableComponents.add(decimalbox);
		this.constrainableComponentById.put("decRuns", decimalbox);
		
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

	public Decimalbox getDecRuns() {
		return decRuns;
	}

	public void setDecRuns(Decimalbox decRuns) {
		this.decRuns = decRuns;
	}
}
