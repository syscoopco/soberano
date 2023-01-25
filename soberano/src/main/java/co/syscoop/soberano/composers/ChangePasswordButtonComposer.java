package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.exception.PasswordsMustMatchException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.exception.SoberanoLDAPException;
import co.syscoop.soberano.ldap.dao.LdapUserDao;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Labels;

import co.syscoop.soberano.domain.tracked.Worker;

@SuppressWarnings({ "serial", "rawtypes" })
public class ChangePasswordButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnApply;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnApply")
    public void btnApply_onClick() throws SoberanoException {
		
		Textbox txtPassword = (Textbox) btnApply.getParent().getParent().getParent().query("#center").query("window").query("#txtPassword");
		Textbox txtConfirmPassword = (Textbox) btnApply.getParent().getParent().getParent().query("#center").query("window").query("#txtConfirmPassword");
		
		try{
			if (txtPassword.getValue().isEmpty() || txtConfirmPassword.getValue().isEmpty()) {				
				throw new WrongValueException();
			}
			else if (!txtPassword.getValue().equals(txtConfirmPassword.getValue())) {
				throw new PasswordsMustMatchException();
			}
			else {
				LdapUserDao userDao  = (LdapUserDao) SpringUtility.webApplicationContext().getBean("ldapUser");
				String errorMessage = userDao.changePassword(new Worker(SpringUtility.loggedUser()), txtPassword.getValue());
				if (!errorMessage.isEmpty()) {
					throw new SoberanoLDAPException(errorMessage);
				}
				else {
					Messagebox.show(Labels.getLabel("message.permissions.PasswordSuccesfullyChanged"), 
								  					Labels.getLabel("messageBoxTitle.Information"), 
													0, 
													Messagebox.INFORMATION);
				}
			}
		}
		catch(WrongValueException ex) {
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(PasswordsMustMatchException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.worker.PasswordsMustMatch"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(SoberanoLDAPException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.error.LDAP.ErrorChangingPassword") + ": " + ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
		catch(Exception ex) {
			Messagebox.show(Labels.getLabel("message.error.LDAP.ErrorChangingPassword") 
								+ ". " 
								+ Labels.getLabel("caption.general.details")
								+ ": " + ex.getMessage(), 
		  					Labels.getLabel("messageBoxTitle.Error"), 
							0, 
							Messagebox.ERROR);
		}
    }
}