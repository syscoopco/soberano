package co.syscoop.soberano.composers;

import java.util.HashMap;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.beans.IExportToFile;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial", "rawtypes" })

public class ExportShiftClosureReportButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnExport;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnExport")
    public void btnExport_onClick() throws Exception {
		
		try{
			Textbox txtShownReport = (Textbox) btnExport.getParent().getParent().getParent().query("#wndShowingAll").query("#txtShownReport");
			Integer scId = ((Intbox) txtShownReport.query("#intObjectId")).getValue();
			HashMap<String, Object> parameters = new HashMap<String, Object>();			
			parameters.put("shownReport", txtShownReport.getValue());
			parameters.put("shiftClosureId", scId);						
			
			if (txtShownReport.getText().equals("receivables")) {
				
			}
			else if (txtShownReport.getText().equals("cashregister")) {
				
			}
			else if (txtShownReport.getText().equals("housebill")) {
				
			}
			else if (txtShownReport.getText().equals("costcenter")) {
				Combobox cmbCostCenter = (Combobox) btnExport.query("#cmbCostCenter");
				if (cmbCostCenter.getSelectedItem() != null) {
					parameters.put("costCenterName", cmbCostCenter.getText());
				}
			}
			else if (txtShownReport.getText().equals("spi")) {
				
			}
			else if (txtShownReport.getText().equals("generalfull")) {
				parameters.put("costCenterName", "");
			}
			else if (txtShownReport.getText().equals("salesbyprice")) {
				
			}
			else if (txtShownReport.getText().equals("notes")) {
				
			}
			else if (txtShownReport.getText().equals("cancellations")) {
				
			}
			else {//export general report csv
				
			}
			
			IExportToFile ietof = null;
			ietof = (IExportToFile) SpringUtility.applicationContext().getBean(((String) btnExport.getAttribute("arg0")).toLowerCase());
			parameters.put("format", ((String) btnExport.getAttribute("arg1")).toLowerCase());
			ietof.setParameters(parameters);
			ietof.export();
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
						ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
		}
	}
}