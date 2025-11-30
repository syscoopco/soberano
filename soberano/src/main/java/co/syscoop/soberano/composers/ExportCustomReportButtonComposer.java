package co.syscoop.soberano.composers;

import java.util.HashMap;
import java.util.Date;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import co.syscoop.soberano.beans.IExportToFile;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial", "rawtypes" })

public class ExportCustomReportButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnExport;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnExport")
    public void btnExport_onClick() throws Exception {
		
		try{
			Date dateFrom = ((Datebox) btnExport.getParent().getParent().getParent().query("#wndContentPanel").query("#dateFrom")).getValue();
			Date dateUntil = ((Datebox) btnExport.getParent().getParent().getParent().query("#wndContentPanel").query("#dateUntil")).getValue();
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("from", dateFrom);
			parameters.put("until", dateUntil);
			
			IExportToFile ietof = null;
			ietof = (IExportToFile) SpringUtility.applicationContext().getBean(((String) btnExport.getAttribute("arg0")).toLowerCase());
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