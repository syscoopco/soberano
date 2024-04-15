package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.ui.helper.PrinterProfileFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class AddPrintJobsSourceButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren treePrintJobsSources;
	
	@Wire
	private Combobox cmbPrintJobsSource;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAddSource")
    public void btnAddSource_onClick() {
		
		try{
			//if the tree has no items,
			if (treePrintJobsSources.getChildren().size() == 0) {
				PrinterProfileFormHelper.addPrintJobsSource(cmbPrintJobsSource.getSelectedItem().getLabel(), 
															((DomainObject) cmbPrintJobsSource.getSelectedItem().getValue()).getEntityTypeInstanceId(),
															treePrintJobsSources);
			}
			else {
				int i = 0;
				for (Component item : treePrintJobsSources.getChildren()) {
					
					//if that item was already added,
					if ((((Treeitem) item).getValue()).equals(((DomainObject) cmbPrintJobsSource.getSelectedItem().getValue()).getEntityTypeInstanceId())) {
						break;
					}
					else i++;
				}
				
				//if that item wasn't already added,
				if (i == treePrintJobsSources.getChildren().size()) {
					PrinterProfileFormHelper.addPrintJobsSource(cmbPrintJobsSource.getSelectedItem().getLabel(),
																((DomainObject) cmbPrintJobsSource.getSelectedItem().getValue()).getEntityTypeInstanceId(),
																treePrintJobsSources);
				}
			}
		}
		catch(Exception ex) {
			Messagebox.show(Labels.getLabel("message.validation.selectAnItemFromTheList"), 
						  					Labels.getLabel("messageBoxTitle.Warning"), 
											0, 
											Messagebox.EXCLAMATION);
		}
    }
}