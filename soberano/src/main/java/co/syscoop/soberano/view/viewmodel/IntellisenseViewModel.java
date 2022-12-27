package co.syscoop.soberano.view.viewmodel;

import java.sql.SQLException;
import java.util.Comparator;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.ComparatorForAutocompletion;

public class IntellisenseViewModel implements IViewModel {
	
	@SuppressWarnings("rawtypes")
	protected ListModelList _model;
	
	@SuppressWarnings("rawtypes")
	protected class AutocompletionComparator implements Comparator {

		@Override
		public int compare(Object arg0, Object arg1) {
			
			return ComparatorForAutocompletion.compare(((String) arg0).toLowerCase(), ((DomainObject) arg1).getName().toLowerCase());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ListModel getModel() throws SQLException {
		return _model;
	};	
}
