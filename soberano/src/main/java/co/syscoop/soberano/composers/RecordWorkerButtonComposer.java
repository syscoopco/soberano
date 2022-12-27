package co.syscoop.soberano.composers;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.domain.untracked.Authority;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.Responsibility;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class RecordWorkerButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnRecord;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
    	
	@Listen("onClick = button#btnRecord")
    public void btnRecord_onClick() {
		try{
			Include incDetails = (Include) btnRecord.getParent().getParent().getParent().query("#wndContentPanel").query("#incDetails");		
			String pwd = ((Textbox) incDetails.query("#txtPassword")).getValue();
			if (!pwd.equals(((Textbox) incDetails.query("#txtConfirmPassword")).getValue())) {
				Messagebox.show(Labels.getLabel("message.validation.worker.PasswordsMustMatch"), 
			  					Labels.getLabel("messageBoxTitle.Warning"), 
								0, 
								Messagebox.EXCLAMATION);
			}
			else {
				ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
				ArrayList<Authority> authorities = new ArrayList<Authority>();
				Include incContactData = (Include) incDetails.query("#incContactData");
				Treechildren tchdnResponsibilities = (Treechildren) incDetails.query("#tchdnResponsibilities");
				for (Component item : tchdnResponsibilities.getChildren()) {
					Integer respId = Integer.parseInt(((Treeitem) item).getValue());
					responsibilities.add(new Responsibility(respId, ""));
					authorities.add(new Authority(1, "soberano.authority.top"));
				}
					
				Worker newWorker = new Worker(0,
												0,
												((Textbox) incDetails.query("#txtUserName")).getValue(),
												((Textbox) incDetails.query("#txtFirstName")).getValue(),
												((Textbox) incDetails.query("#txtLastName")).getValue(),
												pwd,
												((Textbox) incContactData.query("#txtPhoneNumber")).getValue(),
												((DomainObject) (((Combobox) incContactData.query("#cmbCountry")).getSelectedItem().getValue())).getStringId(),
												((Textbox) incContactData.query("#txtAddress")).getValue(),
												((Textbox) incContactData.query("#txtPostalCode")).getValue(),
												((Textbox) incContactData.query("#txtTown")).getValue(),
												((Combobox) incContactData.query("#cmbMunicipality")).getSelectedItem().getValue(),
												((Textbox) incContactData.query("#txtCity")).getValue(),
												((Combobox) incContactData.query("#cmbProvince")).getSelectedItem().getValue(),
												((Doublebox) incContactData.query("#dblLatitude")).getValue(),
												((Doublebox) incContactData.query("#dblLongitude")).getValue(),
												responsibilities,
												authorities);
				if (newWorker.record() == -1) {
					Messagebox.show(Labels.getLabel("message.permissions.NotEnoughRights"), 
									Labels.getLabel("messageBoxTitle.Warning"), 
									0, 
									Messagebox.EXCLAMATION);
				}
				else {
					Executions.sendRedirect("/new_worker.zul");
				}
			}
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