package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;

@SuppressWarnings({ "serial", "rawtypes" })
public class ReceivableFilterComposer extends SelectorComposer {
	
	@Wire
	private Intbox intDelayedDays;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	//TODO: receivables grid filtering
}