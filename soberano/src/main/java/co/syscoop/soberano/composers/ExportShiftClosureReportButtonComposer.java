package co.syscoop.soberano.composers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.domain.tracked.ShiftClosure;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })

public class ExportShiftClosureReportButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnExport;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void getCostCenterReportToCsv(Integer shiftClosureId, String costCenterName) throws SQLException, IOException {
		
		String csv = Translator.translate(new ShiftClosure(shiftClosureId).getCostCenterReportToCsv(costCenterName));
		String relativePath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath());
		String csvFileName = "cost_center_" + (costCenterName.equals("")?"":costCenterName.replace(" ", "")) + "_" + shiftClosureId.toString() + ".csv";
		String fileFullPath = relativePath + "records/csv/" + csvFileName;
		
		Files.write(Paths.get(fileFullPath), csv.getBytes());
					
		java.io.InputStream is = Executions.getCurrent().getDesktop().getWebApp().getResourceAsStream("/records/csv/" + csvFileName);
		if (is != null) {
			Filedownload.save(is, "text/html", csvFileName);
		}
		else {
			Messagebox.show(Labels.getLabel("message.pageProcesses.CostSheetFileNotFound"), 
  					org.zkoss.util.resource.Labels.getLabel("messageBoxTitle.Information"), 
					0, 
					Messagebox.EXCLAMATION);		
		}
	}
	
	@Listen("onClick = button#btnExport")
    public void btnExport_onClick() throws Exception {
		
		try{
			Textbox txtShownReport = (Textbox) btnExport.getParent().getParent().getParent().query("#wndShowingAll").query("#txtShownReport");
			Integer scId = ((Intbox) txtShownReport.query("#intObjectId")).getValue();
			if (txtShownReport.getText().equals("receivables")) {
				
			}
			else if (txtShownReport.getText().equals("cashregister")) {
				
			}
			else if (txtShownReport.getText().equals("housebill")) {
				
			}
			else if (txtShownReport.getText().equals("costcenter")) {
				Combobox cmbCostCenter = (Combobox) btnExport.query("#cmbCostCenter");
				String costCenterName = "";
				if (cmbCostCenter.getSelectedItem() != null) {
					costCenterName = cmbCostCenter.getText();
				}			
				getCostCenterReportToCsv(scId, costCenterName);
			}
			else if (txtShownReport.getText().equals("spi")) {
				
			}
			else if (txtShownReport.getText().equals("generalfull")) {
				getCostCenterReportToCsv(scId, "");
			}
			else if (txtShownReport.getText().equals("salesbyprice")) {
				
			}
			else if (txtShownReport.getText().equals("notes")) {
				
			}
			else if (txtShownReport.getText().equals("cancellations")) {
				
			}
			else {//export general report csv
				
			}
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
						ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
		}
	}
}