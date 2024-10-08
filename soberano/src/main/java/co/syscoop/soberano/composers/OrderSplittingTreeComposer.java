package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Tree;
import co.syscoop.soberano.models.OrderSplittingTreeModelPopulator;

@SuppressWarnings({ "serial", "rawtypes" })
public class OrderSplittingTreeComposer extends SelectorComposer {
	
	@Wire
	private Popup pp;
		
	@Listen("onClick = button#btnPopupButton")
    public void btnPopupButton_onClick() {
		
		OrderSplittingTreeModelPopulator.rerenderActivityTree((Tree) pp.query("tree"));
		pp.setHeight("50%");
    }
}

