package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;

@SuppressWarnings({ "serial", "rawtypes" })
public class WorkerFormComposer extends SelectorComposer {
	
	@Wire
	private Textbox txtUserName;
	
	@Wire
	private Include incContactData;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
    	
	@Listen("onChange = textbox#txtUserName")
    public void txtUserName_onChange() throws SQLException {
		
		((Textbox) incContactData.query("#txtEmailAddress")).setText(txtUserName.getText());
	}
}