package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.ShiftClosure;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.util.Mobile;
import co.syscoop.soberano.util.ui.ZKUtility;
import co.syscoop.soberano.vocabulary.Translator;

public class ShiftClosuresInitiator implements Initiator, InitiatorExt {
	
	private Integer shiftClosureId = 0;

	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {							
			String scReport = new ShiftClosure(shiftClosureId).getReport();
			if (!scReport.isEmpty()) {
				
				Box boxDetails = (Box) comps[0].getPreviousSibling().query("#wndShowingAll").query("#boxDetails");
				Include incSouth = (Include) boxDetails.getParent().getParent().getParent().getParent().getParent().query("south").query("#incSouth");
				
				if (Mobile.isMobile()) {
					((Hbox) incSouth.query("#hboxReportButtons")).setVisible(false);
					((Button) incSouth.query("popup").query("#pp").query("#btnPPButton")).setVisible(true);
				}
				else {
					((Hbox) incSouth.query("#hboxReportButtons")).setVisible(true);
					((Button) incSouth.query("popup").query("#pp").query("#btnPPButton")).setVisible(false);
					((Button) incSouth.query("#btnGeneral")).setClass("ReportButtonPushed");
				}				
				
				//set report					
				((Textbox) boxDetails.query("#txtReport")).setValue(Translator.translate(scReport));
				((Intbox) boxDetails.query("#intObjectId")).setValue(shiftClosureId);
				((Textbox) boxDetails.query("#txtShownReport")).setValue("general");
			}
			else {
				throw new NotEnoughRightsException();
			}
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.permissions.NotEnoughRights"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}		
	}
	
	@Override
	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}
	
	@Override
	public void doFinally() throws Exception {		
	}
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		try {
			shiftClosureId = ZKUtility.getObjectIdFromURLQuery("id");
		}
		catch(Exception ex) {
			shiftClosureId = 0; 
			ExceptionTreatment.log(ex);
		}
	}
}
