package co.syscoop.soberano.ui.helper;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.ShiftClosure;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.vocabulary.Translator;

public class ShiftClosureFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	@Override
	public void cleanForm(Box boxDetails) {
		
		/*
		Textbox txtReport = (Textbox) boxDetails.query("#txtReport");
		txtReport.setText("");
		((Grid) boxDetails.query("#wndContentPanel").getParent().query("center").query("grid")).setModel(new ShiftClosuresGridModel());
		requestedAction = ActionRequested.NONE;
		((Button) boxDetails.query("#wndContentPanel").getParent().query("#incSouth").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.close"));
		Clients.scrollIntoView(txtReport);
		*/
		
		Executions.sendRedirect("/shift_closures.zul?id=" + this.getNewObjectId());
	}
	
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			qryResult = (new ShiftClosure()).record();
			if (qryResult == -1) {
				throw new NotEnoughRightsException();
			}
			else if (qryResult == -2) {
				throw new ShiftHasBeenClosedException();
			}
			requestedAction = ActionRequested.NONE;
			((Button) boxDetails.query("#wndShowingAll").getParent().query("#incSouth").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.close"));
			return qryResult;	
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.query("#wndShowingAll").getParent().query("#incSouth").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.confirm"));
			((Textbox) boxDetails.query("#txtReport")).setText(Translator.translate((new ShiftClosure()).getReport()));
			throw new ConfirmationRequiredException();
		}
	}

	@Override
	public Integer cancelFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public Integer closeFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public Integer billFromForm(Box boxDetails) {
		return null;
	}

	@Override
	public Integer makeFromForm(Component boxDetails) {
		return null;
	}
	
	public static void loadReport(Textbox txtShownReport, Textbox txtReport, String reportType, String param, Integer scId) throws SoberanoException {
		
		try{
			txtShownReport.setValue(reportType);
			((Intbox) txtShownReport.query("#intObjectId")).setValue(scId);
			
			String scReport = "";
			if (reportType.equals("receivables")) {					
				scReport = Translator.translate(new ShiftClosure(scId).getReceivablesReport());
			}
			else if (reportType.equals("cashregister")) {					
				scReport = Translator.translate(new ShiftClosure(scId).getCashRegisterReport());
			}
			else if (reportType.equals("housebill")) {
				scReport = Translator.translate(new ShiftClosure(scId).getHouseBillReport());
			}
			else if (reportType.equals("costcenter")) {
				scReport = Translator.translate(new ShiftClosure(scId).getCostCenterReport(param));
			}
			else if (reportType.equals("spi")) {
				scReport = Translator.translate(new ShiftClosure(scId).getSPIReport(param));
			}
			else if (reportType.equals("generalfull")) {
				scReport = Translator.translate(new ShiftClosure(scId).getGeneralFullReport());
			}
			else if (reportType.equals("salesbyprice")) {
				scReport = Translator.translate(new ShiftClosure(scId).getSalesByPriceReport());
			}
			else if (reportType.equals("notes")) {
				scReport = Translator.translate(new ShiftClosure(scId).getNotesReport());
			}
			else if (reportType.equals("cancellations")) {
				scReport = Translator.translate(new ShiftClosure(scId).getCancellationsReport());
			}
			else {
				scReport = Translator.translate(new ShiftClosure(scId).getReport());
			}
			
			if (!scReport.isEmpty()) {
				
				//set report
				txtReport.setValue(scReport);
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
}
