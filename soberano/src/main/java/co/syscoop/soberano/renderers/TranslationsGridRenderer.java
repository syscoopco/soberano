package co.syscoop.soberano.renderers;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.domain.tracked.Translation;
import co.syscoop.soberano.util.rowdata.TranslationRowData;

@SuppressWarnings("rawtypes")
public class TranslationsGridRenderer extends DomainObjectRowRenderer {

	@SuppressWarnings("unchecked")
	public void prepareRow(Row row, Object data) {
		
		TranslationRowData translation = (TranslationRowData) data;
		
		//language
		row.appendChild(new Label(translation.getLanguage()));
		
		//position
		row.appendChild(new Label(translation.getPosition().toString()));
		
		//fromTerm
		row.appendChild(new Label(translation.getFromTerm()));
		
		//toTerm
		row.appendChild(new Label(translation.getToTerm()));
				
		//action column
		Vbox actionCell = new Vbox();
		actionCell.setHflex("1");
		actionCell.setVflex("1");
		actionCell.setAlign("center");
		actionCell.setPack("center");
		
		Button btnDelete = new Button(Labels.getLabel("caption.action.delete"));
		btnDelete.setId(btnDelete.getUuid());
		btnDelete.setWidth("90%");
		
		//add listener to delete the translation
		btnDelete.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {
					if (requestedActions.get(row) != null && requestedActions.get(row).equals(ActionRequested.DISABLE)) {
						int result = (new Translation(translation.getTranslationId())).disable();
						if (result == -1) {
							throw new NotEnoughRightsException();
						}
						else {					
							row.detach();
						}
					}
					else {
						requestDeletion(row);
					}
				}
				catch(CannotAcquireLockException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.database.CannotAcquireLockException"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
				catch(ConcurrencyFailureException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.database.ConcurrencyFailureException"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
				catch(DataAccessException ex) {
					ExceptionTreatment.logAndShow(ex, 
												Labels.getLabel("message.validation.DataAccessException"), 
												Labels.getLabel("messageBoxTitle.Validation"),
												Messagebox.EXCLAMATION);
				}
				catch(NotEnoughRightsException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.permissions.NotEnoughRights"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);
				}
			}
		});
				
		actionCell.appendChild(btnDelete);
		row.appendChild(btnDelete);
	}
	
	@Override
	public void render(Row row, Object data, int index) throws Exception {

		if (!(row instanceof Group)) {
			prepareRow(row, data);
        }
	}
}
