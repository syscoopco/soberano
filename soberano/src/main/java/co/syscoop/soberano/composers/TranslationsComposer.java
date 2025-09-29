package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

@SuppressWarnings({ "serial", "rawtypes" })
public class TranslationsComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbLanguage;
	
	@Wire
	private Intbox intPosition;
	
	@Wire
	private Textbox txtFromTerm;
	
	@Wire
	private Textbox txtToTerm;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
}