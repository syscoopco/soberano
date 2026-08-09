package co.syscoop.soberano.view.viewmodel;

import java.sql.SQLException;
import org.zkoss.zul.ListModel;
import co.syscoop.soberano.view.autodrop.AcquirableMaterialForProcessInputsSelectionSubModel;

public class AcquirableMaterialForProcessInputsSelectionViewModel extends IntellisenseViewModel {	
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public ListModel getModel() throws SQLException {
		
//		if (_model == null) {
//			List l = new AcquirableMaterial().getAll(true);
//			_model = new ListModelList(l);
//		}
//		return ListModels.toListSubModel(new ListModelList(_model), new AutocompletionComparator(), 15);
		
		//optimization for not bringing from database the entire items set. if not, server can end up in out-of-memory. 
		return new AcquirableMaterialForProcessInputsSelectionSubModel();
	}
}
