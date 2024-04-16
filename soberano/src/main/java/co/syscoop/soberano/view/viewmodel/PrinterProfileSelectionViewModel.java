package co.syscoop.soberano.view.viewmodel;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;

import co.syscoop.soberano.domain.tracked.PrinterProfile;

public class PrinterProfileSelectionViewModel extends IntellisenseViewModel {	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListModel getModel() throws SQLException {
		
		if (_model == null) {
			List l = new PrinterProfile().getAll(false);
			_model = new ListModelList(l);
		}
		return ListModels.toListSubModel(new ListModelList(_model), new AutocompletionComparator(), 15);
	}
}
