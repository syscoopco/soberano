package co.syscoop.soberano.view.viewmodel;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;

import co.syscoop.soberano.domain.tracked.Product;

public class AdditionSelectionForOrderViewModel extends IntellisenseViewModel {	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListModel getModel() throws SQLException {
		
		if (_model == null) {
			List l = new Product().getAdditionsWithUnitsForOrder();
			_model = new ListModelList(l);
		}
		return ListModels.toListSubModel(new ListModelList(_model), new AutocompletionComparator(), 15);
	}
}
