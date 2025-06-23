package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.Configuration;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Labels;

import java.io.File;
import org.apache.commons.io.FileUtils;

@SuppressWarnings({ "serial", "rawtypes" })
public class CleanDataButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnClean;
	
	protected ActionRequested requestedAction = ActionRequested.NONE;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnClean")
    public void btnClean_onClick() throws Throwable {
		
		try{
			if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
				Integer qryResult = (new Configuration()).cleanData();
				if (qryResult == -1) {
					throw new NotEnoughRightsException();
				}
				
				//
				//delete file records
				//
				String dirFullPath = "";
				
				//backups
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/backups/";
				File file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				file = null;
				
				//cash_register
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/cash_register/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				file = null;
				
				//closures
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/closures/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//csv
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/csv/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//expenses
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/expenses/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//inventory_operations
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/inventory_operations/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//orders
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/orders/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//other
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/other/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//process_runs
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/process_runs/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//production_lines
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/production_lines/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//reports
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/reports/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				
				//tickets				
				dirFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/tickets/";
				file = new File(dirFullPath);
				FileUtils.cleanDirectory(file);
				//
				
				requestedAction = ActionRequested.NONE;
				btnClean.setLabel(Labels.getLabel("caption.action.cleanData"));
			}
			else {
				requestedAction = ActionRequested.RECORD;
				btnClean.setLabel(Labels.getLabel("caption.action.confirm"));
				throw new ConfirmationRequiredException();
			}
		}
		catch(ConfirmationRequiredException ex) {
			return;
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.permissions.NotEnoughRights"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}	
}
