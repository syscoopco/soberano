package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class GoToProcessButtonComposer extends SelectorComposer {
	
	private Include incDetails = null;
	
	@Wire
	private Button btnProcess;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          incDetails = (Include) btnProcess.query("#incDetails");
    }
	
	@Listen("onClick = button#btnProcess")
    public void btnProcess_onClick() throws Throwable {
		
		try{
			Integer processId = ((Intbox) incDetails.query("#intProcessId")).getValue();
			if (processId != null)
				Executions.getCurrent().sendRedirect("/processes.zul?id=" + processId.toString(), "_blank");
		}
		catch(Exception ex)
		{
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
}