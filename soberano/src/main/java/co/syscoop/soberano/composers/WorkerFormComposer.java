package co.syscoop.soberano.composers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.untracked.Authority;
import co.syscoop.soberano.domain.untracked.Responsibility;

@SuppressWarnings({ "serial", "rawtypes" })
public class WorkerFormComposer extends SelectorComposer {
	
	@Wire
	private Textbox txtUserName;
	
	@Wire
	protected Include incContactData;
	
	protected ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
	protected ArrayList<Authority> authorities = new ArrayList<Authority>();
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
    	
	@Listen("onChange = textbox#txtUserName")
    public void txtUserName_onChange() throws SQLException {
		
		((Textbox) incContactData.query("#txtEmailAddress")).setText(txtUserName.getText());
	}
}