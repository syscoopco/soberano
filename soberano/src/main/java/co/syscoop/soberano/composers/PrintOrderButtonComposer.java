package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings("serial")
public class PrintOrderButtonComposer extends PrintButtonComposer {
	
	public void doAfterCompose(Component comp) throws Exception {
    	
		try {
			super.doAfterCompose(comp);
			trackedObject = new Order(ZKUtilitity.getObjectIdFromURLQuery("id"));
			trackedObject.get();
			fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
									"records/orders/" + 
									"ORDER_" + (trackedObject.getId() == 0 ? trackedObject.getStringId() : trackedObject.getId() + ".pdf");
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
    }
}
